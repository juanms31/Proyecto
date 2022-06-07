package com.company.Vistas;

import com.company.Controlador.ControladorCliente;
import com.company.Controlador.ControladorProveedor;
import com.company.Entidades.Cliente;
import com.company.Entidades.Proveedor;
import com.company.Formularios.FormCliente;
import com.company.Formularios.FormProveedor;
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

public class ViewProveedor extends JFrame{

    //region Constructores

    public ViewProveedor(ControladorProveedor controladorProveedor, ArrayList<Proveedor> proveedores) {
        this.controladorProveedor = controladorProveedor;
        this.proveedores = proveedores;
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
        setTitle("Proveedores");
        String[] listColumnsName = controladorProveedor.getColumnsName();
        headers = new String[listColumnsName.length - 1];
        for (int i = 0; i < listColumnsName.length- 1; i++){
            headers[i] = listColumnsName[i+1].toUpperCase().replace('_', ' ');;
        }
        refreshTable(headers, proveedores);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Proveedor> proveedores){

        if(proveedores.size() == 0) {
            TableProveedor.setShowGrid(true);
            TableProveedor.setCellSelectionEnabled(false);
            TableProveedor.setAutoCreateRowSorter(true);
            TableProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableProveedor.setRowSelectionAllowed(true);
            TableProveedor.setDefaultEditor(Object.class, null);
            TableProveedor.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableProveedor.getModel());

            TableProveedor.setRowSorter(sorter);

            //Filling Headers
            modelProveedor = new DefaultTableModel(headers, 0);

            TableProveedor.setModel(modelProveedor);
        }else {

            TableProveedor.setShowGrid(true);
            TableProveedor.setCellSelectionEnabled(false);
            TableProveedor.setAutoCreateRowSorter(true);
            TableProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableProveedor.setRowSelectionAllowed(true);
            TableProveedor.setDefaultEditor(Object.class, null);
            TableProveedor.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableProveedor.getModel());

            TableProveedor.setRowSorter(sorter);

            //Filling Headers
            modelProveedor = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Proveedor proveedor: proveedores) {
                data = getProveedorObject(proveedor);
                modelProveedor.addRow(data);
            }

            TableProveedor.setModel(modelProveedor);
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

    public boolean getNewProveedorFromFormulario(Proveedor proveedor){
        return controladorProveedor.createProveedor(proveedor);
    }

    public boolean getUpdateProveedorFromFormulario(Proveedor proveedor) {
        return controladorProveedor.updateProveedor(proveedor);
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
    private void createProveedor(){
        FormProveedor formProveedor = new FormProveedor(this);
    }

    private void readProveedor(){
        int row = TableProveedor.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un proveedor de la tabla.");

        } else {
            Proveedor proveedor = getProveedor(row);
            FormProveedor formProveedor = new FormProveedor(this, proveedor, false);
        }
    }

    private void updateProveedor() {
        int row = TableProveedor.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un proveedor de la tabla.");

        } else {
            Proveedor proveedor = getProveedor(row);
            FormProveedor formProveedor = new FormProveedor(this, proveedor);
        }

    }

    private void deleteProveedor(){

        int row = TableProveedor.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un proveedor de la tabla.");

        } else {
            int id = getIDProveedor(row);
            boolean result = controladorProveedor.deleteProveedor(id);

            if (result) {
                proveedores.remove(row);
                refreshTable(headers, proveedores);
            }
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableProveedor(Proveedor proveedor) {

        int row = TableProveedor.getSelectedRow();

        proveedores.get(row).setCIF(proveedor.getCIF());
        proveedores.get(row).setNombre_proveedor(proveedor.getNombre_proveedor());
        proveedores.get(row).setDireccion(proveedor.getDireccion());
        proveedores.get(row).setMail1(proveedor.getMail1());
        proveedores.get(row).setMail2(proveedor.getMail2());
        proveedores.get(row).setTelefono1(proveedor.getTelefono1());
        proveedores.get(row).setTelefono2(proveedor.getTelefono2());

        refreshTable(headers, proveedores);

    }

    public void addTableProveedor(Proveedor proveedor){

        Object[] newProveedor = getProveedorObject(proveedor);
        modelProveedor.addRow(newProveedor);
        proveedores.add(proveedor);


    }

    public Object[] getProveedorObject(Proveedor proveedor){
        int y = 0;
        Object[] newProveedor = new Object[headers.length];
        newProveedor[y++] = proveedor.getCIF();
        newProveedor[y++] = proveedor.getNombre_proveedor();
        newProveedor[y++] = proveedor.getDireccion();
        newProveedor[y++] = proveedor.getMail1();
        newProveedor[y++] = proveedor.getTelefono1();
        newProveedor[y++] = proveedor.getMail2();
        newProveedor[y++] = proveedor.getTelefono2();

        return newProveedor;
    }



    private Proveedor getProveedor(int row) {

        return proveedores.get(row);

    }

    private int getIDProveedor(int row) {
        return proveedores.get(row).getId();
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
                createProveedor();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readProveedor();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProveedor();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProveedor();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, proveedores);
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
        TableProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    updateProveedor();
                }
            }
        });
    }

    //endregion

    //region Variables
    private JPanel panelPrincipal;
    private JPanel PanelCliente;
    private JTable TableCliente;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;
    private JTable TableProveedor;
    private JButton buttonVolver;
    private JPanel PanelMaterial;
    private JPanel buscador;
    private JPanel panelBotones;
    private JLabel labelTitulo;

    private  ArrayList<Proveedor> proveedores;
    private ControladorProveedor controladorProveedor;
    private String[] headers;
    private TableRowSorter sorter;
    private DefaultTableModel modelProveedor;
    //endregion

}
