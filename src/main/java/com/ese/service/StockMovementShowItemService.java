package com.ese.service;

import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.SearchItemView;
import com.ese.model.view.ShowSNView;
import com.ese.model.view.StockMovementOutView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class StockMovementShowItemService extends Service{
    private static final long serialVersionUID = 4442578634029876540L;
    @Resource private StockInOutLineDAO stockInOutLineDAO;
    @Resource private InvOnHandDAO invOnHandDAO;
    @Resource private WarehouseDAO warehouseDAO;
    @Resource private LocationDAO locationDAO;
    @Resource private StockMovementOutDAO stockMovementOutDAO;
    @Resource private StockInOutDAO stockInOutDAO;

//    public List<StockInOutLineModel> getByStockInOutId(int stockInOutId){
//        return stockInOutLineDAO.findByStockInOutId(stockInOutId);
//    }

    public List<StockMovementOutView> getStockMovementOutByStockInOutId(int stockInOutId){
        return stockMovementOutDAO.findstockMovementOutByStockInOutId(stockInOutId);
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

    public List<MSWarehouseModel> findWarehouseAll(){
        return warehouseDAO.getWarehouseOrderByUpdateDate();
    }

    public List<MSLocationModel> findLocationAll(){
        return locationDAO.getLocationOrderByUpdateDate();
    }

    public List<MSLocationModel> getByWarehouseId(int warehouseId){
        return locationDAO.findByWarehouseId(warehouseId);
    }

    public List<ShowSNView> getBySearch(SearchItemView searchItemView){
        return invOnHandDAO.findBySearch(searchItemView);
    }

    public void select(List<ShowSNView> snViewList, int stockInOutId){
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        StockMovementOutModel stockMovementOutModel;

        try{
            for (ShowSNView showSNView : snViewList){
                stockMovementOutModel = new StockMovementOutModel();
                stockMovementOutModel.setStockInOut(stockInOutDAO.findByID(stockInOutId));
                stockMovementOutModel.setPalletBarcode(showSNView.getPallet());
                stockMovementOutModel.setSnBarcode(showSNView.getSN());
                stockMovementOutModel.setBatchNo(showSNView.getBatch());
                stockMovementOutModel.setStatus(1);
                stockMovementOutModel.setIsValid(0);
                stockMovementOutModel.setCreateBy(staffModel);
                stockMovementOutModel.setCreateDate(Utils.currentDate());
                stockMovementOutModel.setUpdateBy(staffModel);
                stockMovementOutModel.setUpdateDate(Utils.currentDate());

                stockMovementOutDAO.persist(stockMovementOutModel);

            }
        } catch (Exception e){
            log.debug("Exception error select : ", e);
        }


//        InvOnHandModel invOnHandModel;

//        try{
//            for (ShowSNView snView : snViewList){
//                invOnHandModel = invOnHandDAO.findByID(snView.getId());
//                invOnHandModel.setStockInOutLineModel(stockInOutLineModel);
//                invOnHandModel.setUpdateDate(Utils.currentDate());
//                invOnHandModel.setUpdateBy(staffModel);
//                invOnHandDAO.update(invOnHandModel);
//            }
//        } catch (Exception e){
//            log.debug("Exception error select : ", e);
//        }

    }

    public void delete(int stockMoveOutId){
        log.debug("stockMoveOutId : {}", stockMoveOutId);
        try {
            stockMovementOutDAO.delete(stockMovementOutDAO.findByID(stockMoveOutId));
        } catch (Exception e) {
            log.debug("Exception error delete : ", e);
        }
    }
}
