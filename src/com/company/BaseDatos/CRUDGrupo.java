package com.company.BaseDatos;

import com.company.Controlador.ControladorProveedor;
import com.company.Entidades.GrupoMaterial;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDGrupo {

    public CRUDGrupo(ControladorProveedor controladorProveedor) {
        this.controladorProveedor = controladorProveedor;
    }

    public CRUDGrupo() {
    }

    // region Metodos CRUD

    public ArrayList<GrupoMaterial> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM GrupoMaterial";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaGrupos = setListaGrupos(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Grupo = exito");
            return  listaGrupos;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Grupo = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }
    }

    public int createGrupoMaterial(GrupoMaterial grupoMaterial) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO grupomaterial" +
                " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, grupoMaterial.getSiglasGrupo());
            preparedStatement.setString(3, grupoMaterial.getNombreGrupo());
            preparedStatement.setString(4, grupoMaterial.getDescripcion());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, "createGrupoMaterial en Grupo = no afecto a ningun registro");
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowGrupoMaterial = 0;
            if(generatedKeys.next()){
                idRowGrupoMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createGrupoMaterial en Grupo = exito");
            return idRowGrupoMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createGrupoMaterial en Grupo = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    /*
    public int createProveedor(Proveedor proveedor) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO proveedor" +
                " VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, proveedor.getCIF());
            preparedStatement.setString(3, proveedor.getNombre_proveedor());
            preparedStatement.setString(4, proveedor.getDireccion());
            preparedStatement.setString(5, proveedor.getMail1());
            preparedStatement.setString(6, proveedor.getTelefono1());
            preparedStatement.setString(7, proveedor.getMail2());
            preparedStatement.setString(8, proveedor.getTelefono2());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowProveedor = 0;
            if(generatedKeys.next()){
                idRowProveedor = generatedKeys.getInt(1);
            }
            BBDD.close();
            return idRowProveedor;
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

     */

    /*
    public Cliente readProveedor(int cod){

        return new Cliente();
    }

     */

    /*
    public boolean deleteProveedor(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM proveedor WHERE id = ?";
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
        }
    }

     */

    /*
    public boolean updateProveedor(Proveedor proveedor){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE proveedor " +
                "SET nombre_proveedor = ?, direccion = ?, mail1 = ?, mail2 = ?," +
                " telefono1 = ?, telefono2 = ?, CIF = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, proveedor.getNombre_proveedor());
            preparedStatement.setString(2, proveedor.getDireccion());
            preparedStatement.setString(3, proveedor.getMail1());
            preparedStatement.setString(4, proveedor.getMail2());
            preparedStatement.setString(5, proveedor.getTelefono1());
            preparedStatement.setString(6, proveedor.getTelefono2());
            preparedStatement.setString(7, proveedor.getCIF());
            preparedStatement.setInt(8, proveedor.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + proveedor.getId());
            if (affectedRows == 1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
        }

        return false;
    }

     */

    // endregion

    //region consultas MetaDatos

    public String[] getColumnsProveedor(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_PROVEEDORES = "SELECT * FROM proveedor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROVEEDORES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                LOGGER.log(Level.WARNING, "getColumnsProveedor en Grupo = no devolvio columnas");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsProveedor en Grupo = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getColumnsProveedor en Grupo = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Metodos privados

    private ArrayList<GrupoMaterial> setListaGrupos(ResultSet resultSet) {
        ArrayList<GrupoMaterial> grupoMaterials = new ArrayList<>();
        try {
            while (resultSet.next()){
                GrupoMaterial grupoMaterial = new GrupoMaterial();
                grupoMaterial.setId(resultSet.getInt("id"));
                grupoMaterial.setSiglasGrupo(resultSet.getString("siglas_grupo"));
                grupoMaterial.setNombreGrupo(resultSet.getString("nombre_grupo"));
                grupoMaterial.setDescripcion(resultSet.getString("Descripcion"));

                grupoMaterials.add(grupoMaterial);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "setListaGrupos en Grupo = exito");
            return grupoMaterials;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "setListaGrupos en Grupo = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return grupoMaterials;
        }
    }

    // endregion

    //region Variables

    private ControladorProveedor controladorProveedor;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDGrupo");

    //endregion

}
