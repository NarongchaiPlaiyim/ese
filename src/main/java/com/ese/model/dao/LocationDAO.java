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
        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            List<MSLocationModel> locationModels = criteria.list();
            log.debug("locationModels Size : {}", locationModels.size());
            return locationModels;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<MSLocationModel>();
        }
    }

    public void deleteByUpdate(final MSLocationModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }
}
