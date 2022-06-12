package com.company.Vistas;

import com.company.Controlador.ControladorCertificacion;
import com.company.Entidades.*;
import com.company.Formularios.FormCertificacion;
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

public class ViewCertificacion extends JFrame{

    public ViewCertificacion(ControladorCertificacion controladorCertificacion, 
                             ArrayList<Certificacion> certificaciones, 
                             ArrayList<Actuacion> actuaciones) {
        estado = 1;
        this.controladorCertificacion = controladorCertificacion;
        this.certificaciones = certificaciones;
        this.actuaciones =  actuaciones;
        initWindow();
        initListeners();
        setVisible(true);
    }

    public ViewCertificacion(ControladorCertificacion controladorCertificacion,
                             Certificacion certificacion, 
                             ArrayList<Certificacion> certificaciones,
                             ArrayList<Actuacion> actuaciones) {
        estado = 2;
        this.controladorCertificacion = controladorCertificacion;
        this.certificaciones = certificaciones;
        this.actuaciones = actuaciones;
        CertificacionSiendoModificada = certificacion;
        initWindow();
        initListeners();
        setVisible(true);
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
        setTitle("Certificaciones");
        String[] listColumnsName = controladorCertificacion.getColumnsName();
        headersCertificacion = new String[listColumnsName.length - 1];

        for (int i = 0; i < listColumnsName.length-1; i++){

            if(listColumnsName[i+1].equals("id_actuacion")){
                headersCertificacion[i] = "Actuacion".toUpperCase();

            }else {
                headersCertificacion[i] = listColumnsName[i + 1].toUpperCase().replace('_', ' ');
            }
        }
        refreshTable(headersCertificacion, certificaciones);
        initSecondaryTables();
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Certificacion> certificaciones){

        if(actuaciones.size() == 0) {
            TableCertificacion.setShowGrid(true);
            TableCertificacion.setCellSelectionEnabled(false);
            TableCertificacion.setAutoCreateRowSorter(true);
            TableCertificacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableCertificacion.setRowSelectionAllowed(true);
            TableCertificacion.setDefaultEditor(Object.class, null);
            TableCertificacion.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableCertificacion.getModel());

            TableCertificacion.setRowSorter(sorter);

            //Filling Headers
            modelCertificacion = new DefaultTableModel(headers, 0);

            TableCertificacion.setModel(modelCertificacion);
        }else {
            TableCertificacion.setShowGrid(true);
            TableCertificacion.setCellSelectionEnabled(false);
            TableCertificacion.setAutoCreateRowSorter(true);
            TableCertificacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableCertificacion.setRowSelectionAllowed(true);
            TableCertificacion.setDefaultEditor(Object.class, null);
            TableCertificacion.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableCertificacion.getModel());

            TableCertificacion.setRowSorter(sorter);

