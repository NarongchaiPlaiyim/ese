package com.ese.beans;

import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.view.PickingOrderView;
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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "selectPickingListBean")
public class SelectPickingListBean extends Bean{
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{showPickingListService}") private com.ese.service.ShowPickingListService showPickingListService;

    private List<PickingOrderModel> pickingOrderModelList;
    private List<PickingOrderModel> selectPickingOrderModelList;
    @NotNull private PickingOrderView pickingOrderView;
    @NotNull private LoadingOrderModel loadingOrderModel;

    private HttpSession session;
    private boolean flagBtnSelect;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        session = FacesUtil.getSession(true);
        loadingOrderModel = (LoadingOrderModel) session.getAttribute("loadingOrderModel");

        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
        tableOnLoad();
        pickingOrderView = new PickingOrderView();
        flagBtnSelect = Boolean.TRUE;
    }

    private void tableOnLoad(){
        pickingOrderModelList = showPickingListService.getPickingByStatusPostPick();
    }

    public void onSearch(){
        pickingOrderModelList = showPickingListService.getPickingBySearchStatusPostPick(pickingOrderView);
        selectPickingOrderModelList = new ArrayList<PickingOrderModel>();
    }

    public void onClickTablePickingList(){
        if (!Utils.isZero(selectPickingOrderModelList.size())){
            flagBtnSelect = Boolean.FALSE;
        } else {
            flagBtnSelect = Boolean.TRUE;
        }
    }

    public void onSelectPickingList(){
        try{
            if (!Utils.isZero(selectPickingOrderModelList.size())){
                showPickingListService.select(loadingOrderModel.getId(), selectPickingOrderModelList);
                showDialogSaved();
            }
        } catch (Exception e){
            log.error("Exception e : ",e);
            showDialogError(e.getMessage());
        }
    }

    public void onClickDialogConfirm(){
        FacesUtil.redirect("/site/showPickingList.xhtml");
//        session.removeAttribute("loadingOrderModel");
    }
}
