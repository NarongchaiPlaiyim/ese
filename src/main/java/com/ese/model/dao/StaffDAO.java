package com.ese.model.dao;

import com.ese.model.StaffModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDAO extends GenericDAO<StaffModel, Integer>{

    public StaffModel findByUserName(String userName, String password) throws Exception {
        Criteria criteria = getCriteria();

        criteria.add(Restrictions.and(
                Restrictions.eq("username", userName),
                Restrictions.eq("password", password)
        ));
//        criteria.add(Restrictions.eq("username", userName));
//        criteria.add(Restrictions.eq("password", password));
        return (StaffModel) criteria.uniqueResult();
    }

}
