package com.company.BaseDatos;

import com.company.Entidades.SeguimientoLaboral;
import com.company.Entidades.Vacaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDVacaciones {

    // region Metodos CRUD

    public ArrayList<Vacaciones> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_VACACIONES = "SELECT * FROM vacaciones";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_VACACIONES);
            var listaVacaciones = setListaVacaciones(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "readAllVacaciones en Vacaciones = exito");
            return  listaVacaciones;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "readAllVacaciones en Vacaciones = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
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
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "createVacaciones en Vacaciones = afecto a 0 registros");
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowVacaciones = 0;
            if(generatedKeys.next()){
                idRowVacaciones = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createVacaciones en Vacaciones = exito");
            return idRowVacaciones;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createVacaciones en Vacaciones = " + e.getMessage());
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
            LOGGER.log(Level.INFO, "deleteVacaciones en Vacaciones = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteVacaciones en Vacaciones = " + e.getMessage());
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
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "deleteVacaciones en Vacaciones = afecto a 0 registros");
                throw  new SQLException("No se pudo actualizar registro id = " + vacaciones.getId());
            }
            if (affectedRows == 1){
                LOGGER.log(Level.INFO, "deleteVacaciones en Vacaciones = exito");
                return true;
            }
            LOGGER.log(Level.WARNING, "deleteVacaciones en Vacaciones = afecto a mas de 1 registros");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteVacaciones en Vacaciones = " + e.getMessage());
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
            LOGGER.log(Level.INFO, "setListaVacaciones en Vacaciones = exito");
            return listaVacaciones;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "setListaVacaciones en Vacaciones = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return listaVacaciones;
        }
    }

    // endregion

    //region Atributos

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDVacaciones");

    public int getDaysVacaciones(int idTrabajador) {
        int diasVacaciones = 0;
        Connection connection = BBDD.connect();
        String sql = "SELECT TIMESTAMPDIFF(DAY, v.fecha_aprobada_inicio, v.fecha_aprobada_fin) AS diasVacaciones\n" +
                "FROM vacaciones v WHERE v.id_trabajador = " + idTrabajador;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                diasVacaciones += resultSet.getInt("diasVacaciones");
            }
            return diasVacaciones;
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "getDaysVacaciones en Vacaciones = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return diasVacaciones;
        }
    }

    //endregion
}
