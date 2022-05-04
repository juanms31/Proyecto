package com.company.BaseDatos;

import com.company.Entidades.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDMaterial {

    //region Atributos privados

    private Connection connection;

    //endregion

    private void connect() {
        String url = "//127.0.0.1:3306/proyectodam";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:" + url, "root", null);
            //TODO incluir mensajes log para BBDD
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        try {
            connection.close();
            System.out.println("Conexion cerrada con exito");
        } catch (SQLException sqlException) {
            Logger.getLogger(CRUDMaterial.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    //region Metodos CRUD

    public ArrayList<Material> getAll(){
        connect();
        try {
            final String SELECT_MATERIALES = "SELECT * FROM material";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_MATERIALES);
            var listMateriales = setListMateriales(resultSet);

            close();
            return listMateriales;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return null;
        }
    }

    private ArrayList<Material> setListMateriales(ResultSet resultSet) {
        ArrayList<Material> materiales = new ArrayList<>();
        try {
            while (resultSet.next()){
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setCalidad(resultSet.getString("calidad"));
                material.setCodigo(resultSet.getString("cod"));
                material.setDescripcion(resultSet.getString("descripcion"));
                material.setEspecificacion(resultSet.getString("especificacion"));
                material.setEspesor(resultSet.getDouble("espesor"));
                material.setGrupo(resultSet.getString("grupo"));
                //TODO plantear si incluir o no id de foreigh key
                material.setPrecio1(resultSet.getDouble("precio1"));
                material.setPrecio2(resultSet.getDouble("precio2"));
                material.setPrecio3(resultSet.getDouble("precio2"));
                material.setProveedor1(resultSet.getString("proveedor1"));
                material.setProveedor2(resultSet.getString("proveedor2"));
                material.setProveedor3(resultSet.getString("proveedor3"));
                material.setUnidad(resultSet.getString("unidad"));

                materiales.add(material);
            }
            close();
            return materiales;
        } catch (SQLException e) {
            //TODO incluir mensajes log para BBDD
            e.printStackTrace();
            close();
            return  materiales;
        }
    }

    public int addMaterial(Material material) throws SQLException {
        connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO material" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, material.getGrupo());
            preparedStatement.setString(3, material.getCodigo());
            preparedStatement.setString(4, material.getDescripcion());
            preparedStatement.setString(5, material.getEspecificacion());
            preparedStatement.setString(6, material.getUnidad());
            preparedStatement.setDouble(7, material.getEspesor());
            preparedStatement.setString(8, material.getCalidad());
            preparedStatement.setString(9, material.getProveedor1());
            preparedStatement.setDouble(10, material.getPrecio1());
            preparedStatement.setString(11, material.getProveedor2());
            preparedStatement.setDouble(12, material.getPrecio2());
            preparedStatement.setString(13, material.getProveedor3());
            preparedStatement.setDouble(14, material.getPrecio3());
            //Es necesario comprobar si entran a null las Foreigh key
            if (material.getIdGrupo() == null){
                preparedStatement.setNull(15, 1);
            }else{
                preparedStatement.setInt(15, material.getIdGrupo());
            }

            if (material.getIdEspecifiacion() == null){
                preparedStatement.setNull(16, 1);
            } else{
                preparedStatement.setInt(16, material.getIdEspecifiacion());
            }

            if (material.getIdUnidad() == null){
                preparedStatement.setNull(17, 1);
            }else{
                preparedStatement.setInt(17, material.getIdUnidad());
            }
            //Extraemos el id autogenerado
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw  new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowMaterial = 0;
            if (generatedKeys.next()){
                idRowMaterial = generatedKeys.getInt(1);
            }
            return idRowMaterial;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return 0;
        } finally {
            if (!connection.isClosed()){
                close();
            }
        }
    }

    public boolean deleteMaterial(int id){
        connect();
        final String QUERY_DELETE = "DELETE FROM material WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }


    //endregion

    public static void main(String[] args) {
        CRUDMaterial crudMaterial = new CRUDMaterial();
        var listMateriales = crudMaterial.getAll();
        System.out.println("Lista: " + listMateriales.get(0).toString());
        //System.out.println("Lista: " + listMateriales.get(1).toString());
        var borradoOK = crudMaterial.deleteMaterial(2);
        System.out.println(borradoOK);

        Material material = new Material();
        material.setGrupo("Grupo");
        material.setCodigo("cod");
        material.setDescripcion("Descripcion");
        material.setEspecificacion("Especificacion");
        material.setUnidad("Un");
        material.setEspesor(120.2);
        material.setCalidad("Calidad");
        material.setProveedor1("Proveedor1");
        material.setPrecio1(111.1);
        material.setProveedor2("Proovedor2");
        material.setPrecio2(222.2);
        material.setProveedor3("Proveedor3");
        material.setPrecio3(333.3);
        material.setIdGrupo(null);
        material.setIdEspecifiacion(null);
        material.setIdUnidad(null);

        int idRowMaterial = 0;
        try {
            idRowMaterial = crudMaterial.addMaterial(material);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Nuevo registro con id = " + idRowMaterial);
    }
}
