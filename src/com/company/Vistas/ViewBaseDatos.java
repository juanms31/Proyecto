package com.company.Vistas;

import com.company.Controlador.*;
import com.company.Recursos.RoundedBorder;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewBaseDatos extends JDialog {

    //region Constructor
    public ViewBaseDatos() throws HeadlessException {

        centerFrame();
        initWindow();
        initComps();
        setActionListeners();

        setVisible(true);
    }

    private void initComps() {
        buttonVolver.setBorder(new RoundedBorder(10));
        btnMateriales.setBorder(new RoundedBorder(10));
        btnTrabajadores.setBorder(new RoundedBorder(10));
        btnAlbaranes.setBorder(new RoundedBorder(10));
        btnObras.setBorder(new RoundedBorder(10));
        btnClientes.setBorder(new RoundedBorder(10));
        btnCertificacion.setBorder(new RoundedBorder(10));
        btnFacturas.setBorder(new RoundedBorder(10));
        btnProveedores.setBorder(new RoundedBorder(10));
        btnSeguimiento.setBorder(new RoundedBorder(10));
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750, 750));
        setLocationRelativeTo(null);
        setTitle("Panel Principal");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height - 100);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }
    //endregion

    //region <metodos privados>
    private void setActionListeners() {
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
                ControladorCertificacion controladorCertificacion = new ControladorCertificacion();
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
                JOptionPane.showMessageDialog(ViewBaseDatos.this,
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

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void setMouseListeners() {

    }

    private void setWindowListeners() {

    }

    private void setKeyListeners() {

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
