package com.company.Vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame{
    private JButton btnEnter;

    public Welcome(){
        initWindows();
    }

    private void initWindows() {
        setLocationRelativeTo(null);
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                dispose();
            }
        });
    }
}
