package com.company.BaseDatos;

import com.company.Entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDUsuario {

    public ArrayList<Usuario> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_TRABAJADORES = "SELECT * FROM usuario";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_TRABAJADORES);
            var listaTrabajadores = setListaUsuarios(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Usuario = exito");
            return  listaTrabajadores;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Usuario = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createUsuario(Usuario usuario) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO usuario" +
                " VALUES (?,?, ?, ?,?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, usuario.getDNI());
            preparedStatement.setString(3, usuario.getNombre());
            preparedStatement.setString(4, usuario.getApellidos());
            preparedStatement.setString(5, usuario.getTelefono());
            preparedStatement.setDate(6, usuario.getFnac());
            preparedStatement.setString(7, usuario.getNacionalidad());
            preparedStatement.setString(8, usuario.getEmail());
            preparedStatement.setString(9, usuario.getPass());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowUsuario = 0;
            if(generatedKeys.next()){
                idRowUsuario = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createUsuario en Usuario = exito");
            return idRowUsuario;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createUsuario en Usuario = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    private ArrayList<Usuario> setListaUsuarios(ResultSet resultSet) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            while (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setDNI(resultSet.getString("DNI"));
                usuario.setTelefono(resultSet.getString("telefono"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidos(resultSet.getString("apellidos"));
                usuario.setFnac(resultSet.getDate("fecha_nacimiento"));
                usuario.setNacionalidad(resultSet.getString("nacionalidad"));
                usuario.setEmail(resultSet.getString("email"));

                // TODO: 02/06/2022 DESHASHEAR LA CONTRASEÑA
                usuario.setPass(resultSet.getString("pass"));

                usuarios.add(usuario);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "setListaUsuarios en Usuario = exito");
            return usuarios;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "setListaUsuarios en Usuario = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return usuarios;
        }
    }

    //region Atributos

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDUsuario");

    //enderegion
}
