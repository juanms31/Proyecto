package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.Trabajador;
import com.company.Entidades.Vacaciones;

import java.sql.*;
import java.util.ArrayList;

public class CRUDTrabajador {

    // region Metodos CRUD

    public ArrayList<Trabajador> readAllTrabajador() throws SQLException {
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
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }

    }

    public int createTrabajador(Trabajador trabajador) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO trabajador" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setDate(2, trabajador.getFnac());
            preparedStatement.setString(3, trabajador.getNacionalidad());
            preparedStatement.setString(4, trabajador.getNombre());
            preparedStatement.setString(5, trabajador.getApellido1());
            preparedStatement.setString(6, trabajador.getPuesto());
            preparedStatement.setDouble(7, trabajador.getSalario());
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

    public boolean deleteTrabajador(int id) throws SQLException {
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
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public boolean updateTrabajador(Trabajador trabajador) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE trabajador " +
                "SET fnac = ?, nacionalidad = ?, nombre = ?, apellidos = ?," +
                " puesto = ?, salario = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setDate(1, trabajador.getFnac());
            preparedStatement.setString(2, trabajador.getNacionalidad());
            preparedStatement.setString(3, trabajador.getNombre());
            preparedStatement.setString(4, trabajador.getApellido1());
            preparedStatement.setString(5, trabajador.getPuesto());
            preparedStatement.setDouble(6, trabajador.getSalario());
            preparedStatement.setInt(7, trabajador.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + trabajador.getId());
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

    private ArrayList<Trabajador> setListaTrabajador(ResultSet resultSet) {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        try {
            while (resultSet.next()){
                Trabajador trabajador = new Trabajador();
                trabajador.setId(resultSet.getInt("id"));
                trabajador.setNombre(resultSet.getString("nombre"));
                trabajador.setApellido1(resultSet.getString("apellidos"));
                trabajador.setFnac(resultSet.getDate("fnac"));
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

    public static void main(String[] args) throws SQLException {
        CRUDTrabajador crudTrabajador = new CRUDTrabajador();
        var listaTrabajadores = crudTrabajador.readAllTrabajador();
        System.out.println("Lista: " + listaTrabajadores.get(0).toString());

        var borradoOK = crudTrabajador.deleteTrabajador(3);
        System.out.println(borradoOK);

        Trabajador trabajador = new Trabajador();
        trabajador.setNombre("Nombre nuevo desde java");
        trabajador.setApellido1("Apillidos trabajdor");
        trabajador.setFnac(Date.valueOf("1997-10-10"));
        trabajador.setNacionalidad("Javera");
        trabajador.setPuesto("Programador jhava");
        trabajador.setSalario(1289.20); //TODO hay que hacer un trabajador primero

        int idRowTrabajador = 0;
        idRowTrabajador = crudTrabajador.createTrabajador(trabajador);
        System.out.println("Nuevo trabajador con id: " + idRowTrabajador);
        trabajador.setId(idRowTrabajador);

        //UPDATE
        trabajador.setPuesto("Programador modificado");
        boolean updateOk = crudTrabajador.updateTrabajador(trabajador);
        if (updateOk){
            System.out.println("Actualizado");
        }else{
            System.out.println("Error");
        }
    }
}
