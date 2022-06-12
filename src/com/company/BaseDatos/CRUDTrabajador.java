package com.company.BaseDatos;

import com.company.Controlador.ControladorTrabajador;
import com.company.Entidades.Cliente;
import com.company.Entidades.Trabajador;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDTrabajador {

    public CRUDTrabajador(ControladorTrabajador controladorTrabajador){
        this.controladorTrabajador = controladorTrabajador;

    }

    public CRUDTrabajador(){

    }
    // region Metodos CRUD

    public ArrayList<Trabajador> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_TRABAJADORES = "SELECT * FROM trabajador";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_TRABAJADORES);
            var listaTrabajadores = setListaTrabajador(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Trabajador = exito");
            return  listaTrabajadores;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createTrabajador(Trabajador trabajador) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO trabajador" +
                " VALUES (?,?, ?, ?,?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, trabajador.getDNI());
            preparedStatement.setString(3, trabajador.getNombre());
            preparedStatement.setString(4, trabajador.getApellidos());
            preparedStatement.setString(5, trabajador.getTelefono());
            preparedStatement.setDate(6, trabajador.getFnac());
            preparedStatement.setString(7, trabajador.getNacionalidad());
            preparedStatement.setString(8, trabajador.getPuesto());
            preparedStatement.setDouble(9, trabajador.getSalario());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "createTrabajador en Trabajador = no afecto a ningun registro");
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowTrabajador = 0;
            if(generatedKeys.next()){
                idRowTrabajador = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createTrabajador en Trabajador = exito");
            return idRowTrabajador;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createTrabajador en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public Cliente readTrabajador(int cod){

        return new Cliente();
    }

    public boolean deleteTrabajador(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM trabajador WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteTrabajador en Trabajador = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteTrabajador en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateTrabajador(Trabajador trabajador) {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE trabajador " +
                "SET fecha_nacimiento = ?, nacionalidad = ?, nombre = ?, apellidos = ?," +
                " puesto = ?, salario = ?, DNI = ?, telefono = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setDate(1, trabajador.getFnac());
            preparedStatement.setString(2, trabajador.getNacionalidad());
            preparedStatement.setString(3, trabajador.getNombre());
            preparedStatement.setString(4, trabajador.getApellidos());
            preparedStatement.setString(5, trabajador.getPuesto());
            preparedStatement.setDouble(6, trabajador.getSalario());
            preparedStatement.setString(7, trabajador.getDNI());
            preparedStatement.setString(8, trabajador.getTelefono());
            preparedStatement.setInt(9, trabajador.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "updateTrabajador en Trabajador = no afecto a ningun registro");
                throw  new SQLException("No se pudo actualizar registro id = " + trabajador.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateTrabajador en Trabajador = exito");
                return true;
            }
            LOGGER.log(Level.WARNING, "updateTrabajador en Trabajador = afecto a mas de un registro");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateTrabajador en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    // endregion

    //region consultas Meta Datos

    public String[] getColumnsTrabajador(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_TRABAJADORES = "SELECT * FROM trabajador";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_TRABAJADORES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumnsTrabajador en Trabajador = devolvio 0 columnas");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsTrabajador en Trabajador = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsTrabajador en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Metodos privados

    private ArrayList<Trabajador> setListaTrabajador(ResultSet resultSet) {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        try {
            while (resultSet.next()){
                Trabajador trabajador = new Trabajador();
                trabajador.setId(resultSet.getInt("id"));
                trabajador.setDNI(resultSet.getString("DNI"));
                trabajador.setTelefono(resultSet.getString("telefono"));
                trabajador.setNombre(resultSet.getString("nombre"));
                trabajador.setApellidos(resultSet.getString("apellidos"));
                trabajador.setFnac(resultSet.getDate("fecha_nacimiento"));
                trabajador.setNacionalidad(resultSet.getString("nacionalidad"));
                trabajador.setPuesto(resultSet.getString("puesto"));
                trabajador.setSalario(resultSet.getDouble("salario"));

                trabajadores.add(trabajador);
            }
            BBDD.close();
            return trabajadores;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return trabajadores;
        }
    }

    // endregion

    //region Variables

    private ControladorTrabajador controladorTrabajador;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDTrabajador");

    public int getTrabajadorByDNI(String dni) {
        Connection connection = BBDD.connect();
        // SELECT * FROM `trabajador` WHERE DNI = '21035545K'
        final String QUERY_SELECT = "SELECT * FROM `trabajador` WHERE DNI = " + "'" + dni + "'";
        int id = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_SELECT);
            if (resultSet.next()){
                id = resultSet.getInt("id");
            }
            BBDD.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteTrabajador en Trabajador = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
        }
        return id;
    }

    //endregion
}
