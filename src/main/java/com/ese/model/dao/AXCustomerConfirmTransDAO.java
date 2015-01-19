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

    public AXCustomerConfirmTransModel findByPrimaryKey(String saleId, String confirmId, Date confirmDate){
        AXCustomerConfirmTransModel axCustomerConfirmTransModel = new AXCustomerConfirmTransModel();

        log.debug("------------- {}", saleId);
        log.debug("++++++++++++++ {}", confirmId);
        log.debug("############ {}", confirmDate);
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("salesId", saleId));
            criteria.add(Restrictions.eq("confirmId", confirmId));
            criteria.add(Restrictions.eq("confirmDate", confirmDate));

            axCustomerConfirmTransModel = (AXCustomerConfirmTransModel) criteria.uniqueResult();
            log.debug("axCustomerConfirmTransModel : {}", axCustomerConfirmTransModel.toString());

        } catch (Exception e) {
            log.debug("Exception error findByPrimaryKey : ", e);
        }

        return axCustomerConfirmTransModel;
    }
}
