package com.company.Informes;

import com.company.BaseDatos.BBDD;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;

public class InformeActuacion {

    public void getInformeActuacion(int idActuacion){
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/com/company/Informes/InformeAlhambraMetal.jrxml");

            //PARAMETOS PARA EL INFORME
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("idActuacionSelected", idActuacion);

            //PRINT
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, BBDD.connect());

            //PREVISUALIZACION DISEÑO INFORME
            JasperDesignViewer.viewReportDesign(jasperReport);

            //PREVISUALIZACION INFORME GENERADO
            JasperViewer.viewReport(print);

            //EXPORTAMOS A PDF
            //TODO esta ruta esta mal; realmente se puede guardar desde la vista q ofrace jasper
            //JasperExportManager.exportReportToPdfFile(print, "src/recursos/informe_con_parametros.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InformeActuacion ejemplo = new InformeActuacion();
        ejemplo.getInformeActuacion(1);
    }
}
