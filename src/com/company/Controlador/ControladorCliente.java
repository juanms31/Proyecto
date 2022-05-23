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
            viewCliente.ShowMessage("Cliente " + cliente.getNombre() + " agregado con exito", "CORRECTO");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewCliente.ShowErrorMessage("No se ha podido agregar el registro", "ERROR");
            return false;
        }
    }

    public Cliente readCliente(int cod){
        Cliente cliente = crudCliente.readCliente(cod);
        if (cliente.getId() > 0){
            viewCliente.ShowMessage("No se ha podido cargar el cliente con codigo: " + cod, "CORRECTO");
            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
        }else{
            viewCliente.ShowMessage("Correcto, cargando cliente...", "CORRECTO");
            //TODO quizas este mensaje sobre y no sea necesario
        }
        return  cliente;
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

    public String[] getColumnsName() {
        // TODO: 23/05/2022
        String[] headers = {"ID", "NOMBRE", "DIRECCION", "MAIL 1", "MAIL2", "TELEFONO 1", "TELEFONO 2"};

        return headers;
    }

    //endregion
}
