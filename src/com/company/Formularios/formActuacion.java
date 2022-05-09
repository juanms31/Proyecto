package com.company.Formularios;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class formActuacion extends JFrame{
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxCliente;
    private JComboBox comboBoxEspecificacion;
    private JComboBox comboBoxEstado;
    private JTextArea textAreaDescripcion;
    private JSpinner spinnerImporte;
    private JSpinner spinnerTotalCertificacion;
    private JSpinner spinnerPorCertificar;
    private JTextField textFieldHorasOfertadas;
    private JTextField textFieldHorasEjecutadas;
    private JTextField textFieldMaterialOfertado;
    private JSpinner spinnerGastoMaterial;
    private JSpinner spinnerResultadoBalance;
    private JPanel panelFecha;
    private JPanel panelPrincipal;
    private JFormattedTextField formattedTextFieldFechaSolicitud;
    private JFormattedTextField formattedTextFieldFechaEnvio;
    private JFormattedTextField formattedTextFieldFechaComienzo;
    private JFormattedTextField formattedTextFieldFechaFinalizacion;

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
