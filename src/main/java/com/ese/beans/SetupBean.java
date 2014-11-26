package com.ese.beans;

import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.model.view.SetupView;
import com.ese.model.view.WarehouseAndLocationView;
import com.ese.model.view.dilog.WarehouseDialogView;
import com.ese.service.SetupService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "setup")
public class SetupBean extends Bean{
    @ManagedProperty("#{setupService}") private SetupService setupService;

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

    public SetupBean() {

    }

    @PostConstruct
    private void init(){
        preLoad();
        setupView = new SetupView();
        warehouseDialogView = new WarehouseDialogView();
        locationView = new LocationView();
        msWarehouseModel = new MSWarehouseModel();
        modeWarehouse = "Mode(New)";
        nameBtn = "Cancel";
        btnOnload();
        onLoadLocationTB();
        warehouseOnload();
    }

    private void btnOnload(){
        flagBtnNewWarehouse = false;
        flagBtnAddShowItem = true;
        flagBtnAddEdit = false;
        flagBtnDelete = true;
    }

    private void warehouseOnload(){
        msWarehouseModelList = locationService.getWarehouseAll();
    }

    private void onLoadLocationTB(){
        locationViewList = locationService.getLocationAll();
    }

    public void onClickToLocationTB(){
        log.debug("onClickToLocationTB(), {}", locationView.toString());
        modeWarehouse = "Mode(Edit)";
        nameBtn = "New";
    }

    public void onClickDeleteButtonTAB(){
        log.debug("-- onClickDeleteButtonTAB()");
        log.debug("--[BEFORE] warehouseAndLocationViewList.size()[{}]", warehouseAndLocationViewList.size());
        warehouseAndLocationViewList.remove(warehouseAndLocationView);
        log.debug("--[AFTER] warehouseAndLocationViewList.size()[{}]", warehouseAndLocationViewList.size());
    }
}
