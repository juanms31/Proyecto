package com.company.Vistas;

import com.company.Entidades.Settings;
import com.company.Entidades.Usuario;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ViewUsuario extends JDialog{

    //region Constructores

    //todo escribir settings en bbdd
    public ViewUsuario(ViewInicio viewInicio, Usuario usuario, Settings settings) {
        this.usuario = usuario;
        this.settings = settings;
        this.viewInicio = viewInicio;
        setSettings(settings, usuario);
        initWindow();
        initListeners();
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

    private void setSettings(Settings settings, Usuario usuario) {
        formattedTextFieldDNI.setText(usuario.getDNI());
        textFieldNombre.setText(usuario.getNombre());
        formattedTextFieldTelefono.setText(usuario.getTelefono());
        textFieldEmail.setText(usuario.getEmail());
        textFieldApellidos.setText(usuario.getApellidos());

        // TODO: 12/06/2022 PROCESAR FECHA
//        formatedTextFieldFnac.setText(usuario.getFnac());
//        passwordField.setText(usuario.get);

        setImageBackground("src/com/company/Images/Fondos/" +settings.getUrlFondo());
        comboBoxTipoFondo.setSelectedItem(settings.getTipoFondo());
    }

    private void getSettings() {
        usuario.setDNI(formattedTextFieldDNI.getText());
        usuario.setNombre(textFieldNombre.getText());
        usuario.setTelefono(formattedTextFieldTelefono.getText());
        usuario.setEmail(textFieldEmail.getText());
        usuario.setApellidos(textFieldApellidos.getText());

        // TODO: 12/06/2022 PROCESAR FECHA
//        formatedTextFieldFnac.setText(usuario.getFnac());
//        passwordField.setText(usuario.get);

        settings.setTipoFondo((String) comboBoxTipoFondo.getSelectedItem());
    }

    private void setImageBackground(String path) {
        panelFondo.removeAll();
        JPanel fondo = new JPanel() {
            public void paintComponent(Graphics g) {
                Image img = Toolkit.getDefaultToolkit().getImage(path);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
        fondo.setLayout(new BorderLayout(0, 0));
        panelFondo.add(fondo);
        panelFondo.repaint();
        panelFondo.revalidate();
    }
    //endregion

    //regionListeners

    private void initListeners(){
        initActionListeners();
    }

    private void initActionListeners() {
        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSettings();
                dispose();
                viewInicio.getUpdatedSettings(settings, usuario);
            }
        });

        examinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooserImagenFondo = new JFileChooser();
                fileChooserImagenFondo.setCurrentDirectory(new File("src/com/company/Images/Fondos"));
                fileChooserImagenFondo.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("*.jpg", "jpg");
                fileChooserImagenFondo.setFileFilter(filtroPDF);
                int seleccion = fileChooserImagenFondo.showOpenDialog(panelPrincipal);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File imagenFondo = fileChooserImagenFondo.getSelectedFile();
                    direccionFondoLabel.setText(imagenFondo.getName());
                    settings.setUrlFondo(imagenFondo.getName());
                    setImageBackground("src/com/company/Images/Fondos/"+imagenFondo.getName());
                }
            }
        });
    }


    //region Variables

    private Usuario usuario = new Usuario();

    private JFileChooser fileChooserImagenFondo;

    private ViewInicio viewInicio;
    private Settings settings = new Settings();
    private JLabel labelTitulo;
    private JFormattedTextField formattedTextFieldTelefono;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JComboBox comboBoxTipoFondo;
    private JPanel panelFondo;
    private JButton examinarButton;
    private JButton buttonVolver;
    private JPanel panelPrincipal;
    private JFormattedTextField formattedTextFieldDNI;
    private JTextField textFieldNombre;
    private JTextField textFieldApellidos;
    private JFormattedTextField formatedTextFieldFnac;
    private JLabel direccionFondoLabel;

    //endregion

}
