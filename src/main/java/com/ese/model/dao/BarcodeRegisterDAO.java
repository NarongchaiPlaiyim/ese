package com.ese.model.dao;

import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.view.report.BarcodeRegisterModelReport;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BarcodeRegisterDAO extends GenericDAO<BarcodeRegisterModel, Integer> {
    public List<BarcodeRegisterModel> findByIsValid() throws Exception {
        Criteria criteria = getCriteria().add(Restrictions.eq("isValid", 1)).addOrder(Order.desc("updateDate"));
        return Utils.safetyList(criteria.list());
    }

    public List<BarcodeRegisterModel> findByLike(String filed, String text) throws Exception {
        Criteria criteria = getCriteria().add(Restrictions.eq("isValid", 1)).addOrder(Order.desc("updateDate"));
        criteria.add(Restrictions.like(filed, "%"+text+"%"));
        return Utils.safetyList(criteria.list());
    }

    public List<BarcodeRegisterModel> findByBetween(String startBarcode) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ppwms03.dbo.barcode_register");
        sqlBuilder.append(" WHERE ppwms03.dbo.barcode_register.finish_barcode");
        sqlBuilder.append(" BETWEEN "+startBarcode+" AND 999999999");
        SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString()).addEntity(BarcodeRegisterModel.class);
        return Utils.safetyList(query.list());
    }

    public List<BarcodeRegisterModel> findByLike(String text) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ppwms03.dbo.barcode_register");
        sqlBuilder.append(" WHERE ppwms03.dbo.barcode_register.item_id");
        sqlBuilder.append(" IN(SELECT ppwms03.dbo.item_master.id FROM ppwms03.dbo.item_master WHERE");
        sqlBuilder.append(" ppwms03.dbo.item_master.DSGThaiItemDescription LIKE '%"+text+"%')");
        SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString()).addEntity(BarcodeRegisterModel.class);
        return Utils.safetyList(query.list());
    }

    public void deleteByUpdate(final BarcodeRegisterModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }

    public BigDecimal getPrice(String iTemId) throws Exception {
        final String sql = "SELECT a.Price FROM ppwms03.dbo.ax_InventtableModule a WHERE a.ItemId = '"+iTemId+"' AND a.ModuleType = 0";
        return Utils.parseBigDecimal(getSession().createSQLQuery(sql).uniqueResult(), BigDecimal.ZERO);
    }

    public List<BarcodeRegisterModelReport> genSQLReportBarcode(int barcodeId){
        log.debug("genSQLReportBarcode(). {}", barcodeId);
        List<BarcodeRegisterModelReport> reportViews = new ArrayList<BarcodeRegisterModelReport>();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT DISTINCT ppwms03.dbo.item_master.DSGThaiItemDescription AS ITEMDESCRIPTION,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.docno AS DOCNO,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.production_date AS PRODUCT_DATE,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.batchno AS BATCHNO,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.start_barcode_text AS START_BARCODE,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.finish_barcode_text AS FINISH_BARCODE,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.qty AS QTY,");
        sqlBuilder.append(" ppwms03.dbo.barcode_register.remark AS REMARK");
        sqlBuilder.append(" FROM ppwms03.dbo.barcode_register");
        sqlBuilder.append(" LEFT JOIN ppwms03.dbo.item_master");
        sqlBuilder.append(" ON ppwms03.dbo.barcode_register.item_id = ppwms03.dbo.item_master.id");
        sqlBuilder.append(" WHERE ppwms03.dbo.barcode_register.id = " + barcodeId);

        log.debug(sqlBuilder.toString());

        try{
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ITEMDESCRIPTION", StringType.INSTANCE)
                    .addScalar("DOCNO", StringType.INSTANCE)
                    .addScalar("PRODUCT_DATE", TimestampType.INSTANCE)
                    .addScalar("BATCHNO", StringType.INSTANCE)
                    .addScalar("START_BARCODE", StringType.INSTANCE)
                    .addScalar("FINISH_BARCODE", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                BarcodeRegisterModelReport report = new BarcodeRegisterModelReport();
                report.setDSGThaiItemDescription("Item : " + Utils.parseString(entity[0], ""));
                report.setDocNo("Doc No : " + Utils.parseString(entity[1], ""));
                report.setProductDate("Date : " + Utils.convertToStringDDMMYYYY(Utils.parseDate(entity[2], null)));
                report.setBatchNo("Batch No :" + Utils.parseString(entity[3], ""));
                report.setStartBarcode("Start Barcode : " + Utils.parseString(entity[4], ""));
                report.setFinishBarcode("Finish Barcode : " + Utils.parseString(entity[5], ""));
                report.setQty(Utils.parseInt(entity[6], 0));
                report.setRemark("Remark : " + Utils.parseString(entity[7], ""));
                reportViews.add(report);
            }
        } catch (Exception e){
            log.debug("Exception Report : ", e);
        }
        return reportViews;
    }

}
