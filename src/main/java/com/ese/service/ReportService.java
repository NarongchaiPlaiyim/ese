package com.ese.service;

import com.ese.utils.Utils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

@Component
@Transactional
public class ReportService extends Service{

    public void exportPDF(String fileName, Map<String,Object> parameters,String pdfName, Collection reportList) throws Exception {

        log.debug("generate pdf.");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileName);
        JRDataSource dataSource = new JRBeanCollectionDataSource(reportList);

        JasperPrint print ;
        if (!Utils.isNull(dataSource) && Utils.isCollection(reportList) && !Utils.isNull(reportList)){
            print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } else {
            print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        }

        log.debug("--Pring report.");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.addResponseHeader("Content-disposition", "attachment; filename="+pdfName+".pdf");
        OutputStream outputStream =  externalContext.getResponseOutputStream();

        try {
            JasperExportManager.exportReportToPdfStream(print, outputStream);
            FacesContext.getCurrentInstance().responseComplete();
            log.debug("generatePDF completed.");

        } catch (JRException e) {
            log.error("Error generating pdf report!", e);
        } finally {
            outputStream.flush();
            outputStream.close();
        }

    }

    public void exportPDF2(Map map, Collection reportList, String jasperName, String nameReport)
            throws Exception {

        log.debug("exportPDF2() {}, {}, {}.", map, reportList, jasperName);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        log.debug("request ; {}", request);
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        log.debug("response : {}", response);
        response.setHeader("Content-Disposition", "attachment;filename="+nameReport+".pdf");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = request.getSession().getServletContext().getResourceAsStream("D:/parttime/ESE's source/ese/web/site/report/PalletManagement.jasper");
        log.debug("reportStream : {}", reportStream);
//        response.setContentType("application/pdf");
        try {
            JRDataSource dataSource = null;
            dataSource = new JRBeanCollectionDataSource(reportList);
            if(dataSource != null && reportList != null && reportList.size() > 0)
                JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, dataSource);
            else
                JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, new JREmptyDataSource());

        } catch (Exception e) {
            log.debug("Exception Export Report : ", e);
        } finally {
            reportStream.close();
            servletOutputStream.flush();
            servletOutputStream.close();

        }
    }
}
