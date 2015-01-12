package com.ese.beans;

import com.ese.model.db.PickingOrderModel;
import com.ese.model.view.PickingOrderView;
import com.ese.service.PickingOrderService;
import com.ese.service.security.UserDetail;
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
    private String selectType;
    private PickingOrderView pickingOrderView;
    private PickingOrderModel pickingOrderModel;
    private List<PickingOrderModel> pickingOrderModelList;

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
}
