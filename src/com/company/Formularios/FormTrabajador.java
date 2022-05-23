package com.company.Formularios;

import com.company.Entidades.Trabajador;
import com.company.Vistas.ViewTrabajador;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;

public class FormTrabajador extends JDialog{
    //region Constructores
    public FormTrabajador(ViewTrabajador viewTrabajador) {
        estado = 1;
        this.viewTrabajador = viewTrabajador;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormTrabajador(ViewTrabajador viewTrabajador, Trabajador trabajador) {
        estado = 2;
        TrabajadorSiendoModificado = trabajador;
        this.viewTrabajador = viewTrabajador;
        initListeners();
        setTrabajador(trabajador);
        initWindow();
        initComps();
        setVisible(true);
    }

    public FormTrabajador(ViewTrabajador viewTrabajador, Trabajador trabajador, boolean editable) {
        this.viewTrabajador = viewTrabajador;
        setTrabajador(trabajador);
        initWindow();
        initComps();
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
        setTitle("Trabajadores");
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

    }

    //endregion

    //region Metodos privados

    private void loadNewTrabajador() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            Trabajador trabajador = getTrabajador();
            if(viewTrabajador.getNewTrabajadorFromFormulario(trabajador)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el trabajador correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateTrabajador() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            Trabajador trabajador = getTrabajador();
            if (viewTrabajador.getUpdateTrabajadorFromFormulario(trabajador)){
                dispose();
            }else {
                ShowErrorMessage("Error", "No se ha podido crear el trabajadorr correctamente");
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
    private void setTrabajador(Trabajador trabajador) {
        textFieldNombre.setText(trabajador.getNombre());
        textFieldApellidos.setText(trabajador.getApellidos());
        formattedTextFieldFechaNacimiento.setText(String.valueOf(trabajador.getFnac()));
        textFieldPuesto.setText(trabajador.getPuesto());
        textFieldNacionalidad.setText(trabajador.getNacionalidad());
    }

    private boolean checkFields() {
        if (textFieldNombre.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Nombre no puede estar vacio");
            return true;
        }
        if (textFieldApellidos.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Apellido 1 no puede estar vacio");
            return true;
        }
        if (textFieldSalario.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Salario no puede estar vacio");
            return true;
        }

        return false;
    }

    private Trabajador getTrabajador() {
        Trabajador trabajador = new Trabajador();

        boolean conErrores = checkFields();

        if (estado == 2) {
            trabajador.setId(TrabajadorSiendoModificado.getId());

            trabajador.setNombre(textFieldNombre.getText());
            trabajador.setApellidos(textFieldApellidos.getText());
            trabajador.setFnac(Date.valueOf(formattedTextFieldFechaNacimiento.getText()));
            trabajador.setPuesto(textFieldPuesto.getText());
            trabajador.setSalario(Double.parseDouble(textFieldSalario.getText()));
            trabajador.setNacionalidad(textFieldNacionalidad.getText());

        } else {
            trabajador.setNombre(textFieldNombre.getText());
            trabajador.setApellidos(textFieldApellidos.getText());
            trabajador.setFnac(Date.valueOf(formattedTextFieldFechaNacimiento.getText()));
            trabajador.setPuesto(textFieldPuesto.getText());
            trabajador.setSalario(Double.parseDouble(textFieldSalario.getText()));
            trabajador.setNacionalidad(textFieldNacionalidad.getText());
        }

        return trabajador;
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
                        loadNewTrabajador();
                    }

                    case 2 -> {
                        loadUpdateTrabajador();
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
        formattedTextFieldFechaNacimiento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '/')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    //endregion

    //region Variables
    private JLabel labelTitulo;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidos;
    private JTextField textFieldPuesto;
    private JTextField textFieldSalario;
    private JTextField textFieldNacionalidad;
    private JFormattedTextField formattedTextFieldFechaNacimiento;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JPanel panelPrincipal;
    private ViewTrabajador viewTrabajador;
    private int estado = 0;
    private Trabajador TrabajadorSiendoModificado;

    //endregion
}
