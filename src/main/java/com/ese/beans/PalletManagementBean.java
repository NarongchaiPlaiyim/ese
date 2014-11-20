package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.view.PalletManagementView;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "palletManagement")
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
    @Getter
    List<PalletManagementView> palletMeanegementViewList;
    @Getter
    PalletManagementView palletMeanegementView;

    @Getter @Setter
    int statusOnShow;

    @PostConstruct
    public void onCreattion(){
        log.debug("onCreattion().");
        palletMeanegementView = new PalletManagementView();
        warehouseMode = new WarehouseModel();
        conveyorLineModel = new ConveyorLineModel();
        init();
    }

    private void init(){
        log.debug("init().");
        warehouseModelList = warehouseService.getWarehouseList();
        conveyorLineModelList = conveyorLineService.getConveyorLineList();
        statusOnShow = 0;
        onloadPallet();
    }

    private void onloadPallet(){
        log.debug("onloadPallet(). ");
        palletMeanegementViewList = palletService.findPalletJoinLocation();
    }

    public void onfind(){
        log.debug("changeOn : {}", statusOnShow);
        palletMeanegementViewList = palletService.findByChang(statusOnShow, warehouseMode.getId(), conveyorLineModel.getId());
    }
}
