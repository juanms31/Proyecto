package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.Formularios.formCliente;

import java.sql.SQLException;

public class controladorCliente {

    private CRUDCliente crudCliente;
    private formCliente formCliente;

    //Constructor
    public controladorCliente() {

        crudCliente = new CRUDCliente();
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
