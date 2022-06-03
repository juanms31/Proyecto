package com.company.Vistas;

import com.company.Controlador.ControladorAlbaran;
import com.company.Entidades.*;
import com.company.Formularios.FormAlbaran;
import com.formdev.flatlaf.FlatDarculaLaf;
import jdk.swing.interop.SwingInterOpUtils;

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

public class ViewAlbaran extends JFrame {
    public ViewAlbaran(ControladorAlbaran controladorAlbaran,
                       ArrayList<Albaran> albaranes,
                       ArrayList<Material> materiales,
                       ArrayList<Actuacion> actuaciones,
                       ArrayList<Proveedor> proveedores) {
        this.controladorAlbaran = controladorAlbaran;
        this.albaranes = albaranes;
        this.materiales = materiales;
        this.actuaciones = actuaciones;
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
        setMinimumSize(new Dimension(750, 750));
        setLocationRelativeTo(null);
        setTitle("Albaranes");

        initHeaders();
        initSecondaryTables();

        refreshTable(headers, albaranes);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void initHeaders(){
        String[] listColumnsName = controladorAlbaran.getColumnsName();
        headers = new String[listColumnsName.length - 1];
        for (int i = 0; i < listColumnsName.length - 1; i++) {

            if (listColumnsName[i + 1].equals("id_actuacion")) {

                headers[i] = "ACTUACION";

            } else if (listColumnsName[i + 1].equals("id_proveedor")) {

                headers[i] = "PROVEEDOR";

            } else headers[i] = listColumnsName[i + 1].toUpperCase().replace('_', ' ');
        }

    }
    
    private void initSecondaryTables(){
        TableMaterialesAlbaran.setShowGrid(true);
        TableMaterialesAlbaran.setCellSelectionEnabled(false);
        TableMaterialesAlbaran.setAutoCreateRowSorter(true);
        TableMaterialesAlbaran.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableMaterialesAlbaran.setRowSelectionAllowed(true);
        TableMaterialesAlbaran.setDefaultEditor(Object.class, null);
        TableMaterialesAlbaran.setDragEnabled(false);
        sorter = new TableRowSorter<TableModel>(TableMaterialesAlbaran.getModel());

        TableMaterialesAlbaran.setRowSorter(sorter);

        //Filling Headers
        modelMaterialesAlbaran = new DefaultTableModel(headersMateriales, 0);

        TableMaterialesAlbaran.setModel(modelMaterialesAlbaran);
        
        /////////////////////////////////

        TableActuacionesAlbaran.setShowGrid(true);
        TableActuacionesAlbaran.setCellSelectionEnabled(false);
        TableActuacionesAlbaran.setAutoCreateRowSorter(true);
        TableActuacionesAlbaran.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableActuacionesAlbaran.setRowSelectionAllowed(true);
        TableActuacionesAlbaran.setDefaultEditor(Object.class, null);
        TableActuacionesAlbaran.setDragEnabled(false);
        sorter = new TableRowSorter<TableModel>(TableActuacionesAlbaran.getModel());

        TableActuacionesAlbaran.setRowSorter(sorter);

        //Filling Headers
        modelActuacionesAlbaran = new DefaultTableModel(headersMateriales, 0);

        TableActuacionesAlbaran.setModel(modelActuacionesAlbaran);
        
        
    }

    public void refreshTable(String[] headers, ArrayList<Albaran> albaranes) {

        if (albaranes.size() == 0) {
            TableAlbaran.setShowGrid(true);
            TableAlbaran.setCellSelectionEnabled(false);
            TableAlbaran.setAutoCreateRowSorter(true);
            TableAlbaran.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableAlbaran.setRowSelectionAllowed(true);
            TableAlbaran.setDefaultEditor(Object.class, null);
            TableAlbaran.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableAlbaran.getModel());

            TableAlbaran.setRowSorter(sorter);

            //Filling Headers
            modelAlbaran = new DefaultTableModel(headers, 0);

            TableAlbaran.setModel(modelAlbaran);
        } else {

            TableAlbaran.setShowGrid(true);
            TableAlbaran.setCellSelectionEnabled(false);
            TableAlbaran.setAutoCreateRowSorter(true);
            TableAlbaran.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableAlbaran.setRowSelectionAllowed(true);
            TableAlbaran.setDefaultEditor(Object.class, null);
            TableAlbaran.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableAlbaran.getModel());

            TableAlbaran.setRowSorter(sorter);

            //Filling Headers
            modelAlbaran = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Albaran albaran : albaranes) {
                data = getAlbaranObject(albaran);
                modelAlbaran.addRow(data);
            }

