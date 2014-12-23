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

    public void genBarcode128(String pathPDF, String startBarcode, int qty){
        final float MARGIN = 12f;//50mm
        final float INCH = 63.5f;//1"
        final float HEIGHT = INCH;
        final float WIDTH = INCH*4;
        log.debug("path : {}", pathPDF);
//        ExternalContext externalContext = FacesUtil.getExternalContext();
//        externalContext.addResponseHeader("Content-disposition", "attachment; filename="+pathPDF+".pdf");

        // step 1
        Document document = null;
        PdfWriter writer = null;
        OutputStream outputStream = null;
        PdfContentByte cb = null;
        try {
            // step 1
            document = new Document(new Rectangle(WIDTH, HEIGHT));
            document.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
//            outputStream =  externalContext.getResponseOutputStream();
            // step 2
            writer = PdfWriter.getInstance(document, new FileOutputStream(pathPDF));
            // step 3
            document.open();
            // step 4
            cb = writer.getDirectContent();
            // CODE 128
            Barcode128 code128 = new Barcode128();
            code128.setCodeType(Barcode.CODE128);
            code128.setTextAlignment(Element.ALIGN_CENTER);

            for (int i = 0; i < qty; i++) {
                int barcode = Utils.parseInt(replaceFormat(startBarcode),0)+i;
                final String result = barcode > 99999999 ? "99999999" : String.format("%08d", barcode);
                StringBuilder barcodeString = new StringBuilder();
                barcodeString.append("TW").append(result);

                code128.setCode(barcodeString.toString());
                Image image = code128.createImageWithBarcode(cb, null, null);
                float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
                image.scaleAbsolute(documentWidth, documentHeight);
                document.add(image);
                document.newPage();
            }
        } catch (Exception e){
            System.err.println(e);
        } finally {
            if(!Utils.isNull(document)){
                document.close();
            }
            if(!Utils.isNull(writer)){
                writer.close();
            }
            if(!Utils.isNull(cb)){
                cb.closePath();
            }
        }
    }

    private String replaceFormat(String startBarcode){
        return startBarcode.replace("TW", "");
    }
}
