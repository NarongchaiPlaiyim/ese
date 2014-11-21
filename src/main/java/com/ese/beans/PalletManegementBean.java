package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.db.WorkingAreaModel;
//import com.ese.model.view.PalletMeanagementView;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "palletManegement")
@ViewScoped
public class PalletManegementBean extends Bean implements Serializable {

    @Getter @Setter
    List<WarehouseModel> warehouseModelList;
    @Getter
    WarehouseModel warehouseMode;
    @Getter @Setter
    List<ConveyorLineModel> conveyorLineModelList;
    @Getter
    ConveyorLineModel conveyorLineModel;
//    @Getter @Setter
//    List<PalletMeanagementView> palletMeanegementViewList;
//    @Getter @Setter
//    PalletMeanagementView palletMeanegementView;
    @Getter
    WorkingAreaModel workingAreaModel;
    @Getter @Setter
    List<WorkingAreaModel> workingAreaModelList;

    @Getter @Setter
    int statusOnShow;
    @Getter @Setter
    String textTest;

    @PostConstruct
    public void onCreattion(){
//        log.debug("onCreattion().");
//        palletMeanegementView = new PalletMeanagementView();
//        warehouseMode = new WarehouseModel();
//        conveyorLineModel = new ConveyorLineModel();
//        workingAreaModel = new WorkingAreaModel();
//        init();
    }

    private void init(){
        log.debug("init().");
        warehouseModelList = warehouseService.getWarehouseList();
        workingAreaModelList = workingAreaService.getWorkingAreaList();
//        conveyorLineModelList = conveyorLineService.getConveyorLineList();
        statusOnShow = 0;
//        onloadPallet();
    }

//    private void onloadPallet(){
//        log.debug("onloadPallet(). ");
//        palletMeanegementViewList = palletService.findPalletJoinLocation();
//    }
//
//    public void onfind(){
//        log.debug("changeOn : {}", statusOnShow);
//        palletMeanegementViewList = palletService.findByChang(statusOnShow, warehouseMode.getId(), workingAreaModel.getId());
//    }
//
//
//    public void test(){
//        log.debug("palletMeanegementView : {}", palletMeanegementView.toString());
//    }
}
