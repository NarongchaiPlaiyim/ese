package com.ese.model.dao;

import com.ese.model.db.ReservedOrderModel;
import com.ese.model.view.ShowItemStatusView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservedOrderDAO extends GenericDAO<ReservedOrderModel, Integer> {
    public List<ReservedOrderModel> findByPickingOrderLineId(int pickingOrderLineId){
        List<ReservedOrderModel> reservedOrderModels = Utils.getEmptyList();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("pickingOrderLineModel.id", pickingOrderLineId));
            criteria.addOrder(Order.asc("id"));

            reservedOrderModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByPickingOrderLineId : ", e);
        }

        return reservedOrderModels;
    }

    public List<ShowItemStatusView> findByPickingLineId(int pickingOrderLineId){
        List<ShowItemStatusView> itemStatusViewList = new ArrayList<ShowItemStatusView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.location_barcode AS LOCATION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.batchno AS BATCH_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.reserved_qty AS RESERVED_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.picked_qty AS PICKED_QTY ");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".reserved_order");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".reserved_order.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".location.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".reserved_order.picking_order_line_id = " ).append(pickingOrderLineId);

        log.debug("findByPickingOrderId : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("BATCH_NO", StringType.INSTANCE)
                    .addScalar("RESERVED_QTY", StringType.INSTANCE)
                    .addScalar("PICKED_QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ShowItemStatusView showItemStatusView = new ShowItemStatusView();
                showItemStatusView.setId(Utils.parseInt(entity[0], 0));
                showItemStatusView.setWarehouse(Utils.parseString(entity[1], ""));
                showItemStatusView.setLocation(Utils.parseString(entity[2], ""));
                showItemStatusView.setBatchNo(Utils.parseString(entity[3], ""));
                showItemStatusView.setReservedQty(Utils.parseInt(entity[4], 0));
                showItemStatusView.setPickedQty(Utils.parseInt(entity[5], 0));
                itemStatusViewList.add(showItemStatusView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return itemStatusViewList;
    }

    public ReservedOrderModel remove(int reservedOrderId){
        ReservedOrderModel reservedOrderModel = new ReservedOrderModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", reservedOrderId));
            reservedOrderModel = (ReservedOrderModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error remove : ", e);
        }
        return reservedOrderModel;
    }
}
