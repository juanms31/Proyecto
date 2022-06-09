package com.company.Calendario;

import com.company.BaseDatos.CRUDTrabajador;
import com.company.Entidades.Trabajador;
import com.company.Recursos.RoundedBorder;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.ThemeType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class CalendarioPrueba1 extends JFrame{
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

    private DefaultTableModel modelTrabajador;

    private ArrayList<Trabajador> trabajadores;

    public CalendarioPrueba1(){
        trabajadores = getTrabajadores();

//        setPanelBase();
//        setCalendarioPro();
//        setPanelTrabajadores();

        initComps();
        initWindow();
        setVisible(true);
        add(panelPrincipal);
    }

    private void initWindow(){
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

    private void initTrabajadores() {
        //Rellenar Trabajadores
        comboBoxTrabajador.addItem("Selecciona Trabajador");
        for (Trabajador trabajador : trabajadores) {
            comboBoxTrabajador.addItem(trabajador.getId() + " - " + trabajador.getNombre());
        }
    }
    private void setCalendarioPro() {
        Calendar calendar = new Calendar();
        calendar.setTheme(ThemeType.Standard);
        panelCalendario.add(calendar);
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

    public static void main(String[] args) {
        CalendarioPrueba1 calendarioPrueba1 = new CalendarioPrueba1();
    }

}
