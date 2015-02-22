package com.ese.model.dao;

import com.ese.model.view.ReceivingReportView;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReceivingReportDAO extends GenericDAO<ReceivingReportView, Integer>{

    public List<ReceivingReportView> findReceivingReport(String startDate, String endDate){
        log.debug("------ {} : {}", startDate, endDate);
        List<ReceivingReportView> receivingReportViewList = new ArrayList<ReceivingReportView>();
        StringBuilder queryInvOnhandView = new StringBuilder();
        queryInvOnhandView.append(" SELECT ");
        queryInvOnhandView.append(" CONVERT(CHAR(10), ").append(getPrefix()).append(".inv_onhand_view.receiving_date,120) AS RECEIVING_DATE,");
        queryInvOnhandView.append(" ").append(getPrefix()).append(".inv_onhand_view.warehouse_code AS WAREHOUSE_CODE,");
        queryInvOnhandView.append(" ").append(getPrefix()).append(".inv_onhand_view.name AS CONVEYOR_LINE,");
        queryInvOnhandView.append(" ").append(getPrefix()).append(".inv_onhand_view.itemid AS ITEM_NAME,");
        queryInvOnhandView.append(" ").append(getPrefix()).append(".inv_onhand_view.item_description AS ITEM_DESC,");
        queryInvOnhandView.append(" ").append(getPrefix()).append(".inv_onhand_view.grade AS GRADE,");
        queryInvOnhandView.append(" COUNT(").append(getPrefix()).append(".inv_onhand_view.id) AS QTY ");
        queryInvOnhandView.append(" FROM ").append(getPrefix()).append(".inv_onhand_view");

        if (Utils.isZero(startDate.trim()) && Utils.isZero(endDate.trim())){
            queryInvOnhandView.append(" WHERE ").append(getPrefix()).append(".inv_onhand_view.receiving_date >= CONVERT(CHAR(10), GETDATE(), 120)");
            queryInvOnhandView.append(" AND ").append(getPrefix()).append(".inv_onhand_view.receiving_date < CONVERT(CHAR(10), GETDATE()+1, 120)");
        } else {
            queryInvOnhandView.append(" WHERE ").append(getPrefix()).append(".inv_onhand_view.receiving_date >= '").append(startDate).append("'");
            queryInvOnhandView.append(" AND ").append(getPrefix()).append(".inv_onhand_view.receiving_date < '").append(endDate).append("'");
        }

        queryInvOnhandView.append(" GROUP BY ").append(getPrefix()).append(".inv_onhand_view.name, ").append(getPrefix()).append(".inv_onhand_view.ItemId, ")
                .append(getPrefix()).append(".inv_onhand_view.warehouse_code, ").append(getPrefix()).append(".inv_onhand_view.grade, ").append(getPrefix()).append(".inv_onhand_view.item_description, ")
                .append(getPrefix()).append(".inv_onhand_view.receiving_date");
        queryInvOnhandView.append(" ORDER BY ").append(getPrefix()).append(".inv_onhand_view.warehouse_code, ").append(getPrefix()).append(".inv_onhand_view.name, ")
                .append(getPrefix()).append(".inv_onhand_view.ItemId");

        log.debug("findReceivingReport : {}", queryInvOnhandView.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(queryInvOnhandView.toString())
                    .addScalar("RECEIVING_DATE", DateType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("CONVEYOR_LINE", StringType.INSTANCE)
                    .addScalar("ITEM_NAME", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("GRADE", StringType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects){
                ReceivingReportView reportView = new ReceivingReportView();
                reportView.setReceivingDate(Utils.parseDate(entity[0], null));
                reportView.setWarehouseCode(Utils.parseString(entity[1], ""));
                reportView.setConveyorLine(Utils.parseString(entity[2], ""));
                reportView.setItemName(Utils.parseString(entity[3], ""));
                reportView.setItemDesc(Utils.parseString(entity[4], ""));
                reportView.setGrade(Utils.parseString(entity[5], ""));
                reportView.setQty(Utils.parseInt(entity[6], 0));
                receivingReportViewList.add(reportView);
            }
        } catch (Exception e) {
            log.debug("Exception error findReceivingReport : ", e);
        }

        return receivingReportViewList;
    }
}
