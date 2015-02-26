package com.ese.beans;

import com.ese.model.view.InventoryOnhandReportView;
import com.ese.service.InventoryOnhandReportService;
import com.ese.service.security.UserDetail;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "inventoryOnhandReportBean")
public class InventorryOnhandReportBean extends Bean{
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{inventoryOnhandReportService}") private InventoryOnhandReportService inventoryOnhandReportService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private List<InventoryOnhandReportView> onhandReportViewList;
    private List<InventoryOnhandReportView> filterOnhandReport;
    private UserDetail userDetail;

    private int sumAvailableQty;
    private int sumAvailableAmount;
    private int sumReservedQty;
    private int sumReservedAmount;
    private int sumPickQty;
    private int sumPickAmount;
    private int sumPackQty;
    private int sumPackAmount;
    private int sumPhysicalQty;
    private int sumPhysicalAmount;

    @PostConstruct
    private void init(){
        onLoadInventoryReport();
        userDetail = getUser();
    }

    private void onLoadInventoryReport(){
        onhandReportViewList = inventoryOnhandReportService.getInventoryOnhandReport();
    }

    public void onPrint(){
        if (!Utils.isSafetyList(filterOnhandReport)){
            inventoryOnhandReportService.print(onhandReportViewList, userDetail.getUserName());
        } else {
            inventoryOnhandReportService.print(filterOnhandReport, userDetail.getUserName());
        }
    }

    public void onCSV(){
        if (!Utils.isSafetyList(filterOnhandReport)){
            inventoryOnhandReportService.onExportCSV(onhandReportViewList);
        } else {
            inventoryOnhandReportService.onExportCSV(filterOnhandReport);
        }
    }

    public int getSumAvailableQty() {
        if (Utils.isNull(filterOnhandReport)){
            for (InventoryOnhandReportView view : onhandReportViewList){
                sumAvailableQty += view.getAvailableView().getQty();
            }
        } else {
            for (InventoryOnhandReportView view : filterOnhandReport){
                sumAvailableQty += view.getAvailableView().getQty();
            }
        }

        return sumAvailableQty;
    }

    public int getSumAvailableAmount() {
        if (Utils.isNull(filterOnhandReport)){
            for (InventoryOnhandReportView view : onhandReportViewList){
                sumAvailableAmount += view.getAvailableView().getAmount();
            }
        } else {
            for (InventoryOnhandReportView view : filterOnhandReport){
                sumAvailableAmount += view.getAvailableView().getAmount();
            }
        }

        return sumAvailableAmount;
    }
}
