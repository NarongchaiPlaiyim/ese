package com.ese.model.dao;

import com.ese.model.db.PalletModel;
import com.ese.model.view.report.PalletManagemengReportView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PalletDAO extends GenericDAO<PalletModel, Integer>{

    public List<PalletModel> findPalletTable(){
        log.debug("findOnloadPallet().");
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("status", 2));
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }

    public List<PalletModel> findChang(int statusId, int warehouse, int conveyorLine, int location, String keyItemDescription){
        log.debug("findUnPrint().");
        try {
            Criteria criteria = getSession().createCriteria(PalletModel.class, "p");

            if (!Utils.isZero(warehouse)){
                criteria.add(Restrictions.eq("msWarehouseModel.id", warehouse));
            }

            if (!Utils.isZero(conveyorLine)){
                criteria.add(Restrictions.eq("msWorkingAreaModel.id", conveyorLine));
            }

            if (!Utils.isZero(location)){
                criteria.add(Restrictions.eq("msLocationModel.id", location));
            }

            if (!Utils.isNull(keyItemDescription) && !"".equalsIgnoreCase(keyItemDescription)){
                criteria.createAlias("p.msItemModel", "c");
                criteria.add(Restrictions.like("c.dSGThaiItemDescription", "%"+keyItemDescription.trim()+"%"));
            }

            if (statusId == 1){
                criteria.add(Restrictions.lt("status", 3));
            } else if (statusId == 2){
                criteria.add(Restrictions.eq("qty", 0));
            }
            criteria.addOrder(Order.desc("updateDate"));
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }

    public void updauePalletByChangeLocation(int palletId, int locationId){
        log.debug("updauePalletByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ppwms03.dbo.pallet SET ppwms03.dbo.pallet.location_id = ").append("'").append(locationId).append("'");
        stringBuilder.append(" WHERE ppwms03.dbo.pallet.id = ").append("'").append(palletId).append("'");

        log.debug("SQL Pallet : {}",stringBuilder.toString());
        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByChangeLocation(int locationId){
        log.debug("updateLocationByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ppwms03.dbo.location SET ppwms03.dbo.location.reserved_qty += 1 ");
        stringBuilder.append(" WHERE ppwms03.dbo.location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public List<PalletManagemengReportView> genSQLReportPallet(int palletId){
        log.debug("genSQLReportPallet().");
        List<PalletManagemengReportView> reportViews = new ArrayList<PalletManagemengReportView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT DISTINCT\n" +
                " ppwms03.dbo.item_master.DSGThaiItemDescription AS DESCRIPTION,\n" +
                " ppwms03.dbo.warehouse.warehouse_code AS WAREHOUSE_CODE,\n" +
                " ppwms03.dbo.pallet.pallet_barcode AS PALLET_BARCODE,\n" +
                " ppwms03.dbo.location.location_barcode AS LOCATION_BARCODE,\n" +
                " ppwms03.dbo.pallet.create_date AS CREATE_DATE,\n" +
                " ppwms03.dbo.inv_onhand.grade AS GRADE,\n" +
                " ppwms03.dbo.inv_onhand.batchno AS BATHCH_NO,\n" +
                " ppwms03.dbo.working_area.name AS WORKING_NAME,\n" +
                " Count(ppwms03.dbo.inv_onhand.id) AS COUNT_ID\n" +
                " FROM ppwms03.dbo.pallet\n" +
                " LEFT JOIN ppwms03.dbo.item_master\n" +
                " ON  ppwms03.dbo.pallet.item_id = ppwms03.dbo.item_master.id\n" +
                " LEFT JOIN ppwms03.dbo.warehouse\n" +
                " ON ppwms03.dbo.pallet.warehouse_id = ppwms03.dbo.warehouse.id\n" +
                " LEFT JOIN ppwms03.dbo.working_area\n" +
                " ON ppwms03.dbo.pallet.conveyor_line = ppwms03.dbo.working_area.id\n" +
                " LEFT JOIN ppwms03.dbo.location\n" +
                " ON ppwms03.dbo.pallet.location_id = ppwms03.dbo.location.id\n" +
                " LEFT JOIN ppwms03.dbo.inv_onhand\n" +
                " ON ppwms03.dbo.pallet.id = ppwms03.dbo.inv_onhand.pallet_id\n" +
                " WHERE ppwms03.dbo.pallet.ID = 13\n" +
                " GROUP BY ppwms03.dbo.item_master.DSGThaiItemDescription,\n" +
                " ppwms03.dbo.warehouse.warehouse_code,ppwms03.dbo.pallet.pallet_barcode,\n" +
                " ppwms03.dbo.location.location_barcode,ppwms03.dbo.pallet.create_date,ppwms03.dbo.inv_onhand.grade,\n" +
                " ppwms03.dbo.working_area.name,ppwms03.dbo.inv_onhand.batchno");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("DESCRIPTION", StringType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("CREATE_DATE", TimestampType.INSTANCE)
                    .addScalar("GRADE", StringType.INSTANCE)
                    .addScalar("BATHCH_NO", StringType.INSTANCE)
                    .addScalar("WORKING_NAME", StringType.INSTANCE)
                    .addScalar("COUNT_ID", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();
            for (Object[] entity : objects) {
                PalletManagemengReportView report = new PalletManagemengReportView();
                report.setDSGThaiItemDescription(Utils.parseString(entity[0], ""));
                report.setWarehouseCode(Utils.parseString(entity[1], ""));
                report.setPalletBarcode(Utils.parseString(entity[2], ""));
                report.setLocationBarcode(Utils.parseString(entity[3], ""));
                report.setCreateDate(Utils.convertToStringDDMMYYYY(Utils.parseDate(entity[4], null)));
                report.setGrade(Utils.parseString(entity[5], ""));
                report.setBathcgNo(Utils.parseString(entity[6], ""));
                report.setWorkingName(Utils.parseString(entity[7], ""));
                report.setCountId(Utils.parseInt(entity[8], 0));
                reportViews.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reportViews;
    }
}
