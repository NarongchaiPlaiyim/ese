package com.ese.beans;

import com.ese.model.view.ReceivingReportView;
import com.ese.service.ReceivingReportService;
import com.ese.service.security.UserDetail;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "receivingReportBean")
public class ReceivingReportBeans extends Bean{
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{receivingReportService}") private ReceivingReportService receivingReportService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private List<ReceivingReportView> receivingReportViewList;
    private List<ReceivingReportView> filterValue;
    private Date startDate;
    private Date endDate;
    private UserDetail userDetail;

    @PostConstruct
    private void init(){
        newOnload();
        getDataOnload();
        userDetail = getUser();
    }

    private void newOnload(){
        startDate = new Date();
        endDate = new Date();
//        filterValue = new ArrayList<ReceivingReportView>();
    }

    private void getDataOnload(){
        receivingReportViewList = receivingReportService.getReceivingReportView("", "");
    }

    public void onSearchReceivingReport(){
        log.debug("------- {}", endDate.compareTo(startDate)
        );
        if (endDate.compareTo(startDate) < 0){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Start Date > Finish Date.", "msgBoxSystemMessageDlg");
        } else {
            receivingReportViewList = receivingReportService.getReceivingReportView(Utils.convertToStringYYYYMMDDHHmm(startDate), Utils.convertToStringYYYYMMDDHHmm(endDate));
        }
    }

    public void onPrintPDF(){

        if (Utils.isNull(filterValue)){
            receivingReportService.onPrintReceivingReport(receivingReportViewList, userDetail.getUserName());
        } else {
            receivingReportService.onPrintReceivingReport(filterValue, userDetail.getUserName());
        }
    }

    public void onExportCSV(){
        if (Utils.isNull(filterValue)){
            receivingReportService.onExportCSV(receivingReportViewList);
        } else {
            receivingReportService.onExportCSV(filterValue);
        }
    }

//    private List<ReceivingReportView> getData(){
//        ReceivingReportView receivingReportView = new ReceivingReportView();
//        receivingReportView.setReceivingDate(new Date());
//        receivingReportView.setWarehouseCode("w1");
//        receivingReportView.setConveyorLine("c1");
//        receivingReportView.setItemName("i1");
//        receivingReportView.setItemDesc("id1");
//        receivingReportView.setGrade("g1");
//        receivingReportView.setQty(1);
//        receivingReportViewList.add(receivingReportView);
//
//        receivingReportView = new ReceivingReportView();
//        receivingReportView.setReceivingDate(new Date());
//        receivingReportView.setWarehouseCode("w2");
//        receivingReportView.setConveyorLine("c2");
//        receivingReportView.setItemName("i2");
//        receivingReportView.setItemDesc("id2");
//        receivingReportView.setGrade("g2");
//        receivingReportView.setQty(2);
//        receivingReportViewList.add(receivingReportView);
//
//        return receivingReportViewList;
//    }

    public void test(){
        log.debug("------------- : {}", filterValue.size());
        log.debug("----- : {}", Utils.convertToStringYYYYMMDDHHmm(startDate));
        log.debug("-- : {}", Utils.convertToStringYYYYMMDDHHmm(endDate));
//        log.debug("### : {}", endDate.compareTo(startDate));
    }
}
