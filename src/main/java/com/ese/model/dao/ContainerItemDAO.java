package com.ese.model.dao;

import com.ese.model.db.ContainerItemModel;
import com.ese.model.view.ContainerItemView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContainerItemDAO extends GenericDAO<ContainerItemModel, Integer>{

    public List<ContainerItemModel> findByLoadingOrderId(int loadingOrderId){
        List<ContainerItemModel> containerItemModelList = Utils.getEmptyList();

        try {
            Criteria criteria = getSession().createCriteria(ContainerItemModel.class, "conItem");
            criteria.createAlias("conItem.containerModel", "con");
            criteria.createAlias("con.loadingOrderModel", "loadOrder");
            criteria.add(Restrictions.eq("loadOrder.id", loadingOrderId));
            criteria.addOrder(Order.desc("updateDate"));
            containerItemModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception e : ", e);
        }

        return containerItemModelList;
    }

    public List<ContainerItemModel> findByLoadingOrderIdOrderBy(int loadingOrderId){
        List<ContainerItemModel> containerItemModelList = Utils.getEmptyList();

        try {
            Criteria criteria = getSession().createCriteria(ContainerItemModel.class, "conItem");
            criteria.createAlias("conItem.containerModel", "con");
            criteria.createAlias("con.loadingOrderModel", "loadOrder");
            criteria.add(Restrictions.eq("loadOrder.id", loadingOrderId));
            criteria.addOrder(Order.desc("msItemModel.id"));
            containerItemModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception e : ", e);
        }

        return containerItemModelList;
    }

    public List<ContainerItemView> findByInsertModel(int loadingOrderId){
        List<ContainerItemView> containerItemViewList = new ArrayList<ContainerItemView>();

        StringBuilder selectLocationQty = new StringBuilder();

        selectLocationQty.append(" SELECT ");
        selectLocationQty.append(" ").append(getPrefix()).append(".container.id AS CONTAINER_ID,");
        selectLocationQty.append(" ").append(getPrefix()).append(".item_master.id AS ITEM_ID,");
        selectLocationQty.append(" SUM(").append(getPrefix()).append(".reserved_order.picked_qty)/");
        selectLocationQty.append(" COUNT(").append(getPrefix()).append(".container.id) AS CONTAINER_ITEM_QTY");
        selectLocationQty.append(" FROM ").append(getPrefix()).append(".picking_order_line");
        selectLocationQty.append(" INNER JOIN ").append(getPrefix()).append(".reserved_order");
        selectLocationQty.append(" ON ").append(getPrefix()).append(".picking_order_line.id = ").append(getPrefix()).append(".reserved_order.picking_order_line_id");

        selectLocationQty.append(" INNER JOIN ").append(getPrefix()).append(".picking_order");
        selectLocationQty.append(" ON ").append(getPrefix()).append(".picking_order_line.picking_order_id = ").append(getPrefix()).append(".picking_order.id");
        selectLocationQty.append(" INNER JOIN ").append(getPrefix()).append(".container");
        selectLocationQty.append(" ON ").append(getPrefix()).append(".container.loading_order_id = ").append(getPrefix()).append(".picking_order.loading_order_id");
        selectLocationQty.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        selectLocationQty.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");

        selectLocationQty.append(" WHERE ").append(getPrefix()).append(".picking_order.loading_order_id = " ).append(loadingOrderId);
        selectLocationQty.append(" GROUP BY ").append(getPrefix()).append(".container.id, ").append(getPrefix()).append(".item_master.id");

        log.debug("findByInsertModel : {}", selectLocationQty.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(selectLocationQty.toString())
                    .addScalar("CONTAINER_ID", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", IntegerType.INSTANCE)
                    .addScalar("CONTAINER_ITEM_QTY", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ContainerItemView containerItemView = new ContainerItemView();
                containerItemView.setContainnerId(Utils.parseInt(entity[0]));
                containerItemView.setItemId(Utils.parseInt(entity[1]));
                containerItemView.setContainerQty(Utils.parseInt(entity[2]));
                containerItemViewList.add(containerItemView);
            }
        } catch (Exception e) {
            log.debug("Exception findOnPostStatus SQL : {}", e);
        }

        return containerItemViewList;
    }
}
