package com.ese.model.dao;

import com.ese.model.db.MSLocationModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDAO extends GenericDAO<MSLocationModel, Integer>{

    public List<MSLocationModel> getLocationOrderByUpdateDate(){
        log.debug("getLocationOrderByUpdateDate().");
        try{
            Criteria criteria = getCriteria();
            criteria.addOrder(Order.desc("updateDate"));
            List<MSLocationModel> locationModels = criteria.list();
            log.debug("locationModels Size : {}", locationModels.size());
            return locationModels;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<MSLocationModel>();
        }
    }
}
