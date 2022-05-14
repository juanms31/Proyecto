package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCliente extends JFrame{


    public ViewCliente(){
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
        setTitle("Cliente");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region CRUD
    private boolean createCliente(){


        return false;
    }

    private boolean readCliente(){

        return false;
    }

    private boolean updateCliente() {

        return false;
    }

    private boolean deleteCliente(){


        return false;
    }

    //endregion


    //region Listeners
    private void listeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCliente();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCliente();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCliente();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCliente();
            }
        });

    }

    //endregion

    //region Variables
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelCliente;
    private JTable TableCliente;
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
