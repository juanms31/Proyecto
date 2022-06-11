package com.company.Vistas;

import com.company.Controlador.ControladorCliente;
import com.company.Entidades.Cliente;
import com.company.Entidades.Settings;
import com.company.Entidades.Usuario;
import com.formdev.flatlaf.FlatDarculaLaf;
import net.sf.jasperreports.engine.virtualization.UUIDSerializer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewUsuario extends JDialog{

    //region Constructores

    //todo escribir settings en bbdd
    public ViewUsuario(Usuario usuario, Settings settings) {
        this.usuario = usuario;

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
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Panel " + usuario.getNombre());
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion


    //region Variables

    private Usuario usuario = new Usuario();
    private JLabel labelTitulo;
    private JTextField textField3;
    private JFormattedTextField formattedTextField1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private JPanel panelFondo;
    private JButton examinarButton;
    private JButton buttonVolver;
    private JPanel panelPrincipal;

    //endregion

}
