package com.ese.service;

import com.ese.model.dao.PickingOrderLineDAO;
import com.ese.model.dao.ReservedOrderDAO;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.db.ReservedOrderModel;
import com.ese.model.view.PickingOrderShowItemView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PickingOrderShowItemService extends Service{
    @Resource private PickingOrderLineDAO pickingOrderLineDAO;
    @Resource private ReservedOrderDAO reservedOrderDAO;

    public List<PickingOrderShowItemView> getPickingOrderLineByPickingOrderId(int pickingOrderId){
        return pickingOrderLineDAO.findByPickingOrderId(pickingOrderId);
    }

    public void updateIsFoil(int pickingLineId, int isFoil){
        if (!Utils.isZero(isFoil)){
            pickingOrderLineDAO.updateToUnWrap(pickingLineId);
        } else {
            pickingOrderLineDAO.updateToWrap(pickingLineId);
        }
    }
}
