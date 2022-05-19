package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.Entidades.Cliente;
import com.company.Vistas.ViewCliente;

import java.util.ArrayList;

public class ControladorCliente {

    private CRUDCliente crudCliente;
    private ViewCliente viewCliente;

    //Constructor
    public ControladorCliente() {
        crudCliente = new CRUDCliente(this);
        ArrayList<Cliente> clientes = crudCliente.readAllClientes();
        viewCliente = new ViewCliente(this, clientes);
    }

    //region CRUD
    public void createCliente(){
    }

    public Cliente readCliente(String id){
        Cliente cliente = crudCliente.readCliente(id);
        if (cliente.getId() < 1){
            viewCliente.ErrorMessage("No se ha podido cargar el material con codigo: " + id , "ERROR");
            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
        }else{
            viewCliente.ShowMessage("Correcto, cargando material...", "ERROR");
            //TODO quizas este mensaje sobre y no sea necesario
        }
        return  cliente;
    }

    public boolean updateCliente(Cliente cliente) {
        boolean result = crudCliente.updateCliente(cliente);
        if (result){
            viewCliente.updateRowToTable(cliente);
            viewCliente.ShowMessage("El cliente: " + cliente.getNombre() + " ha sido actualizado", "Correcto");
        }else{
            viewCliente.ErrorMessage("El cliente: " + cliente.getNombre() + " no se ha podido actualizar", "ERROR");
        }
        return result;

    }

    public boolean deleteCliente(Cliente cliente){
        boolean result = crudCliente.deleteCliente(cliente.getId());
        if (result){
            viewCliente.ShowMessage("El cliente: " + cliente.getNombre() + " ha sido borrado", "CORRECTO");
        }else{
            viewCliente.ErrorMessage("El cliente: " + cliente.getNombre() + " no se ha podido borrar", "ERROR");
        }
        return result;
    }

    //endregion
}
