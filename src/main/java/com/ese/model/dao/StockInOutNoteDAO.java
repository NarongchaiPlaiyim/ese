package com.ese.model.dao;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockInOutNoteDAO extends GenericDAO<MSStockInOutNoteModel, Integer> {

    public List<MSStockInOutNoteModel> getStockInOutNoteOrderByUpdateDate(){
        log.debug("getStockInOutNoteOrderByUpdateDate().");
        List<MSStockInOutNoteModel> msStockInOutNoteModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            msStockInOutNoteModelList = Utils.safetyList(criteria.list());
            log.debug("msStockInOutNoteModels Size : {}", msStockInOutNoteModelList.size());
        } catch (Exception e) {
            log.debug("Exception Error getStockInOutNoteOrderByUpdateDate : ", e);
        }
        return msStockInOutNoteModelList;
    }

    public void deleteByUpdate(final MSStockInOutNoteModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }
}
