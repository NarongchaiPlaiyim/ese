package com.ese.service;

import com.ese.model.dao.BarcodePrintingDAO;
import com.ese.model.view.report.BarcodeRegisterModelReport;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class BarcodePrintingService extends Service{
    private static final long serialVersionUID = 4112577394029874840L;
    @Resource private BarcodePrintingDAO barcodePrintingDAO;
    @Resource private ReportService reportService;
    @Value("#{config['report.barcodeprinting']}")private String pathBarcodePrintingReport;

    public String getLastSeq(){
        return "";
    }

    public void onPrintBarcode(String startBarcode, int qty){
        String printBarcodeName = Utils.genDateReportStringDDMMYYYY(new Date()) + "_BarcodePrinting.pdf";

        List<BarcodeRegisterModelReport> reports = new ArrayList<BarcodeRegisterModelReport>();
        HashMap map = new HashMap<String, Object>();

        try {
            reportService.genBarcode128(printBarcodeName, startBarcode, qty);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }
}
