package com.company.BaseDatos;

import com.company.Entidades.EspecificacionMaterial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUDEspecificacion {

    public CRUDEspecificacion() {
    }

    // region Metodos CRUD

    public ArrayList<EspecificacionMaterial> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM especificacionmaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaEspecificacionMaterial = setListaEspecificacionMaterial(resultSet);

            BBDD.close();
            return  listaEspecificacionMaterial;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    //endregion

    //region Metodos privados

    private ArrayList<EspecificacionMaterial> setListaEspecificacionMaterial(ResultSet resultSet) {
        ArrayList<EspecificacionMaterial> listEspecificacionMaterial = new ArrayList<>();
        try {
            while (resultSet.next()){
                EspecificacionMaterial especificacionMaterial = new EspecificacionMaterial();
                especificacionMaterial.setId(resultSet.getInt("id"));
                especificacionMaterial.setSiglasEspecificacion(resultSet.getString("siglas_especificacion"));
                especificacionMaterial.setNombreEspecificacion(resultSet.getString("nombre_Especificacion"));
                especificacionMaterial.setDescripcion(resultSet.getString("Descripcion"));

                listEspecificacionMaterial.add(especificacionMaterial);
            }
            BBDD.close();
            return listEspecificacionMaterial;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listEspecificacionMaterial;
        }
    }

    // endregion
}
