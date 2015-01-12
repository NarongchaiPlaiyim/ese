package com.ese.service;

import com.ese.model.dao.PickingOrderDAO;
import com.ese.model.dao.UserAccessDAO;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.UserAccessModel;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PickingOrderService extends Service {

    @Resource private PickingOrderDAO pickingOrderDAO;
    @Resource private UserAccessDAO userAccessDAO;

    public String getTypeBeforeOnLoaf(long staffId){
        List<UserAccessModel> userAccessModelList = userAccessDAO.findByPickingOrder(Utils.parseInt(staffId, 0));
        String mode = "";
        if (userAccessModelList.size() == 2){
            mode = "All";
        } else {
            for(UserAccessModel model : userAccessModelList){
                log.debug("");
                if ("031A".equals(model.getMenuObjectModel().getCode())){
                    mode = "031A";
                } else if ("031B".equals(model.getMenuObjectModel().getCode())){
                    mode = "031B";
                }
            }
        }

        return mode;
    }

    public List<PickingOrderModel> getPickingOrderByOverSeaOrder(String modeFind){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        log.debug("getPickingOrderByOverSeaOrder : {}", modeFind);

        if ("031A".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaOrder();
        } else if ("031B".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByDomesticOrder();
        } else if ("All".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaAndDomesticOrder();
        }

        return pickingOrderModelList;
    }
}
