package com.company.BaseDatos;

import com.company.Controlador.ControladorCertificacion;
import com.company.Entidades.Actuacion;
import com.company.Entidades.Certificacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDCertificacion {
    public CRUDCertificacion(ControladorCertificacion controladorCertificacion) {


    }

    // region Metodos CRUD

    public ArrayList<Certificacion> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM certificacion";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaCertificaciones = setListaCertificacion(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Certificacion = exito");
            return  listaCertificaciones;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Certificacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createCertificacion(Certificacion certificacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO certificacion" +
                " VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setInt(2, certificacion.getActuacion().getId());
            preparedStatement.setDate(3, certificacion.getFecha_certificacion());
            preparedStatement.setDouble(4, certificacion.getValor());
            preparedStatement.setString(5, certificacion.getObservaciones());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowCertificacion = 0;
            if(generatedKeys.next()){
                idRowCertificacion = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createCertificacion en Certificacion = exito");
            return idRowCertificacion;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createCertificacion en Certificacion = "  + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public Certificacion readCertificacion(int cod){

        return new Certificacion();
    }

    public boolean deleteCertificacion(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM certificacion WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteCertificacion en Certificacion = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteCertificacion en Certificacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateCertificacion(Certificacion certificacion){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE certificacion " +
                "SET fecha_certificacion = ?, valor = ?, observaciones = ?, id_actuacion = ?" +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setDate(1, certificacion.getFecha_certificacion());
            preparedStatement.setDouble(2, certificacion.getValor());
            preparedStatement.setString(3, certificacion.getObservaciones());
            preparedStatement.setInt(4, certificacion.getActuacion().getId());
            System.out.println("ID CERT: " + certificacion.getId());
            preparedStatement.setInt(5, certificacion.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "updateCertificacion en Certificacion = no afecto a ningun registro");
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateCertificacion en Certificacion = exito");
                return true;
            }
            LOGGER.log(Level.WARNING, "updateCertificacion en Certificacion = afectó a mas de un registro");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateCertificacion en Certificacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    // endregion

    //region Metodos privados

    private ArrayList<Certificacion> setListaCertificacion(ResultSet resultSet) {
        ArrayList<Certificacion> certificaciones = new ArrayList<>();
        try {
            while (resultSet.next()){
                Certificacion certificacion = new Certificacion();
                certificacion.setId(resultSet.getInt("id"));
                certificacion.setFecha_certificacion(resultSet.getDate("fecha_certificacion"));
                certificacion.setValor(resultSet.getDouble("valor"));
                certificacion.setObservaciones(resultSet.getString("observaciones"));
                certificacion.setActuacion(getActuacionFromID(resultSet.getInt("id_actuacion")));

                certificaciones.add(certificacion);
            }
            BBDD.close();
            return certificaciones;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return certificaciones;
        }
    }

    // endregion

    //region ATRIBUTOS

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDCertificacion");

    public String[] getColumnsCertificacion(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_CERTIFICACION= "SELECT * FROM certificacion";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CERTIFICACION);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumnsCertificacion en Certificacion = no devolvió columnas");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsCertificacion en Certificacion = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsCertificacion en Certificacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    private Actuacion getActuacionFromID(int id_actuacion){
        Actuacion actuacion1 = new Actuacion();
        for(Actuacion actuacion : listActuaciones){
            if(actuacion.getId() == id_actuacion) actuacion1 = actuacion;
        }

        return actuacion1;
    }


    private ArrayList<Actuacion> getActuaciones(){
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuaciones = crudActuacion.getAll();
        return listActuaciones;
    }

    ArrayList<Actuacion> listActuaciones = getActuaciones();


    //endregion
}

