package com.company.Vistas;

import com.company.Controlador.ControladorSeguimiento;
import com.company.Entidades.*;
import com.company.Formularios.FormMaterial;
import com.company.Formularios.FormSeguimientoLaboral;
import com.formdev.flatlaf.FlatDarculaLaf;

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

public class ViewSeguimiento extends JFrame{

    //region Constructores

    public ViewSeguimiento(ControladorSeguimiento controladorSeguimiento, ArrayList<SeguimientoLaboral> seguimientoLaboralList) {
        this.controladorSeguimiento = controladorSeguimiento;
        this.seguimientoLaboralList = seguimientoLaboralList;
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
        setTitle("Seguimiento Laboral");
        String[] listColumnsName = controladorSeguimiento.getColumnsName();
        headers = new String[listColumnsName.length-4];
        for (int i = 0; i < listColumnsName.length-4; i++){
            headers[i] = listColumnsName[i+1].toUpperCase();
        }
        refreshTable(headers, seguimientoLaboralList);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<SeguimientoLaboral> seguimientoLaboralList){

        if(seguimientoLaboralList.size() == 0) {
            TableSeguimiento.setShowGrid(true);
            TableSeguimiento.setCellSelectionEnabled(false);
            TableSeguimiento.setAutoCreateRowSorter(true);
            TableSeguimiento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableSeguimiento.setRowSelectionAllowed(true);
            TableSeguimiento.setDefaultEditor(Object.class, null);
            TableSeguimiento.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableSeguimiento.getModel());

            TableSeguimiento.setRowSorter(sorter);

            //Filling Headers
            modelSeguimiento = new DefaultTableModel(headers, 0);

            TableSeguimiento.setModel(modelSeguimiento);
        }else {
            TableSeguimiento.setShowGrid(true);
            TableSeguimiento.setCellSelectionEnabled(false);
            TableSeguimiento.setAutoCreateRowSorter(true);
            TableSeguimiento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableSeguimiento.setRowSelectionAllowed(true);
            TableSeguimiento.setDefaultEditor(Object.class, null);
            TableSeguimiento.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableSeguimiento.getModel());

            TableSeguimiento.setRowSorter(sorter);

            //Filling Headers
            modelSeguimiento = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (SeguimientoLaboral seguimientoLaboral: seguimientoLaboralList) {
                data = getSeguimientoObject(seguimientoLaboral);
                modelSeguimiento.addRow(data);
            }

            TableSeguimiento.setModel(modelSeguimiento);
        }
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableSeguimiento.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableSeguimiento.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewSeguimientoFromFormulario(SeguimientoLaboral seguimientoLaboral){
        return controladorSeguimiento.createSeguimiento(seguimientoLaboral);
    }

    public boolean getUpdateSeguimientoFromFormulario(SeguimientoLaboral seguimientoLaboral) {
        return controladorSeguimiento.updateSeguimiento(seguimientoLaboral);
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
    private void createSeguimiento(){
        FormSeguimientoLaboral formSeguimientoLaboral = new FormSeguimientoLaboral(this);
    }

    private void readSeguimiento(){
        SeguimientoLaboral seguimientoLaboral = getSeguimiento();
        FormSeguimientoLaboral formSeguimientoLaboral = new FormSeguimientoLaboral(this, seguimientoLaboral, false);
    }

    private void updateMaterial() {
        SeguimientoLaboral seguimientoLaboral = getSeguimiento();
        FormSeguimientoLaboral formSeguimientoLaboral = new FormSeguimientoLaboral(this, seguimientoLaboral);
    }

    private void deleteSeguimiento(){
        String cod = String.valueOf(getCodSeguimiento());

        boolean result = controladorSeguimiento.deleteSeguimiento(cod);

        if(result){
            int row = TableSeguimiento.getSelectedRow();

            seguimientoLaboralList.remove(row);
            refreshTable(headers, seguimientoLaboralList);
        }
    }


    //endregion

    //region Metodos privados

    public void updateTableSeguimiento(SeguimientoLaboral seguimientoLaboral) {

        int row = TableSeguimiento.getSelectedRow();

        seguimientoLaboralList.get(row).setActuacion(seguimientoLaboral.getActuacion());
        // FIXME: 24/05/2022 dara error ya que la actuacion es un objeto tal cual
        seguimientoLaboralList.get(row).setTrabajador(seguimientoLaboral.getTrabajador());
        seguimientoLaboralList.get(row).setAno(seguimientoLaboral.getAno());
        seguimientoLaboralList.get(row).setDia(seguimientoLaboral.getDia());
        seguimientoLaboralList.get(row).setMes(seguimientoLaboral.getMes());
        seguimientoLaboralList.get(row).setHora_entrada(seguimientoLaboral.getHora_entrada());

        seguimientoLaboralList.get(row).setHoras_totales(seguimientoLaboral.getHoras_totales());
        seguimientoLaboralList.get(row).setHoras_extra(seguimientoLaboral.getHoras_extra());

        refreshTable(headers, seguimientoLaboralList);

    }

    public void addTableSeguimiento(SeguimientoLaboral seguimientoLaboral){

        Object[] newSeguimiento = getSeguimientoObject(seguimientoLaboral);
        modelSeguimiento.addRow(newSeguimiento);
        seguimientoLaboralList.add(seguimientoLaboral);


    }

    public Object[] getSeguimientoObject(SeguimientoLaboral seguimientoLaboral){
        int y = 0;
        Object[] newSeguimiento = new Object[headers.length];
        newSeguimiento[y++] = seguimientoLaboral.getActuacion();
        newSeguimiento[y++] = seguimientoLaboral.getTrabajador();
        newSeguimiento[y++] = seguimientoLaboral.getAno();
        newSeguimiento[y++] = seguimientoLaboral.getDia();
        newSeguimiento[y++] = seguimientoLaboral.getMes();
        newSeguimiento[y++] = seguimientoLaboral.getHora_entrada();
        newSeguimiento[y++] = seguimientoLaboral.getHoras_totales();
        newSeguimiento[y++] = seguimientoLaboral.getHoras_extra();

        return newSeguimiento;
    }

    private SeguimientoLaboral getSeguimiento() {
        int row = TableSeguimiento.getSelectedRow();

        SeguimientoLaboral seguimientoLaboral = seguimientoLaboralList.get(row);

        return seguimientoLaboral;
    }

    private int getCodSeguimiento() {
        int row = TableSeguimiento.getSelectedRow();

        return seguimientoLaboralList.get(row).getId();
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
                createSeguimiento();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readSeguimiento();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMaterial();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSeguimiento();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, seguimientoLaboralList);
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
        TableSeguimiento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    updateMaterial();
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
    private JTabbedPane panelPestanas;
    private JTable TableSeguimiento;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JButton buttonRecargar;
    
    private ControladorSeguimiento controladorSeguimiento;
    private ArrayList<SeguimientoLaboral> seguimientoLaboralList;
    private String[] headers;
    private TableRowSorter sorter;
    private DefaultTableModel modelSeguimiento;
    
    //endregion
}
