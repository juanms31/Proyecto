package com.company.Vistas;

import com.company.Controlador.ControladorAlbaran;
import com.company.Entidades.*;
import com.company.Formularios.FormAlbaran;
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

public class ViewAlbaran extends JFrame {

    //region Constructor
    public ViewAlbaran(ControladorAlbaran controladorAlbaran,
                       ArrayList<Albaran> albaranes,
                       ArrayList<Material> materiales,
                       ArrayList<MaterialCompradoProveedor> materialesCompradosProveedor,
                       ArrayList<Actuacion> actuaciones,
                       ArrayList<Proveedor> proveedores) {
        this.controladorAlbaran = controladorAlbaran;
        this.albaranes = albaranes;
        this.materiales = materiales;
        this.materialesCompradosProveedor = materialesCompradosProveedor;
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

    public void initHeaders() {
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

    private void initSecondaryTables() {
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
        modelActuacionesAlbaran = new DefaultTableModel(headersActuacion, 0);

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

    private void refreshSecondaryTables() {
        modelMaterialesAlbaran = new DefaultTableModel(headersMateriales, 0);
        TableMaterialesAlbaran.setModel(modelMaterialesAlbaran);

        modelActuacionesAlbaran = new DefaultTableModel(headersMateriales, 0);
        TableActuacionesAlbaran.setModel(modelActuacionesAlbaran);


    }


    private void filter() {
        DefaultTableModel Model = (DefaultTableModel) TableAlbaran.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableAlbaran.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewAlbaranFromFormulario(Albaran albaran) {
        return controladorAlbaran.createAlbaran(albaran);
    }

    public boolean getUpdateAlbaranFromFormulario(Albaran albaran) {
        return controladorAlbaran.updateAlbaran(albaran);
    }

    public boolean getMaterialesAlbaranFromFormulario(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {

        return controladorAlbaran.createMaterialesCompradoProveedor(materialesCompradoProveedor);
    }

    public boolean getUpdateMaterialAlbaranFromFormulario(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {

        boolean yaEsta = false;

        updateMaterialesCompradosProveedorExistentes(materialesCompradoProveedor);

        addMaterialesCompradosProveedorNuevos(materialesCompradoProveedor);

        return controladorAlbaran.updateMaterialesAlbaran(materialesCompradoProveedor);

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
        FormAlbaran formAlbaran = new FormAlbaran(this, materiales, actuaciones, proveedores);
    }

    private void readAlbaran() {
        Albaran albaran = getAlbaran();
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran, materiales, actuaciones, proveedores, false);
    }

    private void updateAlbaran() {
        Albaran albaran = getAlbaran();
        ArrayList<MaterialCompradoProveedor> materialCompradoProveedores = getMaterialesCompradosProveedor(albaran);
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran, materiales, materialCompradoProveedores, actuaciones, proveedores);
    }


    private void deleteAlbaran() {

        Albaran albaran = getAlbaran();

        boolean result = controladorAlbaran.deleteAlbaran(albaran.getId());

        if (result) {

            ShowMessage("CORRECTO", "El albaran con codigo: " + albaran.getCod() + " ha sido borrado");
            int row = TableAlbaran.getSelectedRow();

            albaranes.remove(row);
            refreshTable(headers, albaranes);
            refreshSecondaryTables();
        }else{
            ShowErrorMessage("ERROR", "El albaran con codigo: " + albaran.getCod() + " no se ha podido borrar");
        }
    }

    //endregion

    //region Metodos privados
    public void updateTableAlbaran(Albaran albaran) {

        int row = TableAlbaran.getSelectedRow();

        albaranes.get(row).setCod(albaran.getCod());
        albaranes.get(row).setActuacion(albaran.getActuacion());
        albaranes.get(row).setProveedor(albaran.getProveedor());
        albaranes.get(row).setConcepto(albaran.getConcepto());
        albaranes.get(row).setFechaEntradaAlbaran(albaran.getFechaEntradaAlbaran());

        refreshTable(headers, albaranes);
        refreshSecondaryTables();
    }

    public void updateMaterialesCompradosAlbaran(MaterialCompradoProveedor materialCompradoProveedor) {
        int cont = 0;
        boolean encontrado = false;

        ArrayList<MaterialCompradoProveedor> temp = new ArrayList<>();

        for(MaterialCompradoProveedor materialCompradoProveedor1 : materialesCompradosProveedor){
            if(materialCompradoProveedor1.getId() == materialCompradoProveedor.getId()){
                encontrado = true;
                materialesCompradosProveedor.get(cont).setMaterial(materialCompradoProveedor.getMaterial());
                materialesCompradosProveedor.get(cont).setProveedor(materialCompradoProveedor.getProveedor());
                materialesCompradosProveedor.get(cont).setActuacion(materialCompradoProveedor.getActuacion());
                materialesCompradosProveedor.get(cont).setAlbaran(materialCompradoProveedor.getAlbaran());
                materialesCompradosProveedor.get(cont).setUnidades(materialCompradoProveedor.getUnidades());
                materialesCompradosProveedor.get(cont).setPrecioUnidad(materialCompradoProveedor.getPrecioUnidad());
                materialesCompradosProveedor.get(cont).setBaseImponible(materialCompradoProveedor.getBaseImponible());
            }else {
                temp.add(materialCompradoProveedor);
            }
            cont++;
        }
    }

    public void addTableAlbaran(Albaran albaran) {
        Object[] newAlbaran = getAlbaranObject(albaran);
        modelAlbaran.addRow(newAlbaran);
        albaranes.add(albaran);


    }

    public void addTableMaterialAlbaran(MaterialCompradoProveedor materialCompradoProveedor) {
        materialesCompradosProveedor.add(materialCompradoProveedor);

    }

    public Object[] getAlbaranObject(Albaran albaran) {
        int y = 0;
        Object[] newAlbaran = new Object[headers.length];

        newAlbaran[y++] = albaran.getCod();
        newAlbaran[y++] = albaran.getActuacion().getNombre();
        newAlbaran[y++] = albaran.getProveedor().getNombre_proveedor();
        newAlbaran[y++] = albaran.getConcepto();
        newAlbaran[y++] = albaran.getFechaEntradaAlbaran();

        return newAlbaran;
    }


    private Albaran getAlbaran() {
        int row = TableAlbaran.getSelectedRow();

        return albaranes.get(row);

    }

    private ArrayList<MaterialCompradoProveedor> getMaterialesCompradosProveedor(Albaran albaran) {
        ArrayList<MaterialCompradoProveedor> temp = new ArrayList<>();

        for (MaterialCompradoProveedor materialCompradoProveedor : materialesCompradosProveedor) {
            if (materialCompradoProveedor.getAlbaran().getId() == albaran.getId()) {
                temp.add(materialCompradoProveedor);
            }
        }

        return temp;
    }


    private void setMateriales(Albaran albaran) {
        modelMaterialesAlbaran = new DefaultTableModel(headersMateriales, 0);
        TableMaterialesAlbaran.setModel(modelMaterialesAlbaran);

        for (MaterialCompradoProveedor materialCompradoProveedor : materialesCompradosProveedor) {
            if (albaran.getId() == materialCompradoProveedor.getAlbaran().getId()) {
                Material material = getMaterialFromCod(materialCompradoProveedor.getMaterial().getId());
                setMaterial(material, materialCompradoProveedor);

            }
        }
    }

    private void setActuacion(Albaran albaran){
        modelActuacionesAlbaran = new DefaultTableModel(headersActuacion, 0);
        TableActuacionesAlbaran.setModel(modelActuacionesAlbaran);

        int y = 0;

        Actuacion actuacion = albaran.getActuacion();

        Object[] newActuacion = new Object[headersActuacion.length];
        newActuacion[y++] = actuacion.getNombre();
        newActuacion[y++] = actuacion.getEspecificacion();
        newActuacion[y++] = actuacion.getCliente().getNombre();
        newActuacion[y++] = actuacion.getEstado();

        modelActuacionesAlbaran.addRow(newActuacion);
    }


    private void setMaterial(Material material, MaterialCompradoProveedor materialCompradoProveedor) {

        int y = 0;

        Object[] newMaterial = new Object[headersMateriales.length];
        newMaterial[y++] = material.getCodigo();
        newMaterial[y++] = material.getDescripcion();
        newMaterial[y++] = materialCompradoProveedor.getUnidades();
        newMaterial[y++] = materialCompradoProveedor.getPrecioUnidad();
        newMaterial[y++] = materialCompradoProveedor.getBaseImponible();

        modelMaterialesAlbaran.addRow(newMaterial);

    }

    private Material getMaterialFromCod(int id) {
        for (Material material : materiales) {
            if (material.getId() == id) {
                return material;
            }
        }
        return new Material();
    }

    public void updateMaterialesCompradosProveedorExistentes(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor){
        int cont = 0;
        for(MaterialCompradoProveedor mat1 : materialesCompradoProveedor){
            cont = 0;
            for(MaterialCompradoProveedor mat2 :  this.materialesCompradosProveedor){
                if(mat1.getId() == mat2.getId()){
                    this.materialesCompradosProveedor.get(cont).setProveedor(mat1.getProveedor());
                    this.materialesCompradosProveedor.get(cont).setAlbaran(mat1.getAlbaran());
                    this.materialesCompradosProveedor.get(cont).setMaterial(mat1.getMaterial());
                    this.materialesCompradosProveedor.get(cont).setActuacion(mat1.getActuacion());
                    this.materialesCompradosProveedor.get(cont).setUnidades(mat1.getUnidades());
                    this.materialesCompradosProveedor.get(cont).setBaseImponible(mat1.getBaseImponible());
                    this.materialesCompradosProveedor.get(cont).setPrecioUnidad(mat1.getPrecioUnidad());
                }
                cont++;
            }
        }

    }

    public void addMaterialesCompradosProveedorNuevos(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor){
        ArrayList<MaterialCompradoProveedor> nuevos = new ArrayList<>();

        for (MaterialCompradoProveedor mat1: materialesCompradoProveedor ) {
            if(!ExisteId(mat1.getId())){
                nuevos.add(mat1);
            }
        }

        for(MaterialCompradoProveedor materialCompradoProveedor :  nuevos){
            this.materialesCompradosProveedor.add(materialCompradoProveedor);
        }
    }

    private boolean ExisteId(int id){

        for (MaterialCompradoProveedor materialCompradoProveedor : materialesCompradosProveedor){
            if(id == materialCompradoProveedor.getId()){
                return true;
            }
        }

        return false;
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

                if (e.getClickCount() == 1) {
                    Albaran albaran = getAlbaran();
                    setMateriales(albaran);
                    setActuacion(albaran);
                }

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
    private JLabel labelTitulo;
    private ControladorAlbaran controladorAlbaran;
    private int estado = 0;
    private ArrayList<Albaran> albaranes;
    private ArrayList<Material> materiales;
    private ArrayList<MaterialCompradoProveedor> materialesCompradosProveedor = new ArrayList<>();
    private ArrayList<Actuacion> actuaciones;
    private final ArrayList<Proveedor> proveedores;
    private String[] headers;

    private String[] headersMateriales = {"COD", "DESC. MATERIAL", "UNIDADES", "PRECIO UNITARIO", "BASE IMPONIBLE"};

    private String[] headersActuacion = {"NOMBRE", "ESPECIFICACION", "CLIENTE", "ESTADO"};

    private TableRowSorter sorter;
    private DefaultTableModel modelAlbaran;
    private DefaultTableModel modelMaterialesAlbaran;
    private DefaultTableModel modelActuacionesAlbaran;


    //endregion
}
