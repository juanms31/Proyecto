package com.company.BaseDatos;

import com.company.Controlador.ControladorProveedor;
import com.company.Entidades.Cliente;
import com.company.Entidades.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDProveedor {

    //region CONSTRUCTORES

    public CRUDProveedor(ControladorProveedor controladorProveedor) {
        this.controladorProveedor = controladorProveedor;
    }

    public CRUDProveedor() {}

    //endregion

    // region Metodos CRUD

    public ArrayList<Proveedor> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM proveedor";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaProveedor = setListaProveedor(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Proveedor = exito");
            return  listaProveedor;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Proveedor = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createProveedor(Proveedor proveedor) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO proveedor" +
                " VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, proveedor.getCIF());
            preparedStatement.setString(3, proveedor.getNombre_proveedor());
            preparedStatement.setString(4, proveedor.getDireccion());
            preparedStatement.setString(5, proveedor.getMail1());
            preparedStatement.setString(6, proveedor.getTelefono1());
            preparedStatement.setString(7, proveedor.getMail2());
            preparedStatement.setString(8, proveedor.getTelefono2());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "createProveedor en Proveedor = no afecto a ningun registro");
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowProveedor = 0;
            if(generatedKeys.next()){
                idRowProveedor = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createProveedor en Proveedor = exito");
            return idRowProveedor;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createProveedor en Proveedor = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public Cliente readProveedor(int cod){

        return new Cliente();
    }

    public boolean deleteProveedor(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM proveedor WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteProveedor en Proveedor = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteProveedor en Proveedor = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateProveedor(Proveedor proveedor){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE proveedor " +
                "SET nombre_proveedor = ?, direccion = ?, mail_1 = ?, mail_2 = ?," +
                " telefono_1 = ?, telefono_2 = ?, CIF = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, proveedor.getNombre_proveedor());
            preparedStatement.setString(2, proveedor.getDireccion());
            preparedStatement.setString(3, proveedor.getMail1());
            preparedStatement.setString(4, proveedor.getMail2());
            preparedStatement.setString(5, proveedor.getTelefono1());
            preparedStatement.setString(6, proveedor.getTelefono2());
            preparedStatement.setString(7, proveedor.getCIF());
            preparedStatement.setInt(8, proveedor.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "updateProveedor en Proveedor = no afecto a ningun registro");
                throw  new SQLException("No se pudo actualizar registro id = " + proveedor.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateProveedor en Proveedor = exito");
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateProveedor en Proveedor = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
        }

        return false;
    }

    // endregion

    //region consultas Meta Datos

    public String[] getColumnsProveedor(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_PROVEEDORES = "SELECT * FROM proveedor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROVEEDORES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumnsProveedor en Proveedor = no devolvio ninguna columan");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsProveedor en Proveedor = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsProveedor en Proveedor = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Metodos privados

    private ArrayList<Proveedor> setListaProveedor(ResultSet resultSet) {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        try {
            while (resultSet.next()){
                Proveedor proveedor = new Proveedor();
                proveedor.setId(resultSet.getInt("id"));
                proveedor.setCIF(resultSet.getString("CIF"));
                proveedor.setNombre_proveedor(resultSet.getString("nombre_proveedor"));
                proveedor.setDireccion(resultSet.getString("direccion"));
                proveedor.setMail1(resultSet.getString("mail_1"));
                proveedor.setTelefono1(resultSet.getString("telefono_1"));
                proveedor.setMail2(resultSet.getString("mail_2"));
                proveedor.setTelefono2(resultSet.getString("telefono_2"));

                proveedores.add(proveedor);
            }
            BBDD.close();
            return proveedores;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return proveedores;
        }
    }

    // endregion

    //region Variables

    private ControladorProveedor controladorProveedor;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDProveedor");

    //endregion
}
