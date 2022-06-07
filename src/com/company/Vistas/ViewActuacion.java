package com.company.Vistas;

import com.company.Controlador.ControladorActuacion;
import com.company.Entidades.Actuacion;
import com.company.Entidades.Cliente;
import com.company.Entidades.EspecificacionActuacion;
import com.company.Formularios.FormActuacion;
import com.company.Formularios.FormCliente;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class ViewActuacion extends JFrame {

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
        setMinimumSize(new Dimension(750, 750));
        setLocationRelativeTo(null);
        setTitle("Actuaciones");
        listColumnsNameActuacion = controladorActuacion.getColumnsName();
        listColumsNameCliente = controladorActuacion.getColumnsNameCliente();


        //Formateo de Columnas
        headersActuacion = new String[listColumnsNameActuacion.length - 9];
        for (int i = 0; i < listColumnsNameActuacion.length - 9; i++) {
            headersActuacion[i] = listColumnsNameActuacion[i + 1].toUpperCase();
        }

        headersFechasActuacion = new String[4];
        for (int i = 16; i < 20; i++) {
            headersFechasActuacion[i - 16] = listColumnsNameActuacion[i].toUpperCase();
        }

        headersCliente = new String[listColumsNameCliente.length - 1];
        for (int i = 1; i < listColumsNameCliente.length; i++) {
            headersCliente[i - 1] = listColumsNameCliente[i].toUpperCase().replace('_', ' ');;
        }

        refreshTable(headersActuacion, headersFechasActuacion, actuaciones);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());

    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headersActuacion, String[] headersFechasActuacion, ArrayList<Actuacion> actuaciones) {
        refreshActuaciones();
        refreshFechas();
        refreshClientes();
    }

    private void refreshActuaciones() {
        if (actuaciones.size() == 0) {
            TableActuaciones.setShowGrid(true);
            TableActuaciones.setCellSelectionEnabled(false);
            TableActuaciones.setAutoCreateRowSorter(true);
            TableActuaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableActuaciones.setRowSelectionAllowed(true);
            TableActuaciones.setDefaultEditor(Object.class, null);
            TableActuaciones.setDragEnabled(false);

            sorter = new TableRowSorter<TableModel>(TableActuaciones.getModel());
            TableActuaciones.setRowSorter(sorter);

            //Filling Headers
            modelActuacion = new DefaultTableModel(headersActuacion, 0);

            TableActuaciones.setModel(modelActuacion);
        } else {

            TableActuaciones.setShowGrid(true);
            TableActuaciones.setCellSelectionEnabled(false);
            TableActuaciones.setAutoCreateRowSorter(true);
            TableActuaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableActuaciones.setRowSelectionAllowed(true);
            TableActuaciones.setDefaultEditor(Object.class, null);
            TableActuaciones.setDragEnabled(false);

            sorter = new TableRowSorter<TableModel>(TableActuaciones.getModel());

            TableActuaciones.setRowSorter(sorter);
            //Filling Headers
            modelActuacion = new DefaultTableModel(headersActuacion, 0);

            //Filling Data
            Object[] data = new Object[headersActuacion.length];

            for (Actuacion actuacion : actuaciones) {
                data = getActuacionObject(actuacion);
                modelActuacion.addRow(data);
            }

            TableActuaciones.setModel(modelActuacion);
        }

    }

    private void refreshFechas() {

        TableFechas.setShowGrid(true);
        TableFechas.setCellSelectionEnabled(false);
        TableFechas.setAutoCreateRowSorter(true);
        TableFechas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableFechas.setRowSelectionAllowed(true);
        TableFechas.setDefaultEditor(Object.class, null);
        TableFechas.setDragEnabled(false);

        //Filling Headers
        modelFechasActuacion = new DefaultTableModel(headersFechasActuacion, 0);

        TableFechas.setModel(modelFechasActuacion);

    }

    private void refreshClientes() {

        TableClientes.setShowGrid(true);
        TableClientes.setCellSelectionEnabled(false);
        TableClientes.setAutoCreateRowSorter(true);
        TableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableClientes.setRowSelectionAllowed(true);
        TableClientes.setDefaultEditor(Object.class, null);
        TableClientes.setDragEnabled(false);

        //Filling Headers
        modelFechasActuacion = new DefaultTableModel(headersCliente, 0);

        TableClientes.setModel(modelFechasActuacion);

    }

    private void filter() {
        DefaultTableModel Model = (DefaultTableModel) TableActuaciones.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableActuaciones.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewActuacionFromFormulario(Actuacion actuacion) {
        return controladorActuacion.createActuacion(actuacion);
    }

    public boolean getUpdateActuacionFromFormulario(Actuacion actuacion) {
        return controladorActuacion.updateActuacion(actuacion);
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
    private void createActuacion() {


        FormActuacion formActuacion = new FormActuacion(this, clientes, especificacionesActuacion);
    }

    private void readActuacion() {


        Actuacion actuacion = getActuacion();
        FormActuacion formActuacion = new FormActuacion(this, actuacion, clientes, especificacionesActuacion, false);
    }

    private void updateActuacion() {
        Actuacion actuacion = getActuacion();
        FormActuacion formActuacion = new FormActuacion(this, actuacion, clientes, especificacionesActuacion);
    }

    private void deleteActuacion() {

        Actuacion actuacion = getActuacion();

        boolean result = controladorActuacion.deleteActuacion(actuacion.getId());

        if (result) {
            int row = TableActuaciones.getSelectedRow();

            actuaciones.remove(row);
            refreshTable(headersActuacion, headersFechasActuacion, actuaciones);
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableActuacion(Actuacion actuacion) {

        int row = TableActuaciones.getSelectedRow();

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

        refreshTable(headersActuacion, headersFechasActuacion, actuaciones);

    }

    public void addTableActuacion(Actuacion actuacion) {

        Object[] newActuacion = getActuacionObject(actuacion);
        modelActuacion.addRow(newActuacion);
        actuaciones.add(actuacion);


    }

    public Object[] getActuacionObject(Actuacion actuacion) {
        int y = 0;
        Object[] newActuacion = new Object[headersActuacion.length];

        newActuacion[y++] = actuacion.getNombre();
        newActuacion[y++] = actuacion.getEspecificacion();
        newActuacion[y++] = actuacion.getCliente().getNombre();
        newActuacion[y++] = actuacion.getEstado();
        newActuacion[y++] = actuacion.getDescripcion();
        newActuacion[y++] = actuacion.getImporte();
        newActuacion[y++] = actuacion.getPorPertificar();
        newActuacion[y++] = actuacion.getTotalCertificicaciones();
        newActuacion[y++] = actuacion.getGastoMaterial();
        newActuacion[y++] = actuacion.getMaterialOfertado();
        newActuacion[y++] = actuacion.getResultadoBalance();

        return newActuacion;
    }

    private void setHojas(Actuacion actuacion) {
        URLhojaPlanificacion = actuacion.getHojaPlanificacion();
        URLhojaPresupuesto = actuacion.getHojaPresupuesto();

        // TODO: 30/05/2022 CLASE JFILECHOSER PARA ABRIR LA URL
    }

    private void setHoras(Actuacion actuacion) {
        textFieldHorasOfertadas.setText(String.valueOf(actuacion.getHorasOfertadas()));
        textFieldHorasEjecutadas.setText(String.valueOf(actuacion.getHorasEjecutadas()));
        progressBarProgreso.setMaximum(actuacion.getHorasOfertadas());
        progressBarProgreso.setValue(actuacion.getHorasEjecutadas());

    }

    private void setFechas(Actuacion actuacion) {
        modelFechasActuacion = new DefaultTableModel(headersFechasActuacion, 0);
        TableFechas.setModel(modelFechasActuacion);


        int y = 0;

        Object[] newActuacionFechas = new Object[headersFechasActuacion.length];
        newActuacionFechas[y++] = actuacion.getFecha_solicitud();
        newActuacionFechas[y++] = actuacion.getFecha_envio();
        newActuacionFechas[y++] = actuacion.getFecha_comienzo();
        newActuacionFechas[y++] = actuacion.getFecha_finalizacion();
        modelFechasActuacion.addRow(newActuacionFechas);

    }

    private void setCliente(Actuacion actuacion) {
        modelClientes = new DefaultTableModel(headersCliente, 0);
        TableClientes.setModel(modelClientes);


        int y = 0;

        Object[] newActuacionCliente = new Object[headersCliente.length-1];
        newActuacionCliente[y++] = actuacion.getCliente().getCIF();
        newActuacionCliente[y++] = actuacion.getCliente().getNombre();
        newActuacionCliente[y++] = actuacion.getCliente().getDireccion();
        newActuacionCliente[y++] = actuacion.getCliente().getMail1();
        newActuacionCliente[y++] = actuacion.getCliente().getTelef1();
        modelClientes.addRow(newActuacionCliente);

    }

    private void setArchivos(Actuacion actuacion) {
        buttonHojaPresupuesto.setText(actuacion.getHojaPresupuesto());
        buttonHojaPlanificacion.setText(actuacion.getHojaPlanificacion());
    }

    private Actuacion getActuacion() {
        int row = TableActuaciones.getSelectedRow();

        return actuaciones.get(row);

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
                refreshTable(headersActuacion, headersFechasActuacion, actuaciones);
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



        buttonHojaPlanificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser pathing = new JFileChooser();
                pathing.setCurrentDirectory(new File("src/com/company"));
                pathing.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("*.pdf", "pdf");
                pathing.setFileFilter(filtroPDF);
                int seleccion = pathing.showOpenDialog(panelPrincipal);

                if(seleccion == JFileChooser.APPROVE_OPTION){
                    // TODO: 06/06/2022 VER QUE HACEMOS CUANDO CLICAMOS AQUI
                }

            }
        });

        buttonHojaPresupuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser pathing = new JFileChooser();
                pathing.setCurrentDirectory(new File("src/com/company"));
                pathing.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("*.pdf", "pdf");
                pathing.setFileFilter(filtroPDF);
                int seleccion = pathing.showOpenDialog(panelPrincipal);

                if(seleccion == JFileChooser.APPROVE_OPTION){
                    // TODO: 06/06/2022 VER QUE HACEMOS CUANDO CLICAMOS AQUI
                }
            }
        });

    }

    private void mouseListeners() {
        TableActuaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Actuacion actuacion = getActuacion();

                    setHojas(actuacion);

                    setHoras(actuacion);

                    setFechas(actuacion);

                    setCliente(actuacion);

                    setArchivos(actuacion);
                }

                if (e.getClickCount() == 2) {
                    updateActuacion();
                }
            }
        });

        TableClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = TableActuaciones.getSelectedRow();

                    Cliente cliente = actuaciones.get(row).getCliente();
                    FormCliente formCliente = new FormCliente(cliente, false);
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

    private String[] headersActuacion;
    private String[] headersFechasActuacion;
    private String[] headersCliente;

    private String[] listColumnsNameActuacion;
    private String[] listColumsNameCliente;

    private TableRowSorter sorter;


    private DefaultTableModel modelActuacion;
    private DefaultTableModel modelFechasActuacion;
    private DefaultTableModel modelClientes;

    private String URLhojaPlanificacion;
    private String URLhojaPresupuesto;

    private JPanel panelPrincipal;
    private JTable TableActuaciones;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;
    private JButton buttonVolver;
    private JPanel buscador;
    private JPanel panelBotones;
    private JButton buttonHojaPlanificacion;
    private JProgressBar progressBarProgreso;
    private JTextField textFieldHorasOfertadas;
    private JTextField textFieldHorasEjecutadas;
    private JTable TableFechas;
    private JTable TableClientes;
    private JLabel labelTitulo;
    private JButton buttonHojaPresupuesto;


    //endregion
}
