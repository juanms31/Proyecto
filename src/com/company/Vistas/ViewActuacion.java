package com.company.Vistas;

import com.company.Controlador.ControladorActuacion;
import com.company.Entidades.Actuacion;
import com.company.Entidades.Cliente;
import com.company.Entidades.EspecificacionActuacion;
import com.company.Entidades.Trabajador;
import com.company.Formularios.FormActuacion;
import com.company.Formularios.FormTrabajador;
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

public class ViewActuacion extends JFrame{

    //region Constructores

    public ViewActuacion(ControladorActuacion controladorActuacion, ArrayList<Actuacion> actuaciones,
                         ArrayList<Cliente> clientes,
                         ArrayList<EspecificacionActuacion> especificacionesActuacion) {
        this.controladorActuacion = controladorActuacion;
        this.actuaciones = actuaciones;
        this.clientes = clientes;
        this.especificacionesActuacion = especificacionesActuacion;
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
        setTitle("Actuaciones");
        String[] listColumnsName = controladorActuacion.getColumnsName();
        headers = new String[listColumnsName.length - 12];
        for (int i = 0; i < listColumnsName.length-12; i++){
            headers[i] = listColumnsName[i+1].toUpperCase();
        }
        refreshTable(headers, actuaciones);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Actuacion> actuaciones){

        if(actuaciones.size() == 0) {
            TableActuacion.setShowGrid(true);
            TableActuacion.setCellSelectionEnabled(false);
            TableActuacion.setAutoCreateRowSorter(true);
            TableActuacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableActuacion.setRowSelectionAllowed(true);
            TableActuacion.setDefaultEditor(Object.class, null);
            TableActuacion.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableActuacion.getModel());

            TableActuacion.setRowSorter(sorter);

            //Filling Headers
            modelActuacion = new DefaultTableModel(headers, 0);

            TableActuacion.setModel(modelActuacion);
        }else {

            TableActuacion.setShowGrid(true);
            TableActuacion.setCellSelectionEnabled(false);
            TableActuacion.setAutoCreateRowSorter(true);
            TableActuacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableActuacion.setRowSelectionAllowed(true);
            TableActuacion.setDefaultEditor(Object.class, null);
            TableActuacion.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableActuacion.getModel());

            TableActuacion.setRowSorter(sorter);

