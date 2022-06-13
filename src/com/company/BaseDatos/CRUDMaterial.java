package com.company.BaseDatos;

import com.company.Controlador.ControladorMaterial;
import com.company.Entidades.Material;
import com.company.Entidades.MaterialEx;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDMaterial {


    public CRUDMaterial(ControladorMaterial controladorMaterial) {
        this.controladorMaterial = controladorMaterial;
    }

    public CRUDMaterial() {

    }

    //region Metodos CRUD

    public ArrayList<Material> getAll(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_MATERIALES = "SELECT * FROM material";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_MATERIALES);
            var listMateriales = setListMateriales(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Materiales = exito");
            return listMateriales;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Materiales = " + e.getMessage());
            BBDD.close();
            return null;
        }
    }

    public int createMaterial(Material material) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO material" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, material.getCodigo());
            preparedStatement.setString(3, material.getGrupo());
            preparedStatement.setString(4, material.getDescripcion());
            preparedStatement.setString(5, material.getEspecificacion());
            preparedStatement.setString(6, material.getUnidad());
            preparedStatement.setDouble(7, material.getEspesor());
            preparedStatement.setString(8, material.getCalidad());
            preparedStatement.setString(9, material.getProveedor1());
            preparedStatement.setDouble(10, material.getPrecio1());
            preparedStatement.setString(11, material.getProveedor2());
            preparedStatement.setDouble(12, material.getPrecio2());
            preparedStatement.setString(13, material.getProveedor3());
            preparedStatement.setDouble(14, material.getPrecio3());
            //Es necesario comprobar si entran a null las Foreigh key
            if (material.getIdGrupo() == null){
                preparedStatement.setNull(15, 1);
            }else{
                preparedStatement.setInt(15, material.getIdGrupo());
            }

            if (material.getIdEspecificacion() == null){
                preparedStatement.setNull(16, 1);
            } else{
                preparedStatement.setInt(16, material.getIdEspecificacion());
            }

            if (material.getIdUnidad() == null){
                preparedStatement.setNull(17, 1);
            }else{
                preparedStatement.setInt(17, material.getIdUnidad());
            }

            if (material.getIdCalidad() == null){
                preparedStatement.setNull(18, 1);
            }else{
                preparedStatement.setInt(18, material.getIdCalidad());
            }

            //Extraemos el id autogenerado
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw  new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowMaterial = 0;
            if (generatedKeys.next()){
                idRowMaterial = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "Crear material = exito");
            return idRowMaterial;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Crear material = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public Material readMaterial(String cod) {

        return new Material();
    }

    public ArrayList<MaterialEx> getMaterialesGroupByAlbaran(int idActuacion){
        String sql ="SELECT m.cod, m.grupo, m.descripcion, al.cod as 'codAlbaran' FROM material m\n" +
                "INNER JOIN materialcompradoproveedores mcp\n" +
                "\ton m.id = mcp.id_material\n" +
                "INNER JOIN proveedor p\n" +
                "\tON mcp.id_proveedor = p.id\n" +
                "INNER JOIN albaran al\n" +
                "\tON p.id = al.id_proveedor\n" +
                "INNER JOIN actuacion a\n" +
                "\tON al.id_actuacion = a.id\n" +
                "WHERE a.id = " + idActuacion;

        /*
        String sql = "SELECT m.cod, m.grupo, m.descripcion, al.cod as 'codAlbaran' FROM material m\n" +
                "INNER JOIN materialutilizadoactuacion ma\n" +
                "\ton m.id = ma.id_material\n" +
                "INNER JOIN actuacion a \n" +
                "\ton ma.id_actuacion = a.id\n" +
                "INNER JOIN albaran al\n" +
                "\ton a.id = al.id_actuacion\n" +
                "where a.id = " + idActuacion + "\n" +
                "GROUP BY a.id";

         */
        try {
            Statement statement = BBDD.connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<MaterialEx> materialesEx = getMaterialesEx(resultSet);
            return materialesEx;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getMaterialesGroupByAlbaran en Materiales = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return new ArrayList<MaterialEx>();
        }
    }

    private ArrayList<MaterialEx> getMaterialesEx(ResultSet resultSet) {
        ArrayList<MaterialEx> materiales = new ArrayList<>();
        try{
            while (resultSet.next()){
                MaterialEx materialEx = new MaterialEx();
                materialEx.setCodigo(resultSet.getString(1));
                materialEx.setGrupo(resultSet.getString(2));
                materialEx.setDescripcion(resultSet.getString(3));
                materialEx.setCodAlbaran(resultSet.getString(4));

                System.out.println(materialEx.toString());

                materiales.add(materialEx);
            }
            BBDD.close();
            return materiales;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "getMaterialesEx en Material = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return materiales;
        }
    }

    public boolean deleteMaterial(String id){
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM material WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            LOGGER.log(Level.INFO, "Borrar material = exito");
            BBDD.close();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Borrar material = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateMaterial(Material material){

        Connection connection = BBDD.connect();
        if (connection == null)return false;
        final String QUERY_UPDATE = "UPDATE material " +
                "SET cod = ?, grupo = ?, descripcion = ?, especificacion = ?, unidad = ?, espesor = ?, " +
                "calidad = ?, Proveedor_1 = ?, precio_1 = ?, Proveedor_2 = ?, precio_2 = ?, Proveedor_3 = ?, precio_3 = ?, " +
                "id_grupo = ?, id_especifiacion = ?, id_unidad = ?, id_calidad = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);

            preparedStatement.setString(1, material.getCodigo());
            preparedStatement.setString(2, material.getGrupo());
            preparedStatement.setString(3, material.getDescripcion());
            preparedStatement.setString(4, material.getEspecificacion());
            preparedStatement.setString(5, material.getUnidad());
            preparedStatement.setDouble(6, material.getEspesor());
            preparedStatement.setString(7, material.getCalidad());
            preparedStatement.setString(8, material.getProveedor1());
            preparedStatement.setDouble(9, material.getPrecio1());
            preparedStatement.setString(10, material.getProveedor2());
            preparedStatement.setDouble(11, material.getPrecio2());
            preparedStatement.setString(12, material.getProveedor3());
            preparedStatement.setDouble(13, material.getPrecio3());

            //Es necesario comprobar si entran a null las Foreigh key
            if (material.getIdGrupo() == null){
                preparedStatement.setNull(14, 1);
            }else{
                preparedStatement.setInt(14, material.getIdGrupo());
            }

            if (material.getIdEspecificacion() == null){
                preparedStatement.setNull(15, 1);
            } else{
                preparedStatement.setInt(15, material.getIdEspecificacion());
            }

            if (material.getIdUnidad() == null){
                preparedStatement.setNull(16, 1);
            }else{
                preparedStatement.setInt(16, material.getIdUnidad());
            }

            if (material.getIdCalidad() == null){
                preparedStatement.setNull(17, 1);
            }else{
                preparedStatement.setInt(17, material.getIdCalidad());
            }

            preparedStatement.setInt(18, material.getId());
            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + material.getId());
            if (affectedRows == 1) return true;
            LOGGER.log(Level.INFO, "Actualizar material = exito");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Actualizar material = " + e.getMessage());
            BBDD.close();
            e.printStackTrace();
            return false;
        }
    }


    //endregion

    //region consultas MetaDatos

    public String[] getColumnsMaterial(){
        Connection connection = BBDD.connect();
        try {
            final String SELECT_MATERIALES = "SELECT * FROM material";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_MATERIALES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                System.out.println("Fallo en sacar los metatados");
                LOGGER.log(Level.WARNING, "Fallo en sacar los metatados o columnas = 0");
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnsMaterial material = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Actualizar material = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }
    }

    //endregion

    //region Metodos Privados

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
                material.setPrecio1(resultSet.getDouble("precio_1"));
                material.setPrecio2(resultSet.getDouble("precio_2"));
                material.setPrecio3(resultSet.getDouble("precio_2"));
                material.setProveedor1(resultSet.getString("proveedor_1"));
                material.setProveedor2(resultSet.getString("proveedor_2"));
                material.setProveedor3(resultSet.getString("proveedor_3"));
                material.setUnidad(resultSet.getString("unidad"));

                materiales.add(material);
            }
            BBDD.close();
            return materiales;
        } catch (SQLException e) {
            //TODO incluir mensajes log para BBDD
            e.printStackTrace();
            BBDD.close();
            return  materiales;
        }
    }

    //endregion

    //region ATRIBUTOS
    private ControladorMaterial controladorMaterial;
    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDMaterial");
    //endregion


    public static void main(String[] args) {
        var materiales = new CRUDMaterial().getMaterialesGroupByAlbaran(1);
        System.out.println(materiales);
    }
}