            //Filling Headers
            modelCertificacion = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Certificacion certificacion : certificaciones) {
                data = getObjectCertificacion(certificacion);
                modelCertificacion.addRow(data);
            }

            TableCertificacion.setModel(modelCertificacion);
        }
    }
    
    private void initSecondaryTables(){
        TableActuaciones.setShowGrid(true);
        TableActuaciones.setCellSelectionEnabled(false);
        TableActuaciones.setAutoCreateRowSorter(true);
        TableActuaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableActuaciones.setRowSelectionAllowed(true);
        TableActuaciones.setDefaultEditor(Object.class, null);
        TableActuaciones.setDragEnabled(false);

        //Filling Headers
        modelActuacion = new DefaultTableModel(headersActuacion, 0);

        TableActuaciones.setModel(modelActuacion);
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableCertificacion.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableCertificacion.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewCertificacionFromFormulario(Certificacion certificacion){
        return controladorCertificacion.createCertificacion(certificacion);
    }

    public boolean getUpdateCertificacionFromFormulario(Certificacion certificacion) {
        return controladorCertificacion.updateCertificacion(certificacion);
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
    private void createCertificacion(){
        FormCertificacion formCertificacion = new FormCertificacion(this, actuaciones );
    }

    private void readCertificacion(){

        int row = TableCertificacion.getSelectedRow();
        if(row == -1){
            ShowErrorMessage("Error", "Error, selecciona un una certificación de la tabla.");

        }else {

            Certificacion certificacion = getCertificacion(row);
            FormCertificacion formCertificacion = new FormCertificacion(this, 
                    actuaciones,
                    certificacion,
                    false);
        }
    }

    private void updateCertificacion() {
        int row = TableCertificacion.getSelectedRow();
        if(row == -1){
            ShowErrorMessage("Error", "Error, selecciona una certificación de la tabla.");

        }else {

            Certificacion certificacion = getCertificacion(row);
            FormCertificacion formCertificacion = new FormCertificacion(this, actuaciones, certificacion);
        }
    }

    private void deleteCertificacion(){

        int row = TableCertificacion.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona una certificación de la tabla.");

        } else {

            Certificacion certificacion = getCertificacion(row);
            boolean result = controladorCertificacion.deleteCertificacion(certificacion.getId());

            if (result){
                ShowMessage("CORRECTO","La certificación ha sido borrada correctamente");
                certificaciones.remove(row);
                refreshTable(headersCertificacion, certificaciones);

            }else{
                ShowErrorMessage( "ERROR","La certificación no se ha podido borrar");
            }

        }
    }

    //endregion

    //region Metodos privados

    public void updateTableCertificacion(Certificacion certificacion) {

        int row = TableCertificacion.getSelectedRow();

        certificaciones.get(row).setId(certificacion.getId());
        certificaciones.get(row).setValor(certificacion.getValor());
        certificaciones.get(row).setFecha_certificacion(certificacion.getFecha_certificacion());
        certificaciones.get(row).setObservaciones(certificacion.getObservaciones());
        certificaciones.get(row).setActuacion(certificacion.getActuacion());

        refreshTable(headersCertificacion, certificaciones);

    }

    public void addTableCertificacion(Certificacion certificacion){

        Object[] newCertificacion = getObjectCertificacion(certificacion);
        modelCertificacion.addRow(newCertificacion);
        certificaciones.add(certificacion);


    }

    public Object[] getObjectCertificacion(Certificacion certificacion){
        int y = 0;
        Object[] newCertificacion = new Object[headersCertificacion.length];
        newCertificacion[y++] = certificacion.getActuacion().getId() + " - " + certificacion.getActuacion().getNombre();
        newCertificacion[y++] = certificacion.getFecha_certificacion();
        newCertificacion[y++] = certificacion.getValor();
        newCertificacion[y++] = certificacion.getObservaciones();

        return newCertificacion;
    }



    private Certificacion getCertificacion(int row) {

        return certificaciones.get(row);
    }

    private void setActuacion(Certificacion certificacion) {
        modelActuacion = new DefaultTableModel(headersActuacion, 0);
        TableActuaciones.setModel(modelActuacion);

        int y = 0;

        Actuacion actuacion = certificacion.getActuacion();

        Object[] newActuacion = new Object[headersActuacion.length];
        newActuacion[y++] = actuacion.getNombre();
        newActuacion[y++] = actuacion.getEspecificacion();
        newActuacion[y++] = actuacion.getCliente().getNombre();
        newActuacion[y++] = actuacion.getEstado();

        modelActuacion.addRow(newActuacion);
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
                createCertificacion();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCertificacion();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCertificacion();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCertificacion();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headersCertificacion, certificaciones);
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
        TableCertificacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) {
                    int row = TableCertificacion.getSelectedRow();

                    if(row == -1 ){
                        ShowErrorMessage("Error", "Error, selecciona un albaran de la tabla.");

                    }else {

                        Certificacion certificacion = getCertificacion(row);
                        setActuacion(certificacion);
                    }
                }


                if(e.getClickCount()==2){
                    updateCertificacion();
                }
            }
        });
    }

    //endregion

    //region Variables


    private ControladorCertificacion controladorCertificacion;
    private ArrayList<Certificacion> certificaciones;
    private ArrayList<Actuacion> actuaciones;
    private Certificacion CertificacionSiendoModificada;
    private int estado = 0;
    private String[] headersCertificacion;
    
    private String[] headersActuacion = {"NOMBRE", "ESPECIFICACION", "CLIENTE", "ESTADO"};

    DefaultTableModel modelCertificacion;
    DefaultTableModel modelActuacion;
    
    TableRowSorter sorter;

    private JPanel panelPrincipal;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JTable TableActuaciones;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JButton buttonRecargar;
    private JPanel PanelAlbaran;
    private JTable TableCertificacion;
    private JLabel labelTitulo;

    //endregion

}
