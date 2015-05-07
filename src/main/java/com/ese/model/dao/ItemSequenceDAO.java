package com.ese.model.dao;

import com.ese.model.db.ItemSequenceModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemSequenceDAO extends GenericDAO<ItemSequenceModel, Integer>{

    public List<ItemSequenceModel> findByLoadingOrderId(int loadingOrderId){
        List<ItemSequenceModel> itemSequenceModelList = new ArrayList<ItemSequenceModel>();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("loadingOrderModel.id", loadingOrderId));
            criteria.addOrder(Order.asc("seq"));
            itemSequenceModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByLoadingOrderId : ", e);
        }

        return itemSequenceModelList;
    }
}
