package com.company.Vistas;

import com.company.Controlador.ControladorCliente;
import com.company.Controlador.ControladorTrabajador;
import com.company.Entidades.Cliente;
import com.company.Entidades.Proveedor;
import com.company.Entidades.Trabajador;
import com.company.Formularios.FormCliente;
import com.company.Formularios.FormTrabajador;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewTrabajador extends JFrame{


    //region Constructores

    public ViewTrabajador(ControladorTrabajador controladorTrabajador, ArrayList<Trabajador> trabajadores) {
        this.controladorTrabajador = controladorTrabajador;
        this.trabajadores = trabajadores;
        initWindow();
        initListeners();
        setVisible(true);
    }

    //endregion

    //region Metodos Vista
    private void initWindow() {
        add(panelPrincipal);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Trabajadores");
        String[] listColumnsName = controladorTrabajador.getColumnsName();
        headers = new String[listColumnsName.length - 1];
        for (int i = 0; i < listColumnsName.length-1; i++){
            headers[i] = listColumnsName[i+1].toUpperCase().replace('_', ' ');
        }
        refreshTable(headers, trabajadores);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Trabajador> trabajadores){

        if(trabajadores.size() == 0) {
            TableTrabajador.setShowGrid(true);
            TableTrabajador.setCellSelectionEnabled(false);
            TableTrabajador.setAutoCreateRowSorter(true);
            TableTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableTrabajador.setRowSelectionAllowed(true);
            TableTrabajador.setDefaultEditor(Object.class, null);
            TableTrabajador.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableTrabajador.getModel());

            TableTrabajador.setRowSorter(sorter);

            //Filling Headers
            modelTrabajador = new DefaultTableModel(headers, 0);

            TableTrabajador.setModel(modelTrabajador);
        }else {

            TableTrabajador.setShowGrid(true);
            TableTrabajador.setCellSelectionEnabled(false);
            TableTrabajador.setAutoCreateRowSorter(true);
            TableTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableTrabajador.setRowSelectionAllowed(true);
            TableTrabajador.setDefaultEditor(Object.class, null);
            TableTrabajador.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableTrabajador.getModel());

            TableTrabajador.setRowSorter(sorter);

            //Filling Headers
            modelTrabajador = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Trabajador trabajador : trabajadores) {
                data = getTrabajadorObject(trabajador);
                modelTrabajador.addRow(data);
            }

            TableTrabajador.setModel(modelTrabajador);
        }
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableTrabajador.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableTrabajador.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewTrabajadorFromFormulario(Trabajador trabajador){
        return controladorTrabajador.createTrabajador(trabajador);
    }

    public boolean getUpdateTrabajadorFromFormulario(Trabajador trabajador) {
        return controladorTrabajador.updateTrabajador(trabajador);
    }


    //endregion

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region CRUD
    private void createTrabajador(){
        FormTrabajador formTrabajador = new FormTrabajador(this);
    }

    private void readTrabajador(){
        Trabajador trabajador = getTrabajador();
        FormTrabajador formTrabajador = new FormTrabajador(this, trabajador, false);
    }

    private void updateTrabajador() {
        Trabajador trabajador = getTrabajador();
        FormTrabajador formTrabajador = new FormTrabajador(this, trabajador);
    }

    private void deleteTrabajador(){

        Trabajador trabajador = getTrabajador();

        boolean result = controladorTrabajador.deleteTrabajador(trabajador);

        if(result){
            int row = TableTrabajador.getSelectedRow();
            trabajadores.remove(row);
            refreshTable(headers, trabajadores);
            ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " ha sido borrado");

        }else{
            ShowErrorMessage("ERROR","Trabajador " + trabajador.getNombre() + " no se ha podido borrar");
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableTrabajador(Trabajador trabajador) {

        int row = TableTrabajador.getSelectedRow();

        trabajadores.get(row).setDNI(trabajador.getDNI());
        trabajadores.get(row).setNombre(trabajador.getNombre());
        trabajadores.get(row).setApellidos(trabajador.getApellidos());
        trabajadores.get(row).setFnac(trabajador.getFnac());
        trabajadores.get(row).setPuesto(trabajador.getPuesto());
        trabajadores.get(row).setSalario(trabajador.getSalario());
        trabajadores.get(row).setNacionalidad(trabajador.getNacionalidad());

        refreshTable(headers, trabajadores);

    }

    public void addTableTrabajador(Trabajador trabajador){

        Object[] newTrabajador = getTrabajadorObject(trabajador);
        modelTrabajador.addRow(newTrabajador);
        trabajadores.add(trabajador);


    }

    public Object[] getTrabajadorObject(Trabajador trabajador){
        int y = 0;
        Object[] newTrabajador = new Object[headers.length];

        newTrabajador[y++] = trabajador.getDNI();
        newTrabajador[y++] = trabajador.getNombre();
        newTrabajador[y++] = trabajador.getApellidos();
        newTrabajador[y++] = trabajador.getTelefono();
        newTrabajador[y++] = trabajador.getFnac();
        newTrabajador[y++] = trabajador.getNacionalidad();
        newTrabajador[y++] = trabajador.getPuesto();
        newTrabajador[y++] = trabajador.getSalario();


        return newTrabajador;
    }



    private Trabajador getTrabajador() {
        int row = TableTrabajador.getSelectedRow();

        return trabajadores.get(row);

    }

    private int getCodTrabajador() {
        int row = TableTrabajador.getSelectedRow();
        return trabajadores.get(row).getId();
    }



    //endregion

    //region Listeners
    private void initListeners(){
        actionListeners();
        mouseListeners();
    }

    private void actionListeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTrabajador();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readTrabajador();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrabajador();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTrabajador();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, trabajadores);
            }
        });

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    private void mouseListeners(){
        TableTrabajador.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    updateTrabajador();
                }
            }
        });
    }

    //endregion


    //region Variables
    private JPanel panelPrincipal;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JPanel PanelMaterial;
    private JTable TableTrabajador;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JButton buttonRecargar;
    private JLabel labelTitulo;
    private ArrayList<Trabajador> trabajadores;
    private ControladorTrabajador controladorTrabajador;
    private String[] headers;
    
    private DefaultTableModel modelTrabajador;
    private TableRowSorter sorter;
    
    
    //endregion
}
