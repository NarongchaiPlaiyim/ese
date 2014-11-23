package com.ese.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Component
@Transactional
public class ReportService extends Service{

    public void exportPDF(HttpServletRequest request, HttpServletResponse response, Map map, Collection reportList, String jasperName)
            throws Exception {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = request.getSession().getServletContext().getResourceAsStream("/reports/" + jasperName + ".jasper");
        response.setContentType("application/pdf");
        try {
            JRDataSource dataSource = null;
            dataSource = new JRBeanCollectionDataSource(reportList);
            if(dataSource != null && reportList != null && reportList.size() > 0)
                JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, dataSource);
            else
                JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, new JREmptyDataSource());

        } catch (Exception e) {
//            logger.error(e.getMessage());
        } finally {
            reportStream.close();
            servletOutputStream.flush();
            servletOutputStream.close();

        }
    }
}
