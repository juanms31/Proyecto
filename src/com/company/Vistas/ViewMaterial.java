package com.company.Vistas;

import com.company.Controlador.ControladorMaterial;;
import com.company.Entidades.Material;
import com.company.Formularios.FormMaterial;
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

public class ViewMaterial extends JFrame{

    //region Constructores

    public ViewMaterial(ControladorMaterial controladorMaterial, ArrayList<Material> materiales) {
        this.controladorMaterial = controladorMaterial;
        this.materiales = materiales;
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
        String[] listColumnsName = controladorMaterial.getColumnsName();
        headers = new String[listColumnsName.length-4];
        for (int i = 0; i < listColumnsName.length-4; i++){
            headers[i] = listColumnsName[i+1].toUpperCase();
        }
        refreshTable(headers, materiales);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Material> materiales){

        if(materiales.size() == 0) {
            TableMaterial.setShowGrid(true);
            TableMaterial.setCellSelectionEnabled(false);
            TableMaterial.setAutoCreateRowSorter(true);
            TableMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableMaterial.setRowSelectionAllowed(true);
            TableMaterial.setDefaultEditor(Object.class, null);
            TableMaterial.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableMaterial.getModel());

            TableMaterial.setRowSorter(sorter);

            //Filling Headers
            modelMaterial = new DefaultTableModel(headers, 0);

            TableMaterial.setModel(modelMaterial);
        }else {
            TableMaterial.setShowGrid(true);
            TableMaterial.setCellSelectionEnabled(false);
            TableMaterial.setAutoCreateRowSorter(true);
            TableMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableMaterial.setRowSelectionAllowed(true);
            TableMaterial.setDefaultEditor(Object.class, null);
            TableMaterial.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableMaterial.getModel());

            TableMaterial.setRowSorter(sorter);

            //Filling Headers
            modelMaterial = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Material material : materiales) {
                data = getMaterialObject(material);
                modelMaterial.addRow(data);
            }

            TableMaterial.setModel(modelMaterial);
        }
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableMaterial.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableMaterial.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewMaterialFromFormulario(Material material){
        return controladorMaterial.createMaterial(material);
    }

    public boolean getUpdateMaterialFromFormulario(Material material) {
        return controladorMaterial.updateMaterial(material);
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
    private void createMaterial(){
        FormMaterial formMaterial = new FormMaterial(this);
    }

    private void readMaterial(){
        Material material = getMaterial();
        FormMaterial formMaterial = new FormMaterial(this, material, false);
    }

    private void updateMaterial() {
        Material material = getMaterial();
        System.out.println("Vista CODIGO: " + material.getCodigo());
        FormMaterial formMaterial = new FormMaterial(this, material);
    }

    private void deleteMaterial(){
        String cod = getCodMaterial();
        boolean result = controladorMaterial.deleteMaterial(cod);

        if(result){
            int row = TableMaterial.getSelectedRow();

            materiales.remove(row);
            refreshTable(headers, materiales);
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableMaterial(Material material) {

        int row = TableMaterial.getSelectedRow();

        materiales.get(row).setGrupo(material.getGrupo());
        materiales.get(row).setDescripcion(material.getDescripcion());
        materiales.get(row).setEspecificacion(material.getEspecificacion());
        materiales.get(row).setUnidad(material.getUnidad());
        materiales.get(row).setEspesor(material.getEspesor());
        materiales.get(row).setCalidad(material.getCalidad());
        materiales.get(row).setProveedor1(material.getProveedor1());
        materiales.get(row).setPrecio1(material.getPrecio1());
        materiales.get(row).setProveedor2(material.getProveedor2());
        materiales.get(row).setPrecio2(material.getPrecio2());
        materiales.get(row).setProveedor3(material.getProveedor3());
        materiales.get(row).setPrecio3(material.getPrecio3());

        refreshTable(headers, materiales);

    }

    public void addTableMaterial(Material material){

        Object[] newMaterial = getMaterialObject(material);
        modelMaterial.addRow(newMaterial);
        materiales.add(material);


    }

    public Object[] getMaterialObject(Material material){
        int y = 0;
        Object[] newMaterial = new Object[headers.length];
        newMaterial[y++] = material.getCodigo();
        newMaterial[y++] = material.getGrupo();
        newMaterial[y++] = material.getDescripcion();
        newMaterial[y++] = material.getEspecificacion();
        newMaterial[y++] = material.getUnidad();
        newMaterial[y++] = material.getEspesor();
        newMaterial[y++] = material.getCalidad();
        newMaterial[y++] = material.getProveedor1();
        newMaterial[y++] = material.getPrecio1();
        newMaterial[y++] = material.getProveedor2();
        newMaterial[y++] = material.getPrecio2();
        newMaterial[y++] = material.getProveedor3();
        newMaterial[y++] = material.getPrecio3();
        return newMaterial;
    }



    private Material getMaterial() {
        int row = TableMaterial.getSelectedRow();

        Material material = materiales.get(row);

        System.out.println("GET MATERIAL VISTA CODIGO: " + material.getCodigo());

        return material;
    }

    private String getCodMaterial() {
        int row = TableMaterial.getSelectedRow();

        return materiales.get(row).getCodigo();
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
                createMaterial();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readMaterial();
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
                deleteMaterial();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, materiales);
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
        TableMaterial.addMouseListener(new MouseAdapter() {
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

    TableRowSorter<TableModel> sorter;

    private DefaultTableModel modelMaterial;
    private String[] headers;
    private ControladorMaterial controladorMaterial;
    private ArrayList<Material> materiales;
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelMaterial;
    private JTable TableMaterial;
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

    //endregion
}
