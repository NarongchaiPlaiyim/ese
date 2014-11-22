package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.LocationModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.db.WorkingAreaModel;
import com.ese.model.view.PalletManagementView;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "palletManegement")
@ViewScoped
public class PalletManagementBean extends Bean implements Serializable {

    @Getter @Setter
    List<WarehouseModel> warehouseModelList;
    @Getter
    WarehouseModel warehouseMode;
    @Getter @Setter
    List<ConveyorLineModel> conveyorLineModelList;
    @Getter
    ConveyorLineModel conveyorLineModel;
    @Getter @Setter
    List<PalletManagementView> palletMeanegementViewList;
    @Getter @Setter
    PalletManagementView palletMeanegementView;
    @Getter
    WorkingAreaModel workingAreaModel;
    @Getter @Setter
    List<WorkingAreaModel> workingAreaModelList;

    @Getter @Setter
    int statusOnShow;
    @Getter @Setter
    String textTest;

    @Getter @Setter
    LocationModel locationModel;
    @Getter @Setter
    List<LocationModel> locationModelList;
    @Getter @Setter
    String messageHeader;
    @Getter @Setter
    String message;
    @Getter @Setter
    String findKeyItemDescription;

    @PostConstruct
    public void onCreattion(){
        log.debug("onCreattion().");
        palletMeanegementView = new PalletManagementView();
        warehouseMode = new WarehouseModel();
        conveyorLineModel = new ConveyorLineModel();
        workingAreaModel = new WorkingAreaModel();
        locationModel = new LocationModel();
        init();
    }

    private void init(){
        log.debug("init().");
        warehouseModelList = warehouseService.getWarehouseList();
        workingAreaModelList = workingAreaService.getWorkingAreaList();
        locationModelList = locationService.getLocationList();
        statusOnShow = 0;
        onloadPallet();
    }

    private void onloadPallet(){
        log.debug("onloadPallet(). ");
        palletMeanegementViewList = palletService.findPalletJoinLocation();
    }

    public void onfind(){
        log.debug("changeOn : {}", statusOnShow);
        palletMeanegementViewList = palletService.findByChang(statusOnShow, warehouseMode.getId(), workingAreaModel.getId(), locationModel.getId(), findKeyItemDescription);
    }

    public void test(){
        log.debug("palletMeanegementView : {}", palletMeanegementView.toString());
    }

    public void onFindLocation(){

    }

    public void OnPrintTag(String redirect){
        log.debug("OnPrintTag(). {}",palletMeanegementView);

        palletService.onUpdateByPrintTag(palletMeanegementView, redirect);
        RequestContext.getCurrentInstance().execute("PF('msgBoxSystemMessageDlg').show()");
        messageHeader = "Update";
        message = "Successfully Update";
        onCreattion();
    }

    public void OnClosePallet(){
        log.debug("OnClosePallet().");

        if (palletMeanegementView.getQty() == 0){
            messageHeader = "Warning";
            message = "The Pallet ID can not be use again. Please click Yes to confirm close this pallet.";
            RequestContext.getCurrentInstance().execute("PF('confirmClosePalletDlg').show()");
        } else if ("Closed".equalsIgnoreCase(palletMeanegementView.getStatus())){
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
        onCreattion();
    }
}
