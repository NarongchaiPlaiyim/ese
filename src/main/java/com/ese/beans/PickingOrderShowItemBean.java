package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.LocationQtyView;
import com.ese.model.view.PickingOrderShowItemView;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import sun.security.timestamp.Timestamper;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private String startBatch;
    private String toBatch;
    boolean resultReserve;

    private List<PickingOrderShowItemView> orderLineModelList;
    private List<PickingOrderShowItemView> selectPickingLine;
    private PickingOrderShowItemView itemView;

    private List<MSWarehouseModel> msWarehouseModelList;
    private int warehouseId;

    private List<MSLocationModel> msLocationModelList;
    private int locationId;

    private List<LocationQtyView> locationQtyBox;
    private int locationQtyId;

    private List<LocationQtyView> locationQtyViewList;
    private LocationQtyView selectLocationQtyView;
    private int rowIndex;

    private int reservedManualQty;

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
        selectLocationQtyView = new LocationQtyView();
        itemView = new PickingOrderShowItemView();
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

    public void FIFOReserved(){

        for (PickingOrderShowItemView view : selectPickingLine){
            resultReserve = pickingOrderShowItemService.onReserved(view.getId(), "", "");

            if (resultReserve){
                showDialog(MessageDialog.SAVE.getMessageHeader(), "Success.", "msgBoxSystemMessageDlg");
                init();
            } else {
                showDialog(MessageDialog.WARNING.getMessageHeader(), "ไม่สามารถจองได้", "msgBoxSystemMessageDlg");
            }
        }
    }

    public void PeriodReserve(){
        String[] sBatch = startBatch.split("-");
        String[] tBatch = toBatch.split("-");

        String batchStart = sBatch[0] + sBatch[1];
        String batchTo = tBatch[0] + tBatch[1];

        if (Utils.parseInt(batchStart, 0) <= Utils.parseInt(batchTo, 0)){
            for (PickingOrderShowItemView view : selectPickingLine){
                resultReserve = pickingOrderShowItemService.onReserved(view.getId(), startBatch, toBatch);

                if (resultReserve){
                    showDialog(MessageDialog.SAVE.getMessageHeader(), "Success.", "msgBoxSystemMessageDlg");
                    init();
                } else {
                    showDialog(MessageDialog.WARNING.getMessageHeader(), "ไม่สามารถจองได้", "msgBoxSystemMessageDlg");
                }
            }
        } else {
            showDialog(MessageDialog.WARNING.getMessageHeader(), "StartBatch > ToBatch", "msgBoxSystemMessageDlg");
        }
    }

    public void preManualReserved(){

        warehouseId = 0;
        msLocationModelList = new ArrayList<MSLocationModel>();
        locationQtyBox = new ArrayList<LocationQtyView>();

        if (selectPickingLine.size() > 1){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "กรุณา Reserved ทีละ 1", "msgBoxSystemMessageDlg");
        } else {
            for (PickingOrderShowItemView view : selectPickingLine){
                locationQtyViewList = pickingOrderShowItemService.onManualReserved(view.getId(), "", 0, 0);
                itemView = view;
                msWarehouseModelList = pickingOrderShowItemService.getWarehouseAll();
                log.debug("msLocationModelList Size : {}", msWarehouseModelList.size());
            }
        }
    }

    public void getLocationByWarehouseId(){
        msLocationModelList = pickingOrderShowItemService.getLocationByWarehouse(warehouseId);
        locationQtyBox = new ArrayList<LocationQtyView>();
    }

    public void getBtachByLocationName(){
        locationQtyBox = pickingOrderShowItemService.getBatchByLocation(locationId);
    }

    public void onSearchByManual(){
        locationQtyViewList = pickingOrderShowItemService.getLocationQtyBySearch(itemView.getItem(), warehouseId, locationId, locationQtyId);
    }

    public void ManualReserved(){
        if (reservedManualQty > selectLocationQtyView.getAvailable()){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Reserved Qty > Avaliable Qty.", "msgBoxSystemMessageDlg");
        } else if (Utils.isZero(reservedManualQty)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "กรุณาใส่จำนวน Reserved Qty.", "msgBoxSystemMessageDlg");
        } else  if (reservedManualQty <= selectLocationQtyView.getAvailable()){
            pickingOrderShowItemService.saveManualReserved(selectLocationQtyView, reservedManualQty, itemView.getId());
            showDialog(MessageDialog.SAVE.getMessageHeader(), "Success.", "msgBoxSystemMessageDlg");
        }
        reservedManualQty = 0;
        locationQtyViewList = pickingOrderShowItemService.getLocationQtyBySearch(itemView.getItem(), warehouseId, locationId, locationQtyId);
        orderLineModelList = pickingOrderShowItemService.getPickingOrderLineByPickingOrderId(pickingOrderModel.getId());
    }
}
