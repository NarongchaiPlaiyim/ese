package com.ese.transform;

import com.ese.model.db.AXCustomerConfirmTransModel;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.StatusModel;
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

        return pickingOrderLineModel;
    }
}