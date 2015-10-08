package com.ese.model.dao;

import com.ese.model.db.StockMovementInModel;
import com.ese.model.view.StockMovementInView;
import com.ese.model.view.report.SubIncomingViewReport;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementInDAO extends GenericDAO<StockMovementInModel, Integer>{

    public List<StockMovementInView> findstockMovementOutByStockInOutId(int stockInOutId){
        log.debug("findstockMovementOutByStockInOutId(). {}", stockInOutId);
        List<StockMovementInView> stockMovementInViewViews = new ArrayList<StockMovementInView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.itemid AS ITEM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.sn_barcode AS BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.status AS STATUS ");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_in");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_in.sn_barcode = ").append(getPrefix()).append(".inv_onhand.sn_barcode");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_onhand.item_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_movement_in.stock_inout_id = ").append(stockInOutId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("BARCODE", StringType.INSTANCE)
                    .addScalar("STATUS", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                StockMovementInView stockMovementInView = new StockMovementInView();
                stockMovementInView.setId(Utils.parseInt(entity[0]));
                stockMovementInView.setItemId(Utils.parseString(entity[1]));
                stockMovementInView.setItemDesc(Utils.parseString(entity[2]));
                stockMovementInView.setBarcode(Utils.parseString(entity[3]));
                stockMovementInView.setStatus(Utils.parseInt(entity[4]));
                stockMovementInViewViews.add(stockMovementInView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return stockMovementInViewViews;
    }

    public List<SubIncomingViewReport> findSubReportByStickInoutId(int stockInOutId){
        log.debug("findSubReportByStickInoutId(). {}", stockInOutId);
        List<SubIncomingViewReport> subIncomingViewReportsList = new ArrayList<SubIncomingViewReport>();
        StringBuilder sqlBuilder = new StringBuilder();

//        sqlBuilder.append("SELECT ");
//        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.stock_inout_id AS STOCK_INOUT_ID,");
//
//        sqlBuilder.append(" coalesce(").append(getPrefix()).append(".stock_movement_in.pallet_barcode,'') AS PALLET_BARCODE,");
//        sqlBuilder.append(" coalesce(").append(getPrefix()).append(".stock_movement_in.sn_barcode,'') AS SN_BARCODE,");
//        sqlBuilder.append(" coalesce(").append(getPrefix()).append(".inv_onhand.batchno,'') AS BATCH_NO,");
//
//
//        //sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.pallet_barcode AS PALLET_BARCODE,");
//        //sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.sn_barcode AS SN_BARCODE,");
//        //sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.batchno AS BATCH_NO,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
//        sqlBuilder.append(" coalesce(").append(getPrefix()).append(".item_master.DSG_InternalItemId,'') AS ITEM_INTERNAL ,");
//
//        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
//        sqlBuilder.append(" coalesce(").append("p2.qty,1) AS QTY");
//
//
//        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_in");
//        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".inv_onhand");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_in.sn_barcode = ").append(getPrefix()).append(".inv_onhand.sn_barcode");
//        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_onhand.item_id");
//
//        //pongwisit<
//        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".pallet");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_onhand.pallet_id");
//        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".warehouse.id = ").append(getPrefix()).append(".pallet.warehouse_id");
//        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".location.id = ").append(getPrefix()).append(".pallet.location_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".pallet p2");
//        sqlBuilder.append(" ON  ").append("p2.pallet_barcode = ").append(getPrefix()).append(".stock_movement_in.pallet_barcode");
//
//        //>pongwisit
//
//        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_movement_in.stock_inout_id = ").append(stockInOutId);




        sqlBuilder.append(" SELECT ")
                .append(getPrefix()).append(".stock_movement_in.stock_inout_id AS STOCK_INOUT_ID, ")
                .append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE, ")
                .append(getPrefix()).append(".stock_movement_in.sn_barcode AS SN_BARCODE, ")
                .append(getPrefix()).append(".inv_buffer.batchno AS BATCH_NO, ")
                .append(getPrefix()).append(".item_master.ItemId AS ITEM_ID, ")
                .append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC ")
                .append(" FROM ")
                .append(getPrefix()).append(".stock_movement_in INNER JOIN ")
                .append(getPrefix()).append(".inv_buffer ON ")
                .append(getPrefix()).append(".stock_movement_in.sn_barcode = ")
                .append(getPrefix()).append(".inv_buffer.sn_barcode ")
                .append(" INNER JOIN ")
                .append(getPrefix()).append(".item_master ON ")
                .append(getPrefix()).append(".inv_buffer.item_id = ")
                .append(getPrefix()).append(".item_master.id INNER JOIN ")
                .append(getPrefix()).append(".pallet ON ")
                .append(getPrefix()).append(".inv_buffer.pallet_id = ")
                .append(getPrefix()).append(".pallet.id ")
                .append(" where ")
                .append(getPrefix()).append(".stock_movement_in.stock_inout_id = ").append(stockInOutId);


        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("STOCK_INOUT_ID", IntegerType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("SN_BARCODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE);
//                    .addScalar("ITEM_INTERNAL", StringType.INSTANCE)
//                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
//                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
//                    .addScalar("QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                SubIncomingViewReport subIncomingViewReport = new SubIncomingViewReport();
                subIncomingViewReport.setNo(i);
                subIncomingViewReport.setStockInoutId(Utils.parseInt(entity[0]));
                subIncomingViewReport.setPalletBarcode(Utils.parseString(entity[1]));
                subIncomingViewReport.setSnBarcode(Utils.parseString(entity[2]));
                subIncomingViewReport.setBatchNo(Utils.parseString(entity[3]));
                subIncomingViewReport.setItemNo(Utils.parseString(entity[4]));
                subIncomingViewReport.setItemDesc(Utils.parseString(entity[5]));
//                subIncomingViewReport.setItemInternal(Utils.parseString(entity[6]));
//                subIncomingViewReport.setWarehouseBarcode(Utils.parseString(entity[7]));
//                subIncomingViewReport.setLocationBarcode(Utils.parseString(entity[8]));
//                subIncomingViewReport.setQty(Utils.parseInt(entity[9]));
                subIncomingViewReportsList.add(subIncomingViewReport);
                i++;
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return subIncomingViewReportsList;
    }
}
