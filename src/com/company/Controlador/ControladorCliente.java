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
            viewCliente.ShowMessage("CORRECTO", "Cliente " + cliente.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewCliente.ShowErrorMessage( "ERROR", "No se ha podido agregar el registro");
            return false;
        }
    }

    public boolean updateCliente(Cliente cliente) {
        boolean result = crudCliente.updateCliente(cliente);
        if (result){
            viewCliente.updateTableCliente(cliente);
            viewCliente.ShowMessage( "CORRECTO", "Cliente " + cliente.getNombre() + " ha sido actualizado");
        }else{
            viewCliente.ShowErrorMessage("ERROR", "No se ha podiddo actualizar cliente con el codigo: " + cliente.getId());
        }
        return result;
    }

    public boolean deleteCliente(Cliente cliente){
        boolean result = crudCliente.deleteCliente(cliente.getId());
        if (result){
            viewCliente.ShowMessage("CORRECTO", "Cliente " + cliente.getNombre() + " ha sido borrado");
        }else{
            viewCliente.ShowErrorMessage("ERROR","Cliente " + cliente.getNombre() + " no se ha podido borrar");
        }
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
