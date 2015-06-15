package com.ese.model.dao;

import com.ese.model.db.LoadingOrderModel;
import com.ese.model.view.ItemSequenceView;
import com.ese.model.view.LoadingOrderView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LoadingOrderDAO extends GenericDAO<LoadingOrderModel, Integer>{

    public List<LoadingOrderModel> findDomesticByStatusIs12(){
        List<LoadingOrderModel> loadingOrderModelList = Utils.getEmptyList();

        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("statusModel.id", 12));
            criteria.add(Restrictions.eq("category", "D"));
            criteria.addOrder(Order.desc("createDate"));
            loadingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findByStatusIs12 : ", e);
        }
        return loadingOrderModelList;
    }

    public List<LoadingOrderView> findOverSeaByStatusIs12(){
        List<LoadingOrderView> loadingOrderModelList = new ArrayList<LoadingOrderView>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.create_by AS CREATE_BY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.create_date AS CREATE_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.update_by AS UPDATE_BY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.update_date AS UPDATE_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.docno AS DOCNO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.loadingdate AS LOADING_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.remark AS REMARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.status AS STATUS_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".loading_order.category AS CATEGORY");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".loading_order");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".loading_order.status = 12 ");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".loading_order.category = 'O' ");
        sqlBuilder.append(" AND CONVERT(VARCHAR, ").append(getPrefix()).append(".loading_order.loadingdate, 110) = CONVERT(VARCHAR, GETDATE(), 110)");
        sqlBuilder.append(" ORDER BY ").append(getPrefix()).append(".loading_order.create_date");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("CREATE_BY", IntegerType.INSTANCE)
                    .addScalar("CREATE_DATE", DateType.INSTANCE)
                    .addScalar("UPDATE_BY", IntegerType.INSTANCE)
                    .addScalar("UPDATE_DATE", DateType.INSTANCE)
                    .addScalar("DOCNO", StringType.INSTANCE)
                    .addScalar("LOADING_DATE", DateType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE)
                    .addScalar("STATUS_ID", IntegerType.INSTANCE)
                    .addScalar("CATEGORY", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                LoadingOrderView loadingOrderModel = new LoadingOrderView();
                loadingOrderModel.setId(Utils.parseInt(entity[0]));
                loadingOrderModel.setCreateBy(Utils.parseInt(entity[1]));
                loadingOrderModel.setCreateDate(Utils.parseDate(entity[2], null));
                loadingOrderModel.setUpdateBy(Utils.parseInt(entity[3]));
                loadingOrderModel.setUpdateDate(Utils.parseDate(entity[4], null));
                loadingOrderModel.setDocNo(Utils.parseString(entity[5]));
                loadingOrderModel.setLoadingDate(Utils.parseDate(entity[6], null));
                loadingOrderModel.setRemark(Utils.parseString(entity[7]));
                loadingOrderModel.setStatus(Utils.parseInt(entity[8]));
                loadingOrderModel.setCategory(Utils.parseString(entity[9]));
                loadingOrderModelList.add(loadingOrderModel);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findOverSeaByStatusIs12 : {}", e);
        }

        return loadingOrderModelList;
    }

    public List<LoadingOrderModel> findDomesticBySearch(int status){
        List<LoadingOrderModel> loadingOrderModelList = Utils.getEmptyList();
//        log.debug("docNo : {[]}, loadingDate : {[]}, status : {[]}", docNo.trim().length(), loadingDate.trim().length(), status);

        try{
            Criteria criteria = getCriteria();
//
//            if (!Utils.isZero(docNo.trim().length())){
//                criteria.add(Restrictions.like("docNo", "%" + docNo + "%"));
//            }
//
//            if (!Utils.isZero(loadingDate.trim().length())){
//                criteria.add(Restrictions.like("loadingDate", "%" + loadingDate + "%"));
//            }

            if (!Utils.isZero(status)){
                criteria.add(Restrictions.eq("statusModel.id", status));
            } else {
                criteria.add(Restrictions.eq("statusModel.id", 12));
            }

            criteria.add(Restrictions.eq("category", "D"));
            criteria.addOrder(Order.desc("createDate"));

            loadingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findByStatusIs12 : ", e);
        }
        return loadingOrderModelList;
    }

    public List<LoadingOrderModel> findOverSeaBySearch(int status){
        List<LoadingOrderModel> loadingOrderModelList = Utils.getEmptyList();
//        log.debug("docNo : {[]}, loadingDate : {[]}, status : {[]}", docNo.trim().length(), loadingDate.trim().length(), status);

        try{
            Criteria criteria = getCriteria();
//
//            if (!Utils.isZero(docNo.trim().length())){
//                criteria.add(Restrictions.like("docNo", "%" + docNo + "%"));
//            }
//
//            if (!Utils.isZero(loadingDate.trim().length())){
//                criteria.add(Restrictions.like("loadingDate", "%" + loadingDate + "%"));
//            }

            if (!Utils.isZero(status)){
                criteria.add(Restrictions.eq("statusModel.id", status));
            } else {
                criteria.add(Restrictions.eq("statusModel.id", 12));
            }

            criteria.add(Restrictions.eq("category", "O"));
            criteria.addOrder(Order.desc("createDate"));

            loadingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findByStatusIs12 : ", e);
        }
        return loadingOrderModelList;
    }

    public void updateStatus(int loadingOrderId, int statusId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".loading_order SET ").append(getPrefix()).append(".loading_order.status =  ").append(statusId);
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".loading_order.id = ").append("'").append(loadingOrderId).append("'");

        log.debug("SQL updateToWrap : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public List<ItemSequenceView> findByLoadingOrderId(int loadingOrderId){
        List<ItemSequenceView> itemList =new ArrayList<ItemSequenceView>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT DISTINCT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.id AS ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".loading_order");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".picking_order");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".loading_order.id = ").append(getPrefix()).append(".picking_order.loading_order_id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.id = ").append(getPrefix()).append(".picking_order_line.picking_order_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".loading_order.id = ").append(loadingOrderId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ID", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ItemSequenceView itemSequenceView = new ItemSequenceView();
                itemSequenceView.setItemName(Utils.parseString(entity[0]));
                itemSequenceView.setId(Utils.parseInt(entity[1]));
                itemList.add(itemSequenceView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByLoadingOrderId : {}", e);
        }

        return itemList;
    }
}
