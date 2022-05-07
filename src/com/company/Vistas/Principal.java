package com.company.Vistas;

//import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    private JButton home;
    private JPanel panelBotones;
    private JPanel principal;
    private JButton btnMateriales;
    private JButton btnTrabajadores;
    private JButton btnClientes;
    private JButton btnSeguimiento;
    private JButton btnAlbaranes;
    private JButton btnCertificacion;
    private JButton btnObras;
    private JButton btnProveedores;
    private JButton btnFacturas;

    public Principal() throws HeadlessException {
        initView();
        setEventView();
        setConfigButtons();
    }

    private void initView(){
        add(principal);
        setVisible(true);
        setSize(1200, 800);
    }

    //region <metodos privados>

    private void setConfigButtons(){
        btnMateriales.setHorizontalTextPosition(SwingConstants.CENTER);
        btnMateriales.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnTrabajadores.setHorizontalTextPosition(SwingConstants.CENTER);
        btnTrabajadores.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnFacturas.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFacturas.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnSeguimiento.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSeguimiento.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnObras.setHorizontalTextPosition(SwingConstants.CENTER);
        btnObras.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnAlbaranes.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAlbaranes.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnCertificacion.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCertificacion.setVerticalTextPosition(SwingConstants.BOTTOM);

        btnProveedores.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProveedores.setVerticalTextPosition(SwingConstants.BOTTOM);
    }
    private void setEventView(){
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
               VIewCertificacion viewCertificado = new VIewCertificacion();
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

    //endregion
}
