package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.LoadingOrderDAO;
import com.ese.model.dao.StatusDAO;
import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.StatusModel;
import com.ese.model.view.LoadingOrderView;
import com.ese.model.view.StatusLoadingOrderView;
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

    public List<LoadingOrderModel> getSearch(int status){
        return loadingOrderDAO.findBySearch(status);
    }

    public void save(LoadingOrderView loadingOrderView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            LoadingOrderModel loadingOrderModel = new LoadingOrderModel();
            loadingOrderModel.setDocNo(loadingOrderView.getDocNo());
            loadingOrderModel.setLoadingDate(loadingOrderView.getLoadingDate());
            loadingOrderModel.setRemark(loadingOrderView.getRemark());
            loadingOrderModel.setCreateBy(staffModel);
            loadingOrderModel.setUpdateBy(staffModel);
            loadingOrderModel.setCategory(loadingOrderView.getCategory());
            loadingOrderModel.setCreateDate(Utils.currentDate());
            loadingOrderModel.setUpdateDate(Utils.currentDate());
            loadingOrderModel.setStatusModel(statusDAO.findByStatusId(StatusLoadingOrderView.CREATE.getId()));
            loadingOrderDAO.persist(loadingOrderModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }

    public void edit(LoadingOrderView loadingOrderView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            LoadingOrderModel loadingOrderModel = new LoadingOrderModel();
            loadingOrderModel.setId(loadingOrderView.getId());
            loadingOrderModel.setDocNo(loadingOrderView.getDocNo());
            loadingOrderModel.setLoadingDate(loadingOrderView.getLoadingDate());
            loadingOrderModel.setRemark(loadingOrderView.getRemark());
            loadingOrderModel.setStatusModel(loadingOrderView.getStatusModel());
            loadingOrderModel.setCategory(loadingOrderView.getCategory());
            loadingOrderModel.setCreateDate(loadingOrderView.getCreateDate());
            loadingOrderModel.setCreateBy(loadingOrderView.getCreateBy());
            loadingOrderModel.setUpdateBy(staffModel);
            loadingOrderModel.setUpdateDate(Utils.currentDate());
            loadingOrderDAO.update(loadingOrderModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }

    public LoadingOrderView transToView(LoadingOrderModel loadingOrderModel){
        LoadingOrderView orderView = new LoadingOrderView();
        orderView.setId(loadingOrderModel.getId());
        orderView.setDocNo(loadingOrderModel.getDocNo());
        orderView.setLoadingDate(loadingOrderModel.getLoadingDate());
        orderView.setRemark(loadingOrderModel.getRemark());
        orderView.setStatusModel(loadingOrderModel.getStatusModel());

        return orderView;
    }
}
