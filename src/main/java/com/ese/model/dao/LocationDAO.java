package com.ese.model.dao;

import com.ese.model.db.MSLocationModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDAO extends GenericDAO<MSLocationModel, Integer>{

    public List<MSLocationModel> getLocationOrderByUpdateDate() throws Exception{
        log.debug("getLocationOrderByUpdateDate().");
        Criteria criteria = getCriteria();
        criteria.addOrder(Order.desc("updateDate"));
        List<MSLocationModel> locationModels = Utils.safetyList(criteria.list());
        log.debug("locationModels Size : {}", locationModels.size());
        return locationModels;
    }
}
