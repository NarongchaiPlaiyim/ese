package com.ese.model.dao;

import com.ese.model.db.LoadingOrderModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoadingOrderDAO extends GenericDAO<LoadingOrderModel, Integer>{

    public List<LoadingOrderModel> findByStatusIs12(){
        List<LoadingOrderModel> loadingOrderModelList = Utils.getEmptyList();

        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("statusModel.id", 12));
            loadingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findByStatusIs12 : ", e);
        }
        System.out.println(loadingOrderModelList.size());
        return loadingOrderModelList;
    }
}
