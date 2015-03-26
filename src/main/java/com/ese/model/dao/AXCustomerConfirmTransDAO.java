package com.ese.model.dao;

import com.ese.model.db.AXCustomerConfirmTransModel;
import com.ese.model.view.CustomerConfirmTransView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    public List<AXCustomerConfirmTransModel> findByPrimaryKey(String saleId, String confirmId, Date confirmDate){
//        List<AXCustomerConfirmTransModel> confirmTransModelList = Utils.getEmptyList();
//
//        log.debug("------------- {}", saleId);
//        log.debug("++++++++++++++ {}", confirmId);
//        log.debug("############ {}", confirmDate);
//        try {
//            Criteria criteria = getCriteria();
//            criteria.add(Restrictions.eq("salesId", saleId));
//            criteria.add(Restrictions.eq("confirmId", confirmId));
//            criteria.add(Restrictions.eq("confirmDate", confirmDate));
//
//            confirmTransModelList = criteria.list();
//            log.debug("axCustomerConfirmTransModel : {}", confirmTransModelList.toString());
//
//        } catch (Exception e) {
//            log.debug("Exception error findByPrimaryKey : ", e);
//        }
//
//        return confirmTransModelList;
//    }

    public List<CustomerConfirmTransView> findByPrimaryKey(String saleId, String confirmId, Date confirmDate){
        List<CustomerConfirmTransView> confirmTransModelList =new ArrayList<CustomerConfirmTransView>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT DISTINCT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.LineNum AS LINE_NUM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.OrigSalesId AS ORIG_SALE_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.Qty AS QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmDate AS CONFIRM_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.SalesUnit AS SALE_UNIT,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.DSG_SubGroupDescription AS GROUP_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.PIDescription AS PI_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.DSG_Ext_Item_NO AS EXT_ITEM_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.Name AS NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.DSG_PackingQty AS PACKING_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.salesUnitTxt AS SALE_UNIT_TXT,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.Cum AS CUM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmTrans.inventtransid AS INVENTRANS_ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".ax_CustConfirmTrans");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmTrans.SalesId = '").append(saleId).append("' ");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmId = '").append(confirmId).append("' ");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmDate = '").append(confirmDate).append("'");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("LINE_NUM", BigDecimalType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ORIG_SALE_ID", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("CONFIRM_DATE", DateType.INSTANCE)
                    .addScalar("SALE_UNIT", StringType.INSTANCE)
                    .addScalar("GROUP_DESC", StringType.INSTANCE)
                    .addScalar("PI_DESC", StringType.INSTANCE)
                    .addScalar("EXT_ITEM_NO", StringType.INSTANCE)
                    .addScalar("NAME", StringType.INSTANCE)
                    .addScalar("PACKING_QTY", BigDecimalType.INSTANCE)
                    .addScalar("SALE_UNIT_TXT", StringType.INSTANCE)
                    .addScalar("CUM", BigDecimalType.INSTANCE)
                    .addScalar("INVENTRANS_ID", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                CustomerConfirmTransView confirmTransView = new CustomerConfirmTransView();
                confirmTransView.setLineNum(Utils.parseBigDecimal(entity[0], BigDecimal.ZERO));
                confirmTransView.setItemId(Utils.parseString(entity[1], ""));
                confirmTransView.setOrigSaleId(Utils.parseString(entity[2], ""));
                confirmTransView.setQty(Utils.parseInt(entity[3], 0));
                confirmTransView.setShipDate(Utils.parseDate(entity[4], null));
                confirmTransView.setSalesUnit(Utils.parseString(entity[5], ""));
                confirmTransView.setDSGSubGroupDescription(Utils.parseString(entity[6], ""));
                confirmTransView.setPIDescription(Utils.parseString(entity[7], ""));
                confirmTransView.setDSGExtItemNO(Utils.parseString(entity[8], ""));
                confirmTransView.setName(Utils.parseString(entity[9], ""));
                confirmTransView.setDSGPackingQty(Utils.parseBigDecimal(entity[10], BigDecimal.ZERO));
                confirmTransView.setSalesUnitTxt(Utils.parseString(entity[11], ""));
                confirmTransView.setCum(Utils.parseBigDecimal(entity[12], BigDecimal.ZERO));
                confirmTransView.setInventransId(Utils.parseString(entity[13]));
                confirmTransModelList.add(confirmTransView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByPrimaryKey : {}", e);
        }

        return confirmTransModelList;
    }
}
