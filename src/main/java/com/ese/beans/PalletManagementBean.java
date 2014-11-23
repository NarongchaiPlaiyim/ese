package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.MSWorkingAreaModel;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.PalletManagementView;
import com.ese.service.PalletService;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "palletManagement")
@ViewScoped
public class PalletManagementBean extends Bean implements Serializable {
    @ManagedProperty("#{palletService}") private PalletService palletService;
    private List<MSWarehouseModel> warehouseModelList;
    private MSWarehouseModel warehouseMode;
    private List<ConveyorLineModel> conveyorLineModelList;
    private ConveyorLineModel conveyorLineModel;
    private List<PalletManagementView> palletManegamentViewList;
    private PalletManagementView palletMeanegementView;
    private MSWorkingAreaModel workingAreaModel;
    private List<MSWorkingAreaModel> workingAreaModelList;
    private int statusOnShow;
    private String textTest;
    private String messageHeader;
    private String message;
    private String findKeyItemDescription;
    private MSLocationModel msLocationModel;
    private List<MSLocationModel> msLocationModelList;
    private List<LocationItemView> locationItemViewList;
    private LocationItemView locationItemViews;
    private boolean isCheck;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        palletMeanegementView = new PalletManagementView();
        warehouseMode = new MSWarehouseModel();
        conveyorLineModel = new ConveyorLineModel();
        workingAreaModel = new MSWorkingAreaModel();
        workingAreaModel = new MSWorkingAreaModel();
        msLocationModel = new MSLocationModel();
        locationItemViews = new LocationItemView();
        init();
    }

    private void init(){
        log.debug("init().");
        warehouseModelList = warehouseService.getWarehouseList();
        workingAreaModelList = workingAreaService.getWorkingAreaList();
        msLocationModelList = locationService.getLocationList();
        statusOnShow = 0;
        isCheck = true;
        onLoadPallet();
    }

    private void onLoadPallet(){
        log.debug("onLoadPallet(). ");
        palletManegamentViewList = palletService.findPalletJoinLocation();
    }

    public void onFind(){
        log.debug("changeOn : {}", statusOnShow);
        palletManegamentViewList = palletService.findByChang(statusOnShow, warehouseMode.getId(), workingAreaModel.getId(), msLocationModel.getId(), findKeyItemDescription);
    }

    public void test(){
        log.debug("test().");
//        System.out.println(Utils.safetyList(palletService.findPalletJoinLocation()).toString());
//
//        log.debug("Location Select. {}", locationItemViews.getId());
        palletService.test();
    }

    public void onFindLocation(){
        locationItemViews = new LocationItemView();
        locationItemViewList = locationItemService.getfindLocationAll();
    }

    public void OnPrintTag(String redirect){
        log.debug("OnPrintTag(). {}",palletMeanegementView);

        palletService.onUpdateByPrintTag(palletMeanegementView, redirect);
        RequestContext.getCurrentInstance().execute("PF('msgBoxSystemMessageDlg').show()");
        messageHeader = "Update";
        message = "Successfully Update";
        onCreation();
    }

    public void OnClosePallet(){
        log.debug("OnClosePallet().");

        if (palletMeanegementView.getQty() == 0 && !"Closed".equalsIgnoreCase(palletMeanegementView.getStatus())){
            messageHeader = "Warning";
            message = "The Pallet ID can not be use again. Please click Yes to confirm close this pallet.";
            RequestContext.getCurrentInstance().execute("PF('confirmClosePalletDlg').show()");
        } else if ("Closed".equalsIgnoreCase(palletMeanegementView.getStatus())){
            log.debug("Already Closed Pallet");
            messageHeader = "Warning";
            message = "Already Closed Pallet";
            RequestContext.getCurrentInstance().execute("PF('msgBoxSystemMessageDlg').show()");
        }else {
            log.debug("Qty > 0");
            messageHeader = "Error";
            message = "Qty > 0";
            RequestContext.getCurrentInstance().execute("PF('msgBoxSystemMessageDlg').show()");
        }
    }

    public void changStatusOnClosePallet(String redirect){
        log.debug("changStatusOnClosePallet().");
        palletService.onUpdateByPrintTag(palletMeanegementView, redirect);
        messageHeader = "Update";
        message = "Successfully Update";
        RequestContext.getCurrentInstance().execute("PF('msgBoxSystemMessageDlg').show()");
        onCreation();
    }
}
