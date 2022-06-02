package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.Entidades.Cliente;
import com.company.Vistas.ViewCliente;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorCliente {
    private CRUDCliente crudCliente;
    private ViewCliente viewCliente;

    //Constructor
    public ControladorCliente() {
        crudCliente = new CRUDCliente(this);
        ArrayList<Cliente> clientes = crudCliente.getAll();
        viewCliente = new ViewCliente(this, clientes);
    }

    //region CRUD
    public boolean createCliente(Cliente cliente){
        try {
            int idCliente = crudCliente.createCliente(cliente);
            cliente.setId(idCliente);
            viewCliente.addTableCliente(cliente);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCliente(Cliente cliente) {
        boolean result = crudCliente.updateCliente(cliente);

        if (result){
            viewCliente.updateTableCliente(cliente);
        }

        return result;
    }

    public boolean deleteCliente(Cliente cliente){
        boolean result = crudCliente.deleteCliente(cliente.getId());

        return result;
    }


    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudCliente.getColumnsCliente();
        if (listColumnsName[0] == null){
            System.out.println("Fallo en base de datos");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            System.out.println("Fallo en CRUD");
        }
        return listColumnsName;
    }

    //endregion
}
