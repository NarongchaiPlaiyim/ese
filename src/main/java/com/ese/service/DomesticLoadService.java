package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.LoadingOrderDAO;
import com.ese.model.dao.StatusDAO;
import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class DomesticLoadService extends Service {
    private static final long serialVersionUID = 4112578634263394999L;
    @Resource private StatusDAO statusDAO;
    @Resource private LoadingOrderDAO loadingOrderDAO;
    public List<StatusModel> getStatusAll(){
        List<StatusModel> statusModelList = Utils.getEmptyList();
        try {
            statusModelList = statusDAO.findByTablePickingOrder(TableValue.LOADING_ORDER.getId());
        } catch (Exception e) {
            log.debug("Exception error getStatusAll", e);
        }
        return statusModelList;
    }

    public List<LoadingOrderModel> getList(){
        return loadingOrderDAO.findByStatusIs12();
    }

    public List<LoadingOrderModel> getSearch(String docNo, String loadingDate, int status){
        return loadingOrderDAO.findBySearch(docNo, loadingDate, status);
    }

    public void save(LoadingOrderModel loadingOrderModel){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            loadingOrderModel.setCreateBy(staffModel);
            loadingOrderModel.setUpdateBy(staffModel);
            loadingOrderModel.setCategory("D");
            loadingOrderModel.setCreateDate(Utils.currentDate());
            loadingOrderModel.setUpdateDate(Utils.currentDate());
            loadingOrderModel.setStatusModel(statusDAO.findByStatusId(12));
            loadingOrderDAO.persist(loadingOrderModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }

    public void edit(LoadingOrderModel loadingOrderModel){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            loadingOrderModel.setUpdateBy(staffModel);
            loadingOrderModel.setUpdateDate(Utils.currentDate());
            loadingOrderDAO.update(loadingOrderModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }
}
