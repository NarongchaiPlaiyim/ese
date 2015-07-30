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

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.itemid AS ITEM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_name AS WAREHOUSE, ");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_name AS LOCATION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.batchno AS BATCH_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.sn_barcode AS SN_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.status AS STATUS");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_out");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_out.sn_barcode = ").append(getPrefix()).append(".inv_onhand.sn_barcode");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".inv_onhand.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".inv_onhand.pallet_id = ").append(getPrefix()).append(".pallet.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
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

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.stock_inout_id AS STOCK_INOUT_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.pallet_barcode AS PALLET_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.sn_barcode AS SN_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_out.batchno AS BATCH_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_out");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_out.sn_barcode = ").append(getPrefix()).append(".inv_onhand.sn_barcode");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_onhand.item_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_movement_out.stock_inout_id = ").append(stockInOutId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("STOCK_INOUT_ID", IntegerType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("SN_BARCODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE);
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
                subIncomingViewReportsList.add(subIncomingViewReport);
                i++;
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return subIncomingViewReportsList;
    }
}
