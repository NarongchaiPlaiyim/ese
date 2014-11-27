package com.ese.beans;

import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.model.view.SetupView;
import com.ese.model.view.WarehouseAndLocationView;
import com.ese.model.view.WarehouseView;
import com.ese.model.view.dilog.WarehouseDialogView;
import com.ese.service.LocationService;
import com.ese.service.SetupService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
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
@ManagedBean(name = "setup")
public class SetupBean extends Bean{
    @ManagedProperty("#{setupService}") private SetupService setupService;
    @ManagedProperty("#{locationService}") private LocationService locationService;

    private final String DIALOG_NAME = "msgBoxSystemMessageDlg";
    private String messageHeader;
    private String message;

    private SetupView setupView;
    private WarehouseAndLocationView warehouseAndLocationView;
    private WarehouseDialogView warehouseDialogView;
    private List<WarehouseAndLocationView> warehouseAndLocationViewList;
    private boolean flagBtnNewWarehouse;
    private boolean flagBtnAddShowItem;
    private boolean flagBtnAddEdit;
    private boolean flagBtnDelete;
    private String modeWarehouse;
    private String nameBtn;
    private List<LocationView> locationViewList;
    private LocationView locationView;
    private List<MSWarehouseModel> msWarehouseModelList;
    private MSWarehouseModel msWarehouseModel;
    private List<MSLocationModel> msLocationModelList;
    private MSLocationModel msLocationModel;
    private WarehouseView warehouseView;

    public SetupBean() {

    }

    @PostConstruct
    private void init(){
        preLoad();
        setupView = new SetupView();
        warehouseDialogView = new WarehouseDialogView();
        locationView = new LocationView();
        msWarehouseModel = new MSWarehouseModel();
        warehouseView = new WarehouseView();
        modeWarehouse = "Mode(New)";
        nameBtn = "Cancel";
        btnOnLoad();
        onLoadLocationTB();
        warehouseOnLoad();
    }

    private void btnOnLoad(){
        flagBtnNewWarehouse = false;
        flagBtnAddShowItem = true;
        flagBtnAddEdit = false;
        flagBtnDelete = true;
    }

    private void warehouseOnLoad(){
        msWarehouseModelList = locationService.getWarehouseAll();
    }

    private void onLoadLocationTB(){
        msLocationModelList = locationService.getLocationAll();
    }

    public void btnWarehouseAndLocation(String target){
        if (target.equalsIgnoreCase("SaveOrUpdate")){
            log.debug("onSaveOrUpdate() {}", locationView);
            try {
                locationService.onSaveOrUpdateLocationToDB(locationView);
                showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
                init();
            } catch (Exception e) {
                log.debug("Exception onSaveLocation : ", e);
                showDialogError(e.getMessage());
            }
        } else if (target.equalsIgnoreCase("Delect")){
            log.debug("-- onDelete()");
            try {
                locationService.delete(msLocationModel);
                showDialog(MessageDialog.DELETE.getMessageHeader(), MessageDialog.DELETE.getMessage());
                init();
            } catch (Exception e) {
                log.error("{}",e);
                showDialogError(e.getMessage());
            }
        } else if (target.equalsIgnoreCase("NewOrCancel")){
            log.debug("NewOrCancel.");
            locationView = new LocationView();
            btnOnload();
        }  else if (target.equalsIgnoreCase("ClickOnTable")){
            log.debug("onClickToLocationTB(), {}", msLocationModel.toString());
            modeWarehouse = "Mode(Edit)";
            nameBtn = "New";
            flagBtnDelete = false;
            flagBtnAddShowItem = false;
            locationView = locationService.clickToView(msLocationModel);
        }
    }

    public void warehouseDlg(String target){
        log.debug("onLoadWarehouseDlg().");

        if (target.equalsIgnoreCase("ADD")){
            log.debug("Open Warehouse Dialog.");
            warehouseView = new WarehouseView();
            msWarehouseModelList = warehouseService.getAll();
        } else if (target.equalsIgnoreCase("New")){
            warehouseView = new WarehouseView();
        } else if (target.equalsIgnoreCase("Save")){
            log.debug("OnSave Warehouse. {}", warehouseView);
            warehouseService.onSaveOrUpdateWarehouse(warehouseView);
            showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
            init();
        } else if (target.equalsIgnoreCase("Delete")){
            log.debug("Delete warehouse.");
            warehouseService.delete(msWarehouseModel);
            showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
            init();
        } else if (target.equalsIgnoreCase("ClickOnTable")){
            log.debug("onclickWarehouseTBDlg(). {}",msWarehouseModel.toString());
            warehouseView = warehouseService.converToView(msWarehouseModel);
        }
    }

    private void showDialog(String messageHeader, String message){
        this.messageHeader = messageHeader;
        this.message = message;
        FacesUtil.showDialog(DIALOG_NAME);
    }

    private void showDialogError(String message){
        showDialog(MessageDialog.ERROR.getMessageHeader(), message);
        init();
    }
}
