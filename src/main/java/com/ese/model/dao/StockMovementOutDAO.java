package com.ese.model.dao;

import com.ese.model.db.StockMovementOutModel;
import com.ese.model.view.StockMovementOutView;
import com.ese.model.view.report.SubIncomingViewReport;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementOutDAO extends GenericDAO<StockMovementOutModel, Integer>{

    public List<StockMovementOutView> findstockMovementOutByStockInOutId(int stockInOutId){
        log.debug("findstockMovementOutByStockInOutId(). {}", stockInOutId);
        List<StockMovementOutView> stockMovementOutViewViews = new ArrayList<StockMovementOutView>();
        StringBuilder sqlBuilder = new StringBuilder();

//        sqlBuilder.append("SELECT DISTINCT");
//        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.id AS ID,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".item_master.ItemId,i2.ItemId) AS ITEM,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".item_master.DSGThaiItemDescription,i2.DSGThaiItemDescription) AS ITEM_DESC,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".warehouse.warehouse_name,w2.warehouse_name) AS WAREHOUSE,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".location.location_barcode,l2.location_barcode) AS LOCATION,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".inv_buffer.batchno,inv2.batchno) AS BATCH_NO,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".stock_movement_out.pallet_barcode,").append(getPrefix()).append(".pallet.pallet_barcode) AS PALLET_BARCODE,");
//        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".stock_movement_out.sn_barcode,'') AS SN_BARCODE,");
//        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.status AS STATUS");
//
//        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_out");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_buffer");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_out.sn_barcode = ").append(getPrefix()).append(".inv_buffer.sn_barcode");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_buffer.item_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".pallet");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_buffer.pallet_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".warehouse.id = ").append(getPrefix()).append(".pallet.warehouse_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location");
//        sqlBuilder.append(" ON  ").append(getPrefix()).append(".location.id = ").append(getPrefix()).append(".pallet.location_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".pallet p2");
//        sqlBuilder.append(" ON  ").append("p2.pallet_barcode = ").append(getPrefix()).append(".stock_movement_out.pallet_barcode");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_buffer inv2");
//        sqlBuilder.append(" ON  ").append("inv2.pallet_id = ").append("p2.id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse w2");
//        sqlBuilder.append(" ON  ").append("w2.id = ").append("p2.warehouse_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location l2");
//        sqlBuilder.append(" ON  ").append("l2.id = ").append("p2.location_id");
//        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master i2");
//        sqlBuilder.append(" ON  ").append("i2.id = ").append("inv2.item_id");



        sqlBuilder.append("SELECT DISTINCT");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.id AS ID ,");
        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".item_master.ItemId, '') AS ITEM ,");
        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".item_master.DSGThaiItemDescription,'') AS ITEM_DESC ,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_name AS WAREHOUSE ,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION ,");
        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".stock_movement_out.batchno, '') AS BATCH_NO ,");
        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".stock_movement_out.pallet_barcode, '') AS PALLET_BARCODE ,");
        sqlBuilder.append(" COALESCE(").append(getPrefix()).append(".stock_movement_out.sn_barcode, '') AS SN_BARCODE ,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.status AS STATUS ");

        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_out");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_out.item_id = ").append(getPrefix()).append(".item_master.id ");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_out.location_id = ").append(getPrefix()).append(".location.id ");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".location.warehouse_id = ").append(getPrefix()).append(".warehouse.id");

        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_movement_out.stock_inout_id = ").append(stockInOutId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("SN_BARCODE", StringType.INSTANCE)
                    .addScalar("STATUS", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                StockMovementOutView stockMovementOutView = new StockMovementOutView();
                stockMovementOutView.setId(Utils.parseInt(entity[0]));
                stockMovementOutView.setItemId(Utils.parseString(entity[1]));
                stockMovementOutView.setItemDesc(Utils.parseString(entity[2]));
                stockMovementOutView.setWarehouse(Utils.parseString(entity[3]));
                stockMovementOutView.setLocation(Utils.parseString(entity[4]));
                stockMovementOutView.setBatchNo(Utils.parseString(entity[5]));
                stockMovementOutView.setPallet(Utils.parseString(entity[6]));
                stockMovementOutView.setSnBarcode(Utils.parseString(entity[7]));
                stockMovementOutView.setStatus(Utils.parseInt(entity[8]));
                stockMovementOutViewViews.add(stockMovementOutView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return stockMovementOutViewViews;
    }

    public List<SubIncomingViewReport> findSubReportByStickInoutId(int stockInOutId){
        log.debug("findSubReportByStickInoutId(). {}", stockInOutId);
        List<SubIncomingViewReport> subIncomingViewReportsList = new ArrayList<SubIncomingViewReport>();
        StringBuilder sqlBuilder = new StringBuilder();



//        sqlBuilder.append(" select ")
//                .append("  coalesce(inv.sn_barcode, ").append(getPrefix()).append(".inv_buffer.sn_barcode) as SN_BARCODE ")
//                .append(" ,coalesce(").append(getPrefix()).append(".pallet.pallet_barcode, p.pallet_barcode) as PALLET_BARCODE ")
//                .append(" ,coalesce(").append(getPrefix()).append(".item_master.itemid, item.itemid) as ITEM_ID ")
//                .append(" ,coalesce(").append(getPrefix()).append(".item_master.dsgthaiitemdescription, item.dsgthaiitemdescription) as ITEM_DESC ")
//                .append(" ,coalesce(").append(getPrefix()).append(".inv_buffer.batchno, inv.batchno) as BATCH_NO ")
//                .append(" from ").append(getPrefix()).append(".stock_movement_out ")
//                .append(" left join ").append(getPrefix()).append(".inv_buffer on ").append(getPrefix()).append(".stock_movement_out.sn_barcode = ").append(getPrefix()).append(".inv_buffer.sn_barcode ")
//                .append(" left join ").append(getPrefix()).append(".pallet p on ").append(getPrefix()).append(".inv_buffer.pallet_id = p.id ")
//                .append(" left join ").append(getPrefix()).append(".pallet on ").append(getPrefix()).append(".stock_movement_out.pallet_barcode = ").append(getPrefix()).append(".pallet.pallet_barcode ")
//                .append(" left join ").append(getPrefix()).append(".inv_buffer inv on ").append(getPrefix()).append(".pallet.id = inv.pallet_id ")
//                .append(" left join ").append(getPrefix()).append(".item_master on ").append(getPrefix()).append(".inv_buffer.item_id = ").append(getPrefix()).append(".item_master.id ")
//                .append(" left join ").append(getPrefix()).append(".item_master item on inv.item_id = item.id ")
//                .append(" where ")
//                .append(getPrefix()).append(".stock_movement_out.stock_inout_id = ").append(stockInOutId);


        //10/11/2015 by Bird
//        sqlBuilder.append(" select ")
//                .append(" ").append(getPrefix()).append(".stock_movement_out.STOCK_INOUT_ID as STOCK_INOUT_ID, ")
//                .append(" coalesce(").append(getPrefix()).append(".pallet.pallet_barcode,p.pallet_barcode) as PALLET_BARCODE, ")
//                .append(" coalesce(inv.sn_barcode,inv_buffer.sn_barcode) as SN_BARCODE, ")
//                .append(" coalesce(").append(getPrefix()).append(".inv_buffer.batchno,inv.batchno) as BATCH_NO, ")
//                .append(" coalesce(").append(getPrefix()).append(".item_master.itemid,item.itemid) as ITEM_ID, ")
//                .append(" coalesce(").append(getPrefix()).append(".item_master.dsgthaiitemdescription,item.dsgthaiitemdescription) as ITEM_DESC ")
//                .append(" from ").append(getPrefix()).append(".stock_movement_out ")
//                .append(" left join ").append(getPrefix()).append(".inv_buffer on ")
//                .append(getPrefix()).append(".stock_movement_out.sn_barcode = ").append(getPrefix()).append(".inv_buffer.sn_barcode ")
//                .append(" left join ").append(getPrefix()).append(".pallet p on ")
//                .append(getPrefix()).append(".inv_buffer.pallet_id = p.id ")
//                .append(" left join ").append(getPrefix()).append(".pallet on ")
//                .append(getPrefix()).append(".stock_movement_out.pallet_barcode = ").append(getPrefix()).append(".pallet.pallet_barcode ")
//                .append(" left join ").append(getPrefix()).append(".inv_buffer inv on ")
//                .append(getPrefix()).append(".pallet.id = inv.pallet_id ")
//                .append(" left join ").append(getPrefix()).append(".item_master on ")
//                .append(getPrefix()).append(".inv_buffer.item_id = ").append(getPrefix()).append(".item_master.id ")
//                .append(" left join ").append(getPrefix()).append(".item_master item on inv.item_id = item.id ")
//                .append(" where ").append(getPrefix()).append(".stock_movement_out.stock_inout_id = ").append(stockInOutId);

        sqlBuilder.append(" SELECT ")
                .append(" ").append(getPrefix()).append(".inv_movement_out_buffer.stock_inout_id AS STOCK_INOUT_ID, ")
                .append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE, ")
                .append(" ''  AS SN_BARCODE, ")
                .append(" ").append(getPrefix()).append(".inv_movement_out_buffer.batchno AS BATCH_NO, ")
                .append(" ").append(getPrefix()).append(".item_master.ItemId  AS ITEM_ID, ")
                .append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC, ")
                .append(" ").append(getPrefix()).append(".item_master.DSG_InternalItemId  AS ITEM_INTERNAL, ")
                .append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE, ")
                .append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION, ")
                .append(" COUNT(").append(getPrefix()).append(".inv_movement_out_buffer.id) AS QTY ")
                .append(" FROM ").append(getPrefix()).append(".inv_movement_out_buffer ")
                .append(" INNER JOIN ").append(getPrefix()).append(".pallet ")
                .append(" ON ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_movement_out_buffer.pallet_id ")
                .append(" INNER JOIN ").append(getPrefix()).append(".location ")
                .append(" ON ").append(getPrefix()).append(".inv_movement_out_buffer.location_id = ").append(getPrefix()).append(".location.id ")
                .append(" INNER JOIN ").append(getPrefix()).append(".warehouse ")
                .append(" ON ").append(getPrefix()).append(".warehouse.id = ").append(getPrefix()).append(".location.warehouse_id ")
                .append(" INNER JOIN ").append(getPrefix()).append(".item_master ")
                .append(" ON ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_movement_out_buffer.item_id ")
                .append(" WHERE ").append(getPrefix()).append(".inv_movement_out_buffer.stock_inout_id = ").append(stockInOutId)
                .append(" GROUP BY ").append(getPrefix()).append(".item_master.DSG_InternalItemId, ").append(getPrefix()).append(".item_master.ItemId, ")
                .append(getPrefix()).append(".item_master.DSGThaiItemDescription, ").append(getPrefix()).append(".pallet.pallet_barcode, ")
                .append(getPrefix()).append(".warehouse.warehouse_code, ").append(getPrefix()).append(".location.location_barcode, ")
                .append(getPrefix()).append(".inv_movement_out_buffer.batchno, ").append(getPrefix()).append(".inv_movement_out_buffer.stock_inout_id ")
                .append(" ORDER BY ").append(getPrefix()).append(".item_master.DSG_InternalItemId, ").append(getPrefix()).append(".inv_movement_out_buffer.batchno");



        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("STOCK_INOUT_ID", IntegerType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("SN_BARCODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("ITEM_INTERNAL", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE);
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
                subIncomingViewReport.setItemInternal(Utils.parseString(entity[6]));
                subIncomingViewReport.setWarehouseBarcode(Utils.parseString(entity[7]));
                subIncomingViewReport.setLocationBarcode(Utils.parseString(entity[8]));
                subIncomingViewReport.setQty(Utils.parseInt(entity[9]));
                subIncomingViewReportsList.add(subIncomingViewReport);
                i++;
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return subIncomingViewReportsList;
    }
}