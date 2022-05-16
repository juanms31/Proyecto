package com.company.BaseDatos;

import com.company.Entidades.Cliente;
import com.company.Entidades.MOInstalacionMaterial;
import com.company.Entidades.SeguimientoLaboral;
import com.company.Entidades.Vacaciones;

import java.sql.*;
import java.util.ArrayList;

public class CRUDMOInstalacionMaterial {

    // region Metodos CRUD

    public ArrayList<MOInstalacionMaterial> readAllMOInstalacionMaterial() throws SQLException {
        Connection connection = BBDD.connect();
        final String SELECT_MOInstalacionMAterial = "SELECT * FROM moinstalacionmaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_MOInstalacionMAterial);
            var listaMOInstalacionMaterial = setListaMOInstalacionMaterial(resultSet);

            BBDD.close();
            return  listaMOInstalacionMaterial;
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

    public int createMOInstalacionMaterial(MOInstalacionMaterial moInstalacionMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO moinstalacionmaterial" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, moInstalacionMaterial.getNombreProveedor1());
            preparedStatement.setDouble(3, moInstalacionMaterial.getPrecio1());
            preparedStatement.setString(4, moInstalacionMaterial.getNombreProveedor2());
            preparedStatement.setDouble(5, moInstalacionMaterial.getPrecio2());
            preparedStatement.setInt(6, moInstalacionMaterial.getIdMaterial());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowMOInstalacionMaterial = 0;
            if(generatedKeys.next()){
                idRowMOInstalacionMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowMOInstalacionMaterial;
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

    public SeguimientoLaboral readMOInstalacionMaterial(int cod){

        return new SeguimientoLaboral();
    }

    public boolean deleteMOInstalacionMaterial(int id) throws SQLException {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM moinstalacionmaterial WHERE id = ?";
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

    public boolean updateMOInstalacionMaterial(MOInstalacionMaterial MOInstalacionMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE moinstalacionmaterial " +
                "SET proveedor1 = ?, precio1 = ?, proveedor2 = ?, precio2 = ?," +
                " id_material = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, MOInstalacionMaterial.getNombreProveedor1());
            preparedStatement.setDouble(2, MOInstalacionMaterial.getPrecio1());
            preparedStatement.setString(3, MOInstalacionMaterial.getNombreProveedor2());
            preparedStatement.setDouble(4, MOInstalacionMaterial.getPrecio2());
            preparedStatement.setInt(5, MOInstalacionMaterial.getIdMaterial());
            preparedStatement.setInt(6, MOInstalacionMaterial.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + MOInstalacionMaterial.getId());
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

    private ArrayList<MOInstalacionMaterial> setListaMOInstalacionMaterial(ResultSet resultSet) {
        ArrayList<MOInstalacionMaterial> ListaMOInstalacionMaterial = new ArrayList<>();
        try {
            while (resultSet.next()){
                MOInstalacionMaterial MOInstalacionMaterial = new MOInstalacionMaterial();
                MOInstalacionMaterial.setId(resultSet.getInt("id"));
                MOInstalacionMaterial.setIdMaterial(resultSet.getInt("id_material"));
                MOInstalacionMaterial.setPrecio1(resultSet.getDouble("precio1"));
                MOInstalacionMaterial.setPrecio2(resultSet.getDouble("precio2"));
                MOInstalacionMaterial.setNombreProveedor1(resultSet.getString("proveedor1"));
                MOInstalacionMaterial.setNombreProveedor2(resultSet.getString("proveedor2"));

                ListaMOInstalacionMaterial.add(MOInstalacionMaterial);
            }
            BBDD.close();
            return ListaMOInstalacionMaterial;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return ListaMOInstalacionMaterial;
        }
    }

    // endregion

    public static void main(String[] args) throws SQLException {
        CRUDMOInstalacionMaterial crudMOInstalacionMaterial = new CRUDMOInstalacionMaterial();
        //READ ALL
        var listaMOInstalacionMaterial = crudMOInstalacionMaterial.readAllMOInstalacionMaterial();
        System.out.println("Lista: " + listaMOInstalacionMaterial.get(0).toString());

        //DELETE
        var borradoOK = crudMOInstalacionMaterial.deleteMOInstalacionMaterial(1);
        System.out.println(borradoOK);

        //CREATE
        MOInstalacionMaterial moInstalacionMaterial = new MOInstalacionMaterial();
        moInstalacionMaterial.setNombreProveedor1("Proovedor1 creado java");
        moInstalacionMaterial.setPrecio1(23893.2);
        moInstalacionMaterial.setNombreProveedor2("Proovedor2 creado java");
        moInstalacionMaterial.setPrecio2(233.2);
        moInstalacionMaterial.setIdMaterial(1); //TODO hay que hacer un trabajador primero

        int idRowMOInstalacionMaterial = 0;
        idRowMOInstalacionMaterial = crudMOInstalacionMaterial.createMOInstalacionMaterial(moInstalacionMaterial);
        System.out.println("Nuevo moInstalacionMaterial con id: " + idRowMOInstalacionMaterial);
        moInstalacionMaterial.setId(idRowMOInstalacionMaterial);

        //UPDATE
        moInstalacionMaterial.setNombreProveedor2("Proveedor 2 modificado");
        boolean updateOk = crudMOInstalacionMaterial.updateMOInstalacionMaterial(moInstalacionMaterial);
        if (updateOk){
            System.out.println("Actualizado");
        }else{
            System.out.println("Error");
        }
    }
}

