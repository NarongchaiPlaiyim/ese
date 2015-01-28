package com.ese.model.dao;

import com.ese.model.db.ReservedOrderModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
}
