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

    private int sumQty;

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

        if (!Utils.isSafetyList(filterValue)){
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

    public int getSumQty() {
        sumQty = 0;
        if (Utils.isNull(filterValue)){
            for (ReceivingReportView view : receivingReportViewList){
                log.debug("-----------1 : [{}]", view.getQty());
                sumQty += view.getQty();
            }
        } else {
            for (ReceivingReportView view : filterValue){
                log.debug("-----------2 : [{}]", view.getQty());
                sumQty += view.getQty();
            }
        }

        return sumQty;
    }
}
