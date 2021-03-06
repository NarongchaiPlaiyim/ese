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
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.ItemId AS ITEM_CODE,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.item_description AS ITEM_DESC,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.batchno AS BATCH_NO,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.location_barcode AS LOCATION,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.warehouse_code AS WAREHOUSE,");

        sqlReport.append(" (SELECT COUNT( ").append(getPrefix()).append(".inv_onhand.id) FROM ").append(getPrefix()).append(".inv_onhand WHERE  ")
                .append(getPrefix()).append(".inv_onhand.status < 6 AND ").append(getPrefix()).append(".inv_onhand.location_id = ").append(getPrefix()).append(".inv_onhand_view.location_id AND ")
                .append(getPrefix()).append(".inv_onhand.item_id = ").append(getPrefix()).append(".inv_onhand_view.item_id AND ").append(getPrefix()).append(".inv_onhand.batchno = ")
                .append(getPrefix()).append(".inv_onhand_view.batchno) AS AVALIABLE,");

        sqlReport.append(" (SELECT ").append(getPrefix()).append(".location_qty.reserved_qty FROM ").append(getPrefix()).append(".location_qty WHERE ")
                .append(getPrefix()).append(".location_qty.batchno = ").append(getPrefix()).append(".inv_onhand_view.batchno AND ").append(getPrefix()).append(".location_qty.item_master_id =")
                .append(getPrefix()).append(".inv_onhand_view.item_id AND ").append(getPrefix()).append(".location_qty.location_id = ").append(getPrefix()).append(".inv_onhand_view.location_id) + (SELECT COUNT(")
                .append(getPrefix()).append(".inv_onhand.id) FROM ").append(getPrefix()).append(".inv_onhand WHERE ").append(getPrefix()).append(".inv_onhand.status = 3 AND ")
                .append(getPrefix()).append(".inv_onhand.location_id = ").append(getPrefix()).append(".inv_onhand_view.location_id AND ").append(getPrefix()).append(".inv_onhand.item_id = ")
                .append(getPrefix()).append(".inv_onhand_view.item_id AND ").append(getPrefix()).append(".inv_onhand.batchno = ").append(getPrefix()).append(".inv_onhand_view.batchno) AS RESERVED_QTY, ");

        sqlReport.append(" (SELECT ").append(getPrefix()).append(".location_qty.picked_qty FROM ").append(getPrefix()).append(".location_qty WHERE ").append(getPrefix()).append(".location_qty.batchno = ")
                .append(getPrefix()).append(".inv_onhand_view.batchno AND ").append(getPrefix()).append(".location_qty.item_master_id = ").append(getPrefix()).append(".inv_onhand_view.item_id AND ")
                .append(getPrefix()).append(".location_qty.location_id = ").append(getPrefix()).append(".inv_onhand_view.location_id) AS PICKED_QTY,");

        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand_view.cost AS COST");

        sqlReport.append(" FROM ").append(getPrefix()).append(".inv_onhand_view ");
        sqlReport.append(" GROUP BY ").append(getPrefix()).append(".inv_onhand_view.item_id, ").append(getPrefix()).append(".inv_onhand_view.itemid, ");
        sqlReport.append(getPrefix()).append(".inv_onhand_view.batchno, ").append(getPrefix()).append(".inv_onhand_view.location_id, ");
        sqlReport.append(getPrefix()).append(".inv_onhand_view.location_barcode, ").append(getPrefix()).append(".inv_onhand_view.warehouse_code, ");
        sqlReport.append(getPrefix()).append(".inv_onhand_view.ItemId, ").append(getPrefix()).append(".inv_onhand_view.item_description, ");
        sqlReport.append(getPrefix()).append(".inv_onhand_view.cost");

        log.debug(sqlReport.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlReport.toString())
                    .addScalar("ITEM_CODE", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("AVALIABLE", IntegerType.INSTANCE)
                    .addScalar("RESERVED_QTY", IntegerType.INSTANCE)
                    .addScalar("PICKED_QTY", IntegerType.INSTANCE)
                    .addScalar("COST", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            //AVALIABLE = 5, RESERVED_QTY = 6, PICKED_QTY = 7

            int availableQty = 0;
            int availableAmount = 0;

            int reservedQty = 0;
            int reservedAmount = 0;

            int pickQty = 0;
            int pickAmount = 0;

            for (Object[] entity : objects){

                availableQty = Utils.parseInt(entity[5]) - Utils.parseInt(entity[6]) - Utils.parseInt(entity[7]);
                availableAmount = Utils.multiply(Utils.parseInt(entity[5]), Utils.parseInt(entity[8]));

                reservedQty = Utils.parseInt(entity[6]) - Utils.parseInt(entity[7]);
                reservedAmount = Utils.multiply(Utils.parseInt(entity[6]), Utils.parseInt(entity[8]));

                pickQty = Utils.parseInt(entity[7]);
                pickAmount = Utils.multiply(Utils.parseInt(entity[7]), Utils.parseInt(entity[8]));

                if (availableQty + reservedQty + pickQty > 0){
                    InventoryOnhandReportView reportView = new InventoryOnhandReportView();
                    AvailivbleView availivbleView = new AvailivbleView();
                    ReservedView reservedView = new ReservedView();
                    PickView pickView = new PickView();
//                PackView packView = new PackView();
                    PhysicalView physicalView = new PhysicalView();

                    reportView.setItemCode(Utils.parseString(entity[0]));
                    reportView.setItemDesc(Utils.parseString(entity[1]));
                    reportView.setBatchNo(Utils.parseString(entity[2]));
                    reportView.setLocationBarcode(Utils.parseString(entity[3]));
                    reportView.setWarehouseName(Utils.parseString(entity[4]));

                    availivbleView.setQty(availableQty);
                    availivbleView.setAmount(availableAmount);

                    reservedView.setQty(reservedQty);
                    reservedView.setAmount(reservedAmount);

                    pickView.setQty(pickQty);
                    pickView.setAmount(pickAmount);

                    physicalView.setQty(availableQty + reservedQty + pickQty);
                    physicalView.setAmount(availableAmount + reservedAmount + pickAmount);

                    reportView.setAvailableView(availivbleView);
                    reportView.setReservedView(reservedView);
                    reportView.setPickView(pickView);
                    reportView.setPhysicalView(physicalView);

                    reportViewList.add(reportView);
                }
            }

        } catch (Exception e) {
            log.debug("Exception errror findInventOnhandReport : ", e);
        }

        return reportViewList;
    }
}
