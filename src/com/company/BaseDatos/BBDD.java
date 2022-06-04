package com.company.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BBDD {

    //region Constructor
    public BBDD(){

    }

    //endregion

    //region Conection

    public static Connection connect() {
        String url = "//127.0.0.1:3306/proyectodam";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:" + url, "root", null);
            LOGGER.log(Level.INFO, "Conexion establecida con exito");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.getStackTrace();
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
            LOGGER.log(Level.INFO, "Conexion cerrada con exito");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
            sqlException.getStackTrace();
        }
    }

    //endregion

    //region Atributos privados

    static private Connection connection;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDMaterial");

    //endregion
}
