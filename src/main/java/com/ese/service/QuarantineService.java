package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.StatusDAO;
import com.ese.model.dao.StockInOutDAO;
import com.ese.model.db.StatusModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.QuarantineView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class QuarantineService extends Service{
    private static final long serialVersionUID = 4442578634029876540L;
    @Resource private StockInOutDAO stockInOutDAO;
    @Resource private StatusDAO statusDAO;


    public List<StockInOutModel> getOnLoad(){
        return stockInOutDAO.findByDocNoQRndCurrentDate();
    }

    public void save(QuarantineView issuingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = new StockInOutModel();
            stockInOutModel.setDocNo(issuingView.getDocNo());
            stockInOutModel.setDocDate(issuingView.getDocDate());
//            stockInOutModel.setMsStockInOutNoteModel(issuingView.getMsStockInOutNoteModel() != null ? issuingView.getMsStockInOutNoteModel() : null);
            stockInOutModel.setRemark(issuingView.getRemark());
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

    public void edit(QuarantineView issuingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = stockInOutDAO.findByID(issuingView.getId());
            stockInOutModel.setDocDate(issuingView.getDocDate());
//            stockInOutModel.setMsStockInOutNoteModel(issuingView.getMsStockInOutNoteModel() != null ? issuingView.getMsStockInOutNoteModel() : null);
            stockInOutModel.setRemark(issuingView.getRemark());
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutDAO.update(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during edit ", e);
        }
    }

    public void post(QuarantineView quarantineView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = stockInOutDAO.findByID(quarantineView.getId());
            stockInOutModel.setDocDate(quarantineView.getDocDate());
            stockInOutModel.setRemark(quarantineView.getRemark());
            stockInOutModel.setStatus(statusDAO.findByTableIdAndStatus(TableValue.STOCK_IN_OUT.getId(), 3));
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutDAO.update(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during edit ", e);
        }
    }

    public List<StockInOutModel> search(QuarantineView quarantineView){
        return stockInOutDAO.findBySearchQR(quarantineView);
    }
}
