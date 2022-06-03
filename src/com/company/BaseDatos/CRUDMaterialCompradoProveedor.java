package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.MaterialCompradoProveedor;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

public class CRUDMaterialCompradoProveedor {

    // region Metodos CRUD
    public ArrayList<MaterialCompradoProveedor> readAllMaterialCompradoProveedor() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM materialcompradoproveedores";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaMaterialCompradoProveedor = setListaMaterialCompradoProveedor(resultSet);

            BBDD.close();
            return  listaMaterialCompradoProveedor;
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

    public int createMaterialCompradoProveedor(MaterialCompradoProveedor materialCompradoProveedor) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;

        final String QUERY_INSERT = "INSERT INTO materialcompradoproveedores" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, materialCompradoProveedor.getMaterial().getId());
            preparedStatement.setInt(3, materialCompradoProveedor.getProveedor().getId());
            preparedStatement.setInt(4, materialCompradoProveedor.getActuacion().getId());
            System.out.println("ID ALBARAN: " +  materialCompradoProveedor.getAlbaran().getId());
            preparedStatement.setInt(5, materialCompradoProveedor.getAlbaran().getId());
            preparedStatement.setInt(6, materialCompradoProveedor.getUnidades());
            preparedStatement.setDouble(7, materialCompradoProveedor.getPrecioUnidad());
            preparedStatement.setDouble(8, materialCompradoProveedor.getBaseImponible());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRow = 0;
            if(generatedKeys.next()){
                idRow = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRow;
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

    public MaterialCompradoProveedor readMaterialCompradoProveedor(int cod){

        return new MaterialCompradoProveedor();
    }

    public boolean deleteMaterialCompradoProveedor(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM materialcompradoproveedores WHERE id = ?";
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

    public boolean updateMaterialCompradoProveedor(MaterialCompradoProveedor materialCompradoProveedor) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE materialcompradoproveedores " +
                "SET id_material = ?, id_proveedor = ?, id_actuacion = ?, id_albaran = ?, unidades = ?, precio_unidad = ?, base_imponible = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, materialCompradoProveedor.getMaterial().getId());
            preparedStatement.setInt(2, materialCompradoProveedor.getProveedor().getId());
            preparedStatement.setInt(3, materialCompradoProveedor.getActuacion().getId());
            preparedStatement.setInt(4, materialCompradoProveedor.getAlbaran().getId());
            preparedStatement.setInt(5, materialCompradoProveedor.getUnidades());
            preparedStatement.setDouble(6, materialCompradoProveedor.getPrecioUnidad());
            preparedStatement.setDouble(7, materialCompradoProveedor.getBaseImponible());
            preparedStatement.setInt(8, materialCompradoProveedor.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + materialCompradoProveedor.getId());
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

    private ArrayList<MaterialCompradoProveedor> setListaMaterialCompradoProveedor(ResultSet resultSet) {
//        ArrayList<MaterialCompradoProveedor> materialCompradoProveedores = new ArrayList<>();
//        try {
//            while (resultSet.next()){
//                MaterialCompradoProveedor cliente = new MaterialCompradoProveedor();
//                cliente.setId(resultSet.getInt("id"));
//                cliente.setFecha_compra(resultSet.getDate("fecha_compra"));
//                cliente.setId_material(resultSet.getInt("id_material"));
//                cliente.setId_material(resultSet.getInt("id_proveedor"));
//                cliente.setId_material(resultSet.getInt("id_actuacion"));
//                cliente.setId_material(resultSet.getInt("id_albaran"));
//
//                materialCompradoProveedores.add(cliente);
//            }
//            BBDD.close();
//            return materialCompradoProveedores;
//        } catch (SQLException e) {
//            //TODO incluis log para bbdd
//            e.printStackTrace();
//            BBDD.close();
//            return materialCompradoProveedores;
//        }

        return new ArrayList<>();
    }

    // endregion
}
