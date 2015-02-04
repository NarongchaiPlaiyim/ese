package com.ese.transform;


import com.ese.model.db.MSLocationModel;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.db.ReservedOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class ReservedOrderTransform {

    public ReservedOrderModel tramsformToModel(PickingOrderLineModel pickingOrderLineModel, MSLocationModel msLocationModel, StatusModel statusModel, int reServedQty){
        ReservedOrderModel reservedOrderModel = new ReservedOrderModel();

        reservedOrderModel.setLocationId(msLocationModel.getId());
        reservedOrderModel.setLocationBarcode(msLocationModel.getLocationBarcode());
        reservedOrderModel.setPickingOrderLineModel(pickingOrderLineModel);
        reservedOrderModel.setReservedQty(reServedQty);
        reservedOrderModel.setStatusModel(statusModel);
        reservedOrderModel.setIsValid(1);
        reservedOrderModel.setCreateDate(Utils.currentDate());
        reservedOrderModel.setCreateBy(1111);
        reservedOrderModel.setUpdateDate(Utils.currentDate());
        reservedOrderModel.setUpdateBy(1111);

        return reservedOrderModel;
    }
}
