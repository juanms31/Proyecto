package com.company.BaseDatos;

import com.company.Entidades.EspecificacionActuacion;
import com.company.Entidades.GrupoMaterial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUDEspecificacionActuacion {
    // region Metodos CRUD

    public ArrayList<EspecificacionActuacion> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM especificacionactuacion";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaEspecifiaciones = setListaEspecificaciones(resultSet);

            BBDD.close();
            return  listaEspecifiaciones;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    //endregion

    //region Metodos privados

    private ArrayList<EspecificacionActuacion> setListaEspecificaciones(ResultSet resultSet) {
        ArrayList<EspecificacionActuacion> especificacionActuaciones = new ArrayList<>();
        try {
            while (resultSet.next()){
                EspecificacionActuacion especificacionActuacion = new EspecificacionActuacion();
                especificacionActuacion.setId(resultSet.getInt("id"));
                especificacionActuacion.setSiglasEspecificacion(resultSet.getString("siglas_especificacion"));
                especificacionActuacion.setNombreEspecificacion(resultSet.getString("nombre_especificacion"));
                especificacionActuacion.setDescripcion(resultSet.getString("descripcion"));

                especificacionActuaciones.add(especificacionActuacion);
            }
            BBDD.close();
            return especificacionActuaciones;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return especificacionActuaciones;
        }
    }

    //endregion

}
