package com.ese.transform;

import com.ese.model.db.AXCustomerConfirmTransModel;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.model.view.ItemQtyView;
import com.ese.service.security.UserDetail;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;


@Component
public class PickingOrderLineTransform extends Transform{

    public PickingOrderLineModel transformToModel(AXCustomerConfirmTransModel confirmTransModel, PickingOrderModel pickingOrderModel, StatusModel statusModel,UserDetail userDetail){
        PickingOrderLineModel pickingOrderLineModel = new PickingOrderLineModel();

        pickingOrderLineModel.setPickingOrderId(pickingOrderModel);
        pickingOrderLineModel.setLine_num(confirmTransModel.getLineNum());
        pickingOrderLineModel.setItemId(confirmTransModel.getItemId());
        pickingOrderLineModel.setOrigSaleId(confirmTransModel.getOrigSalesId());
        pickingOrderLineModel.setQty(confirmTransModel.getQty());
        pickingOrderLineModel.setShipDate(confirmTransModel.getConfirmDate());
        pickingOrderLineModel.setSalesUnit(confirmTransModel.getSalesUnit());
        pickingOrderLineModel.setFoil(false);
        pickingOrderLineModel.setStatus(statusModel);
        pickingOrderLineModel.setCreateBy(userDetail.getId());
        pickingOrderLineModel.setCreateDate(Utils.currentDate());
        pickingOrderLineModel.setUpdateBy(userDetail.getId());
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

        return pickingOrderLineModel;
    }

    public PickingOrderLineModel transformToModelByAddItemQty(PickingOrderModel pickingOrderModel, StatusModel statusModel,UserDetail userDetail, ItemQtyView itemQtyView){
        PickingOrderLineModel pickingOrderLineModel = new PickingOrderLineModel();

        pickingOrderLineModel.setPickingOrderId(pickingOrderModel);
//        pickingOrderLineModel.setLine_num(confirmTransModel.getLineNum());
        pickingOrderLineModel.setItemId(itemQtyView.getItemCode());
        pickingOrderLineModel.setOrigSaleId(pickingOrderModel.getSalesOrder());
        pickingOrderLineModel.setQty(itemQtyView.getOrderQty());
//        pickingOrderLineModel.setShipDate(confirmTransModel.getConfirmDate());
//        pickingOrderLineModel.setSalesUnit(pickingOrderModel.getSalesOrder());
        pickingOrderLineModel.setFoil(false);
        pickingOrderLineModel.setStatus(statusModel);
        pickingOrderLineModel.setCreateBy(userDetail.getId());
        pickingOrderLineModel.setCreateDate(Utils.currentDate());
        pickingOrderLineModel.setUpdateBy(userDetail.getId());
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
