package com.company.BaseDatos;

import com.company.Entidades.EspecificacionMaterial;
import com.company.Entidades.UnidadMaterial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUDUnidadMaterial {

    //region CONSTRUCTORES

    public CRUDUnidadMaterial(){}

    //endregion

    // region Metodos CRUD

    public ArrayList<UnidadMaterial> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM unidadmaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listUnidadMaterial = setListaEspecificacionMaterial(resultSet);

            BBDD.close();
            return  listUnidadMaterial;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    //endregion

    //region Metodos privados

    private ArrayList<UnidadMaterial> setListaEspecificacionMaterial(ResultSet resultSet) {
        ArrayList<UnidadMaterial> listUnidadMaterials = new ArrayList<>();
        try {
            while (resultSet.next()){
                UnidadMaterial unidadMaterial = new UnidadMaterial();
                unidadMaterial.setId(resultSet.getInt("id"));
                unidadMaterial.setSiglasUnidad(resultSet.getString("siglas_unidad"));
                unidadMaterial.setNombreUnidad(resultSet.getString("nombre_unidad"));
                unidadMaterial.setDescripcion(resultSet.getString("Descripcion"));

                listUnidadMaterials.add(unidadMaterial);
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
