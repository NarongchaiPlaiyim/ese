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

        sqlReport.append(" SELECT ");
        sqlReport.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_CODE,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATCH_NO,");
        sqlReport.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        sqlReport.append(" ").append(getPrefix()).append(".location.location_barcode AS BARCODE,");
        sqlReport.append(" COUNT(").append(getPrefix()).append(".inv_onhand.id) AS QTY,");
        sqlReport.append(" COUNT(").append(getPrefix()).append(".inv_onhand.id) * COALESCE(").append(getPrefix()).append(".inv_onhand.cost, 0) AS AMOUNT,");
        sqlReport.append(" ").append(getPrefix()).append(".inv_onhand.status AS STATUS");
        sqlReport.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlReport.append(" ON ").append(getPrefix()).append(".inv_onhand.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        sqlReport.append(" ON ").append(getPrefix()).append(".inv_onhand.location_id = ").append(getPrefix()).append(".location.id");
        sqlReport.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        sqlReport.append(" ON ").append(getPrefix()).append(".location.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlReport.append(" WHERE ").append(getPrefix()).append(".inv_onhand.status > 1 ");
        sqlReport.append(" GROUP BY ").append(getPrefix()).append(".item_master.ItemId, ").append(getPrefix()).append(".item_master.DSGThaiItemDescription, ");
        sqlReport.append(getPrefix()).append(".inv_onhand.batchno, ").append(getPrefix()).append(".inv_onhand.grade, ");
        sqlReport.append(getPrefix()).append(".warehouse.warehouse_code, ").append(getPrefix()).append(".location.location_barcode, ");
        sqlReport.append(getPrefix()).append(".inv_onhand.cost, ").append(getPrefix()).append(".inv_onhand.status");

        log.debug(sqlReport.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlReport.toString())
                    .addScalar("ITEM_CODE", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("BARCODE", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("AMOUNT", IntegerType.INSTANCE)
                    .addScalar("STATUS", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects){
                InventoryOnhandReportView reportView = new InventoryOnhandReportView();
                AvailivbleView availivbleView = new AvailivbleView();
                ReservedView reservedView = new ReservedView();
                PickView pickView = new PickView();
                PackView packView = new PackView();
                PhysicalView physicalView = new PhysicalView();

                reportView.setItemCode(Utils.parseString(entity[0]));
                reportView.setBatchNo(Utils.parseString(entity[1]));
                reportView.setWarehouseName(Utils.parseString(entity[2]));
                reportView.setLocationBarcode(Utils.parseString(entity[3]));

                if (Utils.parseInt(entity[6]) == 2){
                    availivbleView.setQty(Utils.parseInt(entity[4]));
                    availivbleView.setAmount(Utils.parseInt(entity[5]));
                    physicalView.setQty(Utils.parseInt(entity[4]));
                    physicalView.setAmount(Utils.parseInt(entity[5]));
                } else if (Utils.parseInt(entity[6]) == 3){
                    reservedView.setQty(Utils.parseInt(entity[4]));
                    reservedView.setAmount(Utils.parseInt(entity[5]));
                    physicalView.setQty(Utils.parseInt(entity[4]));
                    physicalView.setAmount(Utils.parseInt(entity[5]));
                } else if (Utils.parseInt(entity[6]) == 4){
                    pickView.setQty(Utils.parseInt(entity[4]));
                    pickView.setAmount(Utils.parseInt(entity[5]));
                    physicalView.setQty(Utils.parseInt(entity[4]));
                    physicalView.setAmount(Utils.parseInt(entity[5]));
                } else if (Utils.parseInt(entity[6]) == 5){
                    packView.setQty(Utils.parseInt(entity[4]));
                    packView.setAmount(Utils.parseInt(entity[5]));
                }

                reportView.setAvailableView(availivbleView);
                reportView.setReservedView(reservedView);
                reportView.setPickView(pickView);
                reportView.setPackView(packView);
                reportView.setPhysicalView(physicalView);

                reportViewList.add(reportView);
            }

        } catch (Exception e) {
            log.debug("Exception errror findInventOnhandReport : ", e);
        }

        return reportViewList;
    }
}
