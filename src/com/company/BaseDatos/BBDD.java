package com.company.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BBDD {

    //region Atributos privados

    static private Connection connection;

    //endregion

    //region Conection

    public static Connection connect() {
        String url = "//127.0.0.1:3306/proyectodam";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:" + url, "root", null);
            //TODO incluir mensajes log para BBDD
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return connection;
        }
    }

    public static void close(){
        try {
            connection.close();
            System.out.println("Conexion cerrada con exito");
        } catch (SQLException sqlException) {
            Logger.getLogger(CRUDMaterial.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    //endregion
}
