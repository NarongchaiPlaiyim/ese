package com.ese.beans;

import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.model.view.LoadingOrderView;
import com.ese.service.DomesticLoadService;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "domesticLoadBean")
public class DomesticLoadBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{domesticLoadService}") private DomesticLoadService domesticLoadService;
    @ManagedProperty("#{message['authorize.menu.loading.tab.1']}") private String key;

//    private String docNo;
//    private String loadingDate;
    private int status;
    private boolean flagdBtnReport;
    private boolean flagBtnShowPicking;

    @NotNull private LoadingOrderModel loadingOrderModel;
    @NotNull private List<LoadingOrderModel> loadingOrderModelList;
    @NotNull private List<StatusModel> statusValue;
    @NotNull private LoadingOrderView loadingOrderView;
    private boolean mode = Boolean.TRUE;
    private String labMode;


    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
       if(preLoad()) {//&& isAuthorize(key)){
            init();
       }
    }

    private void init(){
        onLoadStatue();
        onLoadTable();
        loadingOrderModel = new LoadingOrderModel();
        loadingOrderView = new LoadingOrderView();
        labMode = "Mode(New)";
        flagdBtnReport = Boolean.TRUE;
        flagBtnShowPicking = Boolean.TRUE;
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
            loadingOrderModel.setDocNo(Utils.getDocumentDomesticLoad());
        }
    }

    public void onClickSaveLoadingOrder(){
        if (mode) {
            domesticLoadService.save(loadingOrderView);
            showDialogSaved();
        } else {
            domesticLoadService.edit(loadingOrderView);
            showDialogEdited();
        }
        mode = Boolean.TRUE;
        init();
    }

    public void onClickLoadingOrderTB(){
        mode = Boolean.FALSE;
        flagdBtnReport = Boolean.FALSE;
        flagBtnShowPicking = Boolean.FALSE;
        labMode = "Mode(Edit)";
        loadingOrderView = domesticLoadService.transToView(loadingOrderModel);
    }

    public void onClickSearch(){
        loadingOrderModelList = domesticLoadService.getSearch(status);
    }

    public void onClickNew(){
        loadingOrderView = new LoadingOrderView();
        labMode = "Mode(New)";
        mode = Boolean.TRUE;
        loadingOrderModel = new LoadingOrderModel();
    }

    public void onClickShowPickingList(){
        HttpSession session = FacesUtil.getSession(true);
        session.setAttribute("loadingOrderModel", loadingOrderModel);
        session.setAttribute("pageType", "D");
        FacesUtil.redirect("/site/showPickingList.xhtml");
    }
}
