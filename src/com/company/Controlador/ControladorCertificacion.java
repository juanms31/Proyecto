package com.company.Controlador;

import com.company.BaseDatos.CRUDActuacion;
import com.company.BaseDatos.CRUDCertificacion;
import com.company.Entidades.*;
import com.company.Vistas.ViewCertificacion;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorCertificacion {


    private CRUDCertificacion crudCertificacion;
    private ViewCertificacion viewCertificacion;
    private ArrayList<Actuacion> listActuaciones;

    //Constructor
    public ControladorCertificacion() {
        crudCertificacion = new CRUDCertificacion(this);
        ArrayList<Actuacion> actuaciones = getActuaciones();
        ArrayList<Certificacion> certificaciones = crudCertificacion.getAll();
        viewCertificacion = new ViewCertificacion(this,certificaciones, actuaciones );
    }

    //region CRUD
    public boolean createCertificacion(Certificacion Certificacion){

        boolean estado = false;
        try {
            System.out.println("Certificacion " + Certificacion.toString());

            int idCertificacion = crudCertificacion.createCertificacion(Certificacion);
            System.out.println("ID CERT CONTROLADOR:" + idCertificacion);
            Certificacion.setId(idCertificacion);
            estado = crudCertificacion.updateCertificacion(Certificacion);
            if(idCertificacion != -1){
                viewCertificacion.addTableCertificacion(Certificacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            estado = false;
        }
        return estado;
    }

    public boolean updateCertificacion(Certificacion Certificacion) {
        boolean result = crudCertificacion.updateCertificacion(Certificacion);
        if (result){
            viewCertificacion.updateTableCertificacion(Certificacion);
        }
        return result;
    }

    public boolean deleteCertificacion(int id){
        return crudCertificacion.deleteCertificacion(id);
    }

    //endregion

    private ArrayList<Actuacion> getActuaciones(){
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuaciones = crudActuacion.getAll();
        return listActuaciones;
    }

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudCertificacion.getColumnsCertificacion();
        if (listColumnsName[0] == null){
            viewCertificacion.ShowErrorMessage("Error", "No se han detectado atributos para el cliente en la BBDD. Contacte con un administrador");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            viewCertificacion.ShowErrorMessage("Error", "No se han detectado atributos para el cliente en la BBDD. Contacte con un administrador");
        }
        return listColumnsName;
    }
}
