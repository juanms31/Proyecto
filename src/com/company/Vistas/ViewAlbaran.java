package com.company.Vistas;

import com.company.Controlador.ControladorAlbaran;
import com.company.Entidades.Albaran;
import com.company.Entidades.Cliente;
import com.company.Formularios.FormAlbaran;
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

public class ViewAlbaran extends JFrame{


    public ViewAlbaran(ControladorAlbaran controladorAlbaran, ArrayList<Albaran> albaranes) {
        this.controladorAlbaran = controladorAlbaran;
        this.albaranes = albaranes;
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
        setTitle("Albaranes");
        String[] listColumnsName = controladorAlbaran.getColumnsName();
        headers = new String[listColumnsName.length - 1];
        for (int i = 0; i < listColumnsName.length-1; i++){
            headers[i] = listColumnsName[i+1].toUpperCase();
        }
        refreshTable(headers, albaranes);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Albaran> albaranes){

        if(albaranes.size() == 0) {
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
        }else {

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

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableAlbaran.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableAlbaran.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewAlbaranFromFormulario(Albaran albaran){
        return controladorAlbaran.createAlbaran(albaran);
    }

    public boolean getUpdateAlbaranFromFormulario(Albaran albaran) {
        return controladorAlbaran.updateAlbaran(albaran);
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
    private void createAlbaran(){
        FormAlbaran formAlbaran = new FormAlbaran(this);
    }

    private void readAlbaran(){
        Albaran albaran = getAlbaran();
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran, false);
    }

    private void updateAlbaran() {
        Albaran albaran = getAlbaran();
        FormAlbaran formAlbaran = new FormAlbaran(this, albaran);


    }

    private void deleteAlbaran(){

        Albaran albaran = getAlbaran();

        boolean result = controladorAlbaran.deleteAlbaran(albaran);

        if(result){
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
        albaranes.get(row).setUnidades(albaran.getUnidades());
        albaranes.get(row).setFechaEntradaAlbaran(albaran.getFechaEntradaAlbaran());
        albaranes.get(row).setPrecioUnidad(albaran.getPrecioUnidad());
        albaranes.get(row).setBaseImponible(albaran.getBaseImponible());
        albaranes.get(row).setNaturaleza(albaran.getNaturaleza());
        albaranes.get(row).setIdActuacion(albaran.getIdActuacion());
        albaranes.get(row).setIdProveedor(albaran.getIdProveedor());

        refreshTable(headers, albaranes);

    }

    public void addTableAlbaran(Albaran albaran){

        Object[] newAlbaran = getAlbaranObject(albaran);
        modelAlbaran.addRow(newAlbaran);
        albaranes.add(albaran);


    }

    public Object[] getAlbaranObject(Albaran albaran){

        // FIXME: 24/05/2022  cuando añadimos actuacion da fallo porque es un objeto tal cual.
        int y = 0;
        Object[] newAlbaran = new Object[headers.length];
        newAlbaran[y++] = albaran.getActuacion().getNombre();
        newAlbaran[y++] = albaran.getProveedor();
        newAlbaran[y++] = albaran.getConcepto();
        newAlbaran[y++] = albaran.getUnidades();
        newAlbaran[y++] = albaran.getFechaEntradaAlbaran();
        newAlbaran[y++] = albaran.getPrecioUnidad();
        newAlbaran[y++] = albaran.getBaseImponible();
        newAlbaran[y++] = albaran.getNaturaleza();
        newAlbaran[y++] = albaran.getIdActuacion();
        newAlbaran[y++] = albaran.getIdProveedor();

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
    private void initListeners(){
        actionListeners();
        mouseListeners();
    }

    private void actionListeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAlbaran();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { readAlbaran();            }
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

    private void mouseListeners(){
        TableAlbaran.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
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
    private JTabbedPane panelPestanas;
    private JPanel PanelMaterial;
    private JTable TableAlbaran;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JButton buttonRecargar;
    
    private ControladorAlbaran controladorAlbaran;
    private int estado = 0;
    private Albaran AlbaranSiendoModificado;
    private ArrayList<Albaran> albaranes;
    private String[] headers;
    private TableRowSorter sorter;
    private DefaultTableModel modelAlbaran;
    //endregion
}
