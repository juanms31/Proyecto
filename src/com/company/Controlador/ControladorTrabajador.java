package com.company.Controlador;

import com.company.BaseDatos.CRUDCliente;
import com.company.BaseDatos.CRUDTrabajador;
import com.company.Entidades.Cliente;
import com.company.Entidades.Trabajador;
import com.company.Vistas.ViewCliente;
import com.company.Vistas.ViewTrabajador;

import javax.swing.text.View;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorTrabajador {

    private CRUDTrabajador crudTrabajador;
    private ViewTrabajador viewTrabajador;

    //Constructor
    public ControladorTrabajador() {
        crudTrabajador = new CRUDTrabajador(this);
        ArrayList<Trabajador> trabajadores = crudTrabajador.getAll();
        viewTrabajador = new ViewTrabajador(this, trabajadores);
    }

    //region CRUD
    public boolean createTrabajador(Trabajador trabajador){
        try {
            int idTrabajador = crudTrabajador.createTrabajador(trabajador);
            trabajador.setId(idTrabajador);
            viewTrabajador.addTableTrabajador(trabajador);
            viewTrabajador.ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podido agregar el registro");
            return false;
        }
    }

//    public Trabajador readTrabajador(int cod){
//        Cliente cliente = crudCliente.readCliente(cod);
//        if (cliente.getId() > 0){
//            viewCliente.ShowMessage("No se ha podido cargar el cliente con codigo: " + cod, "CORRECTO");
//            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
//        }else{
//            viewCliente.ShowMessage("Correcto, cargando cliente...", "CORRECTO");
//            //TODO quizas este mensaje sobre y no sea necesario
//        }
//        return  cliente;
//    }

    public boolean updateTrabajador(Trabajador trabajador) {
        boolean result = crudTrabajador.updateTrabajador(trabajador);
        if (result){
            viewTrabajador.updateTableTrabajador(trabajador);
            viewTrabajador.ShowMessage( "CORRECTO", "Cliente " + trabajador.getNombre() + " ha sido actualizado");
        }else{
            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podiddo actualizar cliente con el codigo: " + trabajador.getId());
        }
        return result;
    }

    public boolean deleteTrabajador(Trabajador trabajador){
        boolean result = crudTrabajador.deleteTrabajador(trabajador.getId());
        if (result){
            viewTrabajador.ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " ha sido borrado");
        }else{
            viewTrabajador.ShowErrorMessage("ERROR","Trabajador " + trabajador.getNombre() + " no se ha podido borrar");
        }
        return result;
    }

    public String[] getColumnsName() {
        // TODO: 23/05/2022
        String[] headers = {"ID", "DNI", "NOMBRE", "APELLIDOS", "FNAC", "NACIONALIDAD", "PUESTO", "SALARIO"};

        return headers;
    }
//    //endregion
}
