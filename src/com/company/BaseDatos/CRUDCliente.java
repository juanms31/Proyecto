package com.company.BaseDatos;

import com.company.Controlador.ControladorCliente;
import com.company.Entidades.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDCliente {

    public CRUDCliente(ControladorCliente controladorCliente) {
        this.controladorCliente = controladorCliente;
    }

    public CRUDCliente(){

    }

    // region Metodos CRUD

    public ArrayList<Cliente> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_CLIENTES = "SELECT * FROM cliente";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CLIENTES);
            var listaClientes = setListaClientes(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Cliente = exito");
            return  listaClientes;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Cliente = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        } finally {
            try {
                if (!connection.isClosed()){
                    BBDD.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public int createCliente(Cliente cliente) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO cliente" +
                " VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, cliente.getCIF());
            preparedStatement.setString(3, cliente.getNombre());
            preparedStatement.setString(4, cliente.getDireccion());
            preparedStatement.setString(5, cliente.getMail1());
            preparedStatement.setString(6, cliente.getTelef1());
            preparedStatement.setString(7, cliente.getMail2());
            preparedStatement.setString(8, cliente.getTelef2());

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

    public Cliente readCliente(int cod){

        return new Cliente();
    }



    public boolean deleteCliente(int id)  {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM cliente WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteCliente en Cliente = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteCliente en Cliente = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            try {
                if (!connection.isClosed()){
                    BBDD.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateCliente(Cliente cliente){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE cliente " +
                "SET nombre = ?, direccion = ?, mail_1 = ?, mail_2 = ?," +
                " telefono_1 = ?, telefono_2 = ?, CIF = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDireccion());
            preparedStatement.setString(3, cliente.getMail1());
            preparedStatement.setString(4, cliente.getNombre());
            preparedStatement.setString(5, cliente.getTelef1());
            preparedStatement.setString(6, cliente.getTelef2());
            preparedStatement.setString(7, cliente.getCIF());
            preparedStatement.setInt(8, cliente.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "updateCliente en Cliente = no afecto a ningun cliente");
                throw  new SQLException("No se pudo actualizar registro id = " + cliente.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateCliente en Cliente = exito");
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateCliente en Cliente = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            try {
                if (!connection.isClosed()){
                    BBDD.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion

    //region consultas MetaDatos

    public String[] getColumnsCliente(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_CLIENTES = "SELECT * FROM cliente";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CLIENTES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.INFO, "getColumnsCliente en Cliente = devolvio 0 columnas");
            }
            LOGGER.log(Level.INFO, "getColumnsCliente en Cliente = exito");
            BBDD.close();
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsCliente en Cliente = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Metodos privados

    private ArrayList<Cliente> setListaClientes(ResultSet resultSet) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            while (resultSet.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setCIF(resultSet.getString("CIF"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setMail1(resultSet.getString("mail_1"));
                cliente.setTelef1(resultSet.getString("telefono_1"));
                cliente.setMail2(resultSet.getString("mail_2"));
                cliente.setTelef2(resultSet.getString("telefono_2"));

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

    //region ATRIBUTOS

    private ControladorCliente controladorCliente;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDCliente");

    //endregion
}
