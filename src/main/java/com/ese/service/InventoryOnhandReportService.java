package com.ese.service;

import com.ese.model.dao.InventoryOnhandReportDAO;
import com.ese.model.view.InventoryOnhandReportView;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class InventoryOnhandReportService extends Service{
    private static final long serialVersionUID = 4112578634920874840L;
    @Resource private InventoryOnhandReportDAO onhandReportDAO;
    @Resource private ReportService reportService;
    @Resource private CSVService csvService;
    @Value("#{config['report.inventoryreport']}")
    private String pathreport;
    private final String  COMMA_DELIMITED = ",";

    public List<InventoryOnhandReportView> getInventoryOnhandReport(){
        return onhandReportDAO.findInventOnhandReport();
    }

    public void print(List<InventoryOnhandReportView> views, String user){
        String printTagReportname = Utils.genReportName("_ReceivingReport");
        List<InventoryOnhandReportView> reportViewList = new ArrayList<InventoryOnhandReportView>();
        HashMap map = new HashMap<String, Object>();

        map.put("userPrint", user);
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());

        int no = 1;
        for (InventoryOnhandReportView view : views){
            InventoryOnhandReportView reportView = new InventoryOnhandReportView();
            reportView.setNo(no);
            reportView.setItemCode(view.getItemCode());
            reportView.setWarehouseName(view.getWarehouseName());
            reportView.setLocationBarcode(view.getLocationBarcode());
            reportView.setBatchNo(view.getBatchNo());
            reportView.setAvailableQty(view.getAvailableView().getQty());
            reportView.setAvailableAmount(view.getAvailableView().getAmount());
            reportView.setReservedQty(view.getReservedView().getQty());
            reportView.setReservedAmount(view.getReservedView().getAmount());
            reportView.setPickQty(view.getPickView().getQty());
            reportView.setPickAmount(view.getPickView().getAmount());
            reportView.setPackQty(view.getPackView().getQty());
            reportView.setPackAmount(view.getPackView().getAmount());
            reportView.setPhysicalQty(view.getPhysicalView().getQty());
            reportView.setPhysicalAmount(view.getPhysicalView().getAmount());
            reportViewList.add(reportView);

            no++;
        }
        log.debug("reportViewList siz : {}", reportViewList.size());
        try {
            reportService.exportPDF(pathreport, map, printTagReportname, reportViewList);
        } catch (Exception e) {
            log.debug("Exception error onPrintReceivingReport : ", e);
        }
    }

    public void onExportCSV(List<InventoryOnhandReportView> reportViews){
        String printTagReportname = Utils.genReportName("_ReceivingReport");
        int sumAvailableQty = 0;
        int sumAvailableAmount = 0;
        int sumReservedQty = 0;
        int sumReservedAmount = 0;
        int sumPickQty = 0;
        int sumPickAmount = 0;
        int sumPackQty = 0;
        int sumPackAmount = 0;
        int sumPhysicalQty = 0;
        int sumPhysicalAmount = 0;

        StringBuilder csvReceiving =  new StringBuilder();
        csvReceiving.append("NO").append(COMMA_DELIMITED);
        csvReceiving.append("Item_Code").append(COMMA_DELIMITED);
        csvReceiving.append("Warehouse").append(COMMA_DELIMITED);
        csvReceiving.append("Location").append(COMMA_DELIMITED);
        csvReceiving.append("Batch").append(COMMA_DELIMITED);
        csvReceiving.append("Available_Qty").append(COMMA_DELIMITED);
        csvReceiving.append("Available_Amount").append(COMMA_DELIMITED);
        csvReceiving.append("Reserved_Qty").append(COMMA_DELIMITED);
        csvReceiving.append("Reserved_Amount").append(COMMA_DELIMITED);
        csvReceiving.append("Pick_Qty").append(COMMA_DELIMITED);
        csvReceiving.append("Pick_Amount").append(COMMA_DELIMITED);
        csvReceiving.append("Pack_Qty").append(COMMA_DELIMITED);
        csvReceiving.append("Pack_Amount").append(COMMA_DELIMITED);
        csvReceiving.append("Physical_Qty").append(COMMA_DELIMITED);
        csvReceiving.append("Physical_Amount").append('\n');

        int no = 1;
        for (InventoryOnhandReportView view : reportViews){
            csvReceiving.append(no).append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getItemCode()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getWarehouseName()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getLocationBarcode()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getBatchNo()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getAvailableView().getQty()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getAvailableView().getAmount()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getReservedView().getQty()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getReservedView().getAmount()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPickView().getQty()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPickView().getAmount()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPackView().getQty()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPackView().getAmount()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPhysicalView().getQty()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getPhysicalView().getAmount()) + '"').append('\n');

            sumAvailableQty += view.getAvailableView().getQty();
            sumAvailableAmount += view.getAvailableView().getAmount();
            sumReservedQty += view.getReservedView().getQty();
            sumReservedAmount += view.getReservedView().getAmount();
            sumPickQty += view.getPickView().getQty();
            sumPickAmount += view.getPickView().getAmount();
            sumPackQty += view.getPackView().getQty();
            sumPackAmount += view.getPackView().getAmount();
            sumPhysicalQty += view.getPhysicalView().getQty();
            sumPhysicalAmount += view.getPhysicalView().getAmount();

            no++;
        }
        csvReceiving.append(COMMA_DELIMITED).append(COMMA_DELIMITED).append(COMMA_DELIMITED).append(COMMA_DELIMITED);
        csvReceiving.append("Total").append(COMMA_DELIMITED).append(sumAvailableQty).append(COMMA_DELIMITED);
        csvReceiving.append(sumAvailableAmount).append(COMMA_DELIMITED).append(sumReservedQty).append(COMMA_DELIMITED);
        csvReceiving.append(sumReservedAmount).append(COMMA_DELIMITED).append(sumPickQty).append(COMMA_DELIMITED);
        csvReceiving.append(sumPickAmount).append(COMMA_DELIMITED).append(sumPackQty).append(COMMA_DELIMITED);
        csvReceiving.append(sumPackAmount).append(COMMA_DELIMITED).append(sumPhysicalQty).append(COMMA_DELIMITED);
        csvReceiving.append(sumPhysicalAmount);

        csvService.exportCSV(printTagReportname, csvReceiving.toString());
    }
}
