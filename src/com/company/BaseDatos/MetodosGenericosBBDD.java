package com.company.BaseDatos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetodosGenericosBBDD {

    public static String[] getColumnTable(ResultSet resultSet){
        String[] listColumnsName;
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numColumns = resultSetMetaData.getColumnCount();
            listColumnsName = new String[numColumns];
            resultSet.next();
            for (int i = 0; i < numColumns; i++){
                listColumnsName[i] = resultSetMetaData.getColumnName(i+1);
            }
            return listColumnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnTable en MetodosGenericosBBDD = " + e.getMessage());
            e.printStackTrace();
            return new String[1];
        }
    }

    //region Atributos

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.MetodosGenericosBBDD");

    //endregion
}
