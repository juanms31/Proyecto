package com.company.Vistas;

import com.company.Controlador.ControladorMaterial;;
import com.company.Entidades.Material;
import com.company.Formularios.FormMaterial;
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
import java.util.regex.PatternSyntaxException;

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
        refreshTable(headers, materiales);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Material> materiales){

        Material material1 = materiales.get(0);
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

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableMaterial.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableMaterial.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
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
        materiales.add(material);
        modelMaterial.addRow(getMaterialObject(material));
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
        
        Material material = new Material();

        material.setCodigo((String) TableMaterial.getValueAt(row,0));
        material.setGrupo((String) TableMaterial.getValueAt(row,1));
        material.setDescripcion((String) TableMaterial.getValueAt(row,2));
        material.setEspecificacion((String) TableMaterial.getValueAt(row,3));
        material.setUnidad((String) TableMaterial.getValueAt(row,4));
        material.setEspesor((Double) TableMaterial.getValueAt(row,5));
        material.setCalidad((String) TableMaterial.getValueAt(row,6));
        material.setProveedor1((String) TableMaterial.getValueAt(row,7));
        material.setPrecio1((Double) TableMaterial.getValueAt(row,8));
        material.setProveedor2((String) TableMaterial.getValueAt(row,9));
        material.setPrecio2((Double) TableMaterial.getValueAt(row,10));
        material.setProveedor3((String) TableMaterial.getValueAt(row,11));
        material.setPrecio3((Double) TableMaterial.getValueAt(row,12));

        return material;
    }

    private String getCodMaterial() {
        int row = TableMaterial.getSelectedRow();

        return String.valueOf(materiales.get(row).getId());
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

    // TODO: 22/05/2022
    private String[] headers = {"COD", "Grupo", "Descripcion", "Especificacion", "Unidad", "Espesor", "Calidad", "Proveedor 1", "Precio 1", "Proveedor 2", "Precio 2", "Proveedor 3", "Precio 3"};
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

    //endregion
}
