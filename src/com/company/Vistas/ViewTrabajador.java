package com.company.Vistas;

import com.company.BaseDatos.CRUDVacaciones;
import com.company.Controlador.ControladorTrabajador;
import com.company.Entidades.*;
import com.company.Formularios.FormTrabajador;
import com.company.Graficos.GraficosBasicos;
import com.company.Graficos.NodoGraficoCircular;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.joda.time.LocalDateTime;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

public class ViewTrabajador extends JFrame {


    //region Constructores

    public ViewTrabajador(ControladorTrabajador controladorTrabajador, ArrayList<Trabajador> trabajadores) {
        this.controladorTrabajador = controladorTrabajador;
        this.trabajadores = trabajadores;
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
        setTitle("Trabajadores");
        String[] listColumnsName = controladorTrabajador.getColumnsName();
        headers = new String[listColumnsName.length - 1];
        for (int i = 0; i < listColumnsName.length - 1; i++) {
            headers[i] = listColumnsName[i + 1].toUpperCase().replace('_', ' ');
        }
        refreshTable(headers, trabajadores);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region Metodos Tabla

    public void refreshTable(String[] headers, ArrayList<Trabajador> trabajadores) {

        if (trabajadores.size() == 0) {
            TableTrabajador.setShowGrid(true);
            TableTrabajador.setCellSelectionEnabled(false);
            TableTrabajador.setAutoCreateRowSorter(true);
            TableTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableTrabajador.setRowSelectionAllowed(true);
            TableTrabajador.setDefaultEditor(Object.class, null);
            TableTrabajador.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableTrabajador.getModel());

            TableTrabajador.setRowSorter(sorter);

            //Filling Headers
            modelTrabajador = new DefaultTableModel(headers, 0);

            TableTrabajador.setModel(modelTrabajador);
        } else {

            TableTrabajador.setShowGrid(true);
            TableTrabajador.setCellSelectionEnabled(false);
            TableTrabajador.setAutoCreateRowSorter(true);
            TableTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            TableTrabajador.setRowSelectionAllowed(true);
            TableTrabajador.setDefaultEditor(Object.class, null);
            TableTrabajador.setDragEnabled(false);
            sorter = new TableRowSorter<TableModel>(TableTrabajador.getModel());

            TableTrabajador.setRowSorter(sorter);

            //Filling Headers
            modelTrabajador = new DefaultTableModel(headers, 0);

            //Filling Data
            Object[] data = new Object[headers.length];

            for (Trabajador trabajador : trabajadores) {
                data = getTrabajadorObject(trabajador);
                modelTrabajador.addRow(data);
            }

            TableTrabajador.setModel(modelTrabajador);
        }
    }

