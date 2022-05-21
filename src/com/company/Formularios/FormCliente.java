package com.company.Formularios;

import com.company.Entidades.Cliente;
import com.company.Entidades.Material;
import com.company.Vistas.ViewCliente;
import com.company.Vistas.ViewMaterial;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormCliente extends JDialog{
    private JPanel panelPrincipal;

    //region Constructores

    public FormCliente(ViewCliente viewCliente) {
        this.viewCliente = viewCliente;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);
        estado = 1;
    }

    public FormCliente(ViewCliente viewCliente, Cliente cliente){
        estado = 2;
        this.viewCliente = viewCliente;
        initWindow();
        initComps();
        initListeners();
        setCliente(cliente);
        setVisible(true);
    }

    public FormCliente(ViewCliente viewCliente, Cliente cliente, boolean editable) {
        this.viewCliente = viewCliente;
        setCliente(cliente);
        initWindow();
        initComps();
        initListeners();
        //TODO ver como tratamos editable
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
        centerFrame();
        setModal(true);
        setResizable(false);
        setMinimumSize(new Dimension(500,500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Clientes");
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

    public void initComps(){

    }


    //endregion

    //region Metodos privados

    private  void loadNewCliente(){
        Cliente cliente = getCliente();
        viewCliente.getNewClienteFromFormulario(cliente);
    }

    private  void loadUpdateCliente(){
        Cliente cliente = getCliente();
        viewCliente.getUpdateClienteFromFormulario(cliente);
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setCliente(Cliente cliente) {

        textFieldNombre.setText(cliente.getNombre());
        textFieldDireccion.setText(cliente.getDireccion());
        textFieldMail1.setText(cliente.getMail1());
        textFieldMail2.setText(cliente.getMail2());
        textFieldTelefono1.setText(cliente.getTelef1());
        textFieldTelefono2.setText(cliente.getTelef2());

    }

    private Cliente getCliente(){
        Cliente cliente = new Cliente();

        cliente.setNombre(textFieldNombre.getText());
        cliente.setDireccion(textFieldDireccion.getText());
        cliente.setMail1(textFieldMail1.getText());
        cliente.setMail2(textFieldMail2.getText());
        cliente.setTelef1(textFieldMail1.getText());
        cliente.setTelef2(textFieldMail2.getText());

        return cliente;
    }

    //endregion MATERIAL

    //region Listeners

    private void initListeners(){
        actionListeners();
        keyListeners();
    }
    private void actionListeners(){
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado){
                    case 0 -> {
                    }
                    case 1 -> {
                        loadNewCliente();
                    }

                    case 2 -> {
                        loadUpdateCliente();
                    }
                }

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void keyListeners(){
        textFieldTelefono1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && caracter != '\b' /*corresponde a BACK_SPACE*/) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldTelefono2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && caracter != '\b' /*corresponde a BACK_SPACE*/) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });



    }

    //endregion

    //region Variables
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JTextField textFieldNombre;
    private JTextField textFieldDireccion;
    private JTextField textFieldMail1;
    private JTextField textFieldTelefono1;
    private JTextField textFieldMail2;
    private JTextField textFieldTelefono2;
    private JLabel labelTitulo;
    private ViewCliente viewCliente;
    int estado = 0;

    //endregion
}
