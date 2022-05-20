package com.company.BaseDatos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BBDD {

    //region Constructor
    public BBDD(){

    }

    //endregion

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

//    public static Connection connect() {
//        final String url = "jdbc:mysql://localhost";
//        final String user = "root";
//        final String passwd = "";
//        boolean conected = true;
//        try {
//            connection = DriverManager.getConnection(url, user, passwd);
//            if (connection != null) {
//                JOptionPane.showConfirmDialog(null, "Conecction Granted!", "Succesfully Conected", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
//                conected = true;
//                PreparedStatement st;
//                String useDatabase = "USE proyectodam";
//                st = connection.prepareStatement(useDatabase);
//                st.execute();
//
//                return connection;
//            }
//        } catch (SQLException throwables) {
//            JOptionPane.showConfirmDialog(null, "Cannot connect. Error Message: " + throwables.getMessage(), "ERROR!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
//            conected = false;
//        }
//        return connection;
//    }

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
