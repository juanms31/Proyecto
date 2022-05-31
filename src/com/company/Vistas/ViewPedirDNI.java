package com.company.Vistas;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class ViewPedirDNI extends JDialog{
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JFormattedTextField DNITextField;
    private JFrame parent;

    public ViewPedirDNI(JFrame parent) {
        this.parent = parent;

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        DNITextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonOK.doClick();
                }
            }
        });

        DNITextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try {
            MaskFormatter formatter = new MaskFormatter("########-U");
            DNITextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        add(contentPane);
        setSize(370, 190);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setTitle("Validar DNI");
        setIconImage(new ImageIcon("src/images/verificacion25.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void onOK() {
        String aux = DNITextField.getText();
        aux = aux.replaceAll("-","");
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