    private void filter() {
        DefaultTableModel Model = (DefaultTableModel) TableTrabajador.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(Model);
        TableTrabajador.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(filtro.getText().trim()));
    }
    //endregion

    //region Metodos Desde el Formulario

    public boolean getNewTrabajadorFromFormulario(Trabajador trabajador) {
        return controladorTrabajador.createTrabajador(trabajador);
    }

    public boolean getUpdateTrabajadorFromFormulario(Trabajador trabajador) {
        return controladorTrabajador.updateTrabajador(trabajador);
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
    private void createTrabajador() {
        FormTrabajador formTrabajador = new FormTrabajador(this);
    }

    private void readTrabajador() {

        int row = TableTrabajador.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un trabajador de la tabla.");

        } else {

            Trabajador trabajador = getTrabajador();

            FormTrabajador formTrabajador = new FormTrabajador(this, trabajador, false);
        }
    }

    private void updateTrabajador() {

        int row = TableTrabajador.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un trabajador de la tabla.");

        } else {

            Trabajador trabajador = getTrabajador();
            FormTrabajador formTrabajador = new FormTrabajador(this, trabajador);
        }
    }

    private void deleteTrabajador() {

        int row = TableTrabajador.getSelectedRow();
        if (row == -1) {
            ShowErrorMessage("Error", "Error, selecciona un trabajador de la tabla.");

        } else {

            Trabajador trabajador = getTrabajador();

            boolean result = controladorTrabajador.deleteTrabajador(trabajador);

            if (result) {
                trabajadores.remove(row);
                refreshTable(headers, trabajadores);
                ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " ha sido borrado");

            } else {
                ShowErrorMessage("ERROR", "Trabajador " + trabajador.getNombre() + " no se ha podido borrar");
            }
        }
    }


    //endregion

    //region Metodos privados
    public void updateTableTrabajador(Trabajador trabajador) {

        int row = TableTrabajador.getSelectedRow();

        trabajadores.get(row).setDNI(trabajador.getDNI());
        trabajadores.get(row).setNombre(trabajador.getNombre());
        trabajadores.get(row).setApellidos(trabajador.getApellidos());
        trabajadores.get(row).setFnac(trabajador.getFnac());
        trabajadores.get(row).setPuesto(trabajador.getPuesto());
        trabajadores.get(row).setSalario(trabajador.getSalario());
        trabajadores.get(row).setNacionalidad(trabajador.getNacionalidad());

        refreshTable(headers, trabajadores);

    }

    public void addTableTrabajador(Trabajador trabajador) {

        Object[] newTrabajador = getTrabajadorObject(trabajador);
        modelTrabajador.addRow(newTrabajador);
        trabajadores.add(trabajador);


    }

    public Object[] getTrabajadorObject(Trabajador trabajador) {
        int y = 0;
        Object[] newTrabajador = new Object[headers.length];

        newTrabajador[y++] = trabajador.getDNI();
        newTrabajador[y++] = trabajador.getNombre();
        newTrabajador[y++] = trabajador.getApellidos();
        newTrabajador[y++] = trabajador.getTelefono();
        newTrabajador[y++] = trabajador.getFnac();
        newTrabajador[y++] = trabajador.getNacionalidad();
        newTrabajador[y++] = trabajador.getPuesto();
        newTrabajador[y++] = trabajador.getSalario();


        return newTrabajador;
    }


    private Trabajador getTrabajador() {
        int row = TableTrabajador.getSelectedRow();

        return trabajadores.get(row);

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
                createTrabajador();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readTrabajador();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrabajador();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTrabajador();
            }
        });

        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(headers, trabajadores);
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
        TableTrabajador.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JLabelVacaciones.setVisible(false);
                    JPanelVacaciones.setVisible(false);
                }

                if (e.getClickCount() == 2) {
                    updateTrabajador();
                }
            }
        });

        buttonVacaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!viendoVacaciones) {
                    int row = TableTrabajador.getSelectedRow();
                    if (row == -1) {
                        ShowErrorMessage("Error", "Selecciona un trabajador de la tabla para conocer sus vacaciones.");
                    } else {
                        viendoVacaciones = true;
                        setGraficos(getTrabajador());
                    }
                }else{
                    viendoVacaciones = false;
                    JLabelVacaciones.setVisible(false);
                    JPanelVacaciones.setVisible(false);
                }
            }
        });
    }

    //endregion


    //region Graficos
    private ArrayList<Vacaciones> getVaciones(){
        CRUDVacaciones crudVacaciones = new CRUDVacaciones();
        listVacaciones = crudVacaciones.getAll();
        return listVacaciones;
    }

    private int getDiasVacacionesDisfrutados(Trabajador trabajador, ArrayList<Vacaciones> listVacaciones){
        int numAlbaranes = 0;

        for(Vacaciones vacaciones : listVacaciones){
            if(vacaciones.getIdTrabajador() ==  trabajador.getId()){
                if(estaDeVacaciones(vacaciones) > 0){
                    vacacionesParaTrabajador = vacaciones;
                    return Math.toIntExact(estaDeVacaciones(vacaciones));
                }
            }
        }
        return numAlbaranes;
    }

    private long estaDeVacaciones(Vacaciones vacaciones) {
        Date today = new Date(System.currentTimeMillis());

        long dias = diasEntreDosFechas(vacaciones.getFecha_aprobada_inicio(), today);

        return (dias > 1) ? dias:0;
    }

    public long diasEntreDosFechas(Date fechaDesde, Date fechaHasta){
        long startTime = fechaDesde.getTime() ;
        long endTime = fechaHasta.getTime();
        long diasDesde = (long) Math.floor(startTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long diasHasta = (long) Math.floor(endTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long dias = diasHasta - diasDesde;

        return dias;
    }


    private void setGraficos(Trabajador trabajador) {
        listVacaciones = getVaciones();

        int numVacacionesDisrutadas = getDiasVacacionesDisfrutados(trabajador, listVacaciones);
        if(numVacacionesDisrutadas > 0) {

            JPanelVacaciones.removeAll();
            JLabelVacaciones.setVisible(true);
            JPanelVacaciones.setVisible(true);
            new Thread(new Runnable() {
                @Override
                public void run() {


                    ArrayList<NodoGraficoCircular> listNodoCircular = new ArrayList<>();

                    NodoGraficoCircular nodoGraficoCircular1 = new NodoGraficoCircular();
                    nodoGraficoCircular1.setComparableKey(trabajador.getNombre());

                    nodoGraficoCircular1.setValue(Double.valueOf(numVacacionesDisrutadas));

                    listNodoCircular.add(nodoGraficoCircular1);

                    NodoGraficoCircular nodoGraficoCircular2 = new NodoGraficoCircular();
                    nodoGraficoCircular2.setComparableKey("Total");
                    nodoGraficoCircular2.setValue(Double.valueOf(diasEntreDosFechas(vacacionesParaTrabajador.getFecha_aprobada_inicio(), vacacionesParaTrabajador.getFecha_aprobada_fin())));
                    listNodoCircular.add(nodoGraficoCircular2);

                    GraficosBasicos graficosBasicos = new GraficosBasicos();

                    JPanelVacaciones.add(graficosBasicos.metodoGraficoCircular(listNodoCircular, "Vacaciones Disfrutadas"));

                    repaint();
                    revalidate();
                }
            }).start();
        }else ShowErrorMessage("Error", "Este trabajador no cuenta con vacaciones.");
    }

    //endregion



    //region Variables
    private JPanel panelPrincipal;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JPanel PanelMaterial;
    private JTable TableTrabajador;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JPanel panelBotones;
    private JButton buttonVolver;
    private JButton buttonRecargar;
    private JLabel labelTitulo;
    private JLabel JLabelVacaciones;
    private JPanel JPanelVacaciones;
    private JButton buttonVacaciones;
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Vacaciones> listVacaciones;
    private ControladorTrabajador controladorTrabajador;
    private String[] headers;

    private boolean viendoVacaciones;

    private Vacaciones vacacionesParaTrabajador = new Vacaciones();

    private DefaultTableModel modelTrabajador;
    private TableRowSorter sorter;


    //endregion
}
