package com.company.BaseDatos;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
            //TODO quizas añadir algo para contorl de fallos
            e.printStackTrace();
            return new String[1];
        }
    }
}
