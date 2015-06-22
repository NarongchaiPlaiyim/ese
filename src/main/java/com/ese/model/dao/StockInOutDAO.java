package com.ese.model.dao;

import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IncomingView;
import com.ese.model.view.IssuingView;
import com.ese.model.view.StockTransferView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    public List<StockInOutModel> findByDocNoINAndCurrentDate(){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IN%"));
            criteria.add(Restrictions.eq("docDate", Utils.currentDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDocNoINAndCurrentDate : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findByDocNoIOUAndCurrentDate(){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IOU%"));
            criteria.add(Restrictions.eq("docDate", Utils.currentDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDocNoINAndCurrentDate : ", e);
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

    public List<StockInOutModel> findBySearchIN(IncomingView incomingView){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IN%"));
            if (!Utils.isZero(incomingView.getDocNoteId())){
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", incomingView.getDocNoteId()));
            }
            log.debug("FormDate {}", incomingView.getFormDate());
            log.debug("ToDate {}", incomingView.getToDate());
            criteria.add(Restrictions.ge("docDate", incomingView.getFormDate()));
            criteria.add(Restrictions.le("docDate", incomingView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findBySearchIOU(IssuingView issuingView){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IOU%"));
            if (!Utils.isZero(issuingView.getDocNoteId())){
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", issuingView.getDocNoteId()));
            }
            log.debug("FormDate {}", issuingView.getFormDate());
            log.debug("ToDate {}", issuingView.getToDate());
            criteria.add(Restrictions.ge("docDate", issuingView.getFormDate()));
            criteria.add(Restrictions.le("docDate", issuingView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }
}
