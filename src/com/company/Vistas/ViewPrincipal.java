package com.company.Vistas;

import com.company.Controlador.*;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPrincipal extends JFrame{

    //region Constructor
    public ViewPrincipal() throws HeadlessException {

        centerFrame();
        initWindow();

        setActionListeners();

        setVisible(true);
    }

    //endregion


    //region Vista
    private void initWindow() {
        add(panelPrincipal);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Panel Principal");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }
    public void centerFrame(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize( screen.width/2, screen.height/2);
        Dimension window = getSize();
        int width = (screen.width - window.width)/2;
        int height = (screen.height - window.height)/2;
        setLocation(width, height);
    }
    //endregion

    //region <metodos privados>
    private void setActionListeners(){
        btnMateriales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorMaterial controladorMaterial = new ControladorMaterial();
            }
        });
        btnAlbaranes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAlbaran controladorAlbaran = new ControladorAlbaran();
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
                ControladorCliente controladorCliente = new ControladorCliente();
            }
        });
        btnObras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorActuacion controladorActuacion = new ControladorActuacion();
            }
        });
        btnProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorProveedor controladorProveedor = new ControladorProveedor();
            }
        });
        btnFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ViewPrincipal.this,
                        "Este modulo esta en desarrollo. Intentelo más tarde.",
                        "Error",
                        JOptionPane.OK_OPTION,
                        new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg"));
            }
        });
        btnTrabajadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorTrabajador controladorTrabajador = new ControladorTrabajador();
            }
        });
        btnSeguimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorSeguimiento controladorSeguimiento = new ControladorSeguimiento();
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
