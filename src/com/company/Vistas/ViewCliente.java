package com.company.Vistas;

import com.company.Controlador.ControladorCliente;
import com.company.Entidades.Cliente;
import com.company.Formularios.FormCliente;
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

public class ViewCliente extends JFrame{

    //region Constructores

    public ViewCliente(ControladorCliente controladorCliente, ArrayList<Cliente> clientes) {
        this.controladorCliente = controladorCliente;
        this.clientes = clientes;
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
        setTitle("Materiales");
        String[] listColumnsName = controladorCliente.getColumnsName();
//        headers = listColumnsName;
        headers = new String[listColumnsName.length - 4];
        for (int i = 0; i < listColumnsName.length-4; i++){
            headers[i] = listColumnsName[i+1].toUpperCase();
        }
        refreshTable(headers, clientes);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Cliente> clientes){

        if(clientes.size() == 0) {
            TableCliente.setShowGrid(true);
            TableCliente.setCellSelectionEnabled(false);
            TableCliente.setAutoCreateRowSorter(true);
            TableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableCliente.setRowSelectionAllowed(true);
            TableCliente.setDefaultEditor(Object.class, null);
            TableCliente.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableCliente.getModel());

            TableCliente.setRowSorter(sorter);

            //Filling Headers
            modelCliente = new DefaultTableModel(headers, 0);

            TableCliente.setModel(modelCliente);
        }else {

            TableCliente.setShowGrid(true);
            TableCliente.setCellSelectionEnabled(false);
            TableCliente.setAutoCreateRowSorter(true);
            TableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableCliente.setRowSelectionAllowed(true);
            TableCliente.setDefaultEditor(Object.class, null);
            TableCliente.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableCliente.getModel());

            TableCliente.setRowSorter(sorter);

            //Filling Headers
            modelCliente = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Cliente cliente : clientes) {
                data = getClienteObject(cliente);
                modelCliente.addRow(data);
            }

            TableCliente.setModel(modelCliente);
        }
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableCliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableCliente.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewClienteFromFormulario(Cliente cliente){
        return controladorCliente.createCliente(cliente);
    }

    public boolean getUpdateClienteFromFormulario(Cliente cliente) {
        return controladorCliente.updateCliente(cliente);
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
    private void createCliente(){
        FormCliente formCliente = new FormCliente(this);
    }

    private void readCliente(){
        Cliente cliente = getCliente();
        FormCliente formCliente = new FormCliente(this, cliente, false);
    }

    private void updateCliente() {
        Cliente cliente = getCliente();
        FormCliente formCliente = new FormCliente(this, cliente);


    }

    private void deleteCliente(){

        int id = getCodCliente();
        boolean result = controladorCliente.deleteCliente(id);

        if(result){
            int row = TableCliente.getSelectedRow();

            clientes.remove(row);
            refreshTable(headers, clientes);
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableCliente(Cliente cliente) {

        int row = TableCliente.getSelectedRow();

        clientes.get(row).setNombre(cliente.getNombre());
        clientes.get(row).setDireccion(cliente.getDireccion());
        clientes.get(row).setMail1(cliente.getMail1());
        clientes.get(row).setMail2(cliente.getMail2());
        clientes.get(row).setTelef1(cliente.getTelef1());
        clientes.get(row).setTelef2(cliente.getTelef2());

        refreshTable(headers, clientes);

    }

    public void addTableCliente(Cliente cliente){

        Object[] newCliente = getClienteObject(cliente);
        modelCliente.addRow(newCliente);
        clientes.add(cliente);


    }

    public Object[] getClienteObject(Cliente cliente){
        int y = 0;
        Object[] newCliente = new Object[headers.length];
        newCliente[y++] = cliente.getNombre();
        newCliente[y++] = cliente.getDireccion();
        newCliente[y++] = cliente.getMail1();
        newCliente[y++] = cliente.getTelef1();
        newCliente[y++] = cliente.getMail2();
        newCliente[y++] = cliente.getTelef2();

        return newCliente;
    }



    private Cliente getCliente() {
        int row = TableCliente.getSelectedRow();

        return clientes.get(row);

    }

    private int getCodCliente() {
        int row = TableCliente.getSelectedRow();
        return clientes.get(row).getId();
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
                createCliente();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCliente();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCliente();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCliente();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, clientes);
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
        TableCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    updateCliente();
                }
            }
        });
    }

    //endregion

    //region Variables
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelCliente;
    private JTable TableCliente;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;
    private JButton buttonVolver;

    private  ArrayList<Cliente> clientes;
    private ControladorCliente controladorCliente;
    private String[] headers;
    private TableRowSorter sorter;
    private DefaultTableModel modelCliente;
    //endregion

}
