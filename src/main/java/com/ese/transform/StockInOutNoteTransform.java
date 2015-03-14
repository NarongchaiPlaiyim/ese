package com.ese.transform;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.StockInOutNoteView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class StockInOutNoteTransform extends Transform{

    public StockInOutNoteView transformToView(MSStockInOutNoteModel msStockInOutNoteModel){
        log.debug("transformToView().");
        StockInOutNoteView stockInOutNoteView = new StockInOutNoteView();

        stockInOutNoteView.setId(msStockInOutNoteModel.getId());
        stockInOutNoteView.setType(msStockInOutNoteModel.getType());
        stockInOutNoteView.setInoutCode(msStockInOutNoteModel.getInoutCode());
        stockInOutNoteView.setInoutNote(msStockInOutNoteModel.getInoutNote());
        stockInOutNoteView.setRemark(msStockInOutNoteModel.getRemark());
        stockInOutNoteView.setIsValid(msStockInOutNoteModel.getIsValid());
        stockInOutNoteView.setVersion(msStockInOutNoteModel.getVersion());
        stockInOutNoteView.setCreateBy(msStockInOutNoteModel.getCreateBy());
        stockInOutNoteView.setCreateDate(msStockInOutNoteModel.getCreateDate());
        stockInOutNoteView.setUpdateBy(msStockInOutNoteModel.getUpdateBy());
        stockInOutNoteView.setUpdateDate(msStockInOutNoteModel.getUpdateDate());

        return stockInOutNoteView;
    }

    public MSStockInOutNoteModel transformToModel(StockInOutNoteView inOutNoteView){
        log.debug("transformToModel().");
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        MSStockInOutNoteModel stockInOutNoteModel = new MSStockInOutNoteModel();

        stockInOutNoteModel.setId(inOutNoteView.getId());
        stockInOutNoteModel.setType(inOutNoteView.getType());
        stockInOutNoteModel.setInoutCode(inOutNoteView.getInoutCode());
        stockInOutNoteModel.setInoutNote(inOutNoteView.getInoutNote());
        stockInOutNoteModel.setRemark(inOutNoteView.getRemark());


        if (Utils.isZero(inOutNoteView.getId())){
            stockInOutNoteModel.setIsValid(1);
            stockInOutNoteModel.setVersion(1);
            stockInOutNoteModel.setCreateBy(staffModel);
            stockInOutNoteModel.setCreateDate(Utils.currentDate());
            stockInOutNoteModel.setUpdateBy(staffModel);
            stockInOutNoteModel.setUpdateDate(Utils.currentDate());
        } else {
            stockInOutNoteModel.setIsValid(inOutNoteView.getIsValid());
            stockInOutNoteModel.setVersion(inOutNoteView.getVersion());
            stockInOutNoteModel.setCreateBy(inOutNoteView.getCreateBy());
            stockInOutNoteModel.setCreateDate(Utils.currentDate());
            stockInOutNoteModel.setUpdateBy(staffModel);
            stockInOutNoteModel.setUpdateDate(Utils.currentDate());
        }

        return stockInOutNoteModel;
    }
}
