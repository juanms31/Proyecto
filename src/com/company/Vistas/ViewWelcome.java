package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ViewWelcome extends JFrame{
    private JButton buttonEnter;
    private JPanel jpWelcome;

    public ViewWelcome(){
        initWindow();
        add(jpWelcome);
        listeners();
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
        setTitle("Alhambra Metal");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());

    }

    private void listeners(){
        buttonEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewLogin viewLogin = new ViewLogin();
                dispose();
            }
        });

        buttonEnter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    dispose();
                    ViewLogin viewLogin = new ViewLogin();
                }
            }
        });

    }
}
