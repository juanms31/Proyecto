package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Vistas.ViewProveedor;
import com.company.Vistas.ViewSeguimiento;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FormSeguimientoLaboral extends JDialog {

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento, ArrayList<Trabajador> trabajadores, ArrayList<Actuacion> actuaciones) {
        estado = 1;
        this.viewSeguimiento = viewSeguimiento;
        this.trabajadores = trabajadores;
        this.actuaciones = actuaciones;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento,
                                  SeguimientoLaboral seguimientoLaboral,
                                  ArrayList<Trabajador> trabajadores, ArrayList<Actuacion> actuaciones) {
        estado = 2;
        SeguimientoLaboralSiendoModificado = seguimientoLaboral;
        this.viewSeguimiento = viewSeguimiento;
        this.trabajadores = trabajadores;
        this.actuaciones = actuaciones;
        initListeners();
        initComps();
        setSeguimiento(seguimientoLaboral);
        initWindow();
        setVisible(true);
    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento,
                                  SeguimientoLaboral seguimientoLaboral, boolean editable,
                                  ArrayList<Trabajador> trabajadores, ArrayList<Actuacion> actuaciones) {
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
        textFieldAno.setEditable(editable);
        textFieldDia.setEditable(editable);
        textFieldMes.setEditable(editable);
        textFieldHorasTotales.setEditable(editable);
        textFieldHorasExtra.setEditable(editable);
        textFieldHora.setEditable(editable);
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
        setSize( screen.width / 2, screen.height - 100);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {
        //Rellenar Actuaciones
        comboBoxActuacion.addItem("Selecciona Actuacion");
        for(Actuacion actuacion: actuaciones){
            comboBoxActuacion.addItem(actuacion.getId() + " - " + actuacion.getNombre());
        }

        //Rellenar Trabajadores
        comboBoxTrabajador.addItem("Selecciona Trabajador");
        for(Trabajador trabajador: trabajadores){
            comboBoxTrabajador.addItem(trabajador.getId() + " - " + trabajador.getNombre());
        }

        //Rellenar Tipo Seguimiento
        comboBoxTipo.addItem("Selecciona Tipo");
        comboBoxTipo.addItem("Entrada");
        comboBoxTipo.addItem("Salida");

    }

    //endregion

    //region Metodos privados

    private void loadNewSeguimiento() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if(viewSeguimiento.getNewSeguimientoFromFormulario(seguimientoLaboral)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el seguimiento correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateSeguimiento() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if(viewSeguimiento.getUpdateSeguimientoFromFormulario(seguimientoLaboral)){
                dispose();
            }else {
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

    //endregion

    //region SET Y GET MATERIAL
    private void setSeguimiento(SeguimientoLaboral seguimiento) {


        comboBoxActuacion.setSelectedItem(seguimiento.getActuacion().getId() + " - " + seguimiento.getActuacion().getNombre());

        Trabajador trabajador = seguimiento.getTrabajador();
        String trabajadorSeleccionado =  trabajador.getId() + " - " + trabajador.getNombre();
        comboBoxTrabajador.setSelectedItem(trabajadorSeleccionado);

        textFieldAno.setText(String.valueOf(seguimiento.getAno()));
        textFieldDia.setText(String.valueOf(seguimiento.getDia()));
        textFieldMes.setText(String.valueOf(seguimiento.getMes()));
        textFieldHora.setText(String.valueOf(seguimiento.getHora_entrada()));

        comboBoxTipo.setSelectedItem(seguimiento.getTipo());

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

        if (textFieldAno.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Año no puede estar vacio");
            return true;
        }
        if (textFieldDia.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Dia no puede estar vacio");
            return true;
        }
        if (textFieldMes.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Mes no puede estar vacio");
            return true;
        }
        if (textFieldHora.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Hora no puede estar vacio");
            return true;
        }

        return false;
    }

    private SeguimientoLaboral getSeguimiento() {
        SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();

        boolean conErrores = checkFields();

        if (estado == 2) {
            seguimientoLaboral.setId(SeguimientoLaboralSiendoModificado.getId());

            seguimientoLaboral.setTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex()-1));
            seguimientoLaboral.setIdTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex()-1).getId());

            seguimientoLaboral.setAno(Integer.parseInt(textFieldAno.getText()));
            seguimientoLaboral.setDia(Integer.parseInt(textFieldDia.getText()));
            seguimientoLaboral.setMes(Integer.parseInt(textFieldMes.getText()));

            if(comboBoxTipo.getSelectedItem().toString().equals("Entrada")){
                seguimientoLaboral.setHora_entrada(textFieldHora.getText());
                seguimientoLaboral.setHora_salida("");


            }else{
                seguimientoLaboral.setHora_salida(textFieldHora.getText());
                seguimientoLaboral.setHora_entrada("");

            }

            seguimientoLaboral.setTipo(comboBoxTipo.getSelectedItem().toString());
            seguimientoLaboral.setIdActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex()-1).getId());
            seguimientoLaboral.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex()-1));

        } else {

            seguimientoLaboral.setTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex()-1));
            seguimientoLaboral.setIdTrabajador(trabajadores.get(comboBoxTrabajador.getSelectedIndex()-1).getId());


            seguimientoLaboral.setAno(Integer.parseInt(textFieldAno.getText()));
            seguimientoLaboral.setDia(Integer.parseInt(textFieldDia.getText()));
            seguimientoLaboral.setMes(Integer.parseInt(textFieldMes.getText()));
            seguimientoLaboral.setTipo(comboBoxTipo.getSelectedItem().toString());

            if(comboBoxTipo.getSelectedItem().toString().equals("Entrada")){
                seguimientoLaboral.setHora_entrada(textFieldHora.getText());
                seguimientoLaboral.setHora_salida("");


            }else{
                seguimientoLaboral.setHora_salida(textFieldHora.getText());
                seguimientoLaboral.setHora_entrada("");

            }


            // FIXME: 25/05/2022 VER COMO TRATAMOS LAS HORAS TOTALES Y EXTRA
            seguimientoLaboral.setHoras_totales(0);
            seguimientoLaboral.setHoras_extra(0);
            //

            seguimientoLaboral.setIdActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex()-1).getId());
            seguimientoLaboral.setActuacion(actuaciones.get(comboBoxActuacion.getSelectedIndex()-1));

        }

        return seguimientoLaboral;
    }

    //endregion Seguimiento

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
        textFieldDia.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldMes.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldHora.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ':')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldAno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

    }

    //endregion

    //region Variables
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Actuacion> actuaciones;
    private int estado = 0;
    private ViewSeguimiento viewSeguimiento;
    private SeguimientoLaboral SeguimientoLaboralSiendoModificado;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxActuacion;
    private JComboBox comboBoxTrabajador;
    private JTextField textFieldAno;
    private JTextField textFieldDia;
    private JTextField textFieldMes;
    private JTextField textFieldHora;
    private JComboBox comboBoxTipo;
    private JTextField textFieldHorasTotales;
    private JTextField textFieldHorasExtra;
    private JPanel panelPrincipal;

    //endregion

}
