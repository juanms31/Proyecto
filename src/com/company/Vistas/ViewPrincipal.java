package com.company.Vistas;

//import com.toedter.calendar.JCalendar;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPrincipal extends JFrame{

    //Constructor
    public ViewPrincipal() throws HeadlessException {
        initWindow();

        setActionListeners();

        centerFrame();

        setVisible(true);
    }

    private void initWindow() {
        add(panelPrincipal);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setResizable(true);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Panel Principal");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.height/2, screen.width/2);
        Dimension window = getSize();
        int width = (screen.width - window.width)/2;
        int height = (screen.height - window.height)/2;
        setLocation(width, height);
    }

    //region <metodos privados>
    private void setActionListeners(){
        btnMateriales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewMaterial viewMaterial = new ViewMaterial();
            }
        });
        btnAlbaranes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAlbaran viewAlbaran = new ViewAlbaran();
            }
        });
        btnCertificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ViewCertificacion viewCertificado = new ViewCertificacion();
            }
        });
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewCliente viewCliente = new ViewCliente();
            }
        });
        btnObras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewActuacion viewActuacion = new ViewActuacion();
            }
        });
        btnProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewProveedor viewProvedores = new ViewProveedor();
            }
        });
        btnFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewFactura viewFactura = new ViewFactura();
            }
        });
        btnTrabajadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewTrabajador viewTrabajadores = new ViewTrabajador();
            }
        });
        btnSeguimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewSeguimiento viewSeguimiento = new ViewSeguimiento();
            }
        });
    }

    private void setMouseListeners(){

    }

    private void setWindowListeners(){

    }

    private void setKeyListeners(){

    }

    //endregion

    //region Variables
    private JPanel panelBotones;
    private JPanel panelPrincipal;
    private JButton btnMateriales;
    private JButton btnTrabajadores;
    private JButton btnClientes;
    private JButton btnSeguimiento;
    private JButton btnAlbaranes;
    private JButton btnCertificacion;
    private JButton btnObras;
    private JButton btnProveedores;
    private JButton btnFacturas;
    private JButton buttonVolver;

    //endregion
}
