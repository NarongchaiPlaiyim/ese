package com.ese.beans;

import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.PickingOrderView;
import com.ese.service.PickingOrderService;
import com.ese.service.security.UserDetail;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "pickingOrderBean")
public class PickingOrderBean extends Bean {
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{pickingOrderService}") private PickingOrderService pickingOrderService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private boolean flagSync;
    private boolean flagBtnShow;
    private boolean flagBtnPrint;
    private boolean flagBtnSync;
    private String selectType;
    private PickingOrderView pickingOrderView;
    private PickingOrderModel pickingOrderModel;
    private List<PickingOrderModel> pickingOrderModelList;
    private List<StatusModel> statusValue;
    private List<DataSyncConfirmOrderView> syncConfirmOrderViewList;
    private List<DataSyncConfirmOrderView> seleteDataSync;

    private UserDetail userDetail;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()/* && isAuthorize(key)*/){
            init();
        }
    }

    private void init(){
        initBtn();
        pickingOrderView = new PickingOrderView();
        pickingOrderModelList = new ArrayList<PickingOrderModel>();
        getCurrent();
        onLoadTable();
        onLoadStatue();
    }

    private void initBtn(){
        flagSync = false;
        flagBtnShow = true;
        flagBtnPrint = true;
    }

    private void getCurrent(){
        userDetail = getUser();
    }

    private void onLoadTable(){
        pickingOrderModelList = pickingOrderService.getPickingOrderByOverSeaOrder(pickingOrderService.getTypeBeforeOnLoaf(userDetail.getId()));
    }

    private void onLoadStatue(){
        statusValue = pickingOrderService.getStatusAll(1);
    }

    public void onSearch(){
        pickingOrderModelList =  pickingOrderService.getPickingOnSearch(pickingOrderView);
    }

    public void onClickTable(){
        log.debug("pickingOrderModel : {}", pickingOrderModel.toString());

        if (pickingOrderModel.getStatus().getId() > 2){
            flagBtnShow = false;
            flagBtnPrint = false;
        } else if (pickingOrderModel.getStatus().getId() == 1){
            flagBtnShow = false;
            flagBtnPrint = true;
        }
    }

    public void onSyncData(){
        syncConfirmOrderViewList = pickingOrderService.getDataOnSync();

        if (Utils.isSafetyList(syncConfirmOrderViewList)){
            flagBtnSync = false;
        }
        pickingOrderService.updateStatus(syncConfirmOrderViewList);
    }

    public void onClose(){
        pickingOrderService.rollbackStatus();
    }

    public void onSyncOrder(){
        if (!Utils.isZero(seleteDataSync.size())){
            pickingOrderService.syncOrder(seleteDataSync, userDetail);
            showDialog("Sync Suscess.", "Suscess.", "msgBoxSystemMessageDlg2");
            onLoadTable();
        } else {
            showDialogWarning("Please select data.");
        }
    }
}
