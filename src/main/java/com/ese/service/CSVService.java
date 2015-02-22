package com.ese.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@Transactional
public class CSVService extends Service{
    private static final long serialVersionUID = 4112578632409874840L;

    private static final String CHARSET = "TIS-620";

    private ServletOutputStream out = null;
    private BufferedInputStream bis = null;
    private BufferedOutputStream bos = null;


    public void exportCSV(String fileName, String content) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

            response.reset();
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");

            out = response.getOutputStream();
            bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes(CHARSET)));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, bytesRead);
                bos.flush();
            }
            out.flush();

            facesContext.responseComplete();
            facesContext.renderResponse();

        } catch (IOException e) {
//            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) { /*logger.error(e.getMessage());*/
                    System.out.println(e.getMessage());
                }
                out = null;
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) { /*logger.error(e.getMessage());*/
                    System.out.println(e.getMessage());
                }
                bis = null;
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) { /*logger.error(e.getMessage());*/
                    System.out.println(e.getMessage());
                }
                bos = null;
            }
        }
    }
}
