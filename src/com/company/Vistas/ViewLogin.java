package com.company.Vistas;

import com.company.Controlador.ControladorUsuario;
import com.company.Entidades.Usuario;
import com.company.Formularios.FormRegistroUsuario;
import com.company.Recursos.Hash;
import com.company.Recursos.RoundedBorder;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewLogin extends JFrame {
    public ViewLogin() {
        add(panelPrincipal);
        controladorUsuario = new ControladorUsuario();
        usuarios = controladorUsuario.getUsers();
        initWindow();
        initComps();
        initListeners();
        setVisible(true);
    }


    private void initWindow() {
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setSize(500, 600);
        setVisible(false);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Login");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());

    }

    private void initComps(){
        entrarButton.setBorder(new RoundedBorder(10));
        cancelarButton.setBorder(new RoundedBorder(10));
        buttonVerPass.setBorder(new RoundedBorder(10));
        buttonRegistro.setBorder(new RoundedBorder(10));

    }

    //Listeners
    private void initListeners() {
        actionListeners();
        focusListeners();
        mouseListeners();
    }

    public boolean getNewUsuarioFromFormulario(Usuario usuario) {
        usuarios.add(usuario);
        return controladorUsuario.createUsuario(usuario);
    }

//    public boolean getUpdateUsuarioFromFormulario(Usuario usuario) {
//        return controladorUsuario.updateUsuario(usuario);
//
//    }

    private void actionListeners() {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comprobarEmail(usuarios, textFieldEmail.getText())) {
                    if(comprobarPass(usuarios, usuarios.get(numUsuario), String.valueOf(passwordFieldPass.getPassword()))){
                        Usuario usuario = usuarios.get(numUsuario);
                        dispose();
                        ViewCargando viewCargando = new ViewCargando(ViewLogin.this, usuario);
                    }else ShowErrorMessage("Error", "El email o la contraseña son incorrectos. Compruebelo antes de continuar o contacte con un administrador.");
                }else ShowErrorMessage("Error", "El email o la contraseña son incorrectos. Compruebelo antes de continuar o contacte con un administrador.");
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewWelcome viewWelcome = new ViewWelcome();
                viewWelcome.setVisible(true);
            }
        });

        buttonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonRegistro.setVisible(false);
                msj_error.setVisible(false);

                Usuario usuario = new Usuario();

                usuario.setEmail(textFieldEmail.getText());
                usuario.setPass(String.valueOf(passwordFieldPass.getPassword()));
                FormRegistroUsuario registroUsuario = new FormRegistroUsuario(usuario, ViewLogin.this);

            }
        });

    }



    private void mouseListeners() {
        buttonVerPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                passwordFieldPass.setEchoChar((char) 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                passwordFieldPass.setEchoChar('•');
            }
        });
    }

    private void focusListeners() {
        textFieldEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                buttonRegistro.setVisible(false);
                msj_error.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!textFieldEmail.getText().equals("")) {

                    if (!comprobarEmail(usuarios, textFieldEmail.getText())) {
                        msj_error.setText("Usuario no encontrado...");
                        buttonRegistro.setVisible(true);
                        msj_error.setVisible(true);
                    }
                }
            }
        });
    }

    private boolean comprobarEmail(ArrayList<Usuario> usuarios, String email) {
        int contador = 0;
        for (Usuario user : usuarios) {

            if (email.equals(user.getEmail())) {
                numUsuario = contador;
                return true;
            }
            contador++;
        }

        return false;
    }

    private boolean comprobarPass(ArrayList<Usuario> usuarios, Usuario usuario, String password) {
        int contador = 0;

        Hash hash =  new Hash();
        try {

            password = hash.generatePasswordHash(password);
            for (Usuario user : usuarios) {
                contador++;
                if(user.getEmail().equals(usuario.getEmail())){
                    if(user.getDNI().equals(usuario.getDNI())){
                        if(password.equals(user.getPass())){
                            return true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            ShowErrorMessage("Error", "No se ha podido encriptar la contraseña, vuelve a intentarlo mas tarde");
        }

        return false;
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }


    //Variables
    private ControladorUsuario controladorUsuario;
    private ArrayList<Usuario> usuarios;
    private int numUsuario = -1;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldPass;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JLabel msj_error;
    private JLabel pass;
    private JButton buttonVerPass;
    private JPanel panelPrincipal;
    private JButton buttonRegistro;
    //endregion
}
