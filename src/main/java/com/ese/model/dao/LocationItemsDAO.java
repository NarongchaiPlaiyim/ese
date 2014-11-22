package com.ese.model.dao;

import com.ese.model.db.MSLocationItemsModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationItemsDAO extends GenericDAO<MSLocationItemsModel, Integer> {

//    public List<MSLocationItemsModel> findLocationByPallet(){
//        log.debug("findLocationByPallet().");
//        try{
//            Criteria criteria = getCriteria();
////            criteria.add(Restrictions.ne("MSLocationModel."));
//        } catch (Exception e){
//            log.debug("Exception : {}", e);
//            return new ArrayList<MSLocationItemsModel>();
//        }
//    }

}
