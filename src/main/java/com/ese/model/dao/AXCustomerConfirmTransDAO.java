package com.ese.model.dao;

import com.ese.model.db.AXCustomerConfirmTransModel;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class AXCustomerConfirmTransDAO extends GenericDAO<AXCustomerConfirmTransModel, String> {

    public void updateStatusRunning(String saleId, String confirmId, Date confirmDate){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmTrans SET ").append(getPrefix()).append(".ax_CustConfirmTrans.status = 2 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmTrans.salesId = ").append("'").append(saleId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.confirmId = ").append("'").append(confirmId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.confirmDate = ").append("'").append(confirmDate).append("'");

        log.debug("SQL ax_CustConfirmTrans : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void rollbackStatus(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmTrans SET ").append(getPrefix()).append(".ax_CustConfirmTrans.status = 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmTrans.status = 2");

        log.debug("SQL Roll Back ax_CustConfirmTrans : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateStatusFinish(String saleId, String confirmId, Date confirmDate){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmTrans SET ").append(getPrefix()).append(".ax_CustConfirmTrans.status = 3 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmTrans.salesId = ").append("'").append(saleId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.confirmId = ").append("'").append(confirmId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.confirmDate = ").append("'").append(confirmDate).append("'");

        log.debug("SQL ax_CustConfirmTrans : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }
}
