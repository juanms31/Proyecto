package com.company.BaseDatos;

import com.company.Controlador.ControladorAlbaran;
import com.company.Entidades.Actuacion;
import com.company.Entidades.Albaran;
import com.company.Entidades.Cliente;
import com.company.Entidades.SeguimientoLaboral;

import java.sql.*;
import java.util.ArrayList;

public class CRUDAlbaran {

    //region Constructor
    public CRUDAlbaran(ControladorAlbaran controladorAlbaran) {

    }

    public CRUDAlbaran() {

    }

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
            return  listaAlbaranes;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    public int createAlbaran(Albaran albaran) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO albaran" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, albaran.getConcepto());
            preparedStatement.setInt(3, albaran.getUnidades());
            preparedStatement.setDate(4, albaran.getFechaEntradaAlbaran());
            preparedStatement.setDouble(5, albaran.getPrecioUnidad());
            preparedStatement.setDouble(6, albaran.getBaseImponible());
            preparedStatement.setInt(7, albaran.getIdActuacion());
            preparedStatement.setInt(8, albaran.getIdProveedor());
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

    public Actuacion readAlbaran(int cod){

        return new Actuacion();
    }

    public boolean deleteAlbaran(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM albaran WHERE id = ?";
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

    public boolean updateAlbaran(Albaran albaran) {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE albaran " +
                "SET concepto = ?, unidades = ?, fecha_entrada_albarán = ?, precio_unitario = ?," +
                " base_imponible = ?, naturaleza = ?, id_actuacion = ?, id_proveedor = ?" +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, albaran.getConcepto());
            preparedStatement.setInt(2, albaran.getUnidades());
            preparedStatement.setDate(3, albaran.getFechaEntradaAlbaran());
            preparedStatement.setDouble(4, albaran.getPrecioUnidad());
            preparedStatement.setDouble(5, albaran.getBaseImponible());
            preparedStatement.setInt(7, albaran.getIdActuacion());
            preparedStatement.setInt(8, albaran.getIdProveedor());
            preparedStatement.setInt(9, albaran.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + albaran.getId());
            if (affectedRows == 1) return true;
            return false;
        } catch (SQLException e) {
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
                albaran.setConcepto(resultSet.getString("concepto"));
                albaran.setUnidades(resultSet.getInt("unidades"));
                albaran.setFechaEntradaAlbaran(resultSet.getDate("fecha_entrada_albarán")); //TODO qiuitar tilde de la bbdd
                albaran.setPrecioUnidad(resultSet.getDouble("precio_unitario"));
                albaran.setBaseImponible(resultSet.getDouble("base_imponible"));
                albaran.setIdActuacion(resultSet.getInt("id_actuacion"));
                albaran.setIdProveedor(resultSet.getInt("id_proveedor"));

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

    // endregion

    public String[] getColumnsAlbaran(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_PROVEEDORES = "SELECT * FROM albaran";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROVEEDORES);
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
}
