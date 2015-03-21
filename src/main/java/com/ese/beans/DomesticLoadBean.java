package com.ese.beans;

import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.service.DomesticLoadService;
import com.ese.utils.Utils;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "domesticLoadBean")
public class DomesticLoadBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{domesticLoadService}") private DomesticLoadService domesticLoadService;
    @ManagedProperty("#{message['authorize.menu.loading.tab.1']}") private String key;

    private String docNo;
    private Date loadingDate;
    private int status;

    @NotNull private LoadingOrderModel loadingOrderModel;
    @NotNull private List<LoadingOrderModel> loadingOrderModelList;
    @NotNull private List<StatusModel> statusValue;

    private String btnName = "New loading order";
    private boolean mode = Boolean.TRUE;


    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
       if(preLoad()) {//&& isAuthorize(key)){
            init();
       }
    }

    private void init(){
        loadingDate = Utils.currentDate();
        onLoadStatue();
        onLoadTable();
    }

    private void onLoadTable(){
        loadingOrderModelList = domesticLoadService.getList();
    }

    private void onLoadStatue(){
        statusValue = domesticLoadService.getStatusAll();
    }

    public void onClickButtonNew(){
        if (mode) {
            loadingOrderModel = new LoadingOrderModel();
        }
    }

    public void onClickSaveLoadingOrderDialog(){
        System.out.println(mode);
        if (mode) {
            domesticLoadService.save(loadingOrderModel);
            showDialogSaved();
        } else {
            domesticLoadService.edit(loadingOrderModel);
            showDialogEdited();
        }
        init();
    }

    public void onClickPalletTB(){
        btnName = "Edit loading order";
        mode = Boolean.FALSE;
    }

    public void onClickSearch(){
        loadingOrderModelList = domesticLoadService.getSearch(docNo, loadingDate, status);
    }
}
