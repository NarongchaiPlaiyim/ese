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
        InputStream inputStream = null;
        JasperReport jasperReport;
        JRDataSource dataSource;
        JasperPrint print;
        ServletOutputStream servletOutputStream = null;
        try {
            inputStream = FacesUtil.getFacesContext().getExternalContext().getResourceAsStream(fileName);
            jasperReport = JasperCompileManager.compileReport(inputStream);
            dataSource = new JRBeanCollectionDataSource(reportList);
            if (!Utils.isNull(dataSource) && Utils.isCollection(reportList) && !Utils.isNull(reportList)){
                print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            } else {
                print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            }

            byte[] bytes;
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            servletOutputStream = response.getOutputStream();
            servletOutputStream.flush();
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
        } finally {
            if (servletOutputStream != null)servletOutputStream.close();

            if (inputStream != null)inputStream.close();

            if (servletOutputStream != null)servletOutputStream.close();
        }
    }
}
