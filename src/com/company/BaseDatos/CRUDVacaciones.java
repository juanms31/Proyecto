package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;
import com.company.Entidades.Vacaciones;

import java.sql.*;
import java.util.ArrayList;

public class CRUDVacaciones {

    // region Metodos CRUD

    public ArrayList<Vacaciones> readAllVacaciones() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_VACACIONES = "SELECT * FROM vacaciones";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_VACACIONES);
            var listaVacaciones = setListaVacaciones(resultSet);

            BBDD.close();
            return  listaVacaciones;
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

    public int createVacaciones(Vacaciones vacaciones) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO vacaciones" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setDate(2, vacaciones.getFecha_solicitada_inicio());
            preparedStatement.setDate(3, vacaciones.getFecha_solicitada_fin());
            preparedStatement.setDate(4, vacaciones.getFecha_aprobada_inicio());
            preparedStatement.setDate(5, vacaciones.getFecha_aprobada_fin());
            preparedStatement.setString(6, vacaciones.getObservaciones());
            preparedStatement.setInt(7, vacaciones.getIdTrabajador());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowVacaciones = 0;
            if(generatedKeys.next()){
                idRowVacaciones = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowVacaciones;
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

    public SeguimientoLaboral readVacaciones(int cod){

        return new SeguimientoLaboral();
    }

    public boolean deleteVacaciones(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM vacaciones WHERE id = ?";
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

    public boolean updateVacaciones(Vacaciones vacaciones) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE vacaciones " +
                "SET fecha_solicitada_inicio = ?, fecha_solicitada_fin = ?, fecha_aprobada_inicio = ?, fecha_aprobada_fin = ?," +
                " observaciones = ?, id_trabajador = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setDate(1, vacaciones.getFecha_solicitada_inicio());
            preparedStatement.setDate(2, vacaciones.getFecha_solicitada_fin());
            preparedStatement.setDate(3, vacaciones.getFecha_aprobada_inicio());
            preparedStatement.setDate(4, vacaciones.getFecha_aprobada_fin());
            preparedStatement.setString(5, vacaciones.getObservaciones());
            preparedStatement.setInt(6, vacaciones.getIdTrabajador());
            preparedStatement.setInt(7, vacaciones.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + vacaciones.getId());
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

    private ArrayList<Vacaciones> setListaVacaciones(ResultSet resultSet) {
        ArrayList<Vacaciones> listaVacaciones = new ArrayList<>();
        try {
            while (resultSet.next()){
                Vacaciones vacaciones = new Vacaciones();
                vacaciones.setId(resultSet.getInt("id"));
                vacaciones.setFecha_aprobada_fin(resultSet.getDate("fecha_aprobada_fin"));
                vacaciones.setFecha_aprobada_inicio(resultSet.getDate("fecha_aprobada_inicio"));
                vacaciones.setFecha_solicitada_fin(resultSet.getDate("fecha_solicitada_fin"));
                vacaciones.setFecha_solicitada_inicio(resultSet.getDate("fecha_solicitada_inicio"));
                vacaciones.setIdTrabajador(resultSet.getInt("id_trabajador"));
                vacaciones.setObservaciones(resultSet.getString("observaciones"));

                listaVacaciones.add(vacaciones);
            }
            BBDD.close();
            return listaVacaciones;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listaVacaciones;
        }
    }

    // endregion
}
