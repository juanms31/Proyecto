package com.company.Vistas;

import com.company.Controlador.ControladorUsuario;
import com.company.Entidades.Trabajador;
import com.company.Entidades.Usuario;
import com.company.Formularios.FormRegistroUsuario;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewLogin extends JFrame{
    private ControladorUsuario controladorUsuario;
    private ArrayList<Usuario> usuarios;
    private int numUsuario = -1;

    public ViewLogin() throws HeadlessException {
        add(panelPrincipal);
        controladorUsuario = new ControladorUsuario();
        usuarios = controladorUsuario.getUsers();
        initWindow();
        initListeners();
        setVisible(true);
    }

    //Inicio de ventana
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

    //Listeners
    private void initListeners(){
        actionListeners();
        focusListeners();
        mouseListeners();
    }

    public boolean getNewUsuarioFromFormulario(Usuario usuario){
        return controladorUsuario.createUsuario(usuario);
    }

//    public boolean getUpdateUsuarioFromFormulario(Usuario usuario) {
//        return controladorUsuario.updateUsuario(usuario);
//
//    }

    private void actionListeners(){
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario =  usuarios.get(numUsuario);
                ViewCargando viewCargando = new ViewCargando(ViewLogin.this, usuario);


            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
            }
        });

        buttonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();

                usuario.setEmail(textFieldEmail.getText());
                usuario.setPass(String.valueOf(passwordFieldPass.getPassword()));
                System.out.println("Pass: " + String.valueOf(passwordFieldPass.getPassword()));
                FormRegistroUsuario registroUsuario = new FormRegistroUsuario(usuario);

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

    private void focusListeners(){
        textFieldEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                buttonRegistro.setVisible(false);
                msj_error.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!textFieldEmail.getText().equals("")) {

                    if (!comprobarEmail(usuarios, textFieldEmail.getText())) {
                        msj_error.setText("Usuario no encontrado...");
                        buttonRegistro.setVisible(true);
                        msj_error.setVisible(true);
                    }
                }
            }
        });
    }

    private boolean comprobarEmail(ArrayList<Usuario> usuarios, String email){
        int contador = 0;
        for (Usuario user : usuarios){

            if(email.equals(user.getEmail())){
                numUsuario = contador;
                return true;
            }
            contador++;
        }

        return false;
    }



    //Variables
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldPass;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JLabel msj_error;
    private JLabel pass;
    private JButton buttonVerPass;
    private JPanel panelPrincipal;
    private JLabel JLabelError;
    private JButton buttonRegistro;
}
