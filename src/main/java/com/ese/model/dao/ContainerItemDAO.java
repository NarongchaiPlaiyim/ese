package com.ese.model.dao;

import com.ese.model.db.ContainerItemModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
}
