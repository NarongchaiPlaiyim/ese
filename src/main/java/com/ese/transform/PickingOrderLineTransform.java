package com.ese.transform;

import com.ese.model.db.*;
import com.ese.model.view.CustomerConfirmTransView;
import com.ese.model.view.ItemQtyView;
import com.ese.service.security.UserDetail;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;


@Component
public class PickingOrderLineTransform extends Transform{


    public PickingOrderLineModel transformToModel(CustomerConfirmTransView confirmTransModel, PickingOrderModel pickingOrderModel, StatusModel statusModel,UserDetail userDetail){
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        PickingOrderLineModel pickingOrderLineModel = new PickingOrderLineModel();

        pickingOrderLineModel.setPickingOrderId(pickingOrderModel);
        pickingOrderLineModel.setLine_num(confirmTransModel.getLineNum());
        log.debug("-----ItemId : {}", confirmTransModel.getItemId());
        pickingOrderLineModel.setItemId(confirmTransModel.getItemId());
        pickingOrderLineModel.setOrigSaleId(confirmTransModel.getOrigSaleId());
        pickingOrderLineModel.setQty(confirmTransModel.getQty());
        pickingOrderLineModel.setShipDate(confirmTransModel.getShipDate());
        pickingOrderLineModel.setSalesUnit(confirmTransModel.getSalesUnit());
        pickingOrderLineModel.setFoil(false);
        pickingOrderLineModel.setStatus(statusModel);
        pickingOrderLineModel.setCreateBy(staffModel);
        pickingOrderLineModel.setCreateDate(Utils.currentDate());
        pickingOrderLineModel.setUpdateBy(staffModel);
        pickingOrderLineModel.setUpdateDate(Utils.currentDate());
        pickingOrderLineModel.setIsValid(1);
        pickingOrderLineModel.setVersion(1);
        pickingOrderLineModel.setDSGSubGroupDescription(confirmTransModel.getDSGSubGroupDescription());
        pickingOrderLineModel.setPIDescription(confirmTransModel.getPIDescription());
        pickingOrderLineModel.setDSGExtItemNO(confirmTransModel.getDSGExtItemNO());
        pickingOrderLineModel.setName(confirmTransModel.getName());
        pickingOrderLineModel.setDSGPackingQty(confirmTransModel.getDSGPackingQty());
        pickingOrderLineModel.setSalesUnitTxt(confirmTransModel.getSalesUnitTxt());
        pickingOrderLineModel.setCum(confirmTransModel.getCum());
        pickingOrderLineModel.setInventtransId(confirmTransModel.getInventransId());

        return pickingOrderLineModel;
    }

    public PickingOrderLineModel transformToModelByAddItemQty(PickingOrderModel pickingOrderModel, StatusModel statusModel,UserDetail userDetail, ItemQtyView itemQtyView){
        PickingOrderLineModel pickingOrderLineModel = new PickingOrderLineModel();
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        pickingOrderLineModel.setPickingOrderId(pickingOrderModel);
//        pickingOrderLineModel.setLine_num(confirmTransModel.getLineNum());
        pickingOrderLineModel.setItemId(itemQtyView.getItemCode());
        pickingOrderLineModel.setOrigSaleId(pickingOrderModel.getSalesOrder());
        pickingOrderLineModel.setQty(itemQtyView.getOrderQty());
//        pickingOrderLineModel.setShipDate(confirmTransModel.getConfirmDate());
//        pickingOrderLineModel.setSalesUnit(pickingOrderModel.getSalesOrder());
        pickingOrderLineModel.setFoil(false);
        pickingOrderLineModel.setStatus(statusModel);
        pickingOrderLineModel.setCreateBy(staffModel);
        pickingOrderLineModel.setCreateDate(Utils.currentDate());
        pickingOrderLineModel.setUpdateBy(staffModel);
        pickingOrderLineModel.setUpdateDate(Utils.currentDate());
        pickingOrderLineModel.setIsValid(1);
        pickingOrderLineModel.setVersion(1);
//        pickingOrderLineModel.setDSGSubGroupDescription(confirmTransModel.getDSGSubGroupDescription());
//        pickingOrderLineModel.setPIDescription(confirmTransModel.getPIDescription());
//        pickingOrderLineModel.setDSGExtItemNO(confirmTransModel.getDSGExtItemNO());
//        pickingOrderLineModel.setName(confirmTransModel.getName());
//        pickingOrderLineModel.setDSGPackingQty(confirmTransModel.getDSGPackingQty());
//        pickingOrderLineModel.setSalesUnitTxt(confirmTransModel.getSalesUnitTxt());
//        pickingOrderLineModel.setCum(confirmTransModel.getCum());

        return pickingOrderLineModel;
    }
}
