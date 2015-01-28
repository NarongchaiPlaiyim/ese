package com.ese.model.dao;
import com.ese.model.db.PickingOrderLineModel;
import com.ese.model.view.PickingOrderShowItemView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.qty AS ORDER_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.reserved_qty AS RESERVED_QTY,");
        sqlBuilder.append(" (CASE WHEN ").append(getPrefix()).append(".reserved_order.reserved_qty IS NULL THEN 0 ELSE ");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.reserved_qty END /");
        sqlBuilder.append(" CASE WHEN ").append(getPrefix()).append(".reserved_order.picked_qty IS NULL THEN 0 ELSE");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.picked_qty END) *  100 AS PER_PICKED,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.isfoil AS FOIL,");
        sqlBuilder.append(" CASE WHEN ").append(getPrefix()).append(".reserved_order.foil_qty IS NULL THEN 0 ELSE");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.foil_qty END AS FOIL_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".mst_status.caption AS STATUS,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.status AS STATUS_ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".reserved_order");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".picking_order_line.id = ").append(getPrefix()).append(".reserved_order.picking_order_line_id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".mst_status");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.status = ").append(getPrefix()).append(".mst_status.status_seq");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".mst_status.table_id = 1");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order_line.picking_order_id = " ).append(pickingOrderId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM", StringType.INSTANCE)
                    .addScalar("DESCRIPTION", StringType.INSTANCE)
                    .addScalar("ORDER_QTY", BigDecimalType.INSTANCE)
                    .addScalar("RESERVED_QTY", BigDecimalType.INSTANCE)
                    .addScalar("PER_PICKED", BigDecimalType.INSTANCE)
                    .addScalar("FOIL", IntegerType.INSTANCE)
                    .addScalar("FOIL_QTY", BigDecimalType.INSTANCE)
                    .addScalar("STATUS", StringType.INSTANCE)
                    .addScalar("STATUS_ID", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                PickingOrderShowItemView pickingOrderShowItemView = new PickingOrderShowItemView();
                pickingOrderShowItemView.setId(Utils.parseInt(entity[0], 0));
                pickingOrderShowItemView.setItem(Utils.parseString(entity[1], ""));
                pickingOrderShowItemView.setDescription(Utils.parseString(entity[2], ""));
                pickingOrderShowItemView.setOrderQty(Utils.parseBigDecimal(entity[3], BigDecimal.ZERO));
                pickingOrderShowItemView.setReservedQty(Utils.parseBigDecimal(entity[4], BigDecimal.ZERO));
                pickingOrderShowItemView.setPerPicked(Utils.parseBigDecimal(entity[5], BigDecimal.ZERO));
                pickingOrderShowItemView.setFoil(Utils.parseInt(entity[6], 0));
                pickingOrderShowItemView.setFoilQty(Utils.parseBigDecimal(entity[7], BigDecimal.ZERO));
                pickingOrderShowItemView.setStatus(Utils.parseString(entity[8], ""));
                pickingOrderShowItemView.setStatusID(Utils.parseInt(entity[9], 0));
                showItemViews.add(pickingOrderShowItemView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
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
}
