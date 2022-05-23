package com.company.BaseDatos;

import com.company.Controlador.ControladorTrabajador;
import com.company.Entidades.Cliente;
import com.company.Entidades.Trabajador;
import com.company.Entidades.Vacaciones;

import java.sql.*;
import java.util.ArrayList;

public class CRUDTrabajador {

    public CRUDTrabajador(ControladorTrabajador controladorTrabajador){
        this.controladorTrabajador = controladorTrabajador;

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
            return  listaTrabajadores;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createTrabajador(Trabajador trabajador) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO trabajador" +
                " VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, trabajador.getDNI());
            preparedStatement.setString(3, trabajador.getNombre());
            preparedStatement.setString(4, trabajador.getApellidos());
            preparedStatement.setDate(5, trabajador.getFnac());
            preparedStatement.setString(6, trabajador.getNacionalidad());
            preparedStatement.setString(7, trabajador.getPuesto());
            preparedStatement.setDouble(8, trabajador.getSalario());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowTrabajador = 0;
            if(generatedKeys.next()){
                idRowTrabajador = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowTrabajador;
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
            return  true;
        } catch (SQLException e) {
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
                " puesto = ?, salario = ?, DNI = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setDate(1, trabajador.getFnac());
            preparedStatement.setString(2, trabajador.getNacionalidad());
            preparedStatement.setString(3, trabajador.getNombre());
            preparedStatement.setString(4, trabajador.getApellidos());
            preparedStatement.setString(5, trabajador.getPuesto());
            preparedStatement.setDouble(6, trabajador.getSalario());
            preparedStatement.setString(7, trabajador.getDNI());
            preparedStatement.setInt(8, trabajador.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + trabajador.getId());
            if (affectedRows == 1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    // endregion

    //region Metodos privados

    private ArrayList<Trabajador> setListaTrabajador(ResultSet resultSet) {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        try {
            while (resultSet.next()){
                Trabajador trabajador = new Trabajador();
                trabajador.setId(resultSet.getInt("id"));
                trabajador.setDNI(resultSet.getString("DNI"));
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

    //endregion
}
