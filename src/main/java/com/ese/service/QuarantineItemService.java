package com.ese.service;

import com.ese.model.dao.InvOnHandDAO;
import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.StockInOutLineDAO;
import com.ese.model.dao.WarehouseDAO;
import com.ese.model.db.InvOnHandModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.StockInOutLineModel;
import com.ese.model.view.SearchItemView;
import com.ese.model.view.ShowSNView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class QuarantineItemService extends Service{
    private static final long serialVersionUID = 4442578634029876540L;
    @Resource private StockInOutLineDAO stockInOutLineDAO;
    @Resource private InvOnHandDAO invOnHandDAO;
    @Resource private WarehouseDAO warehouseDAO;
    @Resource private LocationDAO locationDAO;

    public List<StockInOutLineModel> getByStockInOutId(int stockInOutId){
        return stockInOutLineDAO.findByStockInOutId(stockInOutId);
    }

    public List<ShowSNView> getInvOnhandByStockInOutLineId(int stockInOutLineId){
        return invOnHandDAO.findByStockInOutLineId(stockInOutLineId);
    }

    public void remove(ShowSNView showSNView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            InvOnHandModel invOnHandModel = invOnHandDAO.findByID(showSNView.getId());
            invOnHandModel.setStatus(2);
            invOnHandModel.setStockInOutLineModel(null);
            invOnHandModel.setUpdateDate(Utils.currentDate());
            invOnHandModel.setUpdateBy(staffModel);
            invOnHandDAO.update(invOnHandModel);
        } catch (Exception e) {
            log.debug("Exception error remove : ", e);
        }
    }

    public List<ShowSNView> getBySearch(SearchItemView searchItemView){
        return invOnHandDAO.findBySearch(searchItemView);
    }

    public void select(List<ShowSNView> snViewList, StockInOutLineModel stockInOutLineModel){
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        InvOnHandModel invOnHandModel;

        try{
            for (ShowSNView snView : snViewList){
                invOnHandModel = invOnHandDAO.findByID(snView.getId());
                invOnHandModel.setStockInOutLineModel(stockInOutLineModel);
                invOnHandModel.setUpdateDate(Utils.currentDate());
                invOnHandModel.setUpdateBy(staffModel);
                invOnHandDAO.update(invOnHandModel);
            }
        } catch (Exception e){
            log.debug("Exception error select : ", e);
        }

    }

    public List<MSWarehouseModel> findWarehouseAll(){
        return warehouseDAO.getWarehouseOrderByUpdateDate();
    }

    public List<MSLocationModel> findLocationAll(){
        return locationDAO.getLocationOrderByUpdateDate();
    }

    public List<MSLocationModel> getByWarehouseId(int warehouseId){
        return locationDAO.findByWarehouseId(warehouseId);
    }
}
