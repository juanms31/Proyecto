package com.company.Vistas;

import com.company.Controlador.ControladorUsuario;
import com.company.Entidades.Usuario;
import com.company.Formularios.FormRegistroUsuario;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class ViewLogin extends JFrame{
    public ViewLogin() throws HeadlessException {
        add(panelPrincipal);

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
    }

    private void actionListeners(){
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
            }
        });

        buttonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormRegistroUsuario registroUsuario = new FormRegistroUsuario();

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
                ControladorUsuario controladorUsuario = new ControladorUsuario();
                ArrayList<Usuario> usuarios = controladorUsuario.getUsers();
                if(!comprobarEmail(usuarios, email.getText())){
                    msj_error.setText("Usuario no encontrado...");
                    buttonRegistro.setVisible(true);
                    msj_error.setVisible(true);
                }

            }
        });

    }

    private boolean comprobarEmail(ArrayList<Usuario> usuarios, String email){

        for (Usuario user : usuarios){
            if(email.equals(user.getEmail())){
                return true;
            }
        }

        return false;
    }



    //Variables
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldPass;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JLabel msj_error;
    private JLabel email;
    private JLabel pass;
    private JButton ver_pass;
    private JPanel panelPrincipal;
    private JLabel JLabelError;
    private JButton buttonRegistro;
}
