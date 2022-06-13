package com.company.BaseDatos;

import com.company.Entidades.Actuacion;
import com.company.Entidades.Albaran;
import com.company.Entidades.Cliente;
import com.company.Entidades.MaterialCompradoProveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDActuacion {

    public CRUDActuacion(){

        clientes = getClientes();

//        arrayListAlbaran = getAlbaranes();
//
//        materialesCompradoProveedores = getMaterialesProveedor();


    }

    // region Metodos CRUD

    public ArrayList<Actuacion> getAll(){
        Connection connection = BBDD.connect();
        final String SELECT_QUERY = "SELECT * FROM actuacion";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            var listaActuaciones = setListaActuacion(resultSet);

            BBDD.close();
            LOGGER.log(Level.INFO, "GetAll en Actuacion = exito");
            return  listaActuaciones;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "GetAll en Actuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  null;
        }

    }

    public int createActuacion(Actuacion actuacion) throws SQLException {
        Connection connection = BBDD.connect();
        if (connection == null) return -1;
        final String QUERY_INSERT = "INSERT INTO actuacion" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, 1);
            preparedStatement.setString(2, actuacion.getNombre());
            preparedStatement.setString(3, actuacion.getEspecificacion());
            preparedStatement.setInt(4, actuacion.getIdCliente());
            preparedStatement.setString(5, actuacion.getEstado());

            preparedStatement.setString(6, actuacion.getDescripcion());
            preparedStatement.setDouble(7, actuacion.getImporte());
            preparedStatement.setDouble(8, actuacion.getPorPertificar());
            preparedStatement.setDouble(9, actuacion.getTotalCertificicaciones());
            preparedStatement.setDouble(10, actuacion.getGastoMaterial());
            preparedStatement.setDouble(11, actuacion.getMaterialOfertado());
            preparedStatement.setDouble(12, actuacion.getResultadoBalance());

            preparedStatement.setString(13, actuacion.getHojaPlanificacion());
            preparedStatement.setString(14, actuacion.getHojaPresupuesto());

            preparedStatement.setInt(15, actuacion.getHorasOfertadas());
            preparedStatement.setInt(16, actuacion.getHorasEjecutadas());

            preparedStatement.setDate(17, actuacion.getFecha_solicitud());
            preparedStatement.setDate(18, actuacion.getFecha_envio());
            preparedStatement.setDate(19, actuacion.getFecha_comienzo());
            preparedStatement.setDate(20, actuacion.getFecha_finalizacion());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("No se pudo guardar");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idRowActuacion = 0;
            if(generatedKeys.next()){
                idRowActuacion = generatedKeys.getInt(1);
            }
            BBDD.close();
            LOGGER.log(Level.INFO, "createActuacion en Actuacion = exito");
            return idRowActuacion;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createActuacion en Actuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return  -1;
        } finally {
            if (!connection.isClosed()){
                BBDD.close();
            }
        }
    }

    public Actuacion readActuacion(int cod){

        return new Actuacion();
    }

    public boolean deleteActuacion(int id) {
        Connection connection = BBDD.connect();
        final String QUERY_DELETE = "DELETE FROM actuacion WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            BBDD.close();
            LOGGER.log(Level.INFO, "deleteActuacion en Actuacion = exito");
            return  true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createActuacion en Actuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    public boolean updateActuacion(Actuacion actuacion){
        Connection connection = BBDD.connect();
        if (connection == null) return false;
        final String QUERY_UPDATE = "UPDATE actuacion " +
                "SET especificacion = ?, estado = ?, fecha_solicitud = ?, fecha_envio = ?," +
                " fecha_comienzo = ?, fecha_finalizacion = ?, descripcion = ?, importe = ?, hoja_planificacion = ?, hoja_presupuesto = ?," +
                " total_certificaciones = ?, por_certificar = ?, horas_ofertadas = ?, horas_ejecutadas = ?, " +
                " material_ofertado = ?, gasto_material = ?, resultado_balance = ?, id_cliente = ?, nombre = ?" +
                " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, actuacion.getEspecificacion());
            preparedStatement.setString(2, actuacion.getEstado());
            preparedStatement.setDate(3, actuacion.getFecha_solicitud());
            preparedStatement.setDate(4, actuacion.getFecha_envio());
            preparedStatement.setDate(5, actuacion.getFecha_comienzo());
            preparedStatement.setDate(6, actuacion.getFecha_finalizacion());
            preparedStatement.setString(7, actuacion.getDescripcion());
            preparedStatement.setDouble(8, actuacion.getImporte());
            preparedStatement.setString(9, actuacion.getHojaPlanificacion());
            preparedStatement.setString(10, actuacion.getHojaPresupuesto());
            preparedStatement.setDouble(11, actuacion.getTotalCertificicaciones());
            preparedStatement.setDouble(12, actuacion.getPorPertificar());
            preparedStatement.setInt(13, actuacion.getHorasOfertadas());
            preparedStatement.setInt(14, actuacion.getHorasEjecutadas());
            preparedStatement.setDouble(15, actuacion.getMaterialOfertado());
            preparedStatement.setDouble(16, actuacion.getGastoMaterial());
            preparedStatement.setDouble(17, actuacion.getResultadoBalance());
            preparedStatement.setInt(18, actuacion.getIdCliente());
            preparedStatement.setString(19, actuacion.getNombre());
            preparedStatement.setInt(20, actuacion.getId());

            int affectedRows = preparedStatement.executeUpdate();
            BBDD.close();
            if (affectedRows == 0) throw  new SQLException("No se pudo actualizar registro id = " + actuacion.getId());
            if (affectedRows == 1) {
                LOGGER.log(Level.INFO, "updateActuacion en Actuacion = exito");
                return true;
            }
            LOGGER.log(Level.WARNING, "updateActuacion en Actuacion = no devuelve datos");
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "updateActuacion en Actuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            return false;
        }
    }

    // endregion

    //region Metodos privados

    private ArrayList<Actuacion> setListaActuacion(ResultSet resultSet) {
        ArrayList<Actuacion> listaActuacion = new ArrayList<>();
        try {
            while (resultSet.next()){
                Actuacion actuacion = new Actuacion();
                actuacion.setId(resultSet.getInt("id"));
                actuacion.setNombre(resultSet.getString("nombre"));
                actuacion.setEspecificacion(resultSet.getString("especificacion"));
                actuacion.setEstado(resultSet.getString("estado"));
                actuacion.setFecha_solicitud(resultSet.getDate("fecha_solicitud"));
                actuacion.setFecha_envio(resultSet.getDate("fecha_envio"));
                actuacion.setFecha_comienzo(resultSet.getDate("fecha_comienzo"));
                actuacion.setFecha_finalizacion(resultSet.getDate("fecha_finalizacion"));
                actuacion.setDescripcion(resultSet.getString("descripcion"));
                actuacion.setImporte(resultSet.getDouble("importe"));
                actuacion.setHojaPlanificacion(resultSet.getString("hoja_planificacion"));
                actuacion.setHojaPresupuesto(resultSet.getString("hoja_presupuesto"));
                actuacion.setTotalCertificicaciones(resultSet.getDouble("total_certificaciones"));
                actuacion.setPorPertificar(resultSet.getDouble("por_certificar"));
                actuacion.setHorasOfertadas(resultSet.getInt("horas_ofertadas"));
                actuacion.setHorasEjecutadas(resultSet.getInt("horas_ejecutadas"));

//                actuacion = setMaterialOfertado(actuacion);

                actuacion.setMaterialOfertado(resultSet.getDouble("material_ofertado"));
                actuacion.setGastoMaterial(resultSet.getDouble("gasto_material"));
                actuacion.setResultadoBalance(resultSet.getDouble("resultado_balance"));
                actuacion.setIdCliente(resultSet.getInt("id_cliente"));
                actuacion.setCliente(getClienteFromId(resultSet.getInt("id_cliente")));

                listaActuacion.add(actuacion);
            }
            BBDD.close();
            return listaActuacion;
        } catch (SQLException e) {
            //TODO incluis log para bbdd
            e.printStackTrace();
            BBDD.close();
            return listaActuacion;
        }
    }

    private Actuacion setMaterialOfertado(Actuacion actuacion) {
        double sumatoria = 0;

//        for(Albaran albaran : arrayListAlbaran){
//            if(albaran.getActuacion().getId() ==  albaran.getId()){
//                for(MaterialCompradoProveedor materialCompradoProveedor: materialesCompradoProveedores){
//                    if(materialCompradoProveedor.getAlbaran().getId() == albaran.getId()){
//                        sumatoria = sumatoria + materialCompradoProveedor.getBaseImponible();
//                    }
//                }
//            }
//        }
//
//        actuacion.setGastoMaterial(sumatoria);

        return actuacion;
    }

    public String[] getColumnActuacion() {
        Connection connection = BBDD.connect();
        try {
            final String SELECT_CLIENTES = "SELECT * FROM actuacion";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CLIENTES);
            String[] columnsName = MetodosGenericosBBDD.getColumnTable(resultSet);
            if (columnsName[0] == null){
                System.out.println("Fallo en sacar los metatados");
                LOGGER.log(Level.WARNING, "getColumnActuacion en Actuacion = no devuelve columnas");
            }

            BBDD.close();
            LOGGER.log(Level.INFO, "getColumnActuacion en Actuacion = exito");
            return  columnsName;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "createActuacion en Actuacion = " + e.getMessage());
            e.printStackTrace();
            BBDD.close();
            String columnsName[] = new String[1];
            columnsName[0] = "Error en CRUD";
            return columnsName;
        }

    }


    private Cliente getClienteFromId(int id_cliente) {
        for(Cliente cliente : clientes){
            if(cliente.getId() == id_cliente) return cliente;
        }
        return null;
    }

    private ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> listClientes;
        CRUDCliente crudCliente = new CRUDCliente();
        listClientes = crudCliente.getAll();
        return listClientes;
    }

    private ArrayList<Albaran> getAlbaranes(){
        ArrayList<Albaran> listAlbaranes;
        CRUDAlbaran crudAlbaran = new CRUDAlbaran();
        listAlbaranes = crudAlbaran.getAll();
        return listAlbaranes;
    }

    private ArrayList<MaterialCompradoProveedor> getMaterialesProveedor(){
        ArrayList<MaterialCompradoProveedor> listMateriales;
        CRUDMaterialCompradoProveedor crudMaterialCompradoProveedor = new CRUDMaterialCompradoProveedor();
        listMateriales = crudMaterialCompradoProveedor.getAll();
        return listMateriales;
    }

    // endregion

    //region ATRIBUTOS

    private static final Logger LOGGER = Logger.getLogger("com.company.BaseDatos.CRUDActuacion");
    ArrayList<Cliente> clientes;
//    ArrayList<Albaran> arrayListAlbaran;
//    ArrayList<MaterialCompradoProveedor> materialesCompradoProveedores;

    //endregion
}
