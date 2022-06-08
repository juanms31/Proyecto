package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Recursos.CheckDate;
import com.company.Vistas.ViewActuacion;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormActuacion extends JDialog {

    //region Constructores
    public FormActuacion(ViewActuacion viewActuacion,
                         ArrayList<Cliente> clientes,
                         ArrayList<EspecificacionActuacion> especificacionesActuacion) {
        estado = 1;
        this.viewActuacion = viewActuacion;
        this.clientes = clientes;
        this.especificacionesActuacion = especificacionesActuacion;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormActuacion(ViewActuacion viewActuacion, Actuacion actuacion,
                         ArrayList<Cliente> clientes,
                         ArrayList<EspecificacionActuacion> especificacionesActuacion) {
        estado = 2;
        ActuacionSiendoModificada = actuacion;
        this.viewActuacion = viewActuacion;
        this.clientes = clientes;
        this.especificacionesActuacion = especificacionesActuacion;
        initListeners();
        initComps();
        setActuacion(actuacion);
        initWindow();
        setVisible(true);
    }

    public FormActuacion(ViewActuacion viewActuacion, Actuacion actuacion,
                         ArrayList<Cliente> clientes,
                         ArrayList<EspecificacionActuacion> especificacionesActuacion,
                         boolean editable) {
        this.viewActuacion = viewActuacion;
        this.clientes = clientes;
        this.especificacionesActuacion = especificacionesActuacion;
        initComps();
        setActuacion(actuacion);
        initWindow();
        initListeners();
        //TODO ver como tratamos editable
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
        setTitle("Actuaciones");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height - 100);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {
        try {
            formattedTextFieldCIFCliente.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("########U")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MaskFormatter formatterFecha = null;
        try {
            formatterFecha = new MaskFormatter("##-##-####");

            formattedTextFieldFechaSolicitud.setFormatterFactory(new DefaultFormatterFactory(formatterFecha));
            formattedTextFieldFechaEnvio.setFormatterFactory(new DefaultFormatterFactory(formatterFecha));
            formattedTextFieldFechaComienzo.setFormatterFactory(new DefaultFormatterFactory(formatterFecha));
            formattedTextFieldFechaFinalizacion.setFormatterFactory(new DefaultFormatterFactory(formatterFecha));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Rellenando Cliente
        comboBoxCliente.addItem("Selecciona Cliente");
        for (Cliente cliente : clientes) {
            comboBoxCliente.addItem(cliente.getCIF() + " - " + cliente.getNombre());
        }

        //Rellando Especificacion
        comboBoxEspecificacion.addItem("Selecciona Especificacion");
        for (EspecificacionActuacion especificacionActuacion : especificacionesActuacion) {
            comboBoxEspecificacion.addItem(especificacionActuacion.getSiglasEspecificacion());
        }

        //Rellenando Estado
        comboBoxEstado.addItem("Selecciona Estado");
        comboBoxEstado.addItem("Pendiente");
        comboBoxEstado.addItem("Concedida");
        comboBoxEstado.addItem("Rechazada");
        comboBoxEstado.addItem("Cancelada");

        //Inicializamos a 0 los diferentes campos numericos
        textFieldHorasOfertadas.setText("0");
        textFieldHorasEjecutadas.setText("0");


    }

    //endregion

    //region Metodos privados

    private void loadNewActuacion() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {

            Actuacion actuacion = getActuacion();
            if (viewActuacion.getNewActuacionFromFormulario(actuacion)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido crear la actuacion correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateActuacion() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {
            Actuacion actuacion = getActuacion();
            if (viewActuacion.getUpdateActuacionFromFormulario(actuacion)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido crear la actuacion correctamente. Intentelo de nuevo más tarde.");
            }
        }
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setActuacion(Actuacion actuacion) {
        textFieldNombreActuacion.setText(actuacion.getNombre());

        comboBoxCliente.setSelectedItem(actuacion.getCliente().getCIF() + " - " + actuacion.getCliente().getNombre());
        formattedTextFieldCIFCliente.setText(actuacion.getCliente().getCIF());

        comboBoxEspecificacion.setSelectedItem(actuacion.getEspecificacion());
        comboBoxEstado.setSelectedItem(actuacion.getEstado());

        //Procesamos Fechas
        setProcesarFechas(actuacion);

        spinnerGastoMaterial.setValue(actuacion.getGastoMaterial());
        spinnerImporte.setValue(actuacion.getImporte());
        textFieldHojaPlanificacion.setText(actuacion.getHojaPlanificacion());
        textFieldHojaPresupuesto.setText(actuacion.getHojaPresupuesto());
        spinnerTotalCertificacion.setValue(actuacion.getTotalCertificicaciones());
        spinnerPorCertificar.setValue(actuacion.getPorPertificar());

        textFieldHorasOfertadas.setText(String.valueOf(actuacion.getHorasOfertadas()));
        textFieldHorasEjecutadas.setText(String.valueOf(actuacion.getHorasEjecutadas()));

        spinnerResultadoBalance.setValue(actuacion.getResultadoBalance());
        textAreaDescripcion.setText(actuacion.getDescripcion());

    }

    private boolean checkFields() {
        if (comboBoxCliente.getSelectedItem().equals("Selecciona Cliente")) {
            ShowErrorMessage("Error", "Campo Cliente no puede estar vacio");
            return true;
        }
        if (comboBoxEspecificacion.getSelectedItem().equals("Selecciona Especificacion")) {
            ShowErrorMessage("Error", "Campo Especificacion no puede estar vacio");
            return true;
        }
        if (comboBoxEstado.getSelectedItem().equals("Selecciona Estado")) {
            ShowErrorMessage("Error", "Campo Estado no puede estar vacio");
            return true;
        }
        if (formattedTextFieldFechaSolicitud.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "Campo Fecha Solicitud no puede estar vacio");
            return true;
        }

        return validarFechas();

    }

    private boolean validarFechas() {
        CheckDate checkDate = new CheckDate();

        if (formattedTextFieldFechaSolicitud.getText().equals("  -  -    ")) {

        }else if (!checkDate.isValidDate(formattedTextFieldFechaSolicitud.getText())) {
            ShowErrorMessage("Error", "La fecha de solicitud no es valida");
            return true;
        }

        if (formattedTextFieldFechaEnvio.getText().equals("  -  -    ")) {

        }else if (!checkDate.isValidDate(formattedTextFieldFechaEnvio.getText())) {
            ShowErrorMessage("Error", "La fecha de envio no es valida");
            return true;
        }

        if (formattedTextFieldFechaComienzo.getText().equals("  -  -    ")) {

        }else if (!checkDate.isValidDate(formattedTextFieldFechaComienzo.getText())) {
            ShowErrorMessage("Error", "La fecha de comienzo no es valida");
            return true;
        }

        if (formattedTextFieldFechaFinalizacion.getText().equals("  -  -    ")) {

        }else if (!checkDate.isValidDate(formattedTextFieldFechaFinalizacion.getText())) {
            ShowErrorMessage("Error", "La fecha de comienzo no es valida");
            return true;
        }

        return false;
    }

    private Actuacion getActuacion() {
        Actuacion actuacion = new Actuacion();


        boolean conErrores = checkFields();

        if (conErrores) {


        } else {

            if (estado == 2) {
                actuacion.setId(ActuacionSiendoModificada.getId());

                actuacion.setNombre(textFieldNombreActuacion.getText());

                actuacion.setCliente(clientes.get(comboBoxCliente.getSelectedIndex() - 1));
                actuacion.setEspecificacion(comboBoxEspecificacion.getSelectedItem().toString());
                actuacion.setEstado(comboBoxEstado.getSelectedItem().toString());

                actuacion = getProcesarFechas(actuacion);

                actuacion.setGastoMaterial(Double.valueOf((String.valueOf(spinnerGastoMaterial.getValue()))));
                actuacion.setMaterialOfertado(Double.valueOf((String.valueOf(spinnerMaterialOfertado.getValue()))));
                actuacion.setImporte(Double.valueOf((String.valueOf(spinnerImporte.getValue()))));

                actuacion.setHojaPlanificacion(textFieldHojaPlanificacion.getText());
                actuacion.setHojaPresupuesto(textFieldHojaPresupuesto.getText());

                actuacion.setTotalCertificicaciones(Double.valueOf((String.valueOf(spinnerTotalCertificacion.getValue()))));
                actuacion.setPorPertificar(Double.valueOf((String.valueOf(spinnerPorCertificar.getValue()))));

                actuacion.setHorasEjecutadas(Integer.parseInt(textFieldHorasEjecutadas.getText()));
                actuacion.setHorasOfertadas(Integer.parseInt(textFieldHorasOfertadas.getText()));

                actuacion.setDescripcion(textAreaDescripcion.getText());

                actuacion.setIdCliente(clientes.get(comboBoxCliente.getSelectedIndex() - 1).getId());

            } else {

                actuacion.setNombre(textFieldNombreActuacion.getText());

                actuacion.setCliente(clientes.get(comboBoxCliente.getSelectedIndex() - 1));
                actuacion.setEspecificacion(comboBoxEspecificacion.getSelectedItem().toString());
                actuacion.setEstado(comboBoxEstado.getSelectedItem().toString());

                //Procesamos Fecha

                actuacion = getProcesarFechas(actuacion);

                actuacion.setGastoMaterial(Double.parseDouble(String.valueOf(spinnerGastoMaterial.getValue())));
                actuacion.setMaterialOfertado(Double.valueOf((String.valueOf(spinnerMaterialOfertado.getValue()))));

                actuacion.setImporte(Double.parseDouble(String.valueOf(spinnerImporte.getValue())));

                actuacion.setHojaPlanificacion(textFieldHojaPlanificacion.getText());
                actuacion.setHojaPresupuesto(textFieldHojaPresupuesto.getText());

                actuacion.setTotalCertificicaciones(Double.parseDouble(String.valueOf(spinnerTotalCertificacion.getValue())));
                actuacion.setPorPertificar(Double.parseDouble(String.valueOf(spinnerPorCertificar.getValue())));

                actuacion.setHorasEjecutadas(Integer.parseInt(textFieldHorasEjecutadas.getText()));
                actuacion.setHorasOfertadas(Integer.parseInt(textFieldHorasOfertadas.getText()));

                actuacion.setDescripcion(textAreaDescripcion.getText());

                actuacion.setIdCliente(clientes.get(comboBoxCliente.getSelectedIndex() - 1).getId());


            }
        }

        return actuacion;
    }

    private Actuacion getProcesarFechas(Actuacion actuacion) {

        // FIXME: 30/05/2022 COMPROBAR QUE LAS FECHAS SEAN CORRECTAS (NO SIRVE CON EL CATCH)
        //Procesamos Fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date UTILDate;
        java.sql.Date SQLDate;

        if (formattedTextFieldFechaSolicitud.getText().equals("  -  -    ")) {
            actuacion.setFecha_solicitud(null);
        } else {

            try {
                UTILDate = dateFormat.parse(formattedTextFieldFechaSolicitud.getText());

                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_solicitud(SQLDate);


            } catch (ParseException e) {
                ShowErrorMessage("Error", "La fecha de solicitud no es valida");
            }
        }


        if (formattedTextFieldFechaEnvio.getText().equals("  -  -    ")) {
            actuacion.setFecha_envio(null);
        } else {

            try {
                UTILDate = dateFormat.parse(formattedTextFieldFechaEnvio.getText());

                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_envio(SQLDate);

            } catch (ParseException e) {
                ShowErrorMessage("Error", "La fecha de envio no es valida");
            }
        }

        if (formattedTextFieldFechaComienzo.getText().equals("  -  -    ")) {
            actuacion.setFecha_comienzo(null);
        } else {

            try {

                UTILDate = dateFormat.parse(formattedTextFieldFechaComienzo.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_comienzo(SQLDate);

            } catch (ParseException e) {
                ShowErrorMessage("Error", "La fecha de comienzo no es valida");
            }
        }

        if (formattedTextFieldFechaFinalizacion.getText().equals("  -  -    ")) {
            actuacion.setFecha_finalizacion(null);
        } else {

            try {

                UTILDate = dateFormat.parse(formattedTextFieldFechaFinalizacion.getText());

                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_finalizacion(SQLDate);

            } catch (ParseException e) {
                ShowErrorMessage("Error", "La fecha de comienzo no es valida");
            }
        }

        return actuacion;
    }

    private Actuacion setProcesarFechas(Actuacion actuacion) {

        //Procesamos Fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String UTILDate;
        java.sql.Date SQLDate;

        if (actuacion.getFecha_solicitud() == null) {

        } else {
            SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            UTILDate = OldDateFormat.format(actuacion.getFecha_solicitud());
            formattedTextFieldFechaSolicitud.setText(UTILDate.toString());
        }

        if (actuacion.getFecha_envio() == null) {

        } else {
            SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            UTILDate = OldDateFormat.format(actuacion.getFecha_envio());
            formattedTextFieldFechaEnvio.setText(UTILDate.toString());
        }

        if (actuacion.getFecha_comienzo() == null) {

        } else {
            SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            UTILDate = OldDateFormat.format(actuacion.getFecha_comienzo());
            formattedTextFieldFechaComienzo.setText(UTILDate.toString());
        }

        if (actuacion.getFecha_finalizacion() == null) {

        } else {
            SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            UTILDate = OldDateFormat.format(actuacion.getFecha_finalizacion());
            formattedTextFieldFechaFinalizacion.setText(UTILDate.toString());
        }

        return actuacion;
    }


    //endregion Actuacion

    //region Listeners

    private void initListeners() {
        actionListeners();
        keyListeners();
        itemListeners();
    }

    private void actionListeners() {
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado) {
                    case 0 -> {
                    }
                    case 1 -> {
                        loadNewActuacion();
                    }

                    case 2 -> {
                        loadUpdateActuacion();
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
        formattedTextFieldFechaSolicitud.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        formattedTextFieldFechaEnvio.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        formattedTextFieldFechaComienzo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        formattedTextFieldFechaFinalizacion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldHorasEjecutadas.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldHorasOfertadas.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerResultadoBalance.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });


        spinnerGastoMaterial.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerImporte.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerTotalCertificacion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPorCertificar.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

    }

    public void itemListeners() {
        comboBoxCliente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBoxCliente.getSelectedItem().equals("Selecciona Cliente")) {
                    formattedTextFieldCIFCliente.setText("");
                } else
                    formattedTextFieldCIFCliente.setText(String.valueOf(clientes.get(comboBoxCliente.getSelectedIndex() - 1).getCIF()));

            }
        });


        buttonHojaPlanificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChoseerHojaPlanificacion.setCurrentDirectory(new File("src/com/company"));
                fileChoseerHojaPlanificacion.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("*.pdf", "pdf");
                fileChoseerHojaPlanificacion.setFileFilter(filtroPDF);
                int seleccion = fileChoseerHojaPlanificacion.showOpenDialog(panelPrincipal);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    setHojaPlanificacion();
                }
            }
        });

        buttonHojaPresupuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChoseerHojaPresupuesto.setCurrentDirectory(new File("src/com/company"));
                fileChoseerHojaPresupuesto.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("*.pdf", "pdf");
                fileChoseerHojaPresupuesto.setFileFilter(filtroPDF);
                int seleccion = fileChoseerHojaPresupuesto.showOpenDialog(panelPrincipal);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    setHojaPresupuesto();
                }
            }
        });

    }

    private void setHojaPresupuesto() {
        File ficheroHojaPresupuesto = fileChoseerHojaPresupuesto.getSelectedFile();
        textFieldHojaPresupuesto.setText(ficheroHojaPresupuesto.getName());
    }

    private void setHojaPlanificacion() {
        File ficheroHojaPlanificacion = fileChoseerHojaPlanificacion.getSelectedFile();
        textFieldHojaPlanificacion.setText(ficheroHojaPlanificacion.getName());
    }

    //endregion

    //region Variables
    private int estado = 0;
    private ViewActuacion viewActuacion;
    private Actuacion ActuacionSiendoModificada;
    private ArrayList<Cliente> clientes;
    private ArrayList<EspecificacionActuacion> especificacionesActuacion;
    private JFileChooser fileChoseerHojaPresupuesto = new JFileChooser();
    private JFileChooser fileChoseerHojaPlanificacion = new JFileChooser();


    private JPanel panelFecha;
    private JPanel panelPrincipal;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxCliente;
    private JComboBox comboBoxEspecificacion;
    private JComboBox comboBoxEstado;
    private JTextArea textAreaDescripcion;
    private JSpinner spinnerImporte;
    private JTextField textFieldHojaPlanificacion;
    private JButton buttonHojaPlanificacion;
    private JButton buttonHojaPresupuesto;
    private JFormattedTextField formattedTextFieldFechaSolicitud;
    private JFormattedTextField formattedTextFieldFechaEnvio;
    private JFormattedTextField formattedTextFieldFechaComienzo;
    private JFormattedTextField formattedTextFieldFechaFinalizacion;
    private JSpinner spinnerTotalCertificacion;
    private JSpinner spinnerPorCertificar;
    private JTextField textFieldHorasOfertadas;
    private JTextField textFieldHorasEjecutadas;
    private JSpinner spinnerGastoMaterial;
    private JTextField textFieldResultadoBalance;
    private JTextField textFieldHojaPresupuesto;
    private JFormattedTextField formattedTextFieldCIFCliente;
    private JSpinner spinnerResultadoBalance;
    private JTextField textFieldNombreActuacion;
    private JList listaMateriales;
    private JButton buttonAnadir;
    private JSpinner spinnerMaterialOfertado;

    //endregion
}
