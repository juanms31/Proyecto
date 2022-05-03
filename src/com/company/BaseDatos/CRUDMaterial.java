package com.company.BaseDatos;

import com.company.Entidades.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDMaterial {

    //region Atributos privados

    public boolean connectionOK;
    private String url;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    //endregion

    private boolean connect() {
        url = "//127.0.0.1:3306/proyectodam";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:" + url, "root", null);
            //TODO incluir mensajes log para BBDD
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_MATERIALES);
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

    public boolean deleteMaterial(int id){
        connect();
        String queryDelete = "DELETE FROM material WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(queryDelete);
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
    }
}
