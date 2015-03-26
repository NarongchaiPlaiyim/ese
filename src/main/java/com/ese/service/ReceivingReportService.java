package com.ese.service;

import com.ese.model.dao.ReceivingReportDAO;
import com.ese.model.view.ReceivingReportView;
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
public class ReceivingReportService extends Service{
    private static final long serialVersionUID = 4112578634920874840L;
    @Resource private ReceivingReportDAO receivingReportDAO;
    @Resource private ReportService reportService;
    @Resource private CSVService csvService;

    @Value("#{config['report.receivingreport']}")
    private String pathreport;

    private final String  COMMA_DELIMITED = ",";

    public List<ReceivingReportView> getReceivingReportView(String startDate, String endDate){
        return receivingReportDAO.findReceivingReport(startDate, endDate);
    }

    public void onPrintReceivingReport(List<ReceivingReportView> receivingReportViews, String user){
        String printTagReportname = Utils.genReportName("_ReceivingReport");
        List<ReceivingReportView> reportViewList = new ArrayList<ReceivingReportView>();
        HashMap map = new HashMap<String, Object>();

        map.put("userPrint", user);
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());

        int no = 1;
        for (ReceivingReportView view : receivingReportViews){
            ReceivingReportView reportView = new ReceivingReportView();
            reportView.setNo(no);
            reportView.setReceivingDate(view.getReceivingDate());
            reportView.setWarehouseCode(view.getWarehouseCode());
            reportView.setConveyorLine(view.getConveyorLine());
            reportView.setItemName(view.getItemName());
            reportView.setItemDesc(view.getItemDesc());
            reportView.setGrade(view.getGrade());
            reportView.setQty(view.getQty());
            reportViewList.add(reportView);

            no++;
        }

        try {
            reportService.exportPDF(pathreport, map, printTagReportname, reportViewList);
        } catch (Exception e) {
            log.debug("Exception error onPrintReceivingReport : ", e);
        }
    }

    public void onExportCSV(List<ReceivingReportView> reportViews){
        String printTagReportname = Utils.genReportName("_ReceivingReport");
        int sumQty = 0;

        StringBuilder csvReceiving =  new StringBuilder();
        csvReceiving.append("NO").append(COMMA_DELIMITED);
        csvReceiving.append("Receiving_Date").append(COMMA_DELIMITED);
        csvReceiving.append("Warehouse").append(COMMA_DELIMITED);
        csvReceiving.append("Conveyor_line").append(COMMA_DELIMITED);
        csvReceiving.append("Item").append(COMMA_DELIMITED);
        csvReceiving.append("Item_Description").append(COMMA_DELIMITED);
        csvReceiving.append("Grade").append(COMMA_DELIMITED);
        csvReceiving.append("Qty").append('\n');

        int no = 1;
        for (ReceivingReportView view : reportViews){
            csvReceiving.append(no).append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getReceivingDate()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getWarehouseCode()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getConveyorLine()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getItemName()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getItemDesc()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyString(view.getGrade()) + '"').append(COMMA_DELIMITED);
            csvReceiving.append('"' + Utils.EmptyInteget(view.getQty()) + '"').append('\n');
            sumQty = sumQty + view.getQty();
            no++;
        }

        csvReceiving.append(COMMA_DELIMITED).append(COMMA_DELIMITED).append(COMMA_DELIMITED).append(COMMA_DELIMITED).append(COMMA_DELIMITED)
                .append(COMMA_DELIMITED).append(" Total :").append(COMMA_DELIMITED).append(Utils.EmptyInteget(sumQty));

        csvService.exportCSV(printTagReportname, csvReceiving.toString());
    }
}
