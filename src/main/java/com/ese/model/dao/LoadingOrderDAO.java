package com.ese.model.dao;

import com.ese.model.db.LoadingOrderModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class LoadingOrderDAO extends GenericDAO<LoadingOrderModel, Integer>{

    public List<LoadingOrderModel> findByStatusIs12(){
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

    public List<LoadingOrderModel> findBySearch(int status){
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

}
