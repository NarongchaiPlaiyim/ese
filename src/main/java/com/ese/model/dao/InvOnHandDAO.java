package com.ese.model.dao;

import com.ese.model.db.InvOnHandModel;
import com.ese.model.view.InvOnhandPostView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvOnHandDAO extends GenericDAO<InvOnHandModel, Integer>{

    public List<InvOnHandModel> findByPickingId(int pickingId){
        List<InvOnHandModel> invOnHandModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("pickingOrderModel.id", pickingId));
            invOnHandModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByPickingId : ", e);
        }

        return invOnHandModels;
    }

    public List<InvOnhandPostView> findCountInvOnhand(int pickingOrderId){
        List<InvOnhandPostView> invOnhandPostViewList = new ArrayList<InvOnhandPostView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" COUNT(").append(getPrefix()).append(".inv_onhand.id) AS COUNT_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.item_id AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.picking_order_id AS PICKING_ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order_id = " ).append(pickingOrderId);
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append("inv_onhand.item_id, ").append(getPrefix()).append(".inv_onhand.picking_order_id ");

        log.debug("findCountInvOnhand : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("COUNT_ID", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", IntegerType.INSTANCE)
                    .addScalar("PICKING_ID", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                InvOnhandPostView invOnhandPostView = new InvOnhandPostView();
                invOnhandPostView.setCountId(Utils.parseInt(entity[0]));
                invOnhandPostView.setItemId(Utils.parseInt(entity[1]));
                invOnhandPostView.setPickingId(Utils.parseInt(entity[2]));
                invOnhandPostViewList.add(invOnhandPostView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findCountInvOnhand : {}", e);
        }

        return invOnhandPostViewList;
    }
}
