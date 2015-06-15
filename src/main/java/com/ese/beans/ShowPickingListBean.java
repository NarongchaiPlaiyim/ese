package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.ContainerView;
import com.ese.model.view.LoadingOrderView;
import com.ese.service.ShowPickingListService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "showPickingListBean")
public class ShowPickingListBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{showPickingListService}") private ShowPickingListService showPickingListService;
    private List<PickingOrderModel> pickingOrderModelList;
    private List<ContainerModel> containerModelList;
    private List<ContainerItemModel> containerItemModelList;
    private List<ItemSequenceModel> itemSequenceModelList;
    @NotNull private ContainerModel containerModel;
    @NotNull private PickingOrderModel pickingOrderModel;
    @NotNull private LoadingOrderModel loadingOrderModel;
    @NotNull private ContainerView containerView;
    @NotNull private ItemSequenceModel itemSequenceModel;

    private String modeContainer;
    private HttpSession session;
    private String type;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        session = FacesUtil.getSession(true);
        loadingOrderModel = (LoadingOrderModel) session.getAttribute("loadingOrderModel");
        type = (String) session.getAttribute("pageType");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
        tableOnload();
    }

    private void tableOnload(){
        pickingOrderModelList =  showPickingListService.getPickingByLoadingOrderId(loadingOrderModel.getId());
    }

    public void onClose(){


        if ("O".equals(type)){
            FacesUtil.redirect("/site/overSeaLoad.xhtml");
        } else if ("D".equals(type)){
            FacesUtil.redirect("/site/domesticLoad.xhtml");
        }

        session.removeAttribute("pageType");
        session.removeAttribute("loadingOrderModel");
    }

    public void onSelectPickingList(){

        if ("O".equals(type)){
            FacesUtil.redirect("/site/axSelectPickingList.xhtml");
        } else if ("D".equals(type)){
            pickingOrderModel = new PickingOrderModel();
            FacesUtil.redirect("/site/selectPickingList.xhtml");
        }
    }

    public void onClickContainerDlg(){
        pickingOrderModel = new PickingOrderModel();
        containerView = new ContainerView();
        containerModel = new ContainerModel();
        modeContainer = "Mode : New ";
        loadingOrderModel = (LoadingOrderModel) session.getAttribute("loadingOrderModel");
        onLoadContainer();
    }

    private void onLoadContainer(){
        containerModelList = showPickingListService.getContainerByLoadingOrderId(loadingOrderModel.getId());
    }

    public void onSaveContainer(){
        try{
            showPickingListService.saveOrupdate(containerView, loadingOrderModel);
            onClickContainerDlg();
            showDialogSaved();
        } catch (Exception e){
            log.debug("Exception e: ", e);
            showDialogError(e.getMessage());
        }
    }

    public void onClickTableContainer(){
        modeContainer = "Mode : Edit ";
        containerView = showPickingListService.transformToView(containerModel);
    }

    public void onConfirmDeleteContainer(){
        showDialog("confirm", "Please confirm delete container no " + containerModel.getContainerNo(), "confirmDeleteDlg");
    }

    public void onDeleteContainer(){
        try {
            showPickingListService.delete(containerModel);
            onClickContainerDlg();
            showDialog("Success", "Delete Success.", "msgBoxSystemMessageDlg");
        } catch (Exception e){
            log.debug("Exception e : ", e);
            showDialogError(e.getMessage());
        }
    }

    public void onSeperateItem(){

        if (!Utils.isZero(loadingOrderModel.getId())){
            containerItemModelList = showPickingListService.getContainerItemByLoadingOrderId(loadingOrderModel.getId());
        }
    }

    public void onItemSeq(){
        itemSequenceModelList = showPickingListService.getItemSeq(loadingOrderModel.getId());
    }

    public void onSaveItemSeq(){
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (ItemSequenceModel model : itemSequenceModelList){
            if (hashMap.containsKey(model.getSeq())){
                showDialog(MessageDialog.WARNING.getMessageHeader(), "Seq dupicate.", "dupicateSeqDlg");
                return;
            } else if(model.getSeq() > itemSequenceModelList.size()){
                showDialog(MessageDialog.WARNING.getMessageHeader(), "Seq > จำนวน record", "dupicateSeqDlg");
                return;
            } else {
                hashMap.put(model.getSeq(), model.getMsItemModel().getItemId());
            }
        }

        onSave();
    }

    private void onSave(){
        try {
            showPickingListService.save(itemSequenceModelList);
            showDialogSaved();
        } catch (Exception e) {
            log.debug("Exception error on save item sequence : ", e);
            showDialogError(e.getMessage());
        }
    }
    public void onClickSeparateItem(){
        FacesUtil.redirect("/site/separateItem.xhtml");
    }
}
