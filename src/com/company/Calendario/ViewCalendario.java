package com.company.Calendario;

import com.company.BaseDatos.CRUDTrabajador;
import com.company.Controlador.ControladorTrabajador;
import com.company.Entidades.Trabajador;
import com.company.Recursos.RoundedBorder;
import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.ItemEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ViewCalendario extends JFrame{
    private JPanel panelPrincipal;
    private JPanel panelCalendario;
    private JPanel panelTrabajador;
    private JComboBox comboBoxTrabajador;
    private JTable tableVacsTrabajador;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JPanel panelBotones;
    private JLabel foto;
    private JSeparator separador;
    private String dateInit, dateEnd;
    private static int contDateRange = 0;

    private DefaultTableModel modelTrabajador;

    private ArrayList<Trabajador> trabajadores;

    public ViewCalendario(){
        trabajadores = getTrabajadores();
        initComps();
        initWindow();
        setVisible(true);
        add(panelPrincipal);
    }

    private void initWindow(){
        setSize(600, 800);
        setLocationRelativeTo(null);
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setLayout(new GridLayout());
    }

    private void setPanelBase() {
        panelPrincipal = new JPanel();
        getContentPane().add(panelPrincipal);

        panelCalendario = new JPanel();
        panelPrincipal.add(panelCalendario);

        panelTrabajador = new JPanel();
        panelPrincipal.add(panelTrabajador);
    }

    private void initComps(){
        aceptarButton.setBorder(new RoundedBorder(10));
        cancelarButton.setBorder(new RoundedBorder(10));
        panelTrabajador.setBorder(new RoundedBorder(10));

        setCalendarioPro();

        initTrabajadores();
        
        initTable();

        initBotones();
    }

    private void initTable() {
        tableVacsTrabajador.setShowGrid(true);
        tableVacsTrabajador.setCellSelectionEnabled(false);
        tableVacsTrabajador.setAutoCreateRowSorter(true);
        tableVacsTrabajador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableVacsTrabajador.setRowSelectionAllowed(true);
        tableVacsTrabajador.setDefaultEditor(Object.class, null);
        tableVacsTrabajador.setDragEnabled(false);

        //Filling Headers
        String[] headers = {"DNI", "TRABAJADOR", "FECHA INICIO", "FECHA FIN"};
        modelTrabajador = new DefaultTableModel(headers, 0);

        tableVacsTrabajador.setModel(modelTrabajador);
    }

    private void initBotones(){
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var listVacaciones = getAllRows();
                if (listVacaciones.size() == 0) return;
                ControladorTrabajador controladorTrabajador = new ControladorTrabajador();
                controladorTrabajador.getViewTrabajador().dispose();
                controladorTrabajador.setlistVacaciones(listVacaciones);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarRowSelected();
            }
        });
    }

    private void initTrabajadores() {
        //Rellenar Trabajadores
        trabajadores = new CRUDTrabajador().getAll();
        comboBoxTrabajador.addItem("Selecciona Trabajador");
        for (Trabajador trabajador : trabajadores) {
            comboBoxTrabajador.addItem(trabajador.getDNI() + " - " + trabajador.getNombre());
        }
        comboBoxTrabajador.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                contDateRange = 0;
            }
        });
    }

    private void setCalendarioPro() {
        Calendar calendar = new Calendar();
        calendar.beginInit();
        calendar.setTheme(ThemeType.Standard);
        calendar.setEnableDragCreate(true);
        calendar.endInit();

        calendar.addCalendarListener(new CalendarAdapter(){
            @Override
            public void dateClick(ResourceDateEvent resourceDateEvent) {
                if (comboBoxTrabajador.getSelectedIndex() == 0) return;
                contDateRange++;
                if (contDateRange == 1){
                    String date =resourceDateEvent.getDate().getDay()
                            + "/" + resourceDateEvent.getDate().getMonth()
                            + "/" + resourceDateEvent.getDate().getYear();
                    dateInit = date;
                }
                if (contDateRange == 2){
                    String date =resourceDateEvent.getDate().getDay()
                            + "/" + resourceDateEvent.getDate().getMonth()
                            + "/" + resourceDateEvent.getDate().getYear();
                    dateEnd = date;
                    getRowVacaciones();
                    contDateRange = 0;
                }
            }
        });

        calendar.addCalendarListener(new CalendarAdapter(){
            @Override
            public void itemCreated(ItemEvent itemEvent) {
                DateTime dateAux =itemEvent.getItem().getEndTime().addDays(-1);
                String date =dateAux.getDay()
                        + "/" + dateAux.getMonth()
                        + "/" + dateAux.getYear();
                dateEnd = date;
                getRowVacaciones();
            }
        });
        panelCalendario.add(calendar);
    }

    private void getRowVacaciones(){
        if (comboBoxTrabajador.getSelectedIndex() == 0) return;
        if (dateInit == null || dateInit.equals("") || dateEnd == null || dateEnd.equals("")){
            contDateRange = 0;
            return;
        }

        //COMP RANGO FECHA != NEGATIVO
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date fecha1 = formato.parse(dateInit);
            Date fecha2 = formato.parse(dateEnd);

            if (fecha1.getTime() > fecha2.getTime()){
                String dateAux = dateInit;
                dateInit = dateEnd;
                dateEnd= dateAux;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            contDateRange = 0;
            return;
        }


        String row = comboBoxTrabajador.getSelectedItem().toString();
        String dni = row.substring(0, 9);
        String nombre = row.substring(11);
        modelTrabajador.addRow(new Object[]{dni, nombre, dateInit, dateEnd});
        dateInit = "";
        dateEnd = "";
        contDateRange = 0;
    }

    private ArrayList<NodoTrabajadorCalendario> getAllRows(){
        var countRow = modelTrabajador.getRowCount();
        ArrayList<NodoTrabajadorCalendario> listTRabajadorCalendario = new ArrayList<>();

        for (int i = 0; i < countRow; i++){
            NodoTrabajadorCalendario trabajadorCalendario = new NodoTrabajadorCalendario();
            trabajadorCalendario.setDni(modelTrabajador.getValueAt(i, 0).toString());
            trabajadorCalendario.setNombre(modelTrabajador.getValueAt(i, 1).toString());;
            trabajadorCalendario.setFechaInicio(modelTrabajador.getValueAt(i, 2).toString());
            trabajadorCalendario.setFechaFin(modelTrabajador.getValueAt(i, 3).toString());
            listTRabajadorCalendario.add(trabajadorCalendario);
        }
        return listTRabajadorCalendario;
    }

    private void borrarRowSelected(){
        if (tableVacsTrabajador.getSelectedRow() != -1){
            modelTrabajador.removeRow(tableVacsTrabajador.getSelectedRow());
        }
    }

    private void setPanelTrabajadores(){
        panelTrabajador.add(comboBoxTrabajador);
        panelTrabajador.add(tableVacsTrabajador);
        panelTrabajador.add(panelBotones);

        panelPrincipal.add(panelTrabajador);
    }

    private ArrayList<Trabajador> getTrabajadores(){
        ArrayList<Trabajador> listTrabajadores;
        CRUDTrabajador crudTrabajador = new CRUDTrabajador();
        listTrabajadores = crudTrabajador.getAll();
        return listTrabajadores;
    }
}
