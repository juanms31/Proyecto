package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.MaterialUtilizadoActuacion;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

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
            return  listamaterialutilizadoactuacion;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }

    }

    public int createMaterialUtilizadoActuacion(MaterialUtilizadoActuacion materialUtilizadoActuacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO materialutilizadoactuacion" +
                " VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, materialUtilizadoActuacion.getId_material());
            preparedStatement.setInt(3, materialUtilizadoActuacion.getId_actuacion());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowMaterialUtilizadoActuacion = 0;
            if(generatedKeys.next()){
                idRowMaterialUtilizadoActuacion = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowMaterialUtilizadoActuacion;
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
            return  true;
        } catch (SQLException e) {
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
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + materialUtilizadoActuacion.getId());
            if (affectedRows == 1) return true;
            return false;
        } catch (SQLException e) {
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
}
