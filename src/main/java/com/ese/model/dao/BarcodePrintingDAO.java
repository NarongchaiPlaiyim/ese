package com.ese.model.dao;

import com.ese.model.db.BarcodePrintingModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BarcodePrintingDAO extends GenericDAO<BarcodePrintingModel, Integer> {
    public BarcodePrintingModel findLastInsert() throws Exception {
        Criteria criteria = getCriteria().addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        return (BarcodePrintingModel) criteria.uniqueResult();
    }
}
