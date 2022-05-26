package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Vistas.ViewActuacion;
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

public class FormActuacion extends JDialog{

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
        setSize(screen.height / 2, screen.width / 2);
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
        for(Cliente cliente : clientes){
            comboBoxCliente.addItem(cliente.getNombre());
        }

        //Rellando Especificacion
        comboBoxEspecificacion.addItem("Selecciona Especificacion");
        for (EspecificacionActuacion especificacionActuacion : especificacionesActuacion){
            comboBoxEspecificacion.addItem(especificacionActuacion.getSiglasEspecificacion());
        }

        //Rellenando Estado
        comboBoxEstado.addItem("Selecciona Estado");
        comboBoxEstado.addItem("Pendiente");
        comboBoxEstado.addItem("Concedida");
        comboBoxEstado.addItem("Rechazada");
        comboBoxEstado.addItem("Cancelada");

    }

    //endregion

    //region Metodos privados

    private void loadNewActuacion() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            Actuacion actuacion = getActuacion();
            if(viewActuacion.getNewActuacionFromFormulario(actuacion)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear la actuacion correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateActuacion() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            Actuacion actuacion = getActuacion();
            if (viewActuacion.getUpdateActuacionFromFormulario(actuacion)){
                dispose();
            }else {
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
        comboBoxCliente.setSelectedItem(actuacion.getCliente().getNombre());
        formattedTextFieldCIFCliente.setText(actuacion.getCliente().getCIF());
        comboBoxEspecificacion.setSelectedItem(actuacion.getEspecificacion());
        comboBoxEstado.setSelectedItem(actuacion.getEstado());

       //Procesamos Fechas
        SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String UTILDate = OldDateFormat.format(actuacion.getFecha_solicitud());
        formattedTextFieldFechaSolicitud.setText(UTILDate.toString());

        UTILDate = OldDateFormat.format(actuacion.getFecha_envio());
        formattedTextFieldFechaEnvio.setText(UTILDate.toString());

        UTILDate = OldDateFormat.format(actuacion.getFecha_comienzo());
        formattedTextFieldFechaComienzo.setText(UTILDate.toString());

        UTILDate = OldDateFormat.format(actuacion.getFecha_finalizacion());
        formattedTextFieldFechaFinalizacion.setText(UTILDate.toString());

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
        if (formattedTextFieldFechaSolicitud.getText().equals(" - - ")) {
            ShowErrorMessage("Error", "Campo Fecha Solicitud no puede estar vacio");
            return true;
        }

        return false;
    }

    private Actuacion getActuacion() {
        Actuacion actuacion = new Actuacion();


        boolean conErrores = checkFields();

        if (estado == 2) {
            actuacion.setId(ActuacionSiendoModificada.getId());

            actuacion.setCliente(clientes.get(comboBoxCliente.getSelectedIndex()-1));
            actuacion.setEspecificacion(comboBoxEspecificacion.getSelectedItem().toString());
            actuacion.setEstado(comboBoxEstado.getSelectedItem().toString());

            //Procesamos Fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                java.util.Date UTILDate = dateFormat.parse(formattedTextFieldFechaSolicitud.getText());
                java.sql.Date SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_solicitud(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaEnvio.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_envio(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaComienzo.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_comienzo(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaFinalizacion.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_envio(SQLDate);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            actuacion.setGastoMaterial((Double) spinnerGastoMaterial.getValue());
            actuacion.setImporte((Double) spinnerImporte.getValue());

            actuacion.setHojaPlanificacion(textFieldHojaPlanificacion.getText());
            actuacion.setHojaPresupuesto(textFieldHojaPresupuesto.getText());

            actuacion.setTotalCertificicaciones((Double) spinnerTotalCertificacion.getValue());
            actuacion.setPorPertificar((Double) spinnerPorCertificar.getValue());

            actuacion.setHorasEjecutadas(Integer.parseInt(textFieldHorasEjecutadas.getText()));
            actuacion.setHorasOfertadas(Integer.parseInt(textFieldHorasOfertadas.getText()));

            actuacion.setDescripcion(textAreaDescripcion.getText());

        } else {

            actuacion.setCliente(clientes.get(comboBoxCliente.getSelectedIndex()-1));
            actuacion.setEspecificacion(comboBoxEspecificacion.getSelectedItem().toString());
            actuacion.setEstado(comboBoxEstado.getSelectedItem().toString());

            //Procesamos Fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                java.util.Date UTILDate = dateFormat.parse(formattedTextFieldFechaSolicitud.getText());
                java.sql.Date SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_solicitud(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaEnvio.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_envio(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaComienzo.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_comienzo(SQLDate);

                UTILDate = dateFormat.parse(formattedTextFieldFechaFinalizacion.getText());
                SQLDate = new Date(UTILDate.getTime());
                actuacion.setFecha_envio(SQLDate);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            actuacion.setGastoMaterial((Double) spinnerGastoMaterial.getValue());
            actuacion.setImporte((Double) spinnerImporte.getValue());

            actuacion.setHojaPlanificacion(textFieldHojaPlanificacion.getText());
            actuacion.setHojaPresupuesto(textFieldHojaPresupuesto.getText());

            actuacion.setTotalCertificicaciones((Double) spinnerTotalCertificacion.getValue());
            actuacion.setPorPertificar((Double) spinnerPorCertificar.getValue());

            actuacion.setHorasEjecutadas(Integer.parseInt(textFieldHorasEjecutadas.getText()));
            actuacion.setHorasOfertadas(Integer.parseInt(textFieldHorasOfertadas.getText()));

            actuacion.setDescripcion(textAreaDescripcion.getText());


        }

        return actuacion;
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

    //endregion

    //region Variables
    private int estado = 0;
    private ViewActuacion viewActuacion;
    private Actuacion ActuacionSiendoModificada;
    ArrayList<Cliente> clientes;
    ArrayList<EspecificacionActuacion> especificacionesActuacion;

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
    private JButton button1;
    private JButton button2;
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
    //endregion
}
