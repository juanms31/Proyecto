package com.company.BaseDatos;

import com.company.Entidades.EspecificacionActuacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            LOGGER.log(Level.INFO, "GetAll en EspecificacionActuacion = exito");
            return  listaEspecifiaciones;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en EspecificacionActuacion = " + e.getMessage());
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

    //region ATRIBUTOS

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDEspecificacionActuacion");

    //endregion

}
