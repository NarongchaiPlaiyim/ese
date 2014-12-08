package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.MSWorkingAreaModel;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.PalletManagementView;
import com.ese.service.*;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "palletManagement")
@ViewScoped
public class PalletManagementBean extends Bean implements Serializable {
    @ManagedProperty("#{palletService}") private PalletService palletService;
    @ManagedProperty("#{warehouseService}") private WarehouseService warehouseService;
    @ManagedProperty("#{workingAreaService}") private WorkingAreaService workingAreaService;
    @ManagedProperty("#{locationService}") private LocationService locationService;
    @ManagedProperty("#{locationItemService}") private LocationItemService locationItemService;

    private List<MSWarehouseModel> warehouseModelList;
    private MSWarehouseModel warehouseMode;
    private List<ConveyorLineModel> conveyorLineModelList;
    private List<PalletManagementView> palletManegamentViewList;
    private PalletManagementView palletMeanegementView;
    private MSWorkingAreaModel workingAreaModel;
    private List<MSWorkingAreaModel> workingAreaModelList;
    private int statusOnShow;
    private String textTest;
    private String findKeyItemDescription;
    private MSLocationModel msLocationModel;
    private List<MSLocationModel> msLocationModelList;
    private List<LocationItemView> locationItemViewList;
    private LocationItemView locationItemViews;
    private boolean isCheckLocation;
    private boolean isCheckPrintTag;
    private boolean isCheckClosePallet;
    private boolean isCheckLocationDialog;

    private int pmvId;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){
            init();
        }
    }

    private void init(){
        log.debug("init().");
        palletMeanegementView = new PalletManagementView();
        warehouseMode = new MSWarehouseModel();
        workingAreaModel = new MSWorkingAreaModel();
        msLocationModel = new MSLocationModel();

        warehouseModelList = warehouseService.getWarehouseAll();
        workingAreaModelList = workingAreaService.getWorkingAreaList();
        msLocationModelList = locationService.getLocationList();
        findKeyItemDescription = "";
        isCheckLocation = true;
        isCheckPrintTag = true;
        isCheckClosePallet = true;
        onLoadPallet();
    }

    private void onLoadPallet(){
        log.debug("onLoadPallet(). ");
        palletManegamentViewList = palletService.findPalletJoinLocation();
    }

    public void onFind(){
        log.debug("changeOn : {}, findKeyItemDescription : {}", statusOnShow, findKeyItemDescription);
        log.debug("{},{},{}",warehouseMode.getId(),workingAreaModel.getId(),msLocationModel.getId());
        int warehouseId = 0;
        int waokingAreaId = 0;
        int locationId = 0;
        palletMeanegementView = new PalletManagementView();

        if (!Utils.isNull(warehouseMode.getId())){
            warehouseId = warehouseMode.getId();
        }

        if (!Utils.isNull(workingAreaModel.getId())){
            waokingAreaId = workingAreaModel.getId();
        }

        if (!Utils.isNull(msLocationModel.getId())){
            locationId = msLocationModel.getId();
        }

        palletManegamentViewList = palletService.findByChang(statusOnShow, warehouseId, waokingAreaId, locationId, findKeyItemDescription);
    }

    public void onClickPalletTB(){
        log.debug("onClickPalletTB(). {}", palletMeanegementView);
        isCheckPrintTag = false;
        isCheckClosePallet = false;
        if (palletMeanegementView.getStatus().getId() > 2){
            isCheckLocation = false;
        } else {
            isCheckLocation = true;
        }
    }

    public void onFindLocation(){
        locationItemViews = new LocationItemView();
        log.debug("onFindLocation : {}",locationItemViews.toString());
        isCheckLocation = true;
        setMessageHeader("Find Location for Item " + palletMeanegementView.getItemModel().getDSGThaiItemDescription());
        locationItemViewList = locationItemService.findLocationByItemId(palletMeanegementView.getItemModel().getId());
        statusOnShow = 0;
    }

    public void onPrintTag(String redirect){
        log.debug("OnPrintTag(). {}",palletMeanegementView);
        pmvId = palletMeanegementView.getId();
        palletService.onUpdateByPrintTag(palletMeanegementView, redirect);
        setMessage(MessageDialog.UPDATE.getMessage());
        setMessageHeader(MessageDialog.UPDATE.getMessageHeader());
        onCreation();
        statusOnShow = 0;
    }

    public void callReport() {
        palletService.onPrintTag(pmvId);
    }

    public void OnClosePallet(){
        log.debug("OnClosePallet().");
        if (palletMeanegementView.getQty() == 0 && !"Closed".equalsIgnoreCase(palletMeanegementView.getStatus().getName())){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "The Pallet ID can not be use again. Please click Yes to confirm close this pallet.", "confirmClosePalletDlg");
        } else if ("Closed".equalsIgnoreCase(palletMeanegementView.getStatus().getName())){
            log.debug("Already Closed Pallet");
            showDialogWarning("Already Closed Pallet");
        }else {
            log.debug("Qty > 0");
            showDialogError("Qty > 0");
        }
        statusOnShow = 0;
    }

    public void changStatusOnClosePallet(String redirect){
        log.debug("changStatusOnClosePallet().");
        palletService.onUpdateByPrintTag(palletMeanegementView, redirect);
        showDialogUpdated();
        onCreation();
    }

    public void onClickLocationTB(){
        log.debug("onClickLocationTB {}", locationItemViews.toString());
        isCheckLocationDialog = false;
    }

    public void OnChangeLocationToPallet(){
        log.debug("OnChangeLocationToPallet().");
        log.debug("PalletView : {}, locationView : {}",palletMeanegementView.getId(),locationItemViews.getId());
        palletService.changeLocation(palletMeanegementView, locationItemViews);
        showDialogUpdated();
        onCreation();
    }
}
