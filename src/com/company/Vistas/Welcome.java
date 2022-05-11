package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Welcome extends JFrame{
    private JButton btnEnter;
    private JPanel jpWelcome;

    public Welcome(){
        initWindow();
        add(jpWelcome);
        setTitle("App Alhambra Metal");
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

    private void listeners(){
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ViewLogin viewLogin = new ViewLogin();
                //viewLogin.setVisible(true);
                dispose();
            }
        });
        btnEnter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    //ViewLogin viewLogin = new ViewLogin();
                    //viewLogin.setVisible(true);
                    dispose();
                }
            }
        });

    }
}
