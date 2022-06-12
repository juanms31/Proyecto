package com.company.BaseDatos;

import com.company.Entidades.Settings;
import java.sql.*;
import java.util.ArrayList;

public class CRUDSettings {

    public ArrayList<Settings> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM UsuarioSettings";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaSettings = setListaSettingsUsuario(resultSet);

            BBDD.close();
            return  listaSettings;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createSettings(Settings settings) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO usuariosettings" +
                " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, settings.getUrlFondo());
            preparedStatement.setString(3, settings.getTipoFondo());
            preparedStatement.setInt(4, settings.getId_usuario());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idsettingsUsuario = 0;
            if(generatedKeys.next()){
                idsettingsUsuario = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idsettingsUsuario;
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

    public boolean updateSettings(Settings settings){

        Connection connection = BBDD.connect();
        if (connection == null)return false;
        final String QUERY_UPDATE = "UPDATE usuariosettings " +
                "SET URLfondo = ?, tipoFondo = ? WHERE id = ? AND id_usuario = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

            preparedStatement.setString(1, settings.getUrlFondo());
            preparedStatement.setString(2, settings.getTipoFondo());
            preparedStatement.setInt(3, settings.getId());
            preparedStatement.setInt(4, settings.getId_usuario());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + settings.getId());
            if (affectedRows == 1) return true;
            return false;
        } catch (SQLException e) {
            BBDD.close();
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Settings> setListaSettingsUsuario(ResultSet resultSet) {
        ArrayList<Settings> settingsUsuarioList = new ArrayList<>();
        try {
            while (resultSet.next()){
                Settings settings = new Settings();
                settings.setId(resultSet.getInt("id"));
                settings.setUrlFondo(resultSet.getString("URLFondo"));
                settings.setTipoFondo(resultSet.getString("tipoFondo"));
                settings.setId_usuario(resultSet.getInt("id_usuario"));

                settingsUsuarioList.add(settings);
            }
            BBDD.close();
            return settingsUsuarioList;
        } catch (SQLException e) {
            //TODO incluir mensajes log para BBDD
            e.printStackTrace();
            BBDD.close();
            return  settingsUsuarioList;
        }
    }
}
