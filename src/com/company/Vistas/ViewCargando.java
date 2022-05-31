package com.company.Vistas;

import com.company.DB.dataBase;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.event.*;

public class ViewCargando extends JFrame {
    public static String DNI;
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JLabel porcentaje;
    private dataBase db;
    private JFrame parent;

    public ViewCargando(JFrame parent, dataBase db, String DNI) {
        this.db = db;
        this.DNI = DNI;
        this.parent = parent;

        setSize(570, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setTitle("Validando DNI");
        setIconImage(new ImageIcon("src/images/verificacion25.png").getImage());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        add(contentPane);
        setVisible(true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    int numAle = 0;
                    if (i < 10) {
                        numAle = (int) (Math.random() * 500) + 1;
                        progressBar.setString("Validando DNI..");
                    } else if (i == 99) {
                        progressBar.setString("Validando DNI..");
                        numAle = 2000;
                        progressBar.setString("Validando DNI...");
                        if (db.validateDNIpaciente(DNI)) {
                            dispose();
                            parent.dispose();
                            pacientesPage pacientesPage = new pacientesPage(db, DNI);
                            pacientesPage.setVisible(true);
                        } else if (db.validateDNImedico(DNI)) {
                            dispose();
                            parent.dispose();
                            medicosPage medicosPage = new medicosPage(db, DNI);
                            medicosPage.setVisible(true);
                        } else if (db.validateDNIadmin(DNI)) {
                            dispose();
                            parent.dispose();
                            adminPage adminPage = new adminPage(db, DNI);
                            adminPage.setVisible(true);
                        } else {
                            //If a DNI it's not found it redirects to create a new paciente
                            int op = JOptionPane.showConfirmDialog(null, "Quieres proceder a darte de alta?", "DNI no encontrado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            switch (op) {
                                case 0: {
                                    dispose();
                                    parent.dispose();
                                    altaPaciente altaPaciente = new altaPaciente(db, DNI, false);
                                    altaPaciente.setVisible(true);
                                    break;
                                }
                                case 1: {
                                    JOptionPane.showMessageDialog(null, "Adios!");
                                }
                            }
                        }
                        progressBar.setString("Validando...");
                    } else if (i < 30) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Validando..");
                    } else numAle = (int) (Math.random() * 10) + 1;
                    progressBar.setValue(i);
                    porcentaje.setText(i + "%");
                    try {
                        Thread.sleep(numAle);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread th = new Thread(runnable);
        th.start();
    }
}
