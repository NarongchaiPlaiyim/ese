package com.ese.model.dao;

import com.ese.model.db.PalletModel;
import com.ese.model.view.PalletTransferView;
import com.ese.model.view.report.PalletManagemengModelReport;
import com.ese.model.view.report.PalletSubReport;
import com.ese.model.view.report.StockInOutDetailViewReport;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PalletDAO extends GenericDAO<PalletModel, Integer>{

    public List<PalletModel> findPalletTable(){
        log.debug("findOnloadPallet().");
        List<PalletModel> palletModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("status", 2));
            palletModelList = Utils.safetyList(criteria.list());
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return palletModelList;
    }

    public List<PalletModel> findByLikePalletBarcode(String palletBarcode) throws Exception {
        log.debug("findByLikePalletBarcode(). {}", palletBarcode);
        List<PalletModel> palletModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(PalletModel.class, "p");
//            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("p.palletBarcode","%" + palletBarcode.trim() + "%"));
            palletModelList = Utils.safetyList(criteria.list());
            log.debug("findByLikePalletBarcode Size : {}", palletModelList.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return palletModelList;
    }

    public List<PalletModel> findChang(int statusId, int warehouse, int conveyorLine, int location, String keyItemDescription, int combine, int foil){
        log.debug("findChang(). statusId {}, warehouse {}, conveyorLine {}, location {}, keyItemDescription {}, combine {}, foil {}", statusId, warehouse, conveyorLine, location, keyItemDescription, combine, foil);
        List<PalletModel> palletModelList = Utils.getEmptyList();
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

            criteria.add(Restrictions.eq("isCombine", combine));

            if (foil == 1){
                criteria.add(Restrictions.gt("isFoil", 0));
            } else {
                criteria.add(Restrictions.eq("isFoil", foil));
            }

            if (!Utils.isNull(keyItemDescription) && !"".equalsIgnoreCase(keyItemDescription)){
                criteria.createAlias("p.msItemModel", "c");
                Criterion itemDes = Restrictions.like("c.dSGThaiItemDescription", "%" + keyItemDescription.trim() + "%");
                criteria.createAlias("p.msLocationModel", "d");
                    Criterion locationBarcode = Restrictions.like("d.locationBarcode", "%"+keyItemDescription.trim()+"%");
                criteria.add(Restrictions.or(itemDes,locationBarcode));
            }

            if (statusId == 0){
                criteria.add(Restrictions.eq("status", 2));
            } else if (statusId == 2){
                criteria.add(Restrictions.lt("status", 3));
            } else if (statusId == 3){
                criteria.add(Restrictions.eq("qty", 0));
            }

            criteria.add(Restrictions.ne("status", 6));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            palletModelList = Utils.safetyList(criteria.list());
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return palletModelList;
        }
    }

    public void updauePalletByChangeLocation(int palletId, int locationId){
        log.debug("updauePalletByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".pallet SET ").append(getPrefix()).append(".pallet.location_id = ").append("'").append(locationId).append("'");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".pallet.id = ").append("'").append(palletId).append("'");

        log.debug("SQL Pallet : {}",stringBuilder.toString());
        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateNewPalletTransferByChangeLocation(int palletId, int locationId){
        log.debug("updauePalletByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".pallet SET ").append(getPrefix()).append(".pallet.location_id = ").append("'").append(locationId).append("', ");
        stringBuilder.append(getPrefix()).append(".pallet.set_to_transfer = 1");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".pallet.id = ").append("'").append(palletId).append("'");

        log.debug("SQL Pallet : {}",stringBuilder.toString());
        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByOld(int locationId){
        log.debug("updateLocationByOld().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".location SET ").append(getPrefix()).append(".location.reserved_qty += 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByNew(int locationId){
        log.debug("updateLocationByNew().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".location SET ").append(getPrefix()).append(".location.reserved_qty -= 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByStatusPrinted(int locationId){
        log.debug("updateLocationByStatusPrinted().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".location SET ").append(getPrefix()).append(".location.reserved_qty -= 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByStatusLocated(int locationId){
        log.debug("updateLocationByStatusLocated().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".location SET ").append(getPrefix()).append(".location.qty -= 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public List<PalletManagemengModelReport> genSQLReportPallet(int palletId){
        log.debug("genSQLReportPallet(). {}", palletId);
        List<PalletManagemengModelReport> reportViews = new ArrayList<PalletManagemengModelReport>();
        StringBuilder sqlBuilder = new StringBuilder();

//        sqlBuilder.append(" SELECT DISTINCT");
//        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS DESCRIPTION,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.create_date AS CREATE_DATE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.grade AS GRADE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATHCH_NO,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name AS WORKING_NAME,");
//        sqlBuilder.append(" Count(").append(getPrefix()).append(".inv_onhand.id) AS COUNT_ID");
//        sqlBuilder.append(" FROM ").append(getPrefix()).append(".pallet");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
//        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".working_area");
//        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.conveyor_line = ").append(getPrefix()).append(".working_area.id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location");
//        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_onhand");
//        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_onhand.pallet_id");
//        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".pallet.ID = " + palletId );
//        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".item_master.DSGThaiItemDescription,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code,").append(getPrefix()).append(".pallet.pallet_barcode,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode,").append(getPrefix()).append(".pallet.create_date,").append(getPrefix()).append(".inv_onhand.grade,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name,").append(getPrefix()).append(".inv_onhand.batchno");


        sqlBuilder.append(" SELECT DISTINCT");
        sqlBuilder.append(" Count(").append(getPrefix()).append(".inv_onhand.id) AS COUNT_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS DESCRIPTION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.create_date AS CREATE_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.grade AS GRADE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATHCH_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name AS WORKING_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.seq AS SEQ,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".working_area");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.working_area_id = ").append(getPrefix()).append(".working_area.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_onhand.pallet_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".pallet.ID = " + palletId );
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".item_master.DSGThaiItemDescription,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code,").append(getPrefix()).append(".pallet.pallet_barcode,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode,").append(getPrefix()).append(".pallet.create_date,").append(getPrefix()).append(".inv_onhand.grade,");
        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name,").append(getPrefix()).append(".inv_onhand.batchno,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.seq,").append(getPrefix()).append(".item_master.itemid");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("COUNT_ID", IntegerType.INSTANCE)
                    .addScalar("DESCRIPTION", StringType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("CREATE_DATE", TimestampType.INSTANCE)
                    .addScalar("GRADE", StringType.INSTANCE)
                    .addScalar("BATHCH_NO", StringType.INSTANCE)
                    .addScalar("WORKING_NAME", StringType.INSTANCE)
                    .addScalar("SEQ", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                PalletManagemengModelReport report = new PalletManagemengModelReport();
                report.setCountId(Utils.parseInt(entity[0]));
                report.setDSGThaiItemDescription(Utils.parseString(entity[1]));
                report.setWarehouseCode(Utils.parseString(entity[2]));
                report.setPalletBarcode(Utils.parseString(entity[3]));
                report.setLocationBarcode(Utils.parseString(entity[4]));
                report.setCreateDate(Utils.convertToStringDDMMYYYY(Utils.parseDate(entity[5], null)));
                report.setGrade(Utils.parseString(entity[6]));
                report.setBathcgNo(Utils.parseString(entity[7]));
                report.setWorkingName(Utils.parseString(entity[8]));
                report.setSeq(Utils.parseInt(entity[9]));
                report.setItemId(Utils.parseString(entity[10]));
                reportViews.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reportViews;
    }

    public PalletModel findByIdToReport(int palletId){
        log.debug("findOnloadPallet().");
        PalletModel palletModel = new PalletModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", palletId));
            palletModel = (PalletModel) criteria.uniqueResult();
            return palletModel;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return palletModel;
        }
    }

    public List<PalletSubReport> genSQLReportPalletV2(int palletId){
        log.debug("genSQLReportPallet(). {}", palletId);
        List<PalletSubReport> reportViews = new ArrayList<PalletSubReport>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" Count(").append(getPrefix()).append(".inv_onhand.id) AS QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESCRIPTION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".shift.name AS SHIFT_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name AS WORKING_AREA_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATCHNO");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".inv_onhand.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".inv_onhand.pallet_id = ").append(getPrefix()).append(".pallet.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".shift");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.shift_id = ").append(getPrefix()).append(".shift.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".working_area");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".inv_onhand.working_area_id = ").append(getPrefix()).append(".working_area.id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.pallet_id = " + palletId );
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".item_master.ItemId,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription,").append(getPrefix()).append(".shift.name,");
        sqlBuilder.append(" ").append(getPrefix()).append(".working_area.name,").append(getPrefix()).append(".inv_onhand.batchno");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESCRIPTION", StringType.INSTANCE)
                    .addScalar("SHIFT_NAME", StringType.INSTANCE)
                    .addScalar("WORKING_AREA_NAME", StringType.INSTANCE)
                    .addScalar("BATCHNO", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                PalletSubReport report = new PalletSubReport();
                report.setNo(i++);
                report.setQty(Utils.parseInt(entity[0], 0));
                report.setItemCode(Utils.parseString(entity[1], ""));
                report.setItemDescription(Utils.parseString(entity[2], ""));
                report.setShiftName(Utils.parseString(entity[3], ""));
                report.setWorkingAreaName(Utils.parseString(entity[4], ""));
                report.setBatchNo(Utils.parseString(entity[5], ""));
                reportViews.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reportViews;
    }

    public List<PalletTransferView> findByStockInOutId(int stockInOutId){
        List<PalletTransferView> palletViewList = new ArrayList<PalletTransferView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.id AS PALLET_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.create_date AS PALLET_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.capacity AS CAPACITY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.qty AS QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.combine AS COMBINE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.foil AS FOIL,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.set_to_transfer AS TO_TRANSFER");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".stock_inout_line");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".stock_inout_line.pallet_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_inout_line.stock_inout_id  = " + stockInOutId ).append(" and ").append(getPrefix()).append(".pallet.isvalid = 1");
        sqlBuilder.append(" GROUP BY  ").append(getPrefix()).append(".pallet.id, ").append(getPrefix()).append(".warehouse.warehouse_code, ");
        sqlBuilder.append(getPrefix()).append(".item_master.ItemId, ").append(getPrefix()).append(".item_master.DSGThaiItemDescription, ");
        sqlBuilder.append(getPrefix()).append(".pallet.pallet_barcode, ").append(getPrefix()).append(".pallet.create_date,  ");
        sqlBuilder.append(getPrefix()).append(".location.location_barcode, ").append(getPrefix()).append(".pallet.capacity, ");
        sqlBuilder.append(getPrefix()).append(".pallet.qty, ").append(getPrefix()).append(".pallet.combine, ");
        sqlBuilder.append(getPrefix()).append(".pallet.foil, ").append(getPrefix()).append(".pallet.set_to_transfer");

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("PALLET_ID", IntegerType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("PALLET_DATE", DateType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("CAPACITY", BigDecimalType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("COMBINE", IntegerType.INSTANCE)
                    .addScalar("FOIL", IntegerType.INSTANCE)
                    .addScalar("TO_TRANSFER", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                PalletTransferView palletView = new PalletTransferView();
                palletView.setId(Utils.parseInt(entity[0]));
                palletView.setWarehouseCode(Utils.parseString(entity[1]));
                palletView.setItemId(Utils.parseString(entity[2]));
                palletView.setItemDesc(Utils.parseString(entity[3]));
                palletView.setPalletBarcode(Utils.parseString(entity[4]));
                palletView.setCreateDate(Utils.parseDate(entity[5], null));
                palletView.setLocationBarcode(Utils.parseString(entity[6]));
                palletView.setCapacity(Utils.parseBigDecimal(entity[7]));
                palletView.setQty(Utils.parseInt(entity[8]));
                palletView.setIsCombine(Utils.parseInt(entity[9]));
                palletView.setIsFoil(Utils.parseInt(entity[10]));
                palletView.setToTransfer(Utils.parseInt(entity[11]));
                palletViewList.add(palletView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return palletViewList;
    }

    public List<PalletTransferView> findBySearch(String palletTag, String itemId, int locationId, int warehouseId){
        List<PalletTransferView> palletViewList = new ArrayList<PalletTransferView>();
        StringBuilder sql = new StringBuilder();

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT ");
        selectSql.append(" ").append(getPrefix()).append(".pallet.id AS PALLET_ID,");
        selectSql.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        selectSql.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
        selectSql.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.create_date AS PALLET_DATE,");
        selectSql.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.capacity AS CAPACITY,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.qty AS QTY,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.combine AS COMBINE,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.foil AS FOIL,");
        selectSql.append(" ").append(getPrefix()).append(".pallet.set_to_transfer AS TO_TRANSFER, ");
        selectSql.append(" ").append(getPrefix()).append(".item_master.id AS ITEM");
        selectSql.append(" FROM ").append(getPrefix()).append(".pallet");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        selectSql.append(" ON  ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        selectSql.append(" ON  ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        selectSql.append(" ON  ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".stock_inout_line");
        selectSql.append(" ON  ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".stock_inout_line.pallet_id");

        StringBuilder whereSql = new StringBuilder();
        whereSql.append(" WHERE ").append(getPrefix()).append(".pallet.isvalid = 1");
        whereSql.append(" AND ").append(getPrefix()).append(".pallet.status = 4");
        if (!Utils.isNull(palletTag) && !Utils.isZero(palletTag.length())){
            whereSql.append(" AND ").append(getPrefix()).append(".pallet.pallet_barcode LIKE '%").append(palletTag).append("%'");
        }

        if (!Utils.isNull(itemId) && !Utils.isZero(itemId.length())){
            whereSql.append(" AND ").append(getPrefix()).append(".item_master.ItemId LIKE '%").append(itemId).append("%'");
            whereSql.append(" OR ").append(getPrefix()).append(".item_master.DSGThaiItemDescription LIKE '%").append(itemId).append("%'");
        }

        if (!Utils.isZero(locationId)){
            whereSql.append(" AND ").append(getPrefix()).append(".pallet.location_id = ").append(locationId);
        }

        if (!Utils.isZero(warehouseId)){
            whereSql.append(" AND ").append(getPrefix()).append(".pallet.warehouse_id = ").append(warehouseId);
        }

        StringBuilder groupSql = new StringBuilder();
        groupSql.append(" GROUP BY  ").append(getPrefix()).append(".pallet.id, ").append(getPrefix()).append(".warehouse.warehouse_code, ");
        groupSql.append(getPrefix()).append(".item_master.ItemId, ").append(getPrefix()).append(".item_master.DSGThaiItemDescription, ");
        groupSql.append(getPrefix()).append(".pallet.pallet_barcode, ").append(getPrefix()).append(".pallet.create_date, ");
        groupSql.append(getPrefix()).append(".location.location_barcode, ").append(getPrefix()).append(".pallet.capacity, ");
        groupSql.append(getPrefix()).append(".pallet.qty, ").append(getPrefix()).append(".pallet.combine, ");
        groupSql.append(getPrefix()).append(".pallet.foil, ").append(getPrefix()).append(".pallet.set_to_transfer, ");
        groupSql.append(getPrefix()).append(".item_master.id");

        sql.append(selectSql.toString()).append(whereSql.toString()).append(groupSql.toString());

        log.debug(sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("PALLET_ID", IntegerType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("PALLET_DATE", DateType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("CAPACITY", BigDecimalType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("COMBINE", IntegerType.INSTANCE)
                    .addScalar("FOIL", IntegerType.INSTANCE)
                    .addScalar("TO_TRANSFER", IntegerType.INSTANCE)
                    .addScalar("ITEM", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                PalletTransferView palletView = new PalletTransferView();
                palletView.setId(Utils.parseInt(entity[0]));
                palletView.setWarehouseCode(Utils.parseString(entity[1]));
                palletView.setItemId(Utils.parseString(entity[2]));
                palletView.setItemDesc(Utils.parseString(entity[3]));
                palletView.setPalletBarcode(Utils.parseString(entity[4]));
                palletView.setCreateDate(Utils.parseDate(entity[5], null));
                palletView.setLocationBarcode(Utils.parseString(entity[6]));
                palletView.setCapacity(Utils.parseBigDecimal(entity[7]));
                palletView.setQty(Utils.parseInt(entity[8]));
                palletView.setIsCombine(Utils.parseInt(entity[9]));
                palletView.setIsFoil(Utils.parseInt(entity[10]));
                palletView.setToTransfer(Utils.parseInt(entity[11]));
                palletView.setItem(Utils.parseInt(entity[12]));
                palletViewList.add(palletView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return palletViewList;
    }

    public List<StockInOutDetailViewReport> findByStokInOutId(int stockInOutId){
        List<StockInOutDetailViewReport> detailViewReportList = new ArrayList<StockInOutDetailViewReport>();
        StringBuilder sql = new StringBuilder();

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT ");
        selectSql.append(" ").append("it.ItemId AS ITEM_ID,");
        selectSql.append(" ").append("it.DSGThaiItemDescription AS ITEM_DESC,");
        selectSql.append(" ").append("p.pallet_barcode AS PALLET_BARCODE,");
        selectSql.append(" ").append("stl.previous_location_id AS PREVIOUS_LOCATION,");
        selectSql.append(" ").append("L1.location_barcode AS LOCATION_BARCODE,");
        selectSql.append(" ").append("p.qty AS QTY,");
        selectSql.append(" ").append("stl.stock_inout_id AS STOCK_INOUT_ID,");
        selectSql.append(" coalesce(").append("it.DSG_InternalItemId, '') AS ITEM_INTERNAL,");
        selectSql.append(" ").append("wh1.warehouse_code AS TO_WH,");
        selectSql.append(" ").append("wh2.warehouse_code as FROM_WH,");
        selectSql.append(" (select TOP 1 ").append("inv_onhand.batchno ");
        selectSql.append(" FROM ").append(getPrefix()).append(".inv_onhand WHERE ").append(getPrefix()).append(".inv_onhand.pallet_id = p.id) AS BATCH_NO");
        selectSql.append(" FROM ").append(getPrefix()).append(".pallet p");
        selectSql.append(" JOIN ").append(getPrefix()).append(".item_master it");
        selectSql.append(" ON ").append("p.item_id = it.id");
        selectSql.append(" JOIN ").append(getPrefix()).append(".location L1");
        selectSql.append(" ON ").append("p.location_id = L1.id");
        selectSql.append(" JOIN ").append(getPrefix()).append(".stock_inout_line stl");
        selectSql.append(" ON ").append("p.id = stl.pallet_id ");
        selectSql.append(" JOIN ").append(getPrefix()).append(".location L2");
        selectSql.append(" ON ").append("stl.location_id = L2.id ");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse wh1");
        selectSql.append(" ON ").append("wh1.id = L1.warehouse_id ");
        selectSql.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse wh2");
        selectSql.append(" ON ").append("wh2.id = L2.warehouse_id ");
        selectSql.append(" WHERE ").append("stl.stock_inout_id = ").append(stockInOutId);

        log.debug(selectSql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectSql.toString())
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("PREVIOUS_LOCATION", IntegerType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("STOCK_INOUT_ID", IntegerType.INSTANCE)
                    .addScalar("ITEM_INTERNAL", StringType.INSTANCE)
                    .addScalar("TO_WH", StringType.INSTANCE)
                    .addScalar("FROM_WH", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                StockInOutDetailViewReport detailViewReport = new StockInOutDetailViewReport();
                detailViewReport.setItemId(Utils.parseString(entity[0]));
                detailViewReport.setItemDesc(Utils.parseString(entity[1]));
                detailViewReport.setPalletBarcode(Utils.parseString(entity[2]));
                detailViewReport.setPreviousLocationId(Utils.parseInt(entity[3]));
                detailViewReport.setLocationBarcode(Utils.parseString(entity[4]));
                detailViewReport.setQty(Utils.parseInt(entity[5]));
                detailViewReport.setStockInOutId(Utils.parseInt(entity[6]));
                detailViewReport.setItemInternal(Utils.parseString(entity[7]));
                detailViewReport.setToWh(Utils.parseString(entity[8]));
                detailViewReport.setFromWh(Utils.parseString(entity[9]));
                detailViewReport.setBatchNo(Utils.parseString(entity[10]));
                detailViewReportList.add(detailViewReport);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return detailViewReportList;
    }
}
