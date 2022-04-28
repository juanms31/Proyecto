package com.company.Vistas;

import javax.swing.*;

public class Login extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JPanel jpLogin;

    public Login(){
        initWindows();
        add(jpLogin);
    }

    private void initWindows() {
        //BASICO
        setSize(600,400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
