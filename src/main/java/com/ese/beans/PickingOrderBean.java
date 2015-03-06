package com.ese.beans;

import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.PickingOrderView;
import com.ese.service.PickingOrderService;
import com.ese.service.security.UserDetail;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
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
@ManagedBean(name = "pickingOrderBean")
public class PickingOrderBean extends Bean {
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{pickingOrderService}") private PickingOrderService pickingOrderService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private boolean flagSync;
    private boolean flagBtnShow;
    private boolean flagBtnPrint;
    private boolean flagBtnSync;
    private boolean flagBtnCancel;
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
            userDetail = getUser();
            init();
        }
    }

    private void init(){
        initBtn();
        pickingOrderView = new PickingOrderView();
        pickingOrderModelList = new ArrayList<PickingOrderModel>();
        onLoadTable();
        onLoadStatue();
    }

    private void initBtn(){
        flagSync = false;
        flagBtnShow = true;
        flagBtnPrint = true;
        flagBtnCancel = true;
    }

    private void onLoadTable(){
        pickingOrderModelList = pickingOrderService.getPickingOrderByOverSeaOrder(pickingOrderService.getTypeBeforeOnLoaf(userDetail.getId()));
    }

    private void onLoadStatue(){
        statusValue = pickingOrderService.getStatusAll();
    }

    public void onSearch(){
        pickingOrderModelList =  pickingOrderService.getPickingOnSearch(pickingOrderView);
    }

    public void onClickTable(){
        log.debug("pickingOrderModel : {}", pickingOrderModel.toString());
        flagBtnCancel = false;
        if (pickingOrderModel.getStatus().getStatusSeq() >= 2){
            flagBtnShow = false;
            flagBtnPrint = false;
        } else if (pickingOrderModel.getStatus().getStatusSeq() == 1){
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
        seleteDataSync = Utils.getEmptyList();
    }

    public void onSyncOrder(){
        if (!Utils.isZero(seleteDataSync.size())){
            pickingOrderService.syncOrder(seleteDataSync, userDetail);
            showDialog("Sync Success.", "Success.", "msgBoxSystemMessageDlg2");
            onLoadTable();
        } else {
            showDialogWarning("Please select data.");
        }
    }

    public void stikerWorkLoadReport(){
        pickingOrderService.getStikerWorkLoadReport(pickingOrderModel.getId(), userDetail);
    }

    public void confirmationPackingReport(){
        pickingOrderService.getConfirmationPackingReport(pickingOrderModel.getId(), userDetail);
    }

    public void onAddtoPickingOrderShowItem(){
        HttpSession session = FacesUtil.getSession(true);
        session.setAttribute("pickingOrderId", pickingOrderModel);
        FacesUtil.redirect("/site/pickingOrderShowItem.xhtml");
    }

    public void onCancel(){
        if (pickingOrderModel.getStatus().getStatusSeq() == 1){
            pickingOrderService.updateOnCancel(pickingOrderModel.getId());
            showDialog("Cancel", "Cancel Suscess");
        }else if (pickingOrderModel.getStatus().getStatusSeq() == 2){
            pickingOrderService.cancel(pickingOrderModel);
            pickingOrderService.updateOnCancel(pickingOrderModel.getId());
            showDialog("Cancel", "Cancel Suscess");
        }else if (pickingOrderModel.getStatus().getStatusSeq() == 3){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "This order has assign to pick. You can’t cancel this order now. You should wait for the picked data from picker");
            pickingOrderModel = new PickingOrderModel();
        }else if (pickingOrderModel.getStatus().getStatusSeq() == 4 || pickingOrderModel.getStatus().getStatusSeq() == 5){
            if (!Utils.isNull(pickingOrderModel.getLoadingOrderModel()) && !Utils.isNull(pickingOrderModel.getLoadingOrderModel().getStatusModel())){
                if (Utils.isZero(pickingOrderModel.getLoadingOrderModel().getStatusModel().getStatusSeq())){
                    pickingOrderService.cancel(pickingOrderModel);
                    pickingOrderService.updateOnCancel(pickingOrderModel.getId());
                    showDialog("Cancel", "Cancel Suscess");
                } else {
                    showDialog(MessageDialog.WARNING.getMessageHeader(), "This order has move to loading area please unpack first.");
                }
            }
        }

        init();
    }
}
