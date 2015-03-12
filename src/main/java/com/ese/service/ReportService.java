package com.ese.service;

import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.io.FileOutputStream;
import com.itextpdf.text.*;

@Component
@Transactional
public class ReportService extends Service{
    private static final long serialVersionUID = 4112578632409874840L;

    public void exportPDF(String fileName, Map parameters,String pdfName, Collection reportList) throws Exception {

        log.debug("generate pdf.");
        InputStream inputStream = FacesUtil.getFacesContext().getExternalContext().getResourceAsStream(fileName);
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRDataSource dataSource = new JRBeanCollectionDataSource(reportList);

        JasperPrint print ;
        if (!Utils.isNull(dataSource) && Utils.isCollection(reportList) && !Utils.isNull(reportList)){
            print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } else {
            print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        }
//
//        log.debug("--Pring report.");
//
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        externalContext.setResponseHeader("Pragma", "no-cache");
//        externalContext.setResponseContentType("application/octet-stream");
//        externalContext.addResponseHeader("Content-disposition", "attachment; filename="+pdfName+".pdf");
//        OutputStream outputStream =  externalContext.getResponseOutputStream();
//
//        try {
//            JasperExportManager.exportReportToPdfStream(print);

//            FacesContext.getCurrentInstance().responseComplete();
//            log.debug("generatePDF completed.");
//        } catch (JRException e) {
//            log.error("Error generating pdf report!", e);
//        } finally {
//            outputStream.flush();
//            outputStream.close();
//        }
        try {
            ServletOutputStream servletOutputStream = null;
            byte[] bytes;
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            servletOutputStream = response.getOutputStream();
            servletOutputStream.flush();
//            String fileName = app.getRealPath() + reportTemplate;
//            String jasperReportString = JasperCompileManager.compileReportToFile(fileName);
//            log.debug("PDF jasperReport : {}", jasperReportString);
//            File file = new File(jasperReportString);
//            if (dataSource == null) {
//                bytes = JasperExportManager.exportReportToPdf(print);
////                bytes = JasperRunManager.runReportToPdf(file.getPath(), parameters, new JREmptyDataSource());
//            } else {
//                bytes = JasperRunManager.runReportToPdf(file.getPath(), parameters, dataSource.getRecordCount() != 0 ? dataSource : new JREmptyDataSource());
//            }
            bytes = JasperExportManager.exportReportToPdf(print);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
            facesContext.responseComplete();
            facesContext.renderResponse();
            log.debug("generatePDF completed.");
        } catch (Exception e) {
            System.out.println();
        }
    }

//    ﻿public void generatePDF(Map<String, Object> parameters,
//                             JRBeanCollectionDataSource dataSource) throws Exception {
//        log.debug("generate pdf. (jrDataSource: {})", dataSource);
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//        byte[] bytes;
//        ServletOutputStream servletOutputStream = null;
//        try {
//            servletOutputStream = response.getOutputStream();
////            String fileName = app.getRealPath() + reportTemplate;
//            String jasperReport = JasperCompileManager.compileReportToFile("");
//            log.debug("PDF jasperReport : {}", jasperReport);
//            File file = new File(jasperReport);
//            if (dataSource == null) {
//                bytes = JasperRunManager.runReportToPdf(file.getPath(), parameters, new JREmptyDataSource());
//            } else {
//                bytes = JasperRunManager.runReportToPdf(file.getPath(), parameters, dataSource.getRecordCount() != 0 ? dataSource : new JREmptyDataSource());
//            }
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            servletOutputStream.write(bytes, 0, bytes.length);
//            servletOutputStream.flush();
//            facesContext.responseComplete();
//            facesContext.renderResponse();
//            log.debug("generatePDF completed.");
//        } catch (JRException e) {
//            log.error("Error generating pdf report!", e);
//        } catch (Exception e) {
//            log.error("", e);
//            StringWriter stringWriter = new StringWriter();
//            response.setContentType("application/txt");
//            response.getOutputStream().print(stringWriter.toString());
//        } finally {
//            if (servletOutputStream != null)
//                servletOutputStream.close();
//        }
//    }
}
