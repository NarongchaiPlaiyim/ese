package com.ese.model.dao;

import com.ese.model.view.*;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryOnhandReportDAO extends GenericDAO<InventoryOnhandReportView, Integer>{
    private int zero = 0;

    public List<InventoryOnhandReportView> findInventOnhandReport(){
        List<InventoryOnhandReportView> reportViewList = new ArrayList<InventoryOnhandReportView>();
        StringBuilder sqlReport = new StringBuilder();

        sqlReport.append(" SELECT DISTINCT");
        sqlReport.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_CODE,");
        sqlReport.append(" ").append(getPrefix()).append(".location_qty.batchno AS BATCH_NO,");
        sqlReport.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION,");
        sqlReport.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE,");

        sqlReport.append(" (CASE WHEN ").append(getPrefix()).append(".location_qty.qty - ").append(getPrefix()).append(".location_qty.reserved_qty - ")
                .append(getPrefix()).append(".location_qty.picked_qty IS NULL THEN 0 ELSE ").append(getPrefix()).append(".location_qty.qty - ")
                .append(getPrefix()).append(".location_qty.reserved_qty - ").append(getPrefix()).append(".location_qty.picked_qty END) AS AVALIABLE,");

        sqlReport.append(" (CASE WHEN ").append(getPrefix()).append(".location_qty.reserved_qty IS NULL THEN 0 ELSE ")
                .append(getPrefix()).append(".location_qty.reserved_qty END) AS RESERVED_QTY,");

        sqlReport.append(" (CASE WHEN ").append(getPrefix()).append(".location_qty.picked_qty IS NULL THEN 0 ELSE ")
                .append(getPrefix()).append(".location_qty.picked_qty END) AS PICKED_QTY,");

        sqlReport.append(" (CASE WHEN ").append(getPrefix()).append(".inv_onhand.cost IS NULL THEN 0 ELSE ")
                .append(getPrefix()).append(".inv_onhand.cost END) AS COST");

//        sqlReport.append(" (CASE WHEN (SELECT DISTINCT ").append(getPrefix()).append(".inv_onhand.cost FROM ")
//                .append(getPrefix()).append(".inv_onhand ").append(" WHERE ").append(getPrefix()).append(".inv_onhand.location_id = ")
//                .append(getPrefix()).append(".location_qty.location_id AND ").append(getPrefix())
//                .append(".inv_onhand.batchno = ").append(getPrefix()).append(".location_qty.batchno) IS NULL THEN 0 ELSE (SELECT DISTINCT ")
//                .append(getPrefix()).append(".inv_onhand.cost FROM ").append(getPrefix()).append(".inv_onhand WHERE ")
//                .append(getPrefix()).append(".inv_onhand.location_id = ").append(getPrefix()).append(".location_qty.location_id AND ")
//                .append(getPrefix()).append(".inv_onhand.batchno = ").append(getPrefix()).append(".location_qty.batchno) END) AS COST ");

        sqlReport.append(" FROM ").append(getPrefix()).append(".location_qty");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlReport.append(" ON ").append(getPrefix()).append(".location_qty.item_master_id = ").append(getPrefix()).append(".item_master.id");
//        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".location_items");
//        sqlReport.append(" ON ").append(getPrefix()).append(".location_qty.item_master_id = ").append(getPrefix()).append(".location_items.item_id");
//        sqlReport.append(" AND ").append(getPrefix()).append(".location_qty.location_id = ").append(getPrefix()).append(".location_items.location_id");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        sqlReport.append(" ON ").append(getPrefix()).append(".location_qty.location_id = ").append(getPrefix()).append(".location.id");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        sqlReport.append(" ON ").append(getPrefix()).append(".location.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlReport.append(" ON ").append(getPrefix()).append(".inv_onhand.location_id = ").append(getPrefix()).append(".location_qty.location_id ");
        sqlReport.append(" AND ").append(getPrefix()).append(".inv_onhand.batchno = ").append(getPrefix()).append(".location_qty.batchno ");
        sqlReport.append(" GROUP BY ").append(getPrefix()).append(".item_master.ItemId, ").append(getPrefix()).append(".location_qty.batchno, ");
        sqlReport.append(getPrefix()).append(".location.location_barcode, ").append(getPrefix()).append(".warehouse.warehouse_code, ");
        sqlReport.append(getPrefix()).append(".location_qty.reserved_qty, ").append(getPrefix()).append(".location_qty.picked_qty, ");
        sqlReport.append(getPrefix()).append(".location_qty.qty, ").append(getPrefix()).append(".inv_onhand.cost");

        log.debug(sqlReport.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlReport.toString())
                    .addScalar("ITEM_CODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("AVALIABLE", IntegerType.INSTANCE)
                    .addScalar("RESERVED_QTY", IntegerType.INSTANCE)
                    .addScalar("PICKED_QTY", IntegerType.INSTANCE)
                    .addScalar("COST", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects){
                InventoryOnhandReportView reportView = new InventoryOnhandReportView();
                AvailivbleView availivbleView = new AvailivbleView();
                ReservedView reservedView = new ReservedView();
                PickView pickView = new PickView();
//                PackView packView = new PackView();
                PhysicalView physicalView = new PhysicalView();

                reportView.setItemCode(Utils.parseString(entity[0]));
                reportView.setBatchNo(Utils.parseString(entity[1]));
                reportView.setLocationBarcode(Utils.parseString(entity[2]));
                reportView.setWarehouseName(Utils.parseString(entity[3]));

                availivbleView.setQty(Utils.parseInt(entity[4]));
                availivbleView.setAmount(Utils.multiply(Utils.parseInt(entity[4]), Utils.parseInt(entity[7])));

                reservedView.setQty(Utils.parseInt(entity[5]));
                reservedView.setAmount(Utils.multiply(Utils.parseInt(entity[5]), Utils.parseInt(entity[7])));

                pickView.setQty(Utils.parseInt(entity[6]));
                pickView.setAmount(Utils.multiply(Utils.parseInt(entity[6]), Utils.parseInt(entity[7])));

                physicalView.setQty(Utils.parseInt(entity[4]) + Utils.parseInt(entity[5]) + Utils.parseInt(entity[6]));
                physicalView.setAmount(Utils.multiply(Utils.parseInt(entity[4]) + Utils.parseInt(entity[5]) + Utils.parseInt(entity[6]), Utils.parseInt(entity[7])));

                reportView.setAvailableView(availivbleView);
                reportView.setReservedView(reservedView);
                reportView.setPickView(pickView);
                reportView.setPhysicalView(physicalView);

//                if (Utils.parseInt(entity[6]) == 2){
//                    availivbleView.setQty(Utils.parseInt(entity[4]));
//                    availivbleView.setAmount(Utils.parseInt(entity[5]));
//                    physicalView.setQty(Utils.parseInt(entity[4]));
//                    physicalView.setAmount(Utils.parseInt(entity[5]));
//                } else if (Utils.parseInt(entity[6]) == 3){
//                    reservedView.setQty(Utils.parseInt(entity[4]));
//                    reservedView.setAmount(Utils.parseInt(entity[5]));
//                    physicalView.setQty(Utils.parseInt(entity[4]));
//                    physicalView.setAmount(Utils.parseInt(entity[5]));
//                } else if (Utils.parseInt(entity[6]) == 4){
//                    pickView.setQty(Utils.parseInt(entity[4]));
//                    pickView.setAmount(Utils.parseInt(entity[5]));
//                    physicalView.setQty(Utils.parseInt(entity[4]));
//                    physicalView.setAmount(Utils.parseInt(entity[5]));
//                } else if (Utils.parseInt(entity[6]) == 5){
//                    packView.setQty(Utils.parseInt(entity[4]));
//                    packView.setAmount(Utils.parseInt(entity[5]));
//                }
//
//                reportView.setAvailableView(availivbleView);
//                reportView.setReservedView(reservedView);
//                reportView.setPickView(pickView);
//                reportView.setPackView(packView);
//                reportView.setPhysicalView(physicalView);

                reportViewList.add(reportView);
            }

        } catch (Exception e) {
            log.debug("Exception errror findInventOnhandReport : ", e);
        }

        return reportViewList;
    }
}
