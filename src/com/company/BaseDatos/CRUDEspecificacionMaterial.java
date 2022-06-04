package com.company.BaseDatos;

import com.company.Entidades.EspecificacionMaterial;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDEspecificacionMaterial {

    public CRUDEspecificacionMaterial() {
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
            LOGGER.log(Level.INFO, "GetAll en EspecificacionMaterial = exito");
            return  listaEspecificacionMaterial;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en EspecificacionMaterial = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createEspecificacionMaterial(EspecificacionMaterial especificacionMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO especificacionmaterial" +
                " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, especificacionMaterial.getSiglasEspecificacion());
            preparedStatement.setString(3, especificacionMaterial.getNombreEspecificacion());
            preparedStatement.setString(4, especificacionMaterial.getDescripcion());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowEspecificacionMaterial = 0;
            if(generatedKeys.next()){
                idRowEspecificacionMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createEspecificacionMaterial en EspecificacionMaterial = exito");
            return idRowEspecificacionMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createEspecificacionMaterial en EspecificacionMaterial = " + e.getMessage());
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

    //region ATRIBUTOS

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDEspecificacionMaterial");

    //endregion
}
