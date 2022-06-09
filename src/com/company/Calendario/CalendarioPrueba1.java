package com.company.Calendario;

import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.ThemeType;

import javax.swing.*;
import java.awt.*;

public class CalendarioPrueba1 extends JFrame{
    private JPanel panel1;
    private JPanel panelCalendario;
    private JPanel panelTrabajador;
    private JComboBox cbTrabajadores;
    private JTable tableVacsTrabajador;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public CalendarioPrueba1(){
        setPanelBase();
        setCalendarioPro();
        setCalendarioTrabajadores();
        setPreferredSize(new Dimension(400, 600));
        setVisible(true);
    }

    private void initWindow(){
        setSize(600, 800);
        setLocationRelativeTo(null);
    }

    private void setPanelBase() {
        panel1 = new JPanel();
        getContentPane().add(panel1);
        panelCalendario = new JPanel();
        panel1.add(panelCalendario);
    }

    private void setCalendarioPro() {
        Calendar calendar = new Calendar();
        calendar.setTheme(ThemeType.Windows2003);
        panelCalendario.add(calendar);
    }

    private void setCalendarioTrabajadores(){
        panelTrabajador = new JPanel();
        panelTrabajador.add(cbTrabajadores);
        panel1.add(panelTrabajador);
    }

    public static void main(String[] args) {
        CalendarioPrueba1 calendarioPrueba1 = new CalendarioPrueba1();
    }

}
