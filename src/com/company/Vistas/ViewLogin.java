package com.company.Vistas;

import javax.swing.*;
import java.awt.*;

public class ViewLogin extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JLabel msj_error;
    private JLabel email;
    private JLabel pass;
    private JButton ver_pass;
    private JPanel panelPrincipal;

    public ViewLogin() throws HeadlessException {
        add(panelPrincipal);
        setSize(400,400);
        setResizable(false);

        ViewCargando viewCargando = new ViewCargando();
        viewCargando.setVisible(true);
    }
}