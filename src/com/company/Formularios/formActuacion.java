package com.company.Formularios;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class formActuacion extends JFrame{
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextArea textArea1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JSpinner spinner4;
    private JSpinner spinner5;
    private JPanel panelFecha;
    private JPanel panelPrincipal;

    public formActuacion() throws HeadlessException {


        createUIComponents();
        add(panelPrincipal);
        setVisible(true);
        setSize(600,600);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        panelFecha = new JPanel();
        panelFecha.add(dateChooser);

    }
}
