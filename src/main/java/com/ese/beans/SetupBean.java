package com.ese.beans;

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

    public SetupBean() {

    }

//    @PostConstruct
//    private void pre(){
//        log.debug("-- pre()");
//    }

    @PostConstruct
    private void init(){
        setupView = new SetupView();
        warehouseDialogView = new WarehouseDialogView();
        warehouseAndLocationViewList = new ArrayList();
        warehouseAndLocationView = new WarehouseAndLocationView();
        onLoadTableWarehouseAndLocation();
    }

    private void onLoadTableWarehouseAndLocation(){
        log.debug("-- onLoadTableWarehouseAndLocation()");
        WarehouseAndLocationView warehouseAndLocationView;
        for (int i = 0; i < 101; i++) {
            warehouseAndLocationView = new WarehouseAndLocationView();
            warehouseAndLocationView.setId(i);
            warehouseAndLocationView.setWarehouseId(i + 3000);
            warehouseAndLocationView.setCapacities(i + 4000);
            warehouseAndLocationView.setLocationCode("LocationCode"+i);
            warehouseAndLocationView.setLocationName("LocationName"+i);
            warehouseAndLocationView.setRemark("Remark"+i);
            warehouseAndLocationViewList.add(warehouseAndLocationView);
        }
        log.debug("-- warehouseAndLocationViewList.size()[{}]", warehouseAndLocationViewList.size());
    }

    public void test(){
        log.debug("test(View[{}])", warehouseAndLocationView.toString());
        log.debug("");
    }

    //TAB Warehouse and location
    public void onClickNewButtonTAB(){
        log.debug("-- onClickNewButtonTAB()");
    }

    public void onClickSaveButtonTAB(){
        log.debug("-- onClickSaveButtonTAB()");
    }

    public void onClickDeleteButtonTAB(){
        log.debug("-- onClickDeleteButtonTAB()");
        log.debug("--[BEFORE] warehouseAndLocationViewList.size()[{}]", warehouseAndLocationViewList.size());
        warehouseAndLocationViewList.remove(warehouseAndLocationView);
        log.debug("--[AFTER] warehouseAndLocationViewList.size()[{}]", warehouseAndLocationViewList.size());
    }

    public void onClickNewEditButtonTAB(){
        log.debug("-- onClickNewEditButtonTAB()");
    }

    public void onClickShowAddItemButtonTAB(){
        log.debug("-- onClickShowAddItemButtonTAB()");
    }

    //Dialog of above Tab
    public void onClickNewButtonTABDialog(){
        log.debug("-- onClickNewButtonTABDialog()");
    }

    public void onClickSaveButtonTABDialog(){
        log.debug("-- onClickSaveButtonTABDialog()");
    }

    public void onClickDeleteButtonTABDialog(){
        log.debug("-- onClickDeleteButtonTABDialog()");
    }


    //Tab Stock in out note
    public void onClickNewButtonTAB2(){
        log.debug("-- onClickNewButtonTAB2()");
    }

    public void onClickSaveButtonTAB2(){
        log.debug("-- onClickSaveButtonTAB2()");
    }

    public void onClickDeleteButtonTAB2(){
        log.debug("-- onClickDeleteButtonTAB2()");
    }
}
