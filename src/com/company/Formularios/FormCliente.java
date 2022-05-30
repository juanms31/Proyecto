package com.company.Formularios;

import com.company.Entidades.Cliente;
import com.company.Vistas.ViewCliente;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.mysql.cj.protocol.a.AbstractRowFactory;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Locale;

public class FormCliente extends JDialog{

    //region Constructores
    public FormCliente(Cliente cliente, boolean editable) {
        estado = 0;
        this.viewCliente = viewCliente;
        initComps();
        setCliente(cliente);
        initWindow();
        initListeners();
        initView(editable);
        setVisible(true);
    }

    public FormCliente(ViewCliente viewCliente) {
        estado = 1;
        this.viewCliente = viewCliente;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormCliente(ViewCliente viewCliente, Cliente cliente) {
        estado = 2;
        ClienteSiendoModificado = cliente;
        this.viewCliente = viewCliente;
        initListeners();
        initComps();
        setCliente(cliente);
        initWindow();
        setVisible(true);
    }


    private void initView(boolean editable) {
        formattedTextFieldCIF.setEditable(editable);
        textFieldNombre.setEditable(editable);
        textFieldDireccion.setEditable(editable);
        textFieldMail1.setEditable(editable);
        textFieldTelefono1.setEditable(editable);
        textFieldMail2.setEditable(editable);
        textFieldTelefono2.setEditable(editable);

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
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Clientes");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {
        try {
            formattedTextFieldCIF.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("########U")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //endregion

    //region Metodos privados

    private void loadNewCliente() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            Cliente cliente = getCliente();
            if(viewCliente.getNewClienteFromFormulario(cliente)){
                dispose();
            }else{

            }
            dispose();
        }
    }

    private void loadUpdateCliente() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            Cliente cliente = getCliente();
            if (viewCliente.getUpdateClienteFromFormulario(cliente)){
                dispose();
            }else {

            }
        }
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setCliente(Cliente cliente) {
        textFieldNombre.setText(cliente.getNombre());
        formattedTextFieldCIF.setText(cliente.getCIF());
        textFieldDireccion.setText(cliente.getDireccion());
        textFieldMail1.setText(cliente.getMail1());
        textFieldMail2.setText(cliente.getMail2());
        textFieldTelefono1.setText(cliente.getTelef1());
        textFieldTelefono2.setText(cliente.getTelef2());
    }

    private boolean checkFields() {
        if (textFieldNombre.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Nombre no puede estar vacio");
            return true;
        }
        if (textFieldDireccion.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Direccion no puede estar vacio");
            return true;
        }
        if (textFieldMail1.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Mail 1 no puede estar vacio");
            return true;
        }
        if (textFieldTelefono1.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Telefono 1 no puede estar vacio");
            return true;
        }

        return false;
    }

    private Cliente getCliente() {
        Cliente cliente = new Cliente();

        boolean conErrores = checkFields();

        if (estado == 2) {
            cliente.setId(ClienteSiendoModificado.getId());

            cliente.setCIF(formattedTextFieldCIF.getText());
            cliente.setNombre(textFieldNombre.getText());
            cliente.setDireccion(textFieldDireccion.getText());
            cliente.setMail1(textFieldMail1.getText());
            cliente.setMail2(textFieldMail2.getText());
            cliente.setTelef1(textFieldTelefono1.getText());
            cliente.setTelef2(textFieldTelefono2.getText());

        } else {

            cliente.setCIF(formattedTextFieldCIF.getText());
            cliente.setNombre(textFieldNombre.getText());
            cliente.setDireccion(textFieldDireccion.getText());
            cliente.setMail1(textFieldMail1.getText());
            cliente.setMail2(textFieldMail2.getText());
            cliente.setTelef1(textFieldTelefono1.getText());
            cliente.setTelef2(textFieldTelefono2.getText());
        }

        return cliente;
    }

    //endregion MATERIAL

    //region Listeners

    private void initListeners() {
        actionListeners();
        keyListeners();
    }

    private void actionListeners() {
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado) {
                    case 0 -> {
                        dispose();
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

    private void keyListeners() {
        textFieldTelefono1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '+')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        formattedTextFieldCIF.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && !Character.isLetter(caracter)) {
                    e.consume();  // ignorar el evento de teclado
                }

            }
        });


        textFieldTelefono2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '+')) {
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
    private JPanel panelPrincipal;
    private JFormattedTextField formattedTextFieldCIF;
    private ViewCliente viewCliente;
    private Cliente ClienteSiendoModificado;
    int estado = 0;

    //endregion
}
