package com.company.BaseDatos;

import com.company.Entidades.CalidadMaterial;
import com.company.Entidades.UnidadMaterial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUDCalidadMaterial {

    //region CONSTRUCTORES

    public CRUDCalidadMaterial(){}

    //endregion

    // region Metodos CRUD

    public ArrayList<CalidadMaterial> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM calidadmaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listCalidadMaterial = setListaCalidadMaterial(resultSet);

            BBDD.close();
            return  listCalidadMaterial;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    //endregion

    //region Metodos privados

    private ArrayList<CalidadMaterial> setListaCalidadMaterial(ResultSet resultSet) {
        ArrayList<CalidadMaterial> listUnidadMaterials = new ArrayList<>();
        try {
            while (resultSet.next()){
                CalidadMaterial calidadMaterial = new CalidadMaterial();
                calidadMaterial.setId(resultSet.getInt("id"));
                calidadMaterial.setSiglasCalidad(resultSet.getString("siglas_calidad"));
                calidadMaterial.setNombreCalidad(resultSet.getString("nombre_calidad"));
                calidadMaterial.setDescripcion(resultSet.getString("Descripcion"));

                listUnidadMaterials.add(calidadMaterial);
            }
            BBDD.close();
            return listUnidadMaterials;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listUnidadMaterials;
        }
    }

    // endregion
}
