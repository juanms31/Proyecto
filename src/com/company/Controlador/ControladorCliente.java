package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.Entidades.Cliente;
import com.company.Entidades.Material;
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
            viewCliente.updateTableCliente(cliente);
            viewCliente.ShowMessage("cliente con id " + idCliente + " agregado con exito", "CORRECTO");
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
            viewCliente.ShowMessage("El cliente con codigo: " + cliente.getId() + " ha sido actualizado", "CORRECTO");
        }else{
            viewCliente.ShowErrorMessage("No se ha podiddo actualizar cliente con el codigo: " + cliente.getId(), "ERROR");
        }
        return result;
    }

    public boolean deleteCliente(int cod){
        boolean result = crudCliente.deleteCliente(cod);
        if (result){
            viewCliente.ShowMessage("El material con codigo: " + cod + " ha sido borrado", "CORRECTO");
        }else{
            viewCliente.ShowErrorMessage("El material con codigo: " + cod + " no se ha podido borrar", "ERROR");
        }
        return result;
    }

    public String[] getColumnsName() {

        // TODO: 23/05/2022
        return new String[4];
    }

    //endregion
}
