package com.company.Vistas;

import com.company.Controlador.ControladorMaterial;;
import com.company.Entidades.Material;
import com.company.Formularios.FormMaterial;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewMaterial extends JFrame{

    //region Constructores

    public ViewMaterial(ControladorMaterial controladorMaterial, ArrayList<Material> materiales) {
        this.controladorMaterial = controladorMaterial;
        this.materiales = materiales;
        initWindow();
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
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Materiales");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion


    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Material> materiales){
        // TODO: 19/05/2022 Para hacer el filtro https://www.tutorialspoint.com/how-can-we-filter-a-jtable-in-java

        TableMaterial.setShowGrid(true);
        TableMaterial.setGridColor(Color.black);
        TableMaterial.setAutoCreateRowSorter(true);
        TableMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableMaterial.setRowSelectionAllowed(true);
        TableMaterial.setDefaultEditor(Object.class, null);
        TableMaterial.setDragEnabled(false);
        //TableMaterial.setRowSorter(sorter);

        //Filling Headers
        modelMaterial = new DefaultTableModel(headers, 0);

        //Filling Data
        Object[] data = new Object[headers.length];

        int y = 0;
        for (Material material : materiales) {
            data[y++] = material.getCodigo();
            data[y++] = material.getGrupo();
            data[y++] = material.getDescripcion();
            data[y++] = material.getEspecificacion();
            data[y++] = material.getUnidad();
            data[y++] = material.getEspesor();
            data[y++] = material.getCalidad();
            data[y++] = material.getProveedor1();
            data[y++] = material.getPrecio1();
            data[y++] = material.getProveedor2();
            data[y++] = material.getPrecio2();
            data[y++] = material.getProveedor3();
            data[y++] = material.getPrecio3();
            y=0;
        }

        TableMaterial.setModel(modelMaterial);
    }
    //endregion


    //region Metodos Desde el Formulario

    public void getNewMaterialFromFormulario(Material material){
        controladorMaterial.createMaterial(material);
    }

    public void getUpdateMaterialFromFormulario(Material material) {
        controladorMaterial.updateMaterial(material);
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
        FormMaterial formMaterial = new FormMaterial(this, material);
    }

    private void deleteMaterial(){
        String cod = getCodMaterial();
        boolean result = controladorMaterial.deleteMaterial(cod);
    }


    //endregion

    //region Metodos privados

    public void updateTableMaterial(Material material) {
        //TODO actualizar tabla
    }

    private Material getMaterial() {
        //TODO coger el material desde la tabla
        return new Material();
    }

    private String getCodMaterial() {
        //TODO coger cod desde la tabla
        return null;
    }

    //endregion

    //region Listeners
    private void listeners(){
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

    }

    //endregion

    //region Variables

    private DefaultTableModel modelMaterial;

    private String[] headers = {"COD", "Grupo", "Descripcion", "Especificacion", "Unidad", "Espesor", "Calidad", "Proveedor 1", "Precio 1", "Proveedor 2", "Precio 2", "Proveedor 3", "Precio 3"};
    private ControladorMaterial controladorMaterial;
    private ArrayList<Material> materiales;
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelMaterial;
    private JTable TableMaterial;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buscar;
    private JPanel panelBotones;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;

    //endregion
}
