package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProveedor extends JFrame{


    public ViewProveedor(){
        initWindow();
        setVisible(true);
    }

    //endregion

    //region Metodos Vista
    private void initWindow() {
        add(panelPrincipal);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Proveedores");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region CRUD
    private boolean createProveedor(){


        return false;
    }

    private boolean readProveedor(){

        return false;
    }

    private boolean updateProveedor() {

        return false;
    }

    private boolean deleteProveedor(){


        return false;
    }

    //endregion

    //region Listeners
    private void listeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProveedor();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readProveedor();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProveedor();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProveedor();
            }
        });

    }

    //endregion


    //region Variables
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelProovedor;
    private JTable TableProveedor;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buscar;
    private JPanel panelBotones;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;

    //endregion
}
