
package Controlador;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Arco02
 */
@Named(value = "reporteEmpleados")
@RequestScoped
public class ReporteEmpleados {

    /**
     * Creates a new instance of ReporteEmpleados
     */
    public ReporteEmpleados() {
    }
    
 public void exportarContrato(int idempleado) throws ClassNotFoundException, SQLException, JRException, IOException {
       Map<String, Object> parametros = new HashMap<>();
       parametros.put("idempleado", idempleado);
       //conexiones
       Class.forName("com.mysql.jdbc.Driver");
       Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/MEDIUSSOFTWARE", "root", "");
       //Para buscar el reporte
        File buscareporte = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Paginas/ReportesGestionHumana/Contrato.jasper"));
       //Para llenar el reporte
       JasperPrint llenareporte=JasperFillManager.fillReport(buscareporte.getPath(),parametros ,conexion);
       //permitir que el browser habra el archivo
       //Visibilidad del reporte
        HttpServletResponse visualizareporte = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
       //nombre cabecera del reporte
        visualizareporte.addHeader("Content-disposition", "attachment; filename=Contrato.pdf");
        ServletOutputStream stream =visualizareporte.getOutputStream();
        
        //Generar el pdf tipo de formato
        JasperExportManager.exportReportToPdfStream(llenareporte, stream);
        
        //Cierro el response
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void exportarempleado(int id) throws ClassNotFoundException, SQLException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("../ReportesGestionHumana/report1.jasper\""));
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/MEDIUSSOFTWARE", "root", "");
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, conexion);
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=Procutos"+id+".pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }
    
    public void exportasimple() throws ClassNotFoundException, SQLException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("../ReportesGestionHumana/report2.jasper\""));
            Map<String, Object> params = new HashMap<>();
            //params.put("id", id);
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/MEDIUSSOFTWARE", "root", "");
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, conexion);
            
            
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=Procutos");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }
    
     
    
}
