package com.ese.beans;

import com.ese.model.db.ContainerItemModel;
import com.ese.model.db.LoadingOrderModel;
import com.ese.model.view.ContainerItemView;
import com.ese.model.view.SeparateItemView;
import com.ese.service.SeparateItemService;
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
@ManagedBean(name = "separateItemBean")
public class SeparateItemBean extends Bean{
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{separateItemService}") private SeparateItemService separateItemService;

//    private List<ContainerItemModel> containerItemModelList;
    private List<SeparateItemView> separateItemViewList;

    @NotNull private SeparateItemView separateItemView;
    @NotNull private LoadingOrderModel loadingOrderModel;
    @NotNull private ContainerItemModel containerItemModel;

    private HttpSession session;
    private int containerItemId;
    private int grendTotal;

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
        onLoadContainerItem();
        grendTotal();
    }

    private void onLoadContainerItem(){
        separateItemViewList = separateItemService.getContainerItemByLoadingOrder(loadingOrderModel.getId());

        if (Utils.isZero(separateItemViewList.size())){
            separateItemViewList = separateItemService.getContainerItemByAutoInsert(loadingOrderModel.getId());
        }
    }

    public void onEditContainerQty(){
        try {
            containerItemModel = separateItemService.getByContainerItemId(containerItemId);
            showDialog("Edit Qty", "", "editDlg");
        } catch (Exception e) {
            showDialogWarning(e.getMessage());
            log.debug("Exception error : ", e);
        }
    }

    public void onSaveEdit(){
        try {
            separateItemService.update(containerItemModel);
            onLoadContainerItem();
        } catch (Exception e) {
            log.debug("Exception error : ", e);
            showDialogWarning(e.getMessage());
        }
    }

    public void onCancel(){
        containerItemModel = new ContainerItemModel();
    }

    public void onSaveSeparateItem(){
        showDialog("Save", "Close and back to previous page", "confirmCloseDlg");
    }

    public void redirect(){
        FacesUtil.redirect("/site/showPickingList.xhtml");
    }

    public void preDelete(){
        showDialog("UnSeparate", "You want to remove?", "confirmDeletetDlg");
    }

    public void onUnSeparate(){
        try {
            separateItemService.delete(loadingOrderModel.getId());
            redirect();
        } catch (Exception e) {
            log.debug("Exception error : ", e);
            showDialogWarning(e.getMessage());
        }
    }

    public int grendTotal(){
        int grand = 0;
        for (SeparateItemView view : separateItemViewList){
            grand += view.getSumQty();
            log.debug("------------- {}", view.getSumQty());
        }
        log.debug("+++++++++++++ {}", grand);
        return grand;
    }
}
