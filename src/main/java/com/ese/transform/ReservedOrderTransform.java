package com.ese.transform;


import com.ese.model.db.*;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class ReservedOrderTransform {

    public ReservedOrderModel tramsformToModel(PickingOrderLineModel pickingOrderLineModel, MSLocationModel msLocationModel, StatusModel statusModel, int reServedQty, String batchNo){
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        ReservedOrderModel reservedOrderModel = new ReservedOrderModel();

        reservedOrderModel.setLocationId(msLocationModel.getId());
        reservedOrderModel.setLocationBarcode(msLocationModel.getLocationBarcode());
        reservedOrderModel.setPickingOrderLineModel(pickingOrderLineModel);
        reservedOrderModel.setReservedQty(reServedQty);
        reservedOrderModel.setStatusModel(statusModel);
        reservedOrderModel.setIsValid(1);
        reservedOrderModel.setCreateDate(Utils.currentDate());
        reservedOrderModel.setCreateBy(staffModel);
        reservedOrderModel.setUpdateDate(Utils.currentDate());
        reservedOrderModel.setUpdateBy(staffModel);
        reservedOrderModel.setBatchNo(batchNo);

        return reservedOrderModel;
    }
}
