package com.company.BaseDatos;

import com.company.Entidades.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class CRUDCliente {

    // region Metodos CRUD

    public ArrayList<Cliente> readAllClientes() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_CLIENTES = "SELECT * FROM cliente";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CLIENTES);
            var listaClientes = setListaClientes(resultSet);

            BBDD.close();
            return  listaClientes;
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

    public int createCliente(Cliente cliente) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO cliente" +
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

    public boolean deleteCLiente(int id) throws SQLException {
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

    public boolean updateCliente(Cliente cliente) throws SQLException {
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

    private ArrayList<Cliente> setListaClientes(ResultSet resultSet) {
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

    public static void main(String[] args) throws SQLException {
        CRUDCliente crudCliente = new CRUDCliente();
        var listaclientes = crudCliente.readAllClientes();
        System.out.println("Lista: " + listaclientes.get(0).toString());

        var borradoOK = crudCliente.deleteCLiente(0);
        System.out.println(borradoOK);

        Cliente cliente = new Cliente();
        cliente.setNombre("Nombre clliente 2");
        cliente.setDireccion("Direccion cliente 2");
        cliente.setMail1("email cliente 2");
        cliente.setMail2("email2 cliente 2");
        cliente.setTelef1("tlf  cliente 2");
        cliente.setTelef2("tlf2  cliente 2");

        int idRowCliente = 0;
        idRowCliente = crudCliente.createCliente(cliente);
        System.out.println("Nuevo cliente con id: " + idRowCliente);
        cliente.setId(idRowCliente);

        //UPDATE
        cliente.setNombre("Nombre cliente 2 actualizado");
        boolean updateOk = crudCliente.updateCliente(cliente);
        if (updateOk){
            System.out.println("Actualizado");
        }else{
            System.out.println("Error");
        }
    }
}