            TableAlbaran.setModel(modelAlbaran);
        }
    }

    private void filter() {
        DefaultTableModel Model = (DefaultTableModel) TableAlbaran.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableAlbaran.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewAlbaranFromFormulario(ArrayList<Albaran> albaranes) {
        return controladorAlbaran.createAlbaran(albaranes);
    }

    public boolean getUpdateAlbaranFromFormulario(Albaran albaran) {
        return controladorAlbaran.updateAlbaran(albaran);
    }

    public boolean getMaterialesAlbaranFromFormulario(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {
        return controladorAlbaran.createMaterialesCompradoProveedor(materialesCompradoProveedor);
    }


    //endregion

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region CRUD
    private void createAlbaran() {
        FormAlbaran formAlbaran = new FormAlbaran(this, materiales, actuaciones, proveedores );
    }

    private void readAlbaran() {
        Albaran albaran = getAlbaran();
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran, materiales, actuaciones, proveedores,false);
    }

    private void updateAlbaran() {
        Albaran albaran = getAlbaran();
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran, materiales, actuaciones, proveedores);
    }

    private void deleteAlbaran() {

        Albaran albaran = getAlbaran();

        boolean result = controladorAlbaran.deleteAlbaran(albaran.getId());

        if (result) {
            int row = TableAlbaran.getSelectedRow();

            albaranes.remove(row);
            refreshTable(headers, albaranes);
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableAlbaran(Albaran albaran) {

        int row = TableAlbaran.getSelectedRow();

        albaranes.get(row).setActuacion(albaran.getActuacion());
        albaranes.get(row).setProveedor(albaran.getProveedor());
        albaranes.get(row).setConcepto(albaran.getConcepto());
        albaranes.get(row).setFechaEntradaAlbaran(albaran.getFechaEntradaAlbaran());

        refreshTable(headers, albaranes);

    }

    public void addTableAlbaran(Albaran albaran) {

        Object[] newAlbaran = getAlbaranObject(albaran);
        modelAlbaran.addRow(newAlbaran);
        albaranes.add(albaran);


    }

    public Object[] getAlbaranObject(Albaran albaran) {
        int y = 0;
        Object[] newAlbaran = new Object[headers.length];

        newAlbaran[y++] = albaran.getCod();
        newAlbaran[y++] = albaran.getActuacion().getNombre();
        newAlbaran[y++] = albaran.getProveedor().getNombre_proveedor();
        System.out.println("Proveedor: " + albaran.getProveedor().getNombre_proveedor());
        newAlbaran[y++] = albaran.getConcepto();
        newAlbaran[y++] = albaran.getFechaEntradaAlbaran();

        return newAlbaran;
    }


    private Albaran getAlbaran() {
        int row = TableAlbaran.getSelectedRow();

        return albaranes.get(row);

    }

    private int getCodAlbaran() {
        int row = TableAlbaran.getSelectedRow();
        return albaranes.get(row).getId();
    }

    //endregion

    //region Listeners
    private void initListeners() {
        actionListeners();
        mouseListeners();
    }

    private void actionListeners() {
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAlbaran();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readAlbaran();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlbaran();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAlbaran();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, albaranes);
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

    private void mouseListeners() {
        TableAlbaran.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    updateAlbaran();
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
    private JPanel PanelAlbaran;
    private JTable TableAlbaran;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JButton buttonRecargar;
    private JTable TableActuacionesAlbaran;
    private JTable TableMaterialesAlbaran;

    private ControladorAlbaran controladorAlbaran;
    private int estado = 0;
    private Albaran AlbaranSiendoModificado;
    private ArrayList<Albaran> albaranes;
    private ArrayList<Material> materiales;
    private ArrayList<Actuacion> actuaciones;
    private final ArrayList<Proveedor> proveedores;
    private String[] headers;

    private String[] headersMateriales = {"COD","DESC. MATERIAL", "UNIDADES", "PRECIO UNITARIO", "BASE IMPONIBLE"};

    private String[] headersActuacion = {"!","asdfsa","{"};

    private TableRowSorter sorter;
    private DefaultTableModel modelAlbaran;
    private DefaultTableModel modelMaterialesAlbaran;
    private DefaultTableModel modelActuacionesAlbaran;

    //endregion
}
