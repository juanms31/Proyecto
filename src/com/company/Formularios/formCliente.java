package com.company.Formularios;

import com.company.Entidades.Cliente;

import javax.swing.*;

public class formCliente {

    public formCliente() {

    }

    //region CRUD
    public Cliente createCliente(){

        return new Cliente();
    }

    public boolean readCliente(Cliente Cliente){

        return false;
    }

    public Cliente updateCliente() {

        return new Cliente();
    }

    public boolean deleteCliente(){


        return false;
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
    //endregion
}
