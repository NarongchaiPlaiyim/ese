package com.ese.model.dao;

import com.ese.model.db.PickingOrderModel;
import com.ese.model.view.StatusPickingValue;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PickingOrderDAO extends GenericDAO<PickingOrderModel, Integer> {

    public List<PickingOrderModel> findByOverSeaOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "o%"));
            criteria.add(Restrictions.lt("status", StatusPickingValue.POST));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "d%"));
            criteria.add(Restrictions.lt("status", StatusPickingValue.POST));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByOverSeaAndDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.lt("status", StatusPickingValue.POST));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }
}
