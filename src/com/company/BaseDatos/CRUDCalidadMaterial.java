package com.company.BaseDatos;

import com.company.Entidades.CalidadMaterial;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            LOGGER.log(Level.INFO, "GetAll en CalidadMaterial = exito");
            return  listCalidadMaterial;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en CalidadMaterial = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    public int createCalidadMaterial(CalidadMaterial calidadMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO calidadmaterial" +
                " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, calidadMaterial.getSiglasCalidad());
            preparedStatement.setString(3, calidadMaterial.getNombreCalidad());
            preparedStatement.setString(4, calidadMaterial.getDescripcion());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowCalidadMaterial = 0;
            if(generatedKeys.next()){
                idRowCalidadMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createCalidadMaterial en CalidadMaterial = exito");
            return idRowCalidadMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createCalidadMaterial en CalidadMaterial = " + e.getMessage());
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

    //region ATRIBUTOS

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDCalidadMaterial");

    //endregion
}
