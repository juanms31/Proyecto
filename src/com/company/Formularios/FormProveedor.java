package com.company.Formularios;

import com.company.Entidades.Cliente;
import com.company.Entidades.Material;
import com.company.Entidades.Proveedor;
import com.company.Vistas.ViewCliente;
import com.company.Vistas.ViewMaterial;
import com.company.Vistas.ViewProveedor;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormProveedor extends JDialog{

    //region Constructores
    public FormProveedor(ViewProveedor viewProveedor) {
        estado = 1;
        this.viewProveedor = viewProveedor;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormProveedor(ViewProveedor viewProveedor, Proveedor proveedor) {
        estado = 2;
        ProveedorSiendoModificado = proveedor;
        this.viewProveedor = viewProveedor;
        initListeners();
        setProveedor(proveedor);
        initWindow();
        initComps();
        setVisible(true);
    }

    public FormProveedor(ViewProveedor viewProveedor, Proveedor proveedor, boolean editable) {
        this.viewProveedor = viewProveedor;
        setProveedor(proveedor);
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
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Proveedores");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.height / 2, screen.width / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

    }

    //endregion

    //region Metodos privados

    private void loadNewProveedor() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            Proveedor proveedor = getProveedor();
            if(viewProveedor.getNewProveedorFromFormulario(proveedor)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el proveedor correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateProveedor() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            Proveedor proveedor = getProveedor();
            if (viewProveedor.getUpdateProveedorFromFormulario(proveedor)){
                dispose();
            }else {
                ShowErrorMessage("Error", "No se ha podido crear el proveedor correctamente");
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
    private void setProveedor(Proveedor proveedor) {
        textFieldNombre.setText(proveedor.getNombre_proveedor());
        textFieldDireccion.setText(proveedor.getDireccion());
        textFieldMail1.setText(proveedor.getMail1());
        textFieldMail2.setText(proveedor.getMail2());
        textFieldTelefono1.setText(proveedor.getTelefono1());
        textFieldTelefono2.setText(proveedor.getTelefono2());
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

    private Proveedor getProveedor() {
        Proveedor proveedor = new Proveedor();

        boolean conErrores = checkFields();

        if (estado == 2) {
            proveedor.setId(ProveedorSiendoModificado.getId());

            proveedor.setNombre_proveedor(textFieldNombre.getText());
            proveedor.setDireccion(textFieldDireccion.getText());
            proveedor.setMail1(textFieldMail1.getText());
            proveedor.setMail2(textFieldMail2.getText());
            proveedor.setTelefono1(textFieldTelefono1.getText());
            proveedor.setTelefono2(textFieldTelefono2.getText());

        } else {
            proveedor.setNombre_proveedor(textFieldNombre.getText());
            proveedor.setDireccion(textFieldDireccion.getText());
            proveedor.setMail1(textFieldMail1.getText());
            proveedor.setMail2(textFieldMail2.getText());
            proveedor.setTelefono1(textFieldTelefono1.getText());
            proveedor.setTelefono2(textFieldTelefono2.getText());
        }

        return proveedor;
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
                    }
                    case 1 -> {
                        loadNewProveedor();
                    }

                    case 2 -> {
                        loadNewProveedor();
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
    private ViewProveedor viewProveedor;
    private Proveedor ProveedorSiendoModificado;
    int estado = 0;

    //endregion
}
