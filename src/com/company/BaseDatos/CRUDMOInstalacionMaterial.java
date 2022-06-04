package com.company.BaseDatos;

import com.company.Entidades.MOInstalacionMaterial;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDMOInstalacionMaterial {

    // region Metodos CRUD

    public ArrayList<MOInstalacionMaterial> readAllMOInstalacionMaterial() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_MOInstalacionMAterial = "SELECT * FROM moinstalacionmaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_MOInstalacionMAterial);
            var listaMOInstalacionMaterial = setListaMOInstalacionMaterial(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "readAllMOInstalacionMaterial en MOInstalacionMaterial = exito");
            return  listaMOInstalacionMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "readAllMOInstalacionMaterial en MOInstalacionMaterial = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public int createMOInstalacionMaterial(MOInstalacionMaterial moInstalacionMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO moinstalacionmaterial" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, moInstalacionMaterial.getNombreProveedor1());
            preparedStatement.setDouble(3, moInstalacionMaterial.getPrecio1());
            preparedStatement.setString(4, moInstalacionMaterial.getNombreProveedor2());
            preparedStatement.setDouble(5, moInstalacionMaterial.getPrecio2());
            preparedStatement.setInt(6, moInstalacionMaterial.getIdMaterial());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "createMOInstalacionMaterial en MOInstalacionMaterial = no afecto a ningun registro");
                throw new SQLException("No se pudo guardar");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowMOInstalacionMaterial = 0;
            if(generatedKeys.next()){
                idRowMOInstalacionMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createMOInstalacionMaterial en MOInstalacionMaterial = exito");
            return idRowMOInstalacionMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createMOInstalacionMaterial en MOInstalacionMaterial = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public SeguimientoLaboral readMOInstalacionMaterial(int cod){

        return new SeguimientoLaboral();
    }

    public boolean deleteMOInstalacionMaterial(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM moinstalacionmaterial WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteMOInstalacionMaterial en MOInstalacionMaterial = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteMOInstalacionMaterial en MOInstalacionMaterial = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public boolean updateMOInstalacionMaterial(MOInstalacionMaterial MOInstalacionMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE moinstalacionmaterial " +
                "SET proveedor1 = ?, precio1 = ?, proveedor2 = ?, precio2 = ?," +
                " id_material = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, MOInstalacionMaterial.getNombreProveedor1());
            preparedStatement.setDouble(2, MOInstalacionMaterial.getPrecio1());
            preparedStatement.setString(3, MOInstalacionMaterial.getNombreProveedor2());
            preparedStatement.setDouble(4, MOInstalacionMaterial.getPrecio2());
            preparedStatement.setInt(5, MOInstalacionMaterial.getIdMaterial());
            preparedStatement.setInt(6, MOInstalacionMaterial.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "updateMOInstalacionMaterial en MOInstalacionMaterial = no afecto a ningun registro");
                throw  new SQLException("No se pudo actualizar registro id = " + MOInstalacionMaterial.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateMOInstalacionMaterial en MOInstalacionMaterial = exito");
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateMOInstalacionMaterial en MOInstalacionMaterial = " + e.getMessage());
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

    private ArrayList<MOInstalacionMaterial> setListaMOInstalacionMaterial(ResultSet resultSet) {
        ArrayList<MOInstalacionMaterial> ListaMOInstalacionMaterial = new ArrayList<>();
        try {
            while (resultSet.next()){
                MOInstalacionMaterial MOInstalacionMaterial = new MOInstalacionMaterial();
                MOInstalacionMaterial.setId(resultSet.getInt("id"));
                MOInstalacionMaterial.setIdMaterial(resultSet.getInt("id_material"));
                MOInstalacionMaterial.setPrecio1(resultSet.getDouble("precio1"));
                MOInstalacionMaterial.setPrecio2(resultSet.getDouble("precio2"));
                MOInstalacionMaterial.setNombreProveedor1(resultSet.getString("proveedor1"));
                MOInstalacionMaterial.setNombreProveedor2(resultSet.getString("proveedor2"));

                ListaMOInstalacionMaterial.add(MOInstalacionMaterial);
            }
            BBDD.close();
            return ListaMOInstalacionMaterial;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return ListaMOInstalacionMaterial;
        }
    }

    // endregion

    //region Atributos

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDMOInstalacionMaterial");

    //endregion
}

