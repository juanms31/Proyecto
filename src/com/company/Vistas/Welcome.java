package com.company.Vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Welcome extends JFrame{
    private JButton btnEnter;
    private JPanel jpWelcome;

    public Welcome(){
        initWindows();
        add(jpWelcome);
        setTitle("App Alhambra Metal");
    }

    private void initWindows() {
        //BASIC
        setSize(600,400);
        setLocationRelativeTo(null);
        setResizable(false);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewLogin viewLogin = new ViewLogin();
                viewLogin.setVisible(true);
                dispose();
            }
        });
        btnEnter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    ViewLogin viewLogin = new ViewLogin();
                    viewLogin.setVisible(true);
                    dispose();
                }
            }
        });
    }
}
