package com.company.Formularios;

import com.company.Controlador.ControladorUsuario;
import com.company.Entidades.Usuario;
import com.company.Recursos.Hash;
import com.company.Recursos.CheckDate;
import com.company.Vistas.ViewLogin;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormRegistroUsuario extends JDialog{

    //region Constructores
    public FormRegistroUsuario(Usuario usuario, ViewLogin viewLogin) {
        estado = 1;
        this.usuario = usuario;
        this.viewLogin = viewLogin;
        controladorUsuario = new ControladorUsuario();
        initWindow();
        initComps();
        setUsuario(usuario);
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
        setTitle("Registro Usuario");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize( screen.width / 2, screen.height - 200);

        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

        MaskFormatter formatterFNAC = null;
        try {
            formatterFNAC = new MaskFormatter("##-##-####");
            formattedTextFieldFechaNacimiento.setFormatterFactory(new DefaultFormatterFactory(formatterFNAC));

            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedTextFieldFechaNacimiento.setText(dft.format(LocalDateTime.now()));

            formattedTextFieldDNI.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("########U")));

            formattedTextFieldTelefono.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("#########")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    //endregion

    //region Metodos privados

    private void loadNewUsuario() {

        boolean conErrores = checkFields();

        if(!conErrores){
            Usuario usuario = getUsuario();

            if(viewLogin.getNewUsuarioFromFormulario(usuario)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el usuario, intentelo de nuevo más tarde.");
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
    private void setUsuario(Usuario usuario) {
        if(!(usuario.getDNI() == null)){
            formattedTextFieldDNI.setText(usuario.getDNI());
        }
        if(!(usuario.getNombre() == null)){
            textFieldNombre.setText(usuario.getNombre());
        }
        if(!(usuario.getApellidos() == null)){
            textFieldApellidos.setText(usuario.getApellidos());
        }
        // FIXME: 02/06/2022 DARA ERRORES
        if(!(usuario.getFnac() == null)){
            formattedTextFieldFechaNacimiento.setText(String.valueOf(usuario.getFnac()));
        }
        if(!(usuario.getTelefono() == null)){
            formattedTextFieldTelefono.setText(usuario.getTelefono());
        }
        if(!(usuario.getNacionalidad() == null)){
            textFieldNacionalidad.setText(usuario.getNacionalidad());
        }
        if(!(usuario.getEmail() == null)){
            formattedTextFieldEmail.setText(usuario.getEmail());
        }
        if(!(usuario.getPass() == null)){
            passwordFieldPass.setText(usuario.getPass());
        }
    }

    private boolean checkFields() {

        if (formattedTextFieldDNI.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo DNI no puede estar vacio");
            return true;
        }
        if (textFieldNombre.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Nombre no puede estar vacio");
            return true;
        }
        if (textFieldApellidos.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Apellidos no puede estar vacio");
            return true;
        }

        if (formattedTextFieldTelefono.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Telefono no puede estar vacio");
            return true;
        }

        validarFechas();

        return false;
    }

    private boolean validarFechas() {
        CheckDate checkDate = new CheckDate();

        if (formattedTextFieldFechaNacimiento.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "La fecha de nacimiento no puede estar vacía.");
            return true;

        } else if (!checkDate.isValidDate(formattedTextFieldFechaNacimiento.getText())) {
            ShowErrorMessage("Error", "La fecha de nacimiento no es valida");
            return true;
        }
        return false;
    }

    private Usuario getUsuario() {
        Usuario usuario = new Usuario();

        boolean conErrores = checkFields();

        if (estado == 1 && !conErrores) {
            usuario.setDNI(formattedTextFieldDNI.getText());
            usuario.setNombre(textFieldNombre.getText());
            usuario.setApellidos(textFieldApellidos.getText());
            usuario.setTelefono(formattedTextFieldTelefono.getText());

            //Procesamos Fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                java.util.Date UTILDate = dateFormat.parse(formattedTextFieldFechaNacimiento.getText());
                java.sql.Date SQLDate = new Date(UTILDate.getTime());
                usuario.setFnac(SQLDate);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            usuario.setNacionalidad(textFieldNacionalidad.getText());
            usuario.setEmail(formattedTextFieldEmail.getText());

            Hash hash = new Hash();
            try {

                usuario.setPass(hash.generatePasswordHash(String.valueOf(passwordFieldPass.getPassword())));
                System.out.println("Pass: " + hash.generatePasswordHash(String.valueOf(passwordFieldPass.getPassword())));

            } catch (Exception e) {
                ShowErrorMessage("Error", "No se ha podido encriptar la contraseña, vuelve a intentarlo mas tarde");

            }
        }
        return usuario;
    }

    //endregion MATERIAL

    //region Listeners
    private void initListeners() {
        actionListeners();
        keyListeners();
        focusListeners();
        mouseListeners();
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
                        loadNewUsuario();
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
    private void mouseListeners(){
        buttonVerPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                passwordFieldPass.setEchoChar((char)0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                passwordFieldPass.setEchoChar('•');
            }
        });
    }

    private void keyListeners() {
        formattedTextFieldFechaNacimiento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '-')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        formattedTextFieldTelefono.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) ) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    private void focusListeners(){

    }

    //endregion



    //region Variables
    private ViewLogin viewLogin;
    private int estado = 0;
    private ControladorUsuario controladorUsuario;
    private Usuario usuario;
    private JLabel labelTitulo;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidos;
    private JTextField textFieldSalario;
    private JTextField textFieldNacionalidad;
    private JFormattedTextField formattedTextFieldFechaNacimiento;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JFormattedTextField formattedTextFieldEmail;
    private JPasswordField passwordFieldPass;
    private JButton buttonVerPass;
    private JPanel panelPrincipal;
    private JFormattedTextField formattedTextFieldDNI;
    private JFormattedTextField formattedTextFieldTelefono;

    //endregion
}
