package com.company.BaseDatos;

import com.company.Controlador.ControladorProveedor;
import com.company.Entidades.GrupoMaterial;

import java.sql.*;
import java.util.ArrayList;

public class CRUDGrupo {

    public CRUDGrupo(ControladorProveedor controladorProveedor) {
        this.controladorProveedor = controladorProveedor;
    }

    public CRUDGrupo() {
    }

    // region Metodos CRUD

    public ArrayList<GrupoMaterial> getAll() {
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM grupo";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaGrupos = setListaGrupos(resultSet);

            BBDD.close();
            return  listaGrupos;

        } catch (SQLException e) {
            e.printStackTrace();
            BBDD.close();
            return  null;
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

    //region consultas Meta Datos

    public String[] getColumnsProveedor(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_PROVEEDORES = "SELECT * FROM proveedor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_PROVEEDORES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                System.out.println("Fallo en sacar los metatados");
            }
            BBDD.close();
            return  columnsName;
        } catch (SQLException e) {
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
            return grupoMaterials;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return grupoMaterials;
        }
    }

    // endregion

    //region Variables

    private ControladorProveedor controladorProveedor;

    //endregion
}