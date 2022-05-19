package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.Entidades.Cliente;
import com.company.Formularios.formCliente;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorCliente {

    private CRUDCliente crudCliente;
    private formCliente formCliente;

    //Constructor
    public ControladorCliente() {
        crudCliente = new CRUDCliente(this);
        ArrayList<Cliente> clientes = crudCliente.readAllClientes();
        formCliente = new formCliente();
    }

    //region CRUD
    public boolean createCliente(){
        try {
            crudCliente.createCliente(formCliente.createCliente());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean readCliente(int cod){

        formCliente.readCliente(crudCliente.readCliente(cod));

        return false;
    }

    public boolean updateCliente() {
        try {
            crudCliente.updateCliente(formCliente.updateCliente());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCliente(int cod){

        try {
            crudCliente.deleteCliente(cod);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //endregion
}
