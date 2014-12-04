package com.ese.model.dao;

import com.ese.model.db.FactionModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FactionDAO extends GenericDAO<FactionModel, Integer>{

//    public List<FactionModel> findFactionByIsValid(){
//        List<FactionModel> factionModelList = null;
//        try {
//            Criteria criteria = getCriteria();
//            criteria.add(Restrictions.eq("isValid", 1));
//            criteria.addOrder(Order.desc("updataDate"));
//            factionModelList = criteria.list();
//        } catch (Exception e) {
//            log.debug("Exception error findFactionByIsValid : ", e);
//        }
//
//        return factionModelList;
//    }

    public List<FactionModel> findByDepartmentId(int departmentId){
        List<FactionModel> factionModelList = null;
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msDepartmentModel.id", departmentId));
            criteria.add(Restrictions.eq("isValid", 1));
            factionModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDepartmentId : ", e);
        }

        return factionModelList;
    }
}
