package com.company.Vistas;

import com.company.Calendario.ViewCalendario;
import com.company.Recursos.RoundedBorder;
import com.company.chat.Cliente;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ViewInicio extends JFrame {
    private JPanel panelPrincipal;
    private JButton buttonVolver;
    private JPanel buscador;
    private JButton buttonBaseDatos;
    private JButton buttonCalendario;
    private JButton buttonChat;
    private JPanel panelFondo;
    private JPanel panelModulos;
    private JButton buttonMenu;
    private JButton baseDeDatosButton;
    private JButton calendarioButton;
    private JButton chatButton;
    private JButton configuracionUsuarioButton;
    private JButton añadirElementosButton;
    private JButton cerrarSesionButton;
    private JPanel JPanelMenu;
    private Socket socket = null;
    private Cliente cliente;

    int numImagen = 1;

    boolean viendoMenu = false;

    //region Constructor
    public ViewInicio() {


        initWindow();
        initListeners();
        initComps();
        setVisible(true);
    }

    //endregion

    //region Metodos Vista
    private void initWindow() {
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setTitle("Inicio");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/com/company/Images/Logo/logoEnano.jpg"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 600));
        setImageBackground("fondo" + numImagen + ".jpg");
        add(panelPrincipal);
        repaint();
        revalidate();

    }

    private void initComps() {
        buttonBaseDatos.setBorder(new RoundedBorder(10));
        buttonChat.setBorder(new RoundedBorder(10));
        buttonCalendario.setBorder(new RoundedBorder(10));
        baseDeDatosButton.setBorder(new RoundedBorder(10));
        chatButton.setBorder(new RoundedBorder(10));
        calendarioButton.setBorder(new RoundedBorder(10));
        añadirElementosButton.setBorder(new RoundedBorder(10));
        cerrarSesionButton.setBorder(new RoundedBorder(10));
        configuracionUsuarioButton.setBorder(new RoundedBorder(10));
        JPanelMenu.setVisible(false);
    }

    private void setImageBackground(String image) {
        JPanel fondo = new JPanel() {
            public void paintComponent(Graphics g) {
                Image img = Toolkit.getDefaultToolkit().getImage("src/com/company/Images/Fondos/" + image);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
        fondo.setLayout(new BorderLayout(0, 0));
        setContentPane(fondo);
        add(panelPrincipal);
        repaint();
        revalidate();
    }

    //endregion

    boolean chatOK = false;
    //region Listeners
    private void initListeners() {
        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setImageBackground("fondo"+numImagen+".jpg");
//                numImagen++;

                JPanelMenu.setVisible(false);
                viendoMenu = false;
                dispose();

                ViewLogin viewLogin = new ViewLogin();
                viewLogin.setVisible(true);
            }
        });

        buttonBaseDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;

                ViewBaseDatos viewBaseDatos = new ViewBaseDatos();
                viewBaseDatos.setVisible(true);
            }
        });

        baseDeDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
                ViewBaseDatos viewBaseDatos = new ViewBaseDatos();
            }
        });

        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!viendoMenu) {
                    JPanelMenu.setVisible(true);
                    viendoMenu = true;
                }else{
                    JPanelMenu.setVisible(false);
                    viendoMenu = false;
                }
            }
        });

        calendarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
                ViewCalendario viewCalendario = new ViewCalendario();
            }
        });

        buttonCalendario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
                ViewCalendario viewCalendario = new ViewCalendario();
            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
            }
        });
        //TODO este es el que funciona
        //TODO probar el boolen chat ok
        buttonChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
                System.out.println(chatOK);
                if (!chatOK){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String usuario = JOptionPane.showInputDialog("Nombre de usuario");
                                Socket socket = new Socket("localhost", 1234);
                                Cliente cliente = new Cliente(socket, usuario);
                                cliente.enviarPrimerMensaje();
                                chatOK = true;
                            } catch (IOException ex) {
                                //TODO sacar nombre de usuario del usuario registrado
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    cliente.setVisible(true);
                }
                /*
                if (socket.isConnected()){
                    cliente.setVisible(true);
                }*/
            }
        });

        configuracionUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanelMenu.setVisible(false);
                viendoMenu = false;
                ViewUsuario viewUsuario = new ViewUsuario();
            }
        });


    }
    //endregion
}
