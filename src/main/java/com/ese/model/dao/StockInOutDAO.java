package com.ese.model.dao;

import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IncomingView;
import com.ese.model.view.IssuingView;
import com.ese.model.view.QuarantineView;
import com.ese.model.view.StockTransferView;
import com.ese.model.view.report.StockViewReport;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StockInOutDAO extends GenericDAO<StockInOutModel, Integer> {

    public List<StockInOutModel> findByDocnoAndCurrentDate(){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();

        try {
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.ilike("docNo", "TR%"));
            criteria.add(Restrictions.ge("docDate", Utils.minDateTime()));
            criteria.add(Restrictions.le("docDate", Utils.maxDateTime()));
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
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IN%"));
            criteria.add(Restrictions.ge("docDate", Utils.minDateTime()));
            criteria.add(Restrictions.le("docDate", Utils.maxDateTime()));
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
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "OU%"));
            criteria.add(Restrictions.ge("docDate", Utils.minDateTime()));
            criteria.add(Restrictions.le("docDate", Utils.maxDateTime()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDocNoINAndCurrentDate : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findByDocNoQRndCurrentDate(){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "QR%"));
            criteria.add(Restrictions.ge("docDate", Utils.minDateTime()));
            criteria.add(Restrictions.le("docDate", Utils.maxDateTime()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDocNoINAndCurrentDate : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findBySearch(StockTransferView stockTransferView){
        log.debug("search {}", stockTransferView.toString());
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();

            if (!Utils.isNull(stockTransferView.getDocNo()) && !Utils.isZero(stockTransferView.getDocNo().length())){
                criteria.add(Restrictions.ilike("docNo", "%" + stockTransferView.getDocNo() + "%"));
            } else {
                criteria.add(Restrictions.ilike("docNo", "TR%"));
            }

            if (!Utils.isZero(stockTransferView.getDocNoteId())){
                log.debug("---------- {}", stockTransferView.getDocNoteId());
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", stockTransferView.getDocNoteId()));
            }
            criteria.add(Restrictions.between("docDate", Utils.minDateTime(stockTransferView.getFormDate()), Utils.maxDateTime(stockTransferView.getToDate())));
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
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "IN%"));
            if (!Utils.isZero(incomingView.getDocNoteId())){
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", incomingView.getDocNoteId()));
            }
            criteria.add(Restrictions.between("docDate", Utils.minDateTime(incomingView.getFormDate()), Utils.maxDateTime(incomingView.getToDate())));
//            criteria.add(Restrictions.ge("docDate", incomingView.getFormDate()));
//            criteria.add(Restrictions.le("docDate", incomingView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
//            log.debug("SQL : [{}] - [{}]", Utils.minDateTime(incomingView.getFormDate()), Utils.maxDateTime(incomingView.getToDate()));
//            log.debug("[{}]", Locale.getDefault());
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findBySearchIOU(IssuingView issuingView){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "OU%"));
            if (!Utils.isZero(issuingView.getDocNoteId())){
                criteria.add(Restrictions.eq("msStockInOutNoteModel.id", issuingView.getDocNoteId()));
            }
            criteria.add(Restrictions.between("docDate", Utils.minDateTime(issuingView.getFormDate()), Utils.maxDateTime(issuingView.getToDate())));
//            criteria.add(Restrictions.ge("docDate", issuingView.getFormDate()));
//            criteria.add(Restrictions.le("docDate", issuingView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockInOutModel> findBySearchQR(QuarantineView quarantineView){
        List<StockInOutModel> stockInOutModelList = Utils.getEmptyList();
        try {
            System.setProperty("user.timezone", "GMT+07:00");
            System.setProperty("user.language", "en");
            System.setProperty("user.country", "US");
            Locale.setDefault(Locale.US);
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "QR%"));
            criteria.add(Restrictions.between("docDate", Utils.minDateTime(quarantineView.getFormDate()), Utils.maxDateTime(quarantineView.getToDate())));
//            criteria.add(Restrictions.ge("docDate", quarantineView.getFormDate()));
//            criteria.add(Restrictions.le("docDate", quarantineView.getToDate()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return stockInOutModelList;
    }

    public List<StockViewReport> findReportByStickInoutId(int stockInOutId){
        log.debug("findReportByStickInoutId(). {}", stockInOutId);
        List<StockViewReport> viewReportList = new ArrayList<StockViewReport>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_inout.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_inout.docno AS DOC_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_inout.docdate AS DOC_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_inout_note.inout_code AS INOUT_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_inout.remark AS REMARK");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_inout");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".stock_inout_note");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_inout.stock_inout_note_id = ").append(getPrefix()).append(".stock_inout_note.id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_inout.id = ").append(stockInOutId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("DOC_NO", StringType.INSTANCE)
                    .addScalar("DOC_DATE", TimestampType.INSTANCE)
                    .addScalar("INOUT_CODE", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                StockViewReport viewReport = new StockViewReport();
                viewReport.setId(Utils.parseInt(entity[0]));
                viewReport.setDocNo(Utils.parseString(entity[1]));
                viewReport.setDocDate(Utils.parseDate(entity[2], null));
                viewReport.setInoutCode(Utils.parseString(entity[3]));
                viewReport.setRemark(Utils.parseString(entity[4]));
                viewReportList.add(viewReport);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return viewReportList;
    }
}