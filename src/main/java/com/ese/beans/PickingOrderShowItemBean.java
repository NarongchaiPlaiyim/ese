package com.ese.beans;

import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.ReservedOrderModel;
import com.ese.model.view.PickingOrderShowItemView;
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
@ManagedBean(name = "pickingOrderShowItemBean")
public class PickingOrderShowItemBean extends Bean {
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{pickingOrderShowItemService}") private com.ese.service.PickingOrderShowItemService pickingOrderShowItemService;

    private PickingOrderModel pickingOrderModel;

    private boolean flagItem;
    private boolean flagFIFOReserved;
    private boolean flagPeriodReserved;
    private boolean flagManualReserved;
    private boolean flagPoil;
    private boolean flagShowStatus;
    private boolean flagPrint;

    private List<PickingOrderShowItemView> orderLineModelList;
    private List<PickingOrderShowItemView> selectPickingLine;

    @PostConstruct
    private void init(){
        HttpSession session = FacesUtil.getSession(false);
        pickingOrderModel = (PickingOrderModel) session.getAttribute("pickingOrderId");

        btnOnload();
        onLoadTable();
        onNewObject();
    }

    private void btnOnload(){
        flagItem = true;
        flagFIFOReserved = true;
        flagPeriodReserved = true;
        flagManualReserved = true;
        flagPoil = true;
        flagShowStatus = true;
        flagPrint = true;
    }

    private void onLoadTable(){
        orderLineModelList = pickingOrderShowItemService.getPickingOrderLineByPickingOrderId(pickingOrderModel.getId());
    }

    private void onNewObject(){
        selectPickingLine = new ArrayList<PickingOrderShowItemView>();
    }

    public void onClose(){
        FacesUtil.redirect("/site/pickingOrder.xhtml");
    }

    public void onClickTable(){

        if (Utils.isSafetyList(selectPickingLine)){
            for (PickingOrderShowItemView view : selectPickingLine){
                if (view.getStatusID() < 3){
                    flagItem = false;
                    flagFIFOReserved = false;
                    flagPeriodReserved = false;
                    flagManualReserved = false;
                    flagPoil = false;
                    flagShowStatus = false;
                    flagPrint = false;
                }
            }
        } else {
            btnOnload();
        }
    }

    public void onClickFoil(){
        if (Utils.isSafetyList(selectPickingLine)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes to unwrap or wrap.", "foilDlg");
        }
    }

    public void warpOnFoil(){
        for (PickingOrderShowItemView itemView : selectPickingLine){
            pickingOrderShowItemService.updateIsFoil(itemView.getId(), itemView.getFoil());
        }

        btnOnload();
        onLoadTable();
        onNewObject();
    }
}
