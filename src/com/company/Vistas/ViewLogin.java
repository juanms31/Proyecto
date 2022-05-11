package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class ViewLogin extends JFrame{
    public ViewLogin() throws HeadlessException {
        add(panelPrincipal);

        initWindow();
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
        //setIconImage(new ImageIcon("src/images/logolittle.png").getImage());
    }

    //Listeners
    private void listeners(){







    }


    //Variables
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JLabel msj_error;
    private JLabel email;
    private JLabel pass;
    private JButton ver_pass;
    private JPanel panelPrincipal;
}
