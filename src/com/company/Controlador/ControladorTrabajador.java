package com.company.Controlador;

import com.company.BaseDatos.CRUDTrabajador;
import com.company.BaseDatos.CRUDVacaciones;
import com.company.Calendario.NodoTrabajadorCalendario;
import com.company.Entidades.Trabajador;
import com.company.Entidades.Vacaciones;
import com.company.Vistas.ViewTrabajador;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public ViewTrabajador getViewTrabajador(){
        return viewTrabajador;
    }

    //region CRUD

    public boolean createTrabajador(Trabajador trabajador){
        try {
            int idTrabajador = crudTrabajador.createTrabajador(trabajador);
            trabajador.setId(idTrabajador);
            viewTrabajador.addTableTrabajador(trabajador);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTrabajador(Trabajador trabajador) {
        boolean result = crudTrabajador.updateTrabajador(trabajador);
        if (result){
            viewTrabajador.updateTableTrabajador(trabajador);
        }
        return result;
    }

    public boolean deleteTrabajador(Trabajador trabajador){
        boolean result = crudTrabajador.deleteTrabajador(trabajador.getId());

        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudTrabajador.getColumnsTrabajador();
        if (listColumnsName[0] == null){
            viewTrabajador.ShowErrorMessage("Error", "No se han detectado atributos para el trabajador en la BBDD. Contacte con un administrador");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            viewTrabajador.ShowErrorMessage("Error", "No se han detectado atributos para el trabajador en la BBDD. Contacte con un administrador");
        }
        return listColumnsName;
    }

    public void setlistVacaciones(ArrayList<NodoTrabajadorCalendario> listVacaciones) {

        ArrayList<Trabajador> trabajadorArrayList= new ArrayList<>();
        for (int i=0; i<listVacaciones.size(); i++){
            NodoTrabajadorCalendario nodoTrabajadorCalendario = listVacaciones.get(i);
            var idTrabajador = new CRUDTrabajador().getTrabajadorByDNI(nodoTrabajadorCalendario.getDni());

            Vacaciones vacaciones = new Vacaciones();
            SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
            try {
                java.util.Date parsed =  format.parse(nodoTrabajadorCalendario.getFechaInicio());
                java.sql.Date sql = new java.sql.Date(parsed.getTime());
                vacaciones.setFecha_solicitada_inicio(sql);

                parsed = format.parse(nodoTrabajadorCalendario.getFechaFin());
                sql = new Date(parsed.getTime());
                vacaciones.setFecha_solicitada_fin(sql);

                parsed = format.parse(nodoTrabajadorCalendario.getFechaInicio());
                sql = new Date((parsed.getTime()));
                vacaciones.setFecha_aprobada_inicio(sql);

                parsed = format.parse(nodoTrabajadorCalendario.getFechaFin());
                sql = new Date(parsed.getTime());
                vacaciones.setFecha_aprobada_fin(sql);

                vacaciones.setIdTrabajador(idTrabajador);
                vacaciones.setObservaciones("Observacion correcta");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                var idVacaciones = new CRUDVacaciones().createVacaciones(vacaciones);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //endregion

}
