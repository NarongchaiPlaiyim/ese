package com.ese.service;

import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.PickingOrderView;
import com.ese.service.security.UserDetail;
import com.ese.transform.PickingOrderTransform;
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
    @Resource private StatusDAO statusDAO;
    @Resource private AXCustomerConfirmJourDAO axCustomerConfirmJourDAO;
    @Resource private AXCustomerConfirmTransDAO axCustomerConfirmTransDAO;
    @Resource private AXCustomerTableDAO axCustomerTableDAO;
    @Resource private PickingOrderTransform pickingOrderTransform;

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

    public List<StatusModel> getStatusAll(){
        try {
            return statusDAO.findAll();
        } catch (Exception e) {
            log.debug("Excrption error getStatusAll", e);
            return null;
        }
    }

    public List<PickingOrderModel> getPickingOnSearch(PickingOrderView pickingOrderView){
        if (!Utils.isNull(pickingOrderView)){
            return pickingOrderDAO.findByPickingView(pickingOrderView);
        } else {
            return Utils.getEmptyList();
        }
    }

    public List<DataSyncConfirmOrderView> getDataOnSync(){
        return axCustomerConfirmJourDAO.genSQLSyncData();
    }

    public void updateStatus(List<DataSyncConfirmOrderView> viewList){

        for (DataSyncConfirmOrderView view : viewList){
            axCustomerConfirmJourDAO.updateStatusRunning(view.getCustomerCode());
            axCustomerConfirmTransDAO.updateStatusRunning(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        }
    }

    public void rollbackStatus(){
        axCustomerConfirmJourDAO.rollbackStatus();
        axCustomerConfirmTransDAO.rollbackStatus();
    }

    public void syncOrder(List<DataSyncConfirmOrderView> viewList, UserDetail userDetail){

        for (DataSyncConfirmOrderView view : viewList){

            try {
                AXCustomerTableModel customerTableModel = axCustomerTableDAO.findByAccountNum(view.getCustomerCode());
                StatusModel statusModel = statusDAO.findByID(2);
                PickingOrderModel model = pickingOrderTransform.tranformToModel(view, customerTableModel, statusModel, userDetail);
                log.debug("model : {}" ,model.toString());

                pickingOrderDAO.persist(model);
            } catch (Exception e) {
                log.debug("Exception error syncOrder : ", e);
            }

            axCustomerConfirmJourDAO.updateStatusFinish(view.getCustomerCode());
            axCustomerConfirmTransDAO.updateStatusFinish(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        }

        axCustomerConfirmJourDAO.rollbackStatus();
        axCustomerConfirmTransDAO.rollbackStatus();
    }
}
