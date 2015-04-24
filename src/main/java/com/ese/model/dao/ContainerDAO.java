package com.ese.model.dao;

import com.ese.model.db.ContainerModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContainerDAO extends GenericDAO<ContainerModel, Integer>{

    public List<ContainerModel> findByLoadingOrderId(int loadingOrderId){
        List<ContainerModel> containerModels = Utils.getEmptyList();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("loadingOrderModel.id", loadingOrderId));
            criteria.addOrder(Order.desc("updateDate"));
            containerModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception e : ", e);
        }

        return containerModels;
    }
}
