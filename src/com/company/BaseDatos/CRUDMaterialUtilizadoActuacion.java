package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

public class CRUDMaterialUtilizadoActuacion {

    // region Metodos CRUD

    public ArrayList<Cliente> readAllMaterialUtilizadoActuacion() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_PROVEEDOR = "SELECT * FROM proveedor";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROVEEDOR);
            var listaProveedor = setListaMaterialUtilizadoActuacion(resultSet);

            BBDD.close();
            return  listaProveedor;
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

    public int createMaterialUtilizadoActuacion(Cliente cliente) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO proveedor" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, cliente.getNombre());
            preparedStatement.setString(3, cliente.getDireccion());
            preparedStatement.setString(4, cliente.getMail1());
            preparedStatement.setString(5, cliente.getMail2());
            preparedStatement.setString(6, cliente.getTelef1());
            preparedStatement.setString(7, cliente.getTelef2());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowCLiente = 0;
            if(generatedKeys.next()){
                idRowCLiente = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowCLiente;
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

    public SeguimientoLaboral readMaterialUtilizadoActuacion(int cod){

        return new SeguimientoLaboral();
    }

    public boolean deleteMaterialUtilizadoActuacion(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM cliente WHERE id = ?";
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

    public boolean updateMaterialUtilizadoActuacion(Cliente cliente) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE cliente " +
                "SET nombre = ?, direccion = ?, mail1 = ?, mail2 = ?," +
                " telefono1 = ?, telefono2 = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDireccion());
            preparedStatement.setString(3, cliente.getMail1());
            preparedStatement.setString(4, cliente.getNombre());
            preparedStatement.setString(5, cliente.getTelef1());
            preparedStatement.setString(6, cliente.getTelef2());
            preparedStatement.setInt(7, cliente.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + cliente.getId());
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

    private ArrayList<Cliente> setListaMaterialUtilizadoActuacion(ResultSet resultSet) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            while (resultSet.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setMail1(resultSet.getString("mail1"));
                cliente.setTelef1(resultSet.getString("telefono1"));
                cliente.setMail2(resultSet.getString("mail2"));
                cliente.setTelef2(resultSet.getString("telefono2"));

                clientes.add(cliente);
            }
            BBDD.close();
            return clientes;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return clientes;
        }
    }

    // endregion
}
