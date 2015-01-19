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

    public void exportPDF(String fileName, Map<String,Object> parameters,String pdfName, Collection reportList) throws Exception {

        log.debug("generate pdf.");
        InputStream inputStream = FacesUtil.getFacesContext().getExternalContext().getResourceAsStream(fileName);

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRDataSource dataSource = new JRBeanCollectionDataSource(reportList);

//        log.debug("############# {}", reportList.toString());
        JasperPrint print ;
        if (!Utils.isNull(dataSource) && Utils.isCollection(reportList) && !Utils.isNull(reportList)){
//            log.debug("++++++++++++ {}", dataSource.toString());
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
}
