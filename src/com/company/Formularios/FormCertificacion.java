package com.company.Formularios;


import com.company.Entidades.Actuacion;
import com.company.Entidades.Certificacion;
import com.company.Entidades.Proveedor;
import com.company.Recursos.CheckDate;
import com.company.Vistas.ViewCertificacion;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormCertificacion extends JDialog {

    //region Constructores
    public FormCertificacion(ViewCertificacion viewCertificacion, ArrayList<Actuacion> actuaciones) {
        estado = 1;
        this.viewCertificacion = viewCertificacion;
        this.actuaciones = actuaciones;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormCertificacion(ViewCertificacion viewCertificacion, ArrayList<Actuacion> actuaciones, Certificacion certificacion) {
        estado = 2;
        CertificacionSiendoModificada = certificacion;
        this.viewCertificacion = viewCertificacion;
        this.actuaciones = actuaciones;

        initListeners();
        initComps();
        setCertificacion(certificacion);
        initWindow();
        setVisible(true);
    }

    public FormCertificacion(ViewCertificacion viewCertificacion, ArrayList<Actuacion> actuaciones, Certificacion certificacion, boolean editable) {
        this.viewCertificacion = viewCertificacion;
        this.actuaciones = actuaciones;
        CertificacionSiendoModificada = certificacion;
        initComps();
        setCertificacion(certificacion);
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
        centerFrame();
        setModal(true);
        setResizable(false);
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Proveedores");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

        //Rellenar Actuaciones
        comboBoxActuacion.addItem("Selecciona Actuacion");
        for (Actuacion actuacion : actuaciones) {
            comboBoxActuacion.addItem(actuacion.getId() + " - " + actuacion.getNombre());
        }

        try {
            formattedTextFieldFechaCertificacion.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##-##-####")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    //region Metodos privados

    private void loadNewCertificacion() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {

            Certificacion certificacion = getCertificacion();
            if (viewCertificacion.getNewCertificacionFromFormulario(certificacion)) {
                dispose();
                ShowMessage( "CORRECTO", "Certificacion agregada con exito");
            } else {
                ShowErrorMessage("Error", "No se ha podido crear la certificacion correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateCertificacion() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {
            Certificacion certificacion = getCertificacion();
            if (viewCertificacion.getUpdateCertificacionFromFormulario(certificacion)) {
                dispose();
                ShowMessage( "CORRECTO", "Certificacion actualizada con exito");

            } else {
                ShowErrorMessage("Error", "No se ha podido crear la actuacion correctamente");
            }
        }
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setCertificacion(Certificacion certificacion) {
        comboBoxActuacion.setSelectedItem(certificacion.getActuacion().getId() + " - " + certificacion.getActuacion().getNombre());

        //Procesamos Fecha
        SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String UTILDate = OldDateFormat.format(certificacion.getFecha_certificacion());

        formattedTextFieldFechaCertificacion.setText(UTILDate.toString());

        spinnerValor.setValue(certificacion.getValor());
        textAreaObservaciones.setText(certificacion.getObservaciones());
    }

    private boolean checkFields() {

        if (comboBoxActuacion.getSelectedItem().equals("Selecciona Actuacion")) {
            ShowErrorMessage("Error", "Campo Actuacion no puede estar vacio");
            return true;
        }
        if (spinnerValor.getValue().equals("")) {
            ShowErrorMessage("Error", "Campo Valor no puede estar vacio");
            return true;
        }

        return validarFechas();
    }


    private boolean validarFechas() {
        CheckDate checkDate = new CheckDate();

        if (formattedTextFieldFechaCertificacion.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "Campo Fecha Entrada no puede estar vacio");
            return true;
        } else if (!checkDate.isValidDate(formattedTextFieldFechaCertificacion.getText())) {
            ShowErrorMessage("Error", "La fecha de certificación no es valida");
            return true;
        }

        return false;
    }

    private Certificacion getCertificacion() {
        Certificacion certificacion = new Certificacion();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date UTILDate;
        java.sql.Date SQLDate;

        boolean conErrores = checkFields();

        if (estado == 2) {
            certificacion.setId(CertificacionSiendoModificada.getId());

            certificacion.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1));

            //Procesamos la fecha
            if (formattedTextFieldFechaCertificacion.getText().equals("  -  -    ")) {
                certificacion.setFecha_certificacion(null);
            } else {

                try {
                    UTILDate = dateFormat.parse(formattedTextFieldFechaCertificacion.getText());

                    SQLDate = new Date(UTILDate.getTime());
                    certificacion.setFecha_certificacion(SQLDate);

                } catch (ParseException e) {
                    ShowErrorMessage("Error", "La fecha de certificacion no es valida");
                }
            }

            certificacion.setValor(Double.valueOf(String.valueOf(spinnerValor.getValue())));

            certificacion.setObservaciones(textAreaObservaciones.getText());

        } else {

            certificacion.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1));

            if (formattedTextFieldFechaCertificacion.getText().equals("  -  -    ")) {
                certificacion.setFecha_certificacion(null);
            } else {

                try {
                    UTILDate = dateFormat.parse(formattedTextFieldFechaCertificacion.getText());

                    SQLDate = new Date(UTILDate.getTime());
                    certificacion.setFecha_certificacion(SQLDate);

                } catch (ParseException e) {
                    ShowErrorMessage("Error", "La fecha de certificacion no es valida");
                }
            }

            certificacion.setValor(Double.valueOf(String.valueOf(spinnerValor.getValue())));

            certificacion.setObservaciones(textAreaObservaciones.getText());

        }

        return certificacion;
    }

    //endregion MATERIAL

    //region Listeners

    private void initListeners() {
        actionListeners();
        keyListeners();
    }

    private void actionListeners() {
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado) {
                    case 0 -> {
                    }
                    case 1 -> {
                        loadNewCertificacion();
                    }

                    case 2 -> {
                        loadUpdateCertificacion();
                    }
                }

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void keyListeners() {
        formattedTextFieldFechaCertificacion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '-')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerValor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    //endregion


    //region Variables
    int estado = 0;
    private ArrayList<Actuacion> actuaciones;
    private Certificacion CertificacionSiendoModificada;
    private ViewCertificacion viewCertificacion;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxActuacion;
    private JFormattedTextField formattedTextFieldFechaCertificacion;
    private JTextArea textAreaObservaciones;
    private JPanel panelPrincipal;
    private JSpinner spinnerValor;
    //endregion

}