            //Filling Headers
            modelActuacion = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Actuacion actuacion : actuaciones) {
                data = getActuacionObject(actuacion);
                modelActuacion.addRow(data);
            }

            TableActuacion.setModel(modelActuacion);
        }
    }

    private void filter(){
        DefaultTableModel Model = (DefaultTableModel) TableActuacion.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableActuacion.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewActuacionFromFormulario(Actuacion actuacion){
        return controladorActuacion.createActuacion(actuacion);
    }

    public boolean getUpdateActuacionFromFormulario(Actuacion actuacion) {
        return controladorActuacion.updateActuacion(actuacion);
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
    private void createActuacion(){
        FormActuacion formActuacion = new FormActuacion(this, clientes, especificacionesActuacion);
    }

    private void readActuacion(){
        Actuacion actuacion = getActuacion();
        FormActuacion formActuacion = new FormActuacion(this, actuacion, clientes, especificacionesActuacion, false);
    }

    private void updateActuacion() {
        Actuacion actuacion = getActuacion();
        FormActuacion formActuacion = new FormActuacion(this, actuacion, clientes, especificacionesActuacion);
    }

    private void deleteActuacion(){

        Actuacion actuacion = getActuacion();

        boolean result = controladorActuacion.deleteActuacion(actuacion.getId());

        if(result){
            int row = TableActuacion.getSelectedRow();

            actuaciones.remove(row);
            refreshTable(headers, actuaciones);
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableActuacion(Actuacion actuacion) {

        int row = TableActuacion.getSelectedRow();

        actuaciones.get(row).setNombre(actuacion.getNombre());
        actuaciones.get(row).setCliente(actuacion.getCliente());
        actuaciones.get(row).setEspecificacion(actuacion.getEspecificacion());
        actuaciones.get(row).setEstado(actuacion.getEstado());
        actuaciones.get(row).setFecha_solicitud(actuacion.getFecha_solicitud());
        actuaciones.get(row).setFecha_envio(actuacion.getFecha_envio());
        actuaciones.get(row).setFecha_comienzo(actuacion.getFecha_comienzo());
        actuaciones.get(row).setFecha_finalizacion(actuacion.getFecha_finalizacion());
        actuaciones.get(row).setGastoMaterial(actuacion.getGastoMaterial());
        actuaciones.get(row).setDescripcion(actuacion.getDescripcion());
        actuaciones.get(row).setImporte(actuacion.getImporte());
        actuaciones.get(row).setHojaPlanificacion(actuacion.getHojaPlanificacion());
        actuaciones.get(row).setHojaPresupuesto(actuacion.getHojaPresupuesto());
        actuaciones.get(row).setTotalCertificicaciones(actuacion.getTotalCertificicaciones());
        actuaciones.get(row).setPorPertificar(actuacion.getPorPertificar());
        actuaciones.get(row).setHorasOfertadas(actuacion.getHorasOfertadas());
        actuaciones.get(row).setHorasEjecutadas(actuacion.getHorasEjecutadas());
        actuaciones.get(row).setResultadoBalance(actuacion.getResultadoBalance());

        refreshTable(headers, actuaciones);

    }

    public void addTableActuacion(Actuacion actuacion){

        Object[] newActuacion = getActuacionObject(actuacion);
        modelActuacion.addRow(newActuacion);
        actuaciones.add(actuacion);


    }

    public Object[] getActuacionObject(Actuacion actuacion){
        int y = 0;
        Object[] newActuacion = new Object[headers.length];

        newActuacion[y++] = actuacion.getNombre();
        newActuacion[y++] = actuacion.getCliente().getNombre();
        newActuacion[y++] = actuacion.getEspecificacion();
        newActuacion[y++] = actuacion.getEstado();
        newActuacion[y++] = actuacion.getFecha_solicitud();
        newActuacion[y++] = actuacion.getFecha_envio();
        newActuacion[y++] = actuacion.getFecha_comienzo();
        newActuacion[y++] = actuacion.getFecha_finalizacion();
//        newActuacion[y++] = actuacion.getDescripcion();
//        newActuacion[y++] = actuacion.getGastoMaterial();
//        newActuacion[y++] = actuacion.getImporte();
//        newActuacion[y++] = actuacion.getHojaPlanificacion();
//        newActuacion[y++] = actuacion.getHojaPresupuesto();
//        newActuacion[y++] = actuacion.getTotalCertificicaciones();
//        newActuacion[y++] = actuacion.getPorPertificar();
//        newActuacion[y++] = actuacion.getHorasOfertadas();
//        newActuacion[y++] = actuacion.getHorasEjecutadas();
//        newActuacion[y++] = actuacion.getResultadoBalance();

        return newActuacion;
    }



    private Actuacion getActuacion() {
        int row = TableActuacion.getSelectedRow();

        return actuaciones.get(row);

    }

    private int getCodActuacion() {
//        int row = TableTrabajador.getSelectedRow();
//        return trabajadores.get(row).getId();
        return 0;
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
                createActuacion();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readActuacion();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateActuacion();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteActuacion();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, actuaciones);
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
        TableActuacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    updateActuacion();
                }
            }
        });
    }

    //endregion
    
    
    

    //region Variables
    private ControladorActuacion controladorActuacion; 
    private ArrayList<Actuacion> actuaciones;
    private ArrayList<Cliente> clientes;
    private ArrayList<EspecificacionActuacion> especificacionesActuacion;
    private String[] headers;
    private TableRowSorter sorter;
    private DefaultTableModel modelActuacion;
    
    private JPanel panelPrincipal;
    private JTable TableActuacion;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;
    private JButton buttonVolver;
    private JTabbedPane panelPestanas;
    private JPanel PanelMaterial;
    private JPanel buscador;
    private JPanel panelBotones;

    //endregion
}
