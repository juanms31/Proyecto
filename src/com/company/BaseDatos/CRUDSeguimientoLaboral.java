package com.company.BaseDatos;

import com.company.Controlador.ControladorProveedor;
import com.company.Controlador.ControladorSeguimiento;
import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

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
            return  listaSeguimientoLaboral;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    public int createSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO seguimientolaboral" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, seguimientoLaboral.getIdTrabajador());

            preparedStatement.setInt(3, seguimientoLaboral.getAno());
            preparedStatement.setInt(4, seguimientoLaboral.getDia());
            preparedStatement.setInt(5, seguimientoLaboral.getMes());
            preparedStatement.setInt(6, seguimientoLaboral.getHora_entrada());
            preparedStatement.setInt(7, seguimientoLaboral.getHora_salida());
            preparedStatement.setInt(8, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(9, seguimientoLaboral.getHoras_extra());

            preparedStatement.setInt(10, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(11, seguimientoLaboral.getIdTrabajador());
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

    public boolean deleteSeguimientoLaboral(int id){
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
        }
    }

    public boolean updateSeguimientoLaboral(SeguimientoLaboral seguimientoLaboral){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE seguimientolaboral " +
                "SET ano = ?, dia = ?, mes = ?, hora_entrada = ?, hora_salida = ?, horas_totales = ?" +
                " horas_extra = ?, id_actuacion = ?, id_trabajador = ?, tipo = ? " +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, seguimientoLaboral.getAno());
            preparedStatement.setInt(2, seguimientoLaboral.getDia());
            preparedStatement.setInt(3, seguimientoLaboral.getMes());
            preparedStatement.setInt(4, seguimientoLaboral.getHora_entrada());
            preparedStatement.setInt(5, seguimientoLaboral.getHora_salida());
            preparedStatement.setInt(6, seguimientoLaboral.getHoras_totales());
            preparedStatement.setDouble(7, seguimientoLaboral.getHoras_extra());
            preparedStatement.setInt(8, seguimientoLaboral.getIdActuacion());
            preparedStatement.setInt(9, seguimientoLaboral.getIdTrabajador());
            preparedStatement.setString(10, seguimientoLaboral.getTipo());
            preparedStatement.setInt(11, seguimientoLaboral.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + seguimientoLaboral.getId());
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

    private ArrayList<SeguimientoLaboral> setListaSeguimientoLaboral(ResultSet resultSet) {
        ArrayList<SeguimientoLaboral> ListaseguimientoLaboral = new ArrayList<>();
        try {
            while (resultSet.next()){
                SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();
                seguimientoLaboral.setId(resultSet.getInt("id"));
                seguimientoLaboral.setAno(resultSet.getInt("ano"));
                seguimientoLaboral.setDia(resultSet.getInt("dia"));
                seguimientoLaboral.setMes(resultSet.getInt("mes"));
                seguimientoLaboral.setHora_entrada(resultSet.getInt("hora_entrada"));
                seguimientoLaboral.setHora_salida(resultSet.getInt("hora_salida"));
                seguimientoLaboral.setHoras_totales(resultSet.getInt("horas_totales"));
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

    //region consultas Meta Datos

    public String[] getColumsSeguimiento(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_SEGUIMIENTO = "SELECT * FROM SeguimientoLaboral";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SEGUIMIENTO);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                System.out.println("Fallo en sacar los metatados");
            }
            BBDD.close();
            return  columnsName;
        } catch (SQLException e) {
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
    //endregion

}
