package com.ese.model.dao;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.view.FIFOReservedView;
import com.ese.model.view.LocationQtyView;
import com.ese.model.view.PickingOrderShowItemView;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PickingOrderLineDAO extends GenericDAO<PickingOrderLineModel, Integer> {

    public List<PickingOrderShowItemView> findByPickingOrderId(int pickingOrderId){
        List<PickingOrderShowItemView> showItemViews = new ArrayList<PickingOrderShowItemView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS DESCRIPTION,");
        sqlBuilder.append(" SUM(").append(getPrefix()).append(".picking_order_line.qty) AS ORDER_QTY,");
        sqlBuilder.append(" SUM(").append(getPrefix()).append(".reserved_order.reserved_qty) AS RESERVED_QTY,");
        sqlBuilder.append(" (CASE WHEN SUM(").append(getPrefix()).append(".reserved_order.picked_qty) = 0 THEN 0 ELSE ");
        sqlBuilder.append(" (CASE WHEN SUM(").append(getPrefix()).append(".reserved_order.reserved_qty) = 0 THEN 0 ELSE ");
        sqlBuilder.append(" SUM(").append(getPrefix()).append(".reserved_order.reserved_qty) END) / ");
        sqlBuilder.append(" (CASE WHEN SUM(").append(getPrefix()).append(".reserved_order.picked_qty) IS NULL THEN 0 ELSE");
        sqlBuilder.append(" SUM(").append(getPrefix()).append(".reserved_order.picked_qty) END) *  100 END) AS PER_PICKED,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.isfoil AS FOIL,");
        sqlBuilder.append(" CASE WHEN SUM(").append(getPrefix()).append(".reserved_order.foil_qty) IS NULL THEN 0 ELSE");
        sqlBuilder.append(" SUM(").append(getPrefix()).append(".reserved_order.foil_qty) END AS FOIL_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".mst_status.caption AS STATUS,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.status AS STATUS_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.qty AS QTY, ");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemName AS ITEM_NAME");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".reserved_order");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".picking_order_line.id = ").append(getPrefix()).append(".reserved_order.picking_order_line_id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".mst_status");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.status = ").append(getPrefix()).append(".mst_status.status_seq");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".mst_status.table_id = 1");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order_line.picking_order_id = " ).append(pickingOrderId);
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".picking_order_line.id, ").append(getPrefix()).append(".picking_order_line.ItemId, ");
        sqlBuilder.append(getPrefix()).append(".item_master.DSGThaiItemDescription, ").append(getPrefix()).append(".picking_order_line.isfoil, ");
        sqlBuilder.append(getPrefix()).append(".mst_status.caption, ").append(getPrefix()).append(".picking_order_line.status, ");
        sqlBuilder.append(getPrefix()).append(".picking_order_line.qty, ").append(getPrefix()).append(".item_master.ItemName");

        log.debug("findByPickingOrderId : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM", StringType.INSTANCE)
                    .addScalar("DESCRIPTION", StringType.INSTANCE)
                    .addScalar("ORDER_QTY", IntegerType.INSTANCE)
                    .addScalar("RESERVED_QTY", IntegerType.INSTANCE)
                    .addScalar("PER_PICKED", BigDecimalType.INSTANCE)
                    .addScalar("FOIL", IntegerType.INSTANCE)
                    .addScalar("FOIL_QTY", BigDecimalType.INSTANCE)
                    .addScalar("STATUS", StringType.INSTANCE)
                    .addScalar("STATUS_ID", IntegerType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("ITEM_NAME", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                PickingOrderShowItemView pickingOrderShowItemView = new PickingOrderShowItemView();
                pickingOrderShowItemView.setId(Utils.parseInt(entity[0]));
                pickingOrderShowItemView.setItem(Utils.parseString(entity[1]));
                pickingOrderShowItemView.setDescription(Utils.parseString(entity[2]));
                pickingOrderShowItemView.setOrderQty(Utils.parseInt(entity[3]));
                pickingOrderShowItemView.setReservedQty(Utils.parseInt(entity[4]));
                pickingOrderShowItemView.setPerPicked(Utils.parseBigDecimal(entity[5]));
                pickingOrderShowItemView.setFoil(Utils.parseInt(entity[6]));
                pickingOrderShowItemView.setFoilQty(Utils.parseBigDecimal(entity[7]));
                pickingOrderShowItemView.setStatus(Utils.parseString(entity[8]));
                pickingOrderShowItemView.setStatusID(Utils.parseInt(entity[9]));
                pickingOrderShowItemView.setQty(Utils.parseInt(entity[10]));
                pickingOrderShowItemView.setItemName(Utils.parseString(entity[11]));
                showItemViews.add(pickingOrderShowItemView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByPickingOrderId : {}", e);
        }

        return showItemViews;
    }

    public void updateToUnWrap(int pickingLineId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order_line SET ").append(getPrefix()).append(".picking_order_line.isfoil = 0 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order_line.id = ").append("'").append(pickingLineId).append("'");

        log.debug("SQL updateToWrap : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public void updateToWrap(int pickingLineId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order_line SET ").append(getPrefix()).append(".picking_order_line.isfoil = 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order_line.id = ").append("'").append(pickingLineId).append("'");

        log.debug("SQL updateToWrap : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public FIFOReservedView findQtyOnInventTran(int pickingOrderLineId){
        FIFOReservedView fifoReservedView = new FIFOReservedView();
        StringBuilder selectInventTrans = new StringBuilder();

        selectInventTrans.append(" SELECT ");
        selectInventTrans.append(" ").append(getPrefix()).append(".picking_order_line.id AS PICKING_ORDER_LINE_ID,");
        selectInventTrans.append(" ").append(getPrefix()).append(".picking_order.sales_order AS SALES_ID,");
        selectInventTrans.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM_ID,");
        selectInventTrans.append(" ").append(getPrefix()).append(".ax_InventTrans.id AS INVENTTRANS_ID,");
        selectInventTrans.append(" COALESCE(").append(getPrefix()).append(".ax_InventTrans.qty, 0) AS INVENTTRANS_QTY,");
        selectInventTrans.append(" SUM(COALESCE(").append(getPrefix()).append(".picking_order_line.qty, 0)) AS PICKING_LINE_QTY ");
        selectInventTrans.append(" FROM ").append(getPrefix()).append(".picking_order_line");
        selectInventTrans.append(" LEFT JOIN ").append(getPrefix()).append(".picking_order");
        selectInventTrans.append(" ON  ").append(getPrefix()).append(".picking_order_line.picking_order_id = ").append(getPrefix()).append(".picking_order.id");
        selectInventTrans.append(" LEFT JOIN ").append(getPrefix()).append(".ax_InventTrans");
        selectInventTrans.append(" ON ").append(getPrefix()).append(".picking_order.sales_order = ").append(getPrefix()).append(".ax_InventTrans.transrefid");
        selectInventTrans.append(" AND ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".ax_InventTrans.itemid");
        selectInventTrans.append(" WHERE ").append(getPrefix()).append(".picking_order_line.id = " ).append(pickingOrderLineId);
        selectInventTrans.append(" GROUP BY ").append(getPrefix()).append(".picking_order_line.id, ").append(getPrefix()).append(".picking_order.sales_order, ");
        selectInventTrans.append(getPrefix()).append(".picking_order_line.ItemId, ").append(getPrefix()).append(".ax_InventTrans.id, ");
        selectInventTrans.append(getPrefix()).append(".ax_InventTrans.qty");

        log.debug("findQtyOnInventTran : {}", selectInventTrans.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectInventTrans.toString())
                    .addScalar("PICKING_ORDER_LINE_ID", IntegerType.INSTANCE)
                    .addScalar("SALES_ID", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("INVENTTRANS_ID", IntegerType.INSTANCE)
                    .addScalar("INVENTTRANS_QTY", IntegerType.INSTANCE)
                    .addScalar("PICKING_LINE_QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                fifoReservedView.setPickingOrderLineId(Utils.parseInt(entity[0]));
                fifoReservedView.setSalesId(Utils.parseString(entity[1]));
                fifoReservedView.setItemId(Utils.parseString(entity[2]));
                fifoReservedView.setInventtransId(Utils.parseInt(entity[3]));
                fifoReservedView.setInventtransQty(Utils.parseInt(entity[4]));
                fifoReservedView.setPickingLineQty(Utils.parseInt(entity[5]));
            }
        } catch (Exception e) {
            log.debug("Exception SQL findQtyOnInventTran : {}", e);
        }
        log.debug("fifoReservedView : {}", fifoReservedView.toString());

        return fifoReservedView;
    }

    public void updateInventTransByUse(int inventransId){
        StringBuilder updateInventTrans = new StringBuilder();
        updateInventTrans.append(" UPDATE ").append(getPrefix()).append(".ax_InventTrans SET ").append(getPrefix()).append(".ax_InventTrans.status = 2 ");
        updateInventTrans.append(" WHERE ").append(getPrefix()).append(".ax_InventTrans.id = ").append("'").append(inventransId).append("'");

        log.debug("SQL updateInventTransByUse : {}", updateInventTrans.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(updateInventTrans.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateInventTransByUse: ", e);
        }
    }

    public void updateInventTransByUseFinish(int inventransId){
        StringBuilder updateInventTrans = new StringBuilder();
        updateInventTrans.append(" UPDATE ").append(getPrefix()).append(".ax_InventTrans SET ").append(getPrefix()).append(".ax_InventTrans.status = 1 ");
        updateInventTrans.append(" WHERE ").append(getPrefix()).append(".ax_InventTrans.id = ").append("'").append(inventransId).append("'");

        log.debug("SQL updateInventTransByUseFinish : {}", updateInventTrans.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(updateInventTrans.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateInventTransByUseFinish: ", e);
        }
    }

    public List<LocationQtyView> findByItemId(String itemId, String startBtach, String toBatch, int warehouseId, int locationId, int locationQtyId){
        List<LocationQtyView> locationQtyViewList = new ArrayList<LocationQtyView>();

        StringBuilder selectLocationQty = new StringBuilder();

        selectLocationQty.append(" SELECT ");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.id AS ID,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.item_master_id AS ITEM_ID,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.location_id AS LOCATION_ID,");
        selectLocationQty.append(" (CASE WHEN ").append(getPrefix()).append(".location_qty.qty IS NULL THEN 0 ELSE ");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.qty END) -");
        selectLocationQty.append(" (CASE WHEN ").append(getPrefix()).append(".location_qty.reserved_qty IS NULL THEN 0 ELSE");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.reserved_qty END) AS AVAILABLE,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.qty AS QTY,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.reserved_qty AS RESERVED_QTY,");
        selectLocationQty.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.batchno AS BATCH_NO,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_NAME,");
        selectLocationQty.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS DESCRIPTION");
        selectLocationQty.append(" FROM ").append(getPrefix()).append(".location_qty");
        selectLocationQty.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        selectLocationQty.append(" ON  ").append(getPrefix()).append(".location_qty.item_master_id = ").append(getPrefix()).append(".item_master.id");
        selectLocationQty.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        selectLocationQty.append(" ON  ").append(getPrefix()).append(".location_qty.location_id = ").append(getPrefix()).append(".location.id");
        selectLocationQty.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        selectLocationQty.append(" ON  ").append(getPrefix()).append(".location.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        selectLocationQty.append(" WHERE ").append(getPrefix()).append(".item_master.ItemId = '" ).append(itemId).append("'");
        selectLocationQty.append(" AND ").append(getPrefix()).append(".location_qty.qty - " ).append(getPrefix()).append(".location_qty.reserved_qty <> 0");

        if (!Utils.isZero(startBtach.trim().length()) && !Utils.isZero(toBatch.trim().length())){
            selectLocationQty.append(" AND ").append(getPrefix()).append(".location_qty.batchno >= '" ).append(startBtach).append("'");
            selectLocationQty.append(" AND ").append(getPrefix()).append(".location_qty.batchno <= '" ).append(toBatch).append("'");
        }

        if (!Utils.isZero(warehouseId)){
            selectLocationQty.append(" AND warehouse_id = ").append(warehouseId);
        }

        if (!Utils.isZero(locationId)){
            selectLocationQty.append(" AND location.id = ").append(locationId);
        }

        if (!Utils.isZero(locationQtyId)){
            selectLocationQty.append(" AND location_qty.id = ").append(locationQtyId);
        }

        selectLocationQty.append(" ORDER BY ").append(getPrefix()).append(".location_qty.batchno ASC");

        log.debug("findByItemId : {}", selectLocationQty.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectLocationQty.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("LOCATION_ID", IntegerType.INSTANCE)
                    .addScalar("AVAILABLE", IntegerType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("RESERVED_QTY", IntegerType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("LOCATION_NAME", StringType.INSTANCE)
                    .addScalar("DESCRIPTION", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                LocationQtyView locationQtyView = new LocationQtyView();
                locationQtyView.setId(Utils.parseInt(entity[0]));
                locationQtyView.setItemId(Utils.parseString(entity[1]));
                locationQtyView.setLocationId(Utils.parseInt(entity[2]));
                locationQtyView.setAvailable(Utils.parseInt(entity[3]));
                locationQtyView.setQty(Utils.parseInt(entity[4]));
                locationQtyView.setReservedQty(Utils.parseInt(entity[5]));
                locationQtyView.setWarehouseCode(Utils.parseString(entity[6]));
                locationQtyView.setBatchNo(Utils.parseString(entity[7]));
                locationQtyView.setLocationName(Utils.parseString(entity[8]));
                locationQtyView.setDescription(Utils.parseString(entity[9]));
                locationQtyViewList.add(locationQtyView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByItemId: {}", e);
        }

        log.debug("locationQtyViewList Size : {}", locationQtyViewList.size());

        return locationQtyViewList;
    }


    public void updateReservedQtyByLocaitonQtyId(int locationQtyId, int reservedQty){
        StringBuilder updateLocationQty = new StringBuilder();
        updateLocationQty.append(" UPDATE ").append(getPrefix()).append(".location_qty SET ").append(getPrefix()).append(".location_qty.reserved_qty += ").append(reservedQty);
        updateLocationQty.append(" WHERE ").append(getPrefix()).append(".location_qty.id = ").append("").append(locationQtyId);

        log.debug("SQL updateReservedQtyByLocaitonQtyId : {}", updateLocationQty.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(updateLocationQty.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateReservedQtyByLocaitonQtyId: ", e);
        }
    }

    public  List<LocationQtyView> findByLocationId(int locationId){
        List<LocationQtyView> locationQtyViewList = new ArrayList<LocationQtyView>();

        StringBuilder selectLocationQty = new StringBuilder();

        selectLocationQty.append(" SELECT ");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.id AS ID,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.batchno AS BATCH_NO");
        selectLocationQty.append(" FROM ").append(getPrefix()).append(".location_qty");
        selectLocationQty.append(" WHERE ").append(getPrefix()).append(".location_qty.location_id = '" ).append(locationId).append("'");

        log.debug("findByLocationId : {}", selectLocationQty.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectLocationQty.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                LocationQtyView locationQtyView = new LocationQtyView();
                locationQtyView.setId(Utils.parseInt(entity[0]));
                locationQtyView.setBatchNo(Utils.parseString(entity[1]));
                locationQtyViewList.add(locationQtyView);
            }
        } catch (Exception e) {
            log.debug("Exception findByLocationId SQL : {}", e);
        }

        log.debug("locationQtyViewList Size : {}", locationQtyViewList.size());

        return locationQtyViewList;
    }

    public void updateOrderQty(int pickingLineId, int orderQty){
        StringBuilder updateOrderQty = new StringBuilder();
        updateOrderQty.append(" UPDATE ").append(getPrefix()).append(".picking_order_line SET ").append(getPrefix()).append(".picking_order_line.qty = ").append(orderQty);
        updateOrderQty.append(" WHERE ").append(getPrefix()).append(".picking_order_line.id = ").append(pickingLineId);

        log.debug("SQL updateOrderQty : {}", updateOrderQty.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(updateOrderQty.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateOrderQty: ", e);
        }
    }

    public LocationQtyView findLocationQtyByRemoveShowItem(int locationId, String batchNo, int itemId){
        LocationQtyView locationQtyView = new LocationQtyView();

        StringBuilder selectLocationQty = new StringBuilder();

        selectLocationQty.append(" SELECT ");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.id AS ID,");
        selectLocationQty.append(" ").append(getPrefix()).append(".location_qty.reserved_qty AS RESERVED_QTY");
        selectLocationQty.append(" FROM ").append(getPrefix()).append(".location_qty");
        selectLocationQty.append(" WHERE ").append(getPrefix()).append(".location_qty.location_id = " ).append(locationId);
        selectLocationQty.append(" AND ").append(getPrefix()).append(".location_qty.batchno = '" ).append(batchNo).append("'");
        selectLocationQty.append(" AND ").append(getPrefix()).append(".location_qty.item_master_id = " ).append(itemId);

        log.debug("findByLocationId : {}", selectLocationQty.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectLocationQty.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("RESERVED_QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                locationQtyView.setId(Utils.parseInt(entity[0]));
                locationQtyView.setReservedQty(Utils.parseInt(entity[1]));
            }
        } catch (Exception e) {
            log.debug("Exception findByLocationId SQL : {}", e);
        }

        log.debug("locationQtyViewList Size : {}", locationQtyView.toString());

        return locationQtyView;
    }

    public void updateLocationQtyByRemoveShowItem(int locationQtyId, int updateValue){
        StringBuilder updateOrderQty = new StringBuilder();
        updateOrderQty.append(" UPDATE ").append(getPrefix()).append(".location_qty SET ").append(getPrefix()).append(".location_qty.reserved_qty = ").append(updateValue);
        updateOrderQty.append(" WHERE ").append(getPrefix()).append(".location_qty.id = ").append(locationQtyId);

        log.debug("SQL updateOrderQty : {}", updateOrderQty.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(updateOrderQty.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateOrderQty: ", e);
        }
    }
}
