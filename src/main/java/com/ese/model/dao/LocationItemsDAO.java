package com.ese.model.dao;

import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.view.LocationItemView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import org.hibernate.Query;
import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationItemsDAO extends GenericDAO<MSLocationItemsModel, Integer> {

    public List<MSLocationItemsModel> findLocationByPallet(){
        log.debug("findLocationByPallet().");
        try{
            Criteria criteria = getSession().createCriteria(MSLocationItemsModel.class, "b");
            criteria.createAlias("b.msLocationModel", "c");
            criteria.add(Restrictions.eq("status", 2));

//            criteria.createCriteria("location_items.msLocationModel", "msLocationModel", JoinType.LEFT_OUTER_JOIN, Restrictions.ne("msLocationModel.status", 2));

//            criteria.setFetchMode("msLocationModel", FetchMode.JOIN).add(Restrictions.ne("status", 2));
//            criteria.add(Restrictions.eq("MSLocationModel.status", 2));
            List<MSLocationItemsModel> msLocationItemsModels = criteria.list();
            for (MSLocationItemsModel model : msLocationItemsModels) {
                System.out.println("location_items : "+model.getId() +
                        " location : "+ model.getMsLocationModel().getId() +
                        " Status : " + model.getMsLocationModel().getStatus()
                );
            }
            log.debug("msLocationItemsModels Size : {}", msLocationItemsModels.size());
            return msLocationItemsModels;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<MSLocationItemsModel>();
        }
    }


}
