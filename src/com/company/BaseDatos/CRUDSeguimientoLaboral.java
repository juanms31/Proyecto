package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

public class CRUDSeguimientoLaboral {

    // region Metodos CRUD

    public ArrayList<SeguimientoLaboral> readAllSeguimientoLaboral() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM seguimientolaboral";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaSeguimientoLaboral = setListaSeguimientoLaboral(resultSet);

            BBDD.close();
            return  listaSeguimientoLaboral;
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

    public int createSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO seguimientolaboral" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, seguimientoLaboral.getAno());
            preparedStatement.setInt(3, seguimientoLaboral.getDia());
            preparedStatement.setInt(4, seguimientoLaboral.getMes());
            preparedStatement.setDate(5, seguimientoLaboral.getHora_entrada());
            preparedStatement.setDate(6, seguimientoLaboral.getHora_salida());
            preparedStatement.setDate(7, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(8, seguimientoLaboral.getHoras_extra());
            preparedStatement.setInt(9, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(10, seguimientoLaboral.getIdTrabajador());
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

    public SeguimientoLaboral readSeguimientoLaboral(int cod){

        return new SeguimientoLaboral();
    }

    public boolean deleteSeguimientoLaboral(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM cliente seguimientolaboral id = ?";
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

    public boolean updateSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE seguimientolaboral " +
                "SET ano = ?, dia = ?, mes = ?, hora_entrada = ?, hora_salida = ?, horas_totales = ?" +
                " horas_extra = ?, id_actuacion = ?, id_trabajador = ? " +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, seguimientoLaboral.getAno());
            preparedStatement.setInt(2, seguimientoLaboral.getDia());
            preparedStatement.setInt(3, seguimientoLaboral.getMes());
            preparedStatement.setDate(4, seguimientoLaboral.getHora_entrada());
            preparedStatement.setDate(5, seguimientoLaboral.getHora_salida());
            preparedStatement.setDate(6, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(7, seguimientoLaboral.getHoras_extra());
            preparedStatement.setInt(8, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(9, seguimientoLaboral.getIdTrabajador());
            preparedStatement.setInt(10, seguimientoLaboral.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + seguimientoLaboral.getId());
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

    private ArrayList<SeguimientoLaboral> setListaSeguimientoLaboral(ResultSet resultSet) {
        ArrayList<SeguimientoLaboral> ListaseguimientoLaboral = new ArrayList<>();
        try {
            while (resultSet.next()){
                SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();
                seguimientoLaboral.setId(resultSet.getInt("id"));
                seguimientoLaboral.setAno(resultSet.getInt("ano"));
                seguimientoLaboral.setDia(resultSet.getInt("dia"));
                seguimientoLaboral.setMes(resultSet.getInt("mes"));
                seguimientoLaboral.setHora_entrada(resultSet.getDate("hora_entrada"));
                seguimientoLaboral.setHora_salida(resultSet.getDate("hora_salida"));
                seguimientoLaboral.setHoras_totales(resultSet.getDate("horas_totales"));
                seguimientoLaboral.setHoras_extra(resultSet.getDouble("horas_extra"));
                seguimientoLaboral.setIdActuacion(resultSet.getInt("id_actuacion"));
                seguimientoLaboral.setIdTrabajador(resultSet.getInt("id_trabajador"));

                ListaseguimientoLaboral.add(seguimientoLaboral);
            }
            BBDD.close();
            return ListaseguimientoLaboral;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return ListaseguimientoLaboral;
        }
    }

    // endregion
}
