package com.ese.model.dao;

import com.ese.model.db.MSDepartmentModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MSDepartmentDAO extends GenericDAO<MSDepartmentModel, Integer>{

    public List<MSDepartmentModel> findDepartmentByIsValid(){
        List<MSDepartmentModel> msDepartmentModels = null;
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            msDepartmentModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByIsValid :", e);
        }

        return msDepartmentModels;
    }
}
