package com.ese.model.dao;

import com.ese.model.db.StockInOutModel;

import com.ese.model.view.StockTransferView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Repository
public class StockInOutDAO extends GenericDAO<StockInOutModel, Integer> {

    public List<StockInOutModel> findByDocnoAndCurrentDate(){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.ilike("docNo", "TR%"));
            criteria.add(Restrictions.eq("docDate", new Date()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDocnoAndCurrentDate : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findBySearch(StockTransferView stockTransferView){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isNull(stockTransferView.getDocNo()) || !Utils.isZero(stockTransferView.getDocNo().length())){
                criteria.add(Restrictions.and(Restrictions.ilike("docNo", "%" + stockTransferView.getDocNo()), Restrictions.ilike("docNo", "TR%")));
            } else {
                criteria.add(Restrictions.ilike("docNo", "TR%"));
            }

            if (!Utils.isZero(stockTransferView.getDocNoteId())){
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", stockTransferView.getDocNoteId()));
            }

            criteria.add(Restrictions.ge("docDate", stockTransferView.getFormDate()));
            criteria.add(Restrictions.lt("docDate", stockTransferView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }
}
