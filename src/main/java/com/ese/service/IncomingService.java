package com.ese.service;

import com.ese.model.dao.StockInOutDAO;
import com.ese.model.dao.StockInOutNoteDAO;
import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StatusModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IncomingView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class IncomingService extends Service {
    private static final long serialVersionUID = 4112578634029876045L;
    @Resource private StockInOutDAO stockInOutDAO;
    @Resource private StockInOutNoteDAO stockInOutNoteDAO;

    public List<MSStockInOutNoteModel> getAllStockInOutNote(){
        return stockInOutNoteDAO.getStockInOutNoteOrderByTypeI();
    }

    public List<StockInOutModel> getOnLoad(){
        return stockInOutDAO.findByDocNoINAndCurrentDate();
    }

    public List<StockInOutModel> search(IncomingView incomingView){
        return stockInOutDAO.findBySearch(incomingView);
    }

    public void save(IncomingView incomingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = new StockInOutModel();
            stockInOutModel.setDocNo(incomingView.getDocNo());
            stockInOutModel.setDocDate(incomingView.getDocDate());
            stockInOutModel.setMsStockInOutNoteModel(incomingView.getMsStockInOutNoteModel());
            stockInOutModel.setRemark(incomingView.getRemark());
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutModel.setCreateDate(Utils.currentDate());
            stockInOutModel.setCreateBy(staffModel);
            stockInOutModel.setIsValid(1);
            StatusModel statusModel = new StatusModel();
            statusModel.setId(17);
            stockInOutModel.setStatus(statusModel);
            stockInOutDAO.persist(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }

    public void edit(IncomingView incomingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = stockInOutDAO.findByID(incomingView.getId());
            stockInOutModel.setDocDate(incomingView.getDocDate());
            stockInOutModel.setMsStockInOutNoteModel(incomingView.getMsStockInOutNoteModel());
            stockInOutModel.setRemark(incomingView.getRemark());
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutDAO.update(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during edit ", e);
        }
    }
}
