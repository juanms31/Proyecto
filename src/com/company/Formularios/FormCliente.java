package com.company.Formularios;

import com.company.Entidades.Cliente;
import com.company.Entidades.Material;
import com.company.Vistas.ViewCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FormCliente extends JFrame{

    // region Constructores

    public FormCliente(ViewCliente viewCliente) {
        this.viewCliente = viewCliente;
        estado = 1;
        InitWindos();
        setVisible(true);
    }

    public FormCliente(ViewCliente viewCliente, Cliente cliente) {
        this.viewCliente = viewCliente;
        estado = 2;
        InitWindos();
        setVisible(true);
    }

    public FormCliente(ViewCliente viewCliente, Cliente cliente, boolean isEditable) {
        this.viewCliente = viewCliente;
        setCliente(cliente);
        //TODO ver como vemos isEditable del construcor clientes
        InitWindos();
        setVisible(true);
    }

    //endregion

    //region Metodos Vista

    private void InitWindos() {
        //TODO aqui cositas del frame
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

    //region SET Y GET CLIENTE

    private Cliente getCliente() {
        //TODO completar metodo getCiente()
        return null;
    }

    private void setCliente(Cliente cliente){
        //TODO completar metodo setCiente()
    }

    //endregion

    //region Listeners


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
    }


    private void keyListeners(){
//        textFieldEspesor.addKeyListener(new KeyAdapter()
//        {
//            public void keyTyped(KeyEvent e)
//            {
//                char caracter = e.getKeyChar();
//
//                // Verificar si la tecla pulsada no es un digito
//                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
//                {
//                    e.consume();  // ignorar el evento de teclado
//                }
//            }
//        });
//
//        spinnerPrecio1.addKeyListener(new KeyAdapter()
//        {
//            public void keyTyped(KeyEvent e)
//            {
//                char caracter = e.getKeyChar();
//
//                // Verificar si la tecla pulsada no es un digito
//                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
//                {
//                    e.consume();  // ignorar el evento de teclado
//                }
//            }
//        });
//
//        spinnerPrecio2.addKeyListener(new KeyAdapter()
//        {
//            public void keyTyped(KeyEvent e)
//            {
//                char caracter = e.getKeyChar();
//
//                // Verificar si la tecla pulsada no es un digito
//                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
//                {
//                    e.consume();  // ignorar el evento de teclado
//                }
//            }
//        });
//
//        spinnerPrecio3.addKeyListener(new KeyAdapter()
//        {
//            public void keyTyped(KeyEvent e)
//            {
//                char caracter = e.getKeyChar();
//
//                // Verificar si la tecla pulsada no es un digito
//                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
//                {
//                    e.consume();  // ignorar el evento de teclado
//                }
//            }
//        });
    }


    //endregion

    //region Variables

    private JButton aceptarButton;
    private JButton cancelarButton;
    private JTextField textFieldNombre;
    private JTextField textFieldDireccion;
    private JTextField textFieldMail1;
    private JTextField textFieldMail2;
    private JTextField textFieldTelefono1;
    private JTextField textFieldTelefono2;
    private JLabel labelTitulo;

    private ViewCliente viewCliente;
    private int estado = 0;

    private ArrayList<Cliente> clientes;

    //endregion
}
