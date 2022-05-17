package com.company.BaseDatos;

import com.company.Entidades.Actuacion;
import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

public class CRUDActuacion {

    // region Metodos CRUD

    public ArrayList<Actuacion> readAllActuacion() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM actuacion";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaActuaciones = setListaActuacion(resultSet);

            BBDD.close();
            return  listaActuaciones;
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

    public int createActuacion(Actuacion actuacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO actuacion" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, actuacion.getEspecificacion());
            preparedStatement.setString(3, actuacion.getEstado());
            preparedStatement.setDate(4, actuacion.getFecha_solicitud());
            preparedStatement.setDate(5, actuacion.getFecha_envio());
            preparedStatement.setDate(6, actuacion.getFecha_comienzo());
            preparedStatement.setDate(7, actuacion.getFecha_finalizacion());
            preparedStatement.setString(8, actuacion.getDescripcion());
            preparedStatement.setDouble(9, actuacion.getImporte());
            preparedStatement.setString(10, actuacion.getHojaPlanificacion());
            preparedStatement.setString(11, actuacion.getHojaPresupuesto());
            preparedStatement.setDouble(12, actuacion.getTotalCertificicaciones());
            preparedStatement.setDouble(13, actuacion.getPorPertificar());
            preparedStatement.setInt(14, actuacion.getHorasOfertadas());
            preparedStatement.setInt(15, actuacion.getHorasEjecutadas());
            preparedStatement.setDouble(16, actuacion.getMaterialOfertado());
            preparedStatement.setDouble(17, actuacion.getGastoMaterial());
            preparedStatement.setDouble(18, actuacion.getResultadoBalance());
            preparedStatement.setInt(19, actuacion.getIdCliente());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowActuacion = 0;
            if(generatedKeys.next()){
                idRowActuacion = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowActuacion;
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

    public Actuacion readActuacion(int cod){

        return new Actuacion();
    }

    public boolean deleteActuacion(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM actuacion WHERE id = ?";
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

    public boolean updateActuacion(Actuacion actuacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE actuacion " +
                "SET especificacion = ?, estado = ?, fecha_solicitud = ?, fecha_envio = ?," +
                " fecha_comienzo = ?, fecha_finalizacion = ?, descripcion = ?, importe = ?, hoja_planificacion = ?, hoja_presupuesto = ?," +
                " total_certificaciones = ?, por_certificar = ?, horas_ofertadas = ?, horas_ejecutadas = ?, " +
                " material_ofertado = ?, gasto_material = ?, resultado_balance = ?, id_cliente = ?" +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, actuacion.getEspecificacion());
            preparedStatement.setString(2, actuacion.getEstado());
            preparedStatement.setDate(3, actuacion.getFecha_solicitud());
            preparedStatement.setDate(4, actuacion.getFecha_envio());
            preparedStatement.setDate(5, actuacion.getFecha_comienzo());
            preparedStatement.setDate(6, actuacion.getFecha_finalizacion());
            preparedStatement.setString(7, actuacion.getDescripcion());
            preparedStatement.setDouble(8, actuacion.getImporte());
            preparedStatement.setString(9, actuacion.getHojaPlanificacion());
            preparedStatement.setString(10, actuacion.getHojaPresupuesto());
            preparedStatement.setDouble(11, actuacion.getTotalCertificicaciones());
            preparedStatement.setDouble(12, actuacion.getPorPertificar());
            preparedStatement.setInt(13, actuacion.getHorasOfertadas());
            preparedStatement.setInt(14, actuacion.getHorasEjecutadas());
            preparedStatement.setDouble(15, actuacion.getMaterialOfertado());
            preparedStatement.setDouble(16, actuacion.getGastoMaterial());
            preparedStatement.setDouble(17, actuacion.getResultadoBalance());
            preparedStatement.setInt(18, actuacion.getIdCliente());
            preparedStatement.setInt(19, actuacion.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + actuacion.getId());
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

    private ArrayList<Actuacion> setListaActuacion(ResultSet resultSet) {
        ArrayList<Actuacion> listaActuacion = new ArrayList<>();
        try {
            while (resultSet.next()){
                Actuacion actuacion = new Actuacion();
                actuacion.setId(resultSet.getInt("id"));
                actuacion.setEspecificacion(resultSet.getString("especificacion"));
                actuacion.setEstado(resultSet.getString("estado"));
                actuacion.setFecha_solicitud(resultSet.getDate("fecha_solicitud"));
                actuacion.setFecha_envio(resultSet.getDate("fecha_envio"));
                actuacion.setFecha_comienzo(resultSet.getDate("fecha_comienzo"));
                actuacion.setFecha_finalizacion(resultSet.getDate("fecha_finalizacion"));
                actuacion.setDescripcion(resultSet.getString("descripcion"));
                actuacion.setImporte(resultSet.getDouble("importe"));
                actuacion.setHojaPlanificacion(resultSet.getString("hoja_planificacion"));
                actuacion.setHojaPresupuesto(resultSet.getString("hoja_presupuesto"));
                actuacion.setTotalCertificicaciones(resultSet.getDouble("total_certificaciones"));
                actuacion.setPorPertificar(resultSet.getDouble("por_certificacr"));
                actuacion.setHorasOfertadas(resultSet.getInt("horas_ofertadas"));
                actuacion.setHorasEjecutadas(resultSet.getInt("horas_ejecutadas"));
                actuacion.setMaterialOfertado(resultSet.getDouble("material_ofertado"));
                actuacion.setGastoMaterial(resultSet.getDouble("gasto_material"));
                actuacion.setResultadoBalance(resultSet.getDouble("resultado_balance"));
                actuacion.setIdCliente(resultSet.getInt("id_cliente"));

                listaActuacion.add(actuacion);
            }
            BBDD.close();
            return listaActuacion;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listaActuacion;
        }
    }

    // endregion
}
