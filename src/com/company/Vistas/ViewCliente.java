package com.company.Vistas;

import com.company.Controlador.ControladorCliente;
import com.company.Entidades.Cliente;
import com.company.Formularios.FormCliente;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewCliente extends JFrame{

    //region Constructores

    public ViewCliente(ControladorCliente controladorCliente, ArrayList<Cliente> clientes){
        initWindow();
        this.controladorCliente = controladorCliente;
        this.clientes = clientes;
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

    //TODO faltan metodos tabla de cliente

    //region Metodos desde Formulario

    public void getNewClienteFromFormulario(Cliente cliente) {
        controladorCliente.createCliente(cliente);
    }

    public void getUpdateClienteFromFormulario(Cliente cliente) {
        controladorCliente.updateMaterial(cliente);
    }

    //endregion

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg ,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region CRUD
    private void createCliente(){
        FormCliente formCliente = new FormCliente(this);
    }

    private void readCliente(){
        Cliente cliente = getCliente();
        FormCliente formCliente = new FormCliente(this, cliente, false);
    }

    private void updateCliente() {
        Cliente cliente = getCliente();
        FormCliente formCliente = new FormCliente(this, cliente);
    }

    private void deleteCliente(){
        int id = getIdCliente();
        boolean result = controladorCliente.deleteMaterial(id);
    }

    //endregion

    //region Metodos privados

    private Cliente getCliente() {
        //TODO hacer cuerpo getCliente()
        return null;
    }

    private int getIdCliente() {
        //TODO Coger id desde la tabla
        return 0;
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

    private  ArrayList<Cliente> clientes;
    private ControladorCliente controladorCliente;

    public void updateTableCliente(Cliente cliente) {
        //TODO completar
    }

    //endregion

}
