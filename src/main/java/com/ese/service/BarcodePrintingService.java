package com.ese.service;

import com.ese.model.dao.BarcodePrintingDAO;
import com.ese.model.db.BarcodePrintingModel;
import com.ese.model.view.BarcodePrintingView;
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
    @Value("#{config['report.barcodeprinting']}")
    private String pathBarcodePrinting;

    public String getLastSeq(){
        String result = "";
        BarcodePrintingModel barcodePrintingModel;
        try {
            barcodePrintingModel = barcodePrintingDAO.findLastInsert();
            result = barcodePrintingModel.getFinishBarcode();
        } catch (Exception e) {
//            System.err.println(e);
        }
        return result;
    }

    public void save(int qty, String sbc, String fbc){
        BarcodePrintingModel barcodePrintingModel = new BarcodePrintingModel();
        try {
            barcodePrintingModel.setCreateDate(Utils.currentDate());
            barcodePrintingModel.setCreateBy(99);
            barcodePrintingModel.setUpdateBy(88);
            barcodePrintingModel.setUpdateDate(Utils.currentDate());
            barcodePrintingModel.setStartBarcode(sbc);
            barcodePrintingModel.setFinishBarcode(fbc);
            barcodePrintingModel.setQty(qty);
            barcodePrintingDAO.persist(barcodePrintingModel);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void insert(){
        BarcodePrintingModel barcodePrintingModel = new BarcodePrintingModel();

        try {
            barcodePrintingModel.setCreateDate(Utils.currentDate());
            barcodePrintingModel.setCreateBy(99);
            barcodePrintingModel.setUpdateBy(88);
            barcodePrintingModel.setUpdateDate(Utils.currentDate());
            barcodePrintingModel.setStartBarcode("TW00000101");
            barcodePrintingModel.setFinishBarcode("TW00000109");
            barcodePrintingModel.setQty(10);
            barcodePrintingDAO.persist(barcodePrintingModel);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void onPrintBarcode(String startBarcode, int qty){
        String printBarcodeName = Utils.genReportName("_BarcodePrinting");
        List<BarcodePrintingView> barcodePrintingViewList = new ArrayList<BarcodePrintingView>();
        try {

            for (int i = 0; i < qty; i++) {
                BarcodePrintingView printingView = new BarcodePrintingView();
                int barcode = Utils.parseInt(replaceFormat(startBarcode))+i;
                final String result = barcode > 99999999 ? "99999999" : String.format("%08d", barcode);
                StringBuilder barcodeString = new StringBuilder();
                barcodeString.append("TW").append(result);
                printingView.setBarcode(barcodeString.toString());
                barcodePrintingViewList.add(printingView);
            }
             log.debug("------- {}", barcodePrintingViewList.toString());
            HashMap map = new HashMap<String, Object>();
//            map.put("barcode", barcodePrintingViewList);
//            reportService.genBarcode128(printBarcodeName, startBarcode, qty);
            reportService.exportPDF(pathBarcodePrinting, map, printBarcodeName, barcodePrintingViewList);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    private String replaceFormat(String startBarcode){
        return startBarcode.replace("TW", "");
    }
}
