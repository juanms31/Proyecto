package com.company.BaseDatos;

import com.company.Controlador.ControladorAlbaran;
import com.company.Entidades.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDAlbaran {

    //region Constructor
    public CRUDAlbaran(ControladorAlbaran controladorAlbaran) {

    }

    public CRUDAlbaran() {}

    //endregion

    // region Metodos CRUD

    public ArrayList<Albaran> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM albaran";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaAlbaranes = setListaAlbaran(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Albaran = exito");
            return  listaAlbaranes;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Albaran = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    public int createAlbaran(Albaran albaran) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO albaran" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, albaran.getCod());
            preparedStatement.setInt(3, albaran.getActuacion().getId());
            preparedStatement.setInt(4, albaran.getProveedor().getId());
            preparedStatement.setString(5, albaran.getConcepto());
            preparedStatement.setDate(6, albaran.getFechaEntradaAlbaran());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowActuacion = 0;
            if(generatedKeys.next()){
                idRowActuacion = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createAlbaran en Albaran = exito");
            return idRowActuacion;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Albaran = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public boolean deleteAlbaran(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM albaran WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteAlbaran en Albaran = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "deleteAlbaran en Albaran = "+ e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateAlbaran(Albaran albaran) {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE albaran " +
                "SET cod = ?,  fecha_entrada_albarán = ?, " +
                " id_actuacion = ?, id_proveedor = ?, concepto = ? " +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, albaran.getCod());
            System.out.println("FECHA ALBARAN: " + albaran.getFechaEntradaAlbaran());
            preparedStatement.setDate(2, albaran.getFechaEntradaAlbaran());
            preparedStatement.setInt(3, albaran.getActuacion().getId());
            preparedStatement.setInt(4, albaran.getProveedor().getId());
            preparedStatement.setString(5, albaran.getConcepto());
            preparedStatement.setInt(6, albaran.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0){
                LOGGER.log(Level.WARNING, "updateAlbaran en Albaran = no ha afectado a ningun registro");
                return false;
            }
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateAlbaran en Albaran = exito");
                return true;
            }
            if (affectedRows > 1) {
                LOGGER.log(Level.WARNING, "updateAlbaran en Albaran = ha afectado a mas de un registro");
            }

            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateAlbaran en Albaran = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        } finally {
            try {
                if (!connection.isClosed()){
                    BBDD.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion

    //region Metodos privados

    private ArrayList<Albaran> setListaAlbaran(ResultSet resultSet) {
        ArrayList<Albaran> albaranes = new ArrayList<>();
        try {
            while (resultSet.next()){
                Albaran albaran = new Albaran();
                albaran.setId(resultSet.getInt("id"));
                albaran.setCod(resultSet.getString("cod"));

                int id_actuacion = resultSet.getInt("id_actuacion");
                int id_proveedor = resultSet.getInt("id_proveedor");


                albaran.setProveedor(getProveedorFromId(id_proveedor));
                albaran.setActuacion(getActuacionFromId(id_actuacion));

                albaran.setId(resultSet.getInt("id"));
                albaran.setConcepto(resultSet.getString("concepto"));
                albaran.setFechaEntradaAlbaran(resultSet.getDate("fecha_entrada_albarán"));
                albaranes.add(albaran);
            }
            BBDD.close();
            return albaranes;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return albaranes;
        }
    }

    private Proveedor getProveedorFromId(int id_proveedor) {
        for(Proveedor proveedor : listProveedores){
            if(id_proveedor == proveedor.getId()) return proveedor;
        }
        return null;
    }

    private Actuacion getActuacionFromId(int id_Actuacion) {
        for(Actuacion Actuacion : listActuaciones){
            if(id_Actuacion == Actuacion.getId()) return Actuacion;
        }
        return null;
    }
    // endregion

    public String[] getColumnsAlbaran(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_ALBARANES= "SELECT * FROM albaran";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALBARANES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumnsAlbaran en Albaran = no devolvió columnas");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsAlbaran en Albaran = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsAlbaran en Albaran = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    private ArrayList<Proveedor> getProveedores(){
        CRUDProveedor crudProveedor = new CRUDProveedor();
        listProveedores = crudProveedor.getAll();
        return listProveedores;
    }

    ArrayList<Proveedor> listProveedores = getProveedores();
    
    private ArrayList<Actuacion> getActuaciones(){
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuaciones = crudActuacion.getAll();
        return listActuaciones;
    }

    //region ATRIBUTOS

    ArrayList<Actuacion> listActuaciones = getActuaciones();
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDAlbaran");

    //endregion


}
