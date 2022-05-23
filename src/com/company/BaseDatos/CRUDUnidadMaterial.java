package com.company.BaseDatos;

import com.company.Entidades.CalidadMaterial;
import com.company.Entidades.EspecificacionMaterial;
import com.company.Entidades.UnidadMaterial;

import java.sql.*;
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

    public int createUnidadMaterial(UnidadMaterial unidadMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO unidadmaterial" +
                " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, unidadMaterial.getSiglasUnidad());
            preparedStatement.setString(3, unidadMaterial.getNombreUnidad());
            preparedStatement.setString(4, unidadMaterial.getDescripcion());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowUnidadMaterial = 0;
            if(generatedKeys.next()){
                idRowUnidadMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowUnidadMaterial;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
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
