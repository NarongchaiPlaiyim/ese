package com.ese.service;

import com.ese.model.StatusValue;
import com.ese.model.TableValue;
import com.ese.model.dao.StatusDAO;
import com.ese.model.dao.StockInOutDAO;
import com.ese.model.dao.StockInOutNoteDAO;
import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.StockTransferView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class StockTransferService extends Service{
    private static final long serialVersionUID = 4112578634029876540L;
    @Resource private StockInOutDAO stockInOutDAO;
    @Resource private StockInOutNoteDAO stockInOutNoteDAO;
    @Resource private StatusDAO statusDAO;

    public List<StockInOutModel> getOnload(){
        return stockInOutDAO.findByDocnoAndCurrentDate();
    }

    public List<MSStockInOutNoteModel> getAllStockInOutNote(){
        return stockInOutNoteDAO.getStockInOutNoteOrderByTypeT();
    }

    public void saveOrupdate(StockInOutModel stockInOutModel, int stockInOutMNoteId){
        try {
            MSStockInOutNoteModel outNoteModel = stockInOutNoteDAO.findByID(stockInOutMNoteId);
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            if (Utils.isZero(stockInOutModel.getId())){
                stockInOutModel.setStatus(statusDAO.findByTableIdAndStatus(TableValue.STOCK_IN_OUT.getId(), StatusValue.CREATE.getId()));
                stockInOutModel.setCreateBy(staffModel);
                stockInOutModel.setCreateDate(Utils.currentDate());
                stockInOutModel.setUpdateBy(staffModel);
                stockInOutModel.setUpdateDate(Utils.currentDate());
            } else {
                stockInOutModel.setUpdateBy(staffModel);
                stockInOutModel.setUpdateDate(Utils.currentDate());
            }

            stockInOutModel.setIsValid(1);
            stockInOutModel.setMsStockInOutNoteModel(outNoteModel);
            stockInOutDAO.saveOrUpdate(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error saveOredit : ", e);
        }
    }

    public List<StockInOutModel> search(StockTransferView stockTransferView){
        return stockInOutDAO.findBySearch(stockTransferView);
    }
}
