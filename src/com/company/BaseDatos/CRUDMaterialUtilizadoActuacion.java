package com.company.BaseDatos;

import com.company.Entidades.Albaran;
import com.company.Entidades.Material;
import com.company.Entidades.MaterialUtilizadoActuacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDMaterialUtilizadoActuacion {

    // region Metodos CRUD

    public ArrayList<MaterialUtilizadoActuacion> readAllMaterialUtilizadoActuacion() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM materialutilizadoactuacion";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listamaterialutilizadoactuacion = setListaMaterialUtilizadoActuacion(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "readAllMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = exito");
            return  listamaterialutilizadoactuacion;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "readAllMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }

    }

    public int createMaterialUtilizadoActuacion(int id_material, int id_Actuacion) throws SQLException {
        Connection connection = BBDD.connect();
        int idRowMaterialUtilizadoActuacion = 0;

            if (connection == null) return -1;
            final String QUERY_INSERT = "INSERT INTO materialutilizadoactuacion" +
                    " VALUES (?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setNull(1, 1);
                preparedStatement.setInt(2, id_material);
                preparedStatement.setInt(3,id_Actuacion);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) throw new SQLException("No se pudo guardar");

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                idRowMaterialUtilizadoActuacion = 0;
                if (generatedKeys.next()) {
                    idRowMaterialUtilizadoActuacion = generatedKeys.getInt(1);
                }
                LOGGER.log(Level.INFO, "createMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = exito");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "createMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = " + e.getMessage());
                e.printStackTrace();
                return -1;
        }
        return -1;
    }

    public MaterialUtilizadoActuacion readMaterialUtilizadoActuacion(int cod){

        return new MaterialUtilizadoActuacion();
    }

    public boolean deleteMaterialUtilizadoActuacion(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM materialutilizadoactuacion WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public boolean updateMaterialUtilizadoActuacion(MaterialUtilizadoActuacion materialUtilizadoActuacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE materialutilizadoactuacion " +
                "SET id_material = ?, id_actuacion = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, materialUtilizadoActuacion.getId_material());
            preparedStatement.setInt(2, materialUtilizadoActuacion.getId_actuacion());
            preparedStatement.setInt(3, materialUtilizadoActuacion.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "updateMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = no afecto a ningun registro");
                throw  new SQLException("No se pudo actualizar registro id = " + materialUtilizadoActuacion.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = exito");
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateMaterialUtilizadoActuacion en MaterialUtilizadoActuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    // endregion

    //region Metodos privados

    private ArrayList<MaterialUtilizadoActuacion> setListaMaterialUtilizadoActuacion(ResultSet resultSet) {
        ArrayList<MaterialUtilizadoActuacion> listaMaterialUtilizadoActuacion = new ArrayList<>();
        try {
            while (resultSet.next()){
                MaterialUtilizadoActuacion materialUtilizadoActuacion = new MaterialUtilizadoActuacion();
                materialUtilizadoActuacion.setId(resultSet.getInt("id"));
                materialUtilizadoActuacion.setId_material(resultSet.getInt("id_material"));
                materialUtilizadoActuacion.setId_actuacion(resultSet.getInt("id_actuacion"));

                listaMaterialUtilizadoActuacion.add(materialUtilizadoActuacion);
            }
            BBDD.close();
            return listaMaterialUtilizadoActuacion;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listaMaterialUtilizadoActuacion;
        }
    }

    // endregion

    //region Atributos

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.public class CRUDMaterialUtilizadoActuacion");

    //endregion
}
