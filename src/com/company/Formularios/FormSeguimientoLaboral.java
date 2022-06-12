package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Recursos.CheckDate;
import com.company.Recursos.RoundedBorder;
import com.company.Vistas.ViewSeguimiento;
import com.formdev.flatlaf.FlatDarculaLaf;
import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FormSeguimientoLaboral extends JDialog {

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento,
                                  ArrayList<Trabajador> trabajadores,
                                  ArrayList<SeguimientoLaboral> seguimientosLaborales,
                                  ArrayList<Actuacion> actuaciones) {
        estado = 1;
        this.viewSeguimiento = viewSeguimiento;
        this.trabajadores = trabajadores;
        this.actuaciones = actuaciones;
        this.seguimientosLaborales = seguimientosLaborales;

        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento,
                                  SeguimientoLaboral seguimientoLaboral,
                                  ArrayList<SeguimientoLaboral> seguimientosLaborales,
                                  ArrayList<Trabajador> trabajadores,
                                  ArrayList<Actuacion> actuaciones) {
        estado = 2;
        SeguimientoLaboralSiendoModificado = seguimientoLaboral;
        this.viewSeguimiento = viewSeguimiento;
        this.trabajadores = trabajadores;
        this.actuaciones = actuaciones;
        this.seguimientosLaborales = seguimientosLaborales;

        initListeners();
        initComps();

        setSeguimiento(seguimientoLaboral);
        initWindow();
        setVisible(true);
    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento,
                                  SeguimientoLaboral seguimientoLaboral,
                                  boolean editable,
                                  ArrayList<Trabajador> trabajadores,
                                  ArrayList<Actuacion> actuaciones) {

        this.viewSeguimiento = viewSeguimiento;
        this.trabajadores = trabajadores;
        this.actuaciones = actuaciones;
        initComps();

        setSeguimiento(seguimientoLaboral);
        initWindow();
        initListeners();
        initview(editable);
        setVisible(true);
    }


    //endregion

    //region Metodos Vista

    private void initview(boolean editable) {

        textFieldHorasTotales.setEditable(editable);
        textFieldHorasExtra.setEditable(editable);
    }

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
        setTitle("Seguimiento Laboral");
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

        aceptarButton.setBorder(new RoundedBorder(10));
        cancelarButton.setBorder(new RoundedBorder(10));

        //Rellenar Actuaciones
        comboBoxActuacion.addItem("Selecciona Actuacion");
        for (Actuacion actuacion : actuaciones) {
            comboBoxActuacion.addItem(actuacion.getId() + " - " + actuacion.getNombre());
        }

        //Rellenar Trabajadores
        comboBoxTrabajador.addItem("Selecciona Trabajador");
        for (Trabajador trabajador : trabajadores) {
            comboBoxTrabajador.addItem(trabajador.getId() + " - " + trabajador.getNombre());
        }

        //Rellenar Tipo Seguimiento
        comboBoxTipo.addItem("Selecciona Tipo");
        comboBoxTipo.addItem("Entrada");
        comboBoxTipo.addItem("Salida");

        setHoras();

    }

    private void setHoras() {

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##-##-####");
            formattedTextFieldFecha.setFormatterFactory(new DefaultFormatterFactory(formatter));

            formatter = new MaskFormatter("##:##");
            formattedTextFieldHora.setFormatterFactory(new DefaultFormatterFactory(formatter));

            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedTextFieldFecha.setText(dft.format(LocalDateTime.now()));

            dft = DateTimeFormatter.ofPattern("HH:mm");
            formattedTextFieldHora.setText(dft.format(LocalDateTime.now()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    //endregion

    //region Metodos privados

    private void loadNewSeguimiento() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {

            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if (viewSeguimiento.getNewSeguimientoFromFormulario(seguimientoLaboral)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido crear el seguimiento correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateSeguimiento() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {
            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if (viewSeguimiento.getUpdateSeguimientoFromFormulario(seguimientoLaboral)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido actualizar el seguimiento correctamente");
            }
        }
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

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

    //endregion

    //region SET Y GET MATERIAL
    private void setSeguimiento(SeguimientoLaboral seguimiento) {


        comboBoxActuacion.setSelectedItem(seguimiento.getActuacion().getId() + " - " + seguimiento.getActuacion().getNombre());

        Trabajador trabajador = seguimiento.getTrabajador();
        String trabajadorSeleccionado = trabajador.getId() + " - " + trabajador.getNombre();
        comboBoxTrabajador.setSelectedItem(trabajadorSeleccionado);

        comboBoxTipo.setSelectedItem(seguimiento.getTipo());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String UTILDate = "";
        java.sql.Date SQLDate;

        if (seguimiento.getFechaCompleta() == null) {

        } else {
            formattedTextFieldFecha.setText(seguimiento.getFechaCompleta());
        }

        if (seguimiento.getTipo().equals("Entrada")) {
            formattedTextFieldHora.setText(seguimiento.getHora_entrada());

        } else formattedTextFieldHora.setText(seguimiento.getHora_salida());

        textFieldHorasTotales.setText(String.valueOf(seguimiento.getHoras_totales()));
        textFieldHorasExtra.setText(String.valueOf(seguimiento.getHoras_extra()));
    }

    private boolean checkFields() {

        if (comboBoxActuacion.getSelectedIndex() == 0) {
            ShowErrorMessage("Error", "Debes seleccionar una actuacion");
            return true;
        }

        if (comboBoxTrabajador.getSelectedIndex() == 0) {
            ShowErrorMessage("Error", "Debes seleccionar un trabajador");
            return true;
        }

        if (comboBoxTipo.getSelectedIndex() == 0) {
            ShowErrorMessage("Error", "Debes seleccionar un tipo");
            return true;
        }

        return validarFechas();

    }

    private boolean validarFechas() {
        CheckDate checkDate = new CheckDate();

        if (formattedTextFieldFecha.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "La fecha no puede estar vacia");
            return true;

        } else if (!checkDate.isValidDate(formattedTextFieldFecha.getText())) {
            ShowErrorMessage("Error", "La fecha no es valida");
            return true;
        }

        if (Integer.valueOf(formattedTextFieldHora.getText().substring(0, 2)) > 24) {
            ShowErrorMessage("Error", "La hora no puede ser mayor de las 23:59");
            return true;
        }

        return false;
    }

    private SeguimientoLaboral getSeguimiento() {

        SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();

        if (estado == 1) {


            boolean conErrores = checkFields();

            seguimientoLaboral.setTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex() - 1));
            seguimientoLaboral.setIdTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex() - 1).getId());

            String[] splittedDate = formattedTextFieldFecha.getText().split("-");

            int dayOfMonth = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int year = Integer.parseInt(splittedDate[2]);

            seguimientoLaboral.setAno(year);
            seguimientoLaboral.setDia(dayOfMonth);
            seguimientoLaboral.setMes(month);

            seguimientoLaboral.setFechaCompleta(formattedTextFieldFecha.getText());

            if (comboBoxTipo.getSelectedItem().toString().equals("Entrada")) {
                seguimientoLaboral.setHora_entrada(formattedTextFieldHora.getText());
                seguimientoLaboral.setHora_salida("");

            } else {
                seguimientoLaboral.setHora_salida(formattedTextFieldHora.getText());
                seguimientoLaboral.setHora_entrada("");
            }

            seguimientoLaboral.setTipo(comboBoxTipo.getSelectedItem().toString());

            seguimientoLaboral = updateHoras(seguimientoLaboral, 1);

            seguimientoLaboral.setIdActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1).getId());
            seguimientoLaboral.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1));

        } else if (estado == 2) {

            boolean conErrores = checkFields();

            seguimientoLaboral.setId(SeguimientoLaboralSiendoModificado.getId());

            seguimientoLaboral.setTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex() - 1));
            seguimientoLaboral.setIdTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex() - 1).getId());

            String[] splittedDate = formattedTextFieldFecha.getText().split("-");

            int dayOfMonth = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);
            int year = Integer.parseInt(splittedDate[2]);

            seguimientoLaboral.setAno(year);
            seguimientoLaboral.setDia(dayOfMonth);
            seguimientoLaboral.setMes(month);

            seguimientoLaboral.setFechaCompleta(formattedTextFieldFecha.getText());

            if (comboBoxTipo.getSelectedItem().toString().equals("Entrada")) {
                seguimientoLaboral.setHora_entrada(formattedTextFieldHora.getText());
                seguimientoLaboral.setHora_salida("");

            } else {
                seguimientoLaboral.setHora_salida(formattedTextFieldHora.getText());
                seguimientoLaboral.setHora_entrada("");
            }

            seguimientoLaboral.setTipo(comboBoxTipo.getSelectedItem().toString());

            seguimientoLaboral = updateHoras(seguimientoLaboral, 1);

            seguimientoLaboral.setIdActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1).getId());
            seguimientoLaboral.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex() - 1));

        }


        return seguimientoLaboral;
    }

    private SeguimientoLaboral updateHoras(SeguimientoLaboral seguimientoLaboral, int tipo) {

        //tipo 1: viene desde view
        //tipo 2: se actualiza cuando pierde el foco

//
        if(comboBoxTrabajador.getSelectedIndex() - 1 == -1){

            ShowWarningMessage("Warning", "Selecciona un trabajador de la lista desplegable.");

        }else if(formattedTextFieldHora.getText().equals("  :  ")){

            ShowWarningMessage("Warning", "Introduce una hora.");
        }else {

            Date horaEntrada = hayEntrada(formattedTextFieldFecha.getText(), trabajadores.get(comboBoxTrabajador.getSelectedIndex() - 1));

            Date horaSalida = new Date();
            Date DateDiff = new Date();
            SimpleDateFormat sdfDiff = new SimpleDateFormat("h");

            if (tipo == 1) {
                //Primero recorremos los seguimientos para ver si hay una entrada ese dia
                if (horaEntrada == null && !comboBoxTipo.getSelectedItem().equals("Entrada")) {
                    seguimientoLaboral.setHoras_totales(0);
                    ShowWarningMessage("Warning", "Estas añadiendo una salida sin una entrada para la fecha " + seguimientoLaboral.getFechaCompleta());

                    //Si lo que se esta registrando es una salida
                } else if ((comboBoxTipo.getSelectedIndex() - 1) == 1) {

                    //Obtenemos las Horas Totales
                    if (!formattedTextFieldHora.getText().isEmpty()) {
                        SimpleDateFormat sdfSalida = new SimpleDateFormat("dd-MM-yyyy hh:mm");

                        try {
                            horaSalida = sdfSalida.parse(formattedTextFieldFecha.getText() + " " + formattedTextFieldHora.getText());

                        } catch (ParseException e) {
                            ShowErrorMessage("Error", "No se ha podido procesar la fecha, intentelo de nuevo más tarde.");
                        }


                        //Obtenemos la diferencia entre las fechas
                        DateDiff = getDateDiff(horaEntrada, horaSalida);

                        int horasTotales = Integer.valueOf(sdfDiff.format(DateDiff));


                        //
                        if (horasTotales > 8) {
                            seguimientoLaboral.setHoras_extra(Integer.valueOf(horasTotales - 8));

                            seguimientoLaboral.setHoras_totales(horasTotales - seguimientoLaboral.getHoras_extra());

                        } else seguimientoLaboral.setHoras_totales(horasTotales);


                    } else ShowErrorMessage("Error", "La hora no puede estar vacía.");
                }
            } else if (tipo == 2) {

                //Primero recorremos los seguimientos para ver si hay una entrada ese dia
                if (horaEntrada == null && !comboBoxTipo.getSelectedItem().equals("Entrada")) {
                    seguimientoLaboral.setHoras_totales(0);

                    ShowWarningMessage("Warning", "Estas añadiendo una salida sin una entrada para la fecha " + seguimientoLaboral.getFechaCompleta());

                    //Si lo que se esta registrando es una salida
                } else if ((comboBoxTipo.getSelectedIndex() - 1) == 1) {

                    //Obtenemos las Horas Totales
                    if (!formattedTextFieldHora.getText().isEmpty()) {
                        SimpleDateFormat sdfSalida = new SimpleDateFormat("dd-MM-yyyy hh:mm");

                        try {
                            horaSalida = sdfSalida.parse(formattedTextFieldFecha.getText() + " " + formattedTextFieldHora.getText());

                        } catch (ParseException e) {
                            ShowErrorMessage("Error", "No se ha podido procesar la fecha, intentelo de nuevo más tarde.");
                        }


                        //Obtenemos la diferencia entre las fechas
                        DateDiff = getDateDiff(horaEntrada, horaSalida);

                        int horasTotales = Integer.valueOf(sdfDiff.format(DateDiff));

                        //Calculamos y seteamos
                        if (horasTotales > 8) {
                            textFieldHorasExtra.setText(String.valueOf(horasTotales - 8));

                            textFieldHorasTotales.setText(String.valueOf((horasTotales) - (horasTotales - 8 )));
                        }else {
                            textFieldHorasExtra.setText(String.valueOf(0));

                            textFieldHorasTotales.setText(String.valueOf(horasTotales));

                        }

                    } else ShowErrorMessage("Error", "La hora no puede estar vacía.");
                }
            }
        }


        return seguimientoLaboral;
    }

    private Date getDateDiff(Date Entrada, Date Salida) {

        long miliseconds = Salida.getTime() - Entrada.getTime();
        int seconds = (int) (miliseconds / 1000) % 60;
        int minutes = (int) ((miliseconds / (1000 * 60)) % 60);
        int hours = (int) ((miliseconds / (1000 * 60 * 60)) % 24);

        Calendar c = Calendar.getInstance();

        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);


        return c.getTime();

    }

    private Date hayEntrada(String fecha, Trabajador trabajador) {

        String[] fechaSplitted = fecha.split("-");

        SimpleDateFormat sdfFechaEntrada = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date FechaEntrada = new Date();

        int dia = Integer.parseInt(fechaSplitted[0]);

        int mes = Integer.parseInt(fechaSplitted[1]);

        int ano = Integer.parseInt(fechaSplitted[2]);

        boolean hayEntrada = false;

        int cont = 0;


        while ((!hayEntrada) && (cont < seguimientosLaborales.size())) {

            SeguimientoLaboral seguimientoLaboral = seguimientosLaborales.get(cont++);
            if (seguimientoLaboral.getAno() == ano) {
                if (seguimientoLaboral.getMes() == mes) {
                    if (seguimientoLaboral.getDia() == dia) {
                        if (seguimientoLaboral.getTipo().equals("Entrada")) {
                            if (seguimientoLaboral.getTrabajador().getId() == trabajador.getId()) {
                                try {
                                    hayEntrada = true;
                                    String fechaIncompleta = procesarFechaCompleta(dia + "-" + mes + "-" + ano);
                                    FechaEntrada = sdfFechaEntrada.parse(fechaIncompleta + " " + seguimientoLaboral.getHora_entrada());
                                    return FechaEntrada;

                                } catch (ParseException e) {
                                    ShowErrorMessage("Error", "No se ha podido procesar la fecha, intentelo de nuevo más tarde.");
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private String procesarFechaCompleta(String s) {
        String[] splitted = s.split("-");

        if (splitted[0].length() == 1) {
            splitted[0] = "0" + splitted[0];
        }

        if (splitted[1].length() == 1) {
            splitted[1] = "0" + splitted[1];
        }

        String fecha = splitted[0] + "-" + splitted[1] + "-" + splitted[2];
        return fecha;
    }

    //endregion Seguimiento

    //region Listeners

    private void initListeners() {
        actionListeners();
        keyListeners();
        focusListeners();
    }

    private void actionListeners() {
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado) {
                    case 0 -> {
                        dispose();
                    }
                    case 1 -> {
                        loadNewSeguimiento();
                    }

                    case 2 -> {
                        loadUpdateSeguimiento();
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
        formattedTextFieldFecha.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '-')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    private void focusListeners() {
        comboBoxTipo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(comboBoxTipo.getSelectedItem().toString().equals("Salida")) {
                    SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();
                    seguimientoLaboral.setFechaCompleta(formattedTextFieldFecha.getText());
                    updateHoras(seguimientoLaboral, 2);
                }
            }
        });

        formattedTextFieldHora.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if(comboBoxTipo.getSelectedItem().toString().equals("Salida")) {
                    SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();
                    seguimientoLaboral.setFechaCompleta(formattedTextFieldFecha.getText());
                    updateHoras(seguimientoLaboral, 2);
                }
            }
        });

    }

    //endregion

    //region Variables
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Actuacion> actuaciones;
    private ArrayList<SeguimientoLaboral> seguimientosLaborales;
    private int estado = 0;
    private ViewSeguimiento viewSeguimiento;
    private SeguimientoLaboral SeguimientoLaboralSiendoModificado;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxActuacion;
    private JComboBox comboBoxTrabajador;
    private JComboBox comboBoxTipo;
    private JTextField textFieldHorasTotales;
    private JTextField textFieldHorasExtra;
    private JPanel panelPrincipal;
    private JFormattedTextField formattedTextFieldFecha;
    private JFormattedTextField formattedTextFieldHora;

    //endregion

}
