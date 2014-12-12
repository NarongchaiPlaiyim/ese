package com.ese.service;

import com.ese.model.dao.StockInOutNoteDAO;
import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.view.StockInOutNoteView;
import com.ese.transform.StockInOutNoteTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class StockInOutNoteService extends Service{
    @Resource private StockInOutNoteDAO stockInOutNoteDAO;
    @Resource private StockInOutNoteTransform stockInOutNoteTransform;

    public void deleteStockInOutNote(MSStockInOutNoteModel msStockInOutNoteModel){
        log.debug("deleteStockInOutNote() {}", msStockInOutNoteModel);
        try {
            stockInOutNoteDAO.deleteByUpdate(msStockInOutNoteModel);
        } catch (Exception e) {
            log.debug("Exception error deleteStockInOutNote : ", e);
        }
    }

    public List<MSStockInOutNoteModel> getStockInOutNoteAll(){
        log.debug("getStockInOutNoteAll().");
        List<MSStockInOutNoteModel> msStockInOutNoteModels = stockInOutNoteDAO.getStockInOutNoteOrderByUpdateDate();
        return msStockInOutNoteModels;
    }

    public StockInOutNoteView clickToStockInOutNoteView(MSStockInOutNoteModel msStockInOutNoteModel){
        log.debug("clickToStockInOutNoteView(). {}", msStockInOutNoteModel.toString());
        StockInOutNoteView stockInOutNoteView;

        if (!Utils.isNull(msStockInOutNoteModel)){
            stockInOutNoteView = stockInOutNoteTransform.transformToView(msStockInOutNoteModel);
            return stockInOutNoteView;
        } else {
            return new StockInOutNoteView();
        }
    }

    public void onSaveStockInOutNote(StockInOutNoteView stockInOutNoteView){
        log.debug("onSaveStockInOutNote().");

        if (!Utils.isNull(stockInOutNoteView)){
            try {
                if (Utils.isZero(stockInOutNoteView.getId())){
                    stockInOutNoteDAO.persist(stockInOutNoteTransform.transformToModel(stockInOutNoteView));
                } else {
                    stockInOutNoteDAO.update(stockInOutNoteTransform.transformToModel(stockInOutNoteView));
                }
            } catch (Exception e) {
                log.debug("Exception error onSaveStockInOutNote : ", e);
            }
        }
    }
}
