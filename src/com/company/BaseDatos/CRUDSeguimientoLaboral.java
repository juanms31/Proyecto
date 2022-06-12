package com.company.BaseDatos;

import com.company.Controlador.ControladorSeguimiento;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDSeguimientoLaboral {

    public CRUDSeguimientoLaboral(ControladorSeguimiento controladorSeguimiento) {
        this.controladorSeguimiento = controladorSeguimiento;
    }


    // region Metodos CRUD

    public ArrayList<SeguimientoLaboral> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM seguimientolaboral";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaSeguimientoLaboral = setListaSeguimientoLaboral(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en SeguimientoLaboral = exito");
            return  listaSeguimientoLaboral;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en SeguimientoLaboral = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_INSERT = "INSERT INTO seguimientolaboral" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, seguimientoLaboral.getIdTrabajador());
            preparedStatement.setString(3, seguimientoLaboral.getTipo());
            preparedStatement.setInt(4, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(5, seguimientoLaboral.getAno());
            preparedStatement.setInt(6, seguimientoLaboral.getDia());
            preparedStatement.setInt(7, seguimientoLaboral.getMes());
            preparedStatement.setString(8, seguimientoLaboral.getHora_entrada());
            preparedStatement.setString(9, seguimientoLaboral.getHora_salida());
            preparedStatement.setInt(10, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(11, seguimientoLaboral.getHoras_extra());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "createSeguimientoLaboral en SeguimientoLaboral = no afecto a ningun registro");
                throw new SQLException("No se pudo guardar");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRow = 0;
            if(generatedKeys.next()){
                idRow = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createSeguimientoLaboral en SeguimientoLaboral = exito");
            return idRow;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createSeguimientoLaboral en SeguimientoLaboral = " + e.getMessage());
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

    public boolean deleteSeguimientoLaboral(int id){
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM seguimientolaboral WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteSeguimientoLaboral en SeguimientoLaboral = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteSeguimientoLaboral en SeguimientoLaboral = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE seguimientolaboral " +
                " SET ano = ?, dia = ?, mes = ?, hora_entrada = ?, hora_salida = ?, horas_totales = ?, " +
                " horas_extra = ?, id_actuacion = ?, id_trabajador = ?, tipo = ? " +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, seguimientoLaboral.getAno());
            preparedStatement.setInt(2, seguimientoLaboral.getDia());
            preparedStatement.setInt(3, seguimientoLaboral.getMes());
            preparedStatement.setString(4, seguimientoLaboral.getHora_entrada());
            preparedStatement.setString(5, seguimientoLaboral.getHora_salida());
            preparedStatement.setInt(6, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(7, seguimientoLaboral.getHoras_extra());
            preparedStatement.setInt(8, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(9, seguimientoLaboral.getIdTrabajador());
            preparedStatement.setString(10, seguimientoLaboral.getTipo());
            preparedStatement.setInt(11, seguimientoLaboral.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "updateSeguimientoLaboral en SeguimientoLaboral = no afecto a ningun registro");
                throw  new SQLException("No se pudo actualizar registro id = " + seguimientoLaboral.getId());
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateSeguimientoLaboral en SeguimientoLaboral = exito");
                return true;
            }
            LOGGER.log(Level.WARNING, "updateSeguimientoLaboral en SeguimientoLaboral = afecto a mas de un registro");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateSeguimientoLaboral en SeguimientoLaboral = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
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

                seguimientoLaboral.setFechaCompleta(procesarFechaCompleta(resultSet.getInt("dia") + "-" +
                        resultSet.getInt("mes") + "-" +
                        resultSet.getInt("ano")));

                seguimientoLaboral.setTipo(resultSet.getString("tipo"));
                seguimientoLaboral.setHora_entrada(resultSet.getString("hora_entrada"));
                seguimientoLaboral.setHora_salida(resultSet.getString("hora_salida"));
                seguimientoLaboral.setHoras_totales(resultSet.getInt("horas_totales"));
                seguimientoLaboral.setHoras_extra(resultSet.getInt("horas_extra"));
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

    private String procesarFechaCompleta(String s) {
        String[] splitted = s.split("-");

        if(splitted[0].length() == 1){
            splitted[0] = "0" + splitted[0];
        }

        if(splitted[1].length() == 1){
            splitted[1] = "0" + splitted[1];
        }

        String fecha = splitted[0] + "-" + splitted[1]  + "-" + splitted[2];
        return fecha;
    }

    //region consultas Meta Datos

    public String[] getColumsSeguimiento(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_SEGUIMIENTO = "SELECT * FROM SeguimientoLaboral";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SEGUIMIENTO);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumsSeguimiento en SeguimientoLaboral = no afecto a ninguna columna");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumsSeguimiento en SeguimientoLaboral = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumsSeguimiento en SeguimientoLaboral = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Variables

    ControladorSeguimiento controladorSeguimiento;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDSeguimientoLaboral");

    //endregion

}
