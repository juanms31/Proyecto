package com.company.Controlador;

import com.company.BaseDatos.*;
import com.company.Entidades.*;
import com.company.Vistas.ViewSeguimiento;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorSeguimiento {

    //Constructor
    public ControladorSeguimiento(){
        crudSeguimientoLaboral = new CRUDSeguimientoLaboral(this);
        seguimientoLaborales = crudSeguimientoLaboral.getAll();
        trabajadores = getTrabajadores();
        actuaciones = getActuaciones();

        setTrabajadorObject();
        setActuacionObject();

        viewSeguimiento = new ViewSeguimiento(this, seguimientoLaborales, trabajadores, actuaciones);
    }
    

    public boolean createSeguimiento(SeguimientoLaboral seguimientoLaboral) {

        try {
            int idSeguimiento = crudSeguimientoLaboral.createSeguimientoLaboral(seguimientoLaboral);
            seguimientoLaboral.setId(idSeguimiento);
            viewSeguimiento.addTableSeguimiento(seguimientoLaboral);
            viewSeguimiento.ShowMessage( "CORRECTO", "Seguimiento Laboral agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewSeguimiento.ShowErrorMessage("No se ha podido agregar el registro", "ERROR");
            return false;
        }
        
    }

    public boolean updateSeguimiento(SeguimientoLaboral seguimientoLaboral) {

        boolean result = crudSeguimientoLaboral.updateSeguimientoLaboral(seguimientoLaboral);
        if (result){
            viewSeguimiento.updateTableSeguimiento(seguimientoLaboral);
            viewSeguimiento.ShowMessage("CORRECTO","El seguimiento ha sido actualizado");
        }else{
            viewSeguimiento.ShowErrorMessage("ERROR", "No se ha podiddo actualizar el seguimiento");
        }
        return result;
    }

    public boolean deleteSeguimiento(int cod) {

        boolean result = crudSeguimientoLaboral.deleteSeguimientoLaboral(cod);
        if (result){
            viewSeguimiento.ShowMessage( "CORRECTO", "El seguimiento con codigo: " + cod + " ha sido borrado");
        }else{
            viewSeguimiento.ShowErrorMessage("ERROR", "El seguimiento con codigo: " + cod + " no se ha podido borrar");
        }
        return result;
    }
    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudSeguimientoLaboral.getColumsSeguimiento();
        if (listColumnsName[0] == null){
            System.out.println("Fallo en base de datos");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            System.out.println("Fallo en CRUD");
        }
        return listColumnsName;
    }
    //region Parametros constructor

    private ArrayList<Trabajador> getTrabajadores(){
        ArrayList<Trabajador> listTrabajadores;
        CRUDTrabajador crudTrabajador = new CRUDTrabajador();
        listTrabajadores = crudTrabajador.getAll();
        return listTrabajadores;
    }

    private ArrayList<Actuacion> getActuaciones(){
        ArrayList<Actuacion> listActuacion;
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuacion = crudActuacion.getAll();
        return listActuacion;
    }

    //endregion

    //region Completar ArrayList
    private void setActuacionObject() {

        int posicion = 0;
        for (SeguimientoLaboral seguimientoLaboral : seguimientoLaborales){
            for(Actuacion actuacion : actuaciones){
                if(seguimientoLaboral.getIdActuacion() ==  actuacion.getId()) {
                    System.out.println("Actuacion: " + actuacion.getId());
                    seguimientoLaborales.get(posicion).setActuacion(actuacion);
                    seguimientoLaborales.get(posicion).setIdActuacion(actuacion.getId());
                }
            }

            posicion++;
        }
    }

    private void setTrabajadorObject() {

        int posicion = 0;
        for (SeguimientoLaboral seguimientoLaboral : seguimientoLaborales){
            for(Trabajador trabajador : trabajadores){
                if(seguimientoLaboral.getIdTrabajador() ==  trabajador.getId()) {
                    System.out.println("Trabajador: " + trabajador.getId());
                    seguimientoLaborales.get(posicion).setTrabajador(trabajador);
                    seguimientoLaborales.get(posicion).setIdTrabajador(trabajador.getId());
                }
            }

            posicion++;
        }
    }

    //endregion



    //region Variables
    private CRUDSeguimientoLaboral  crudSeguimientoLaboral;
    private ViewSeguimiento viewSeguimiento;
    private ArrayList<SeguimientoLaboral> seguimientoLaborales;
    private ArrayList<Trabajador> trabajadores;
    private ArrayList<Actuacion> actuaciones;
}
