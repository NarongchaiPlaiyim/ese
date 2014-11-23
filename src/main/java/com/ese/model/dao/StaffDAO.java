package com.ese.model.dao;

import com.ese.model.db.StaffModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffDAO extends GenericDAO<StaffModel, Integer>{

    public StaffModel findByUserName(String userName, String password) throws Exception {
        return (StaffModel) getCriteria().add(Restrictions.and(
                Restrictions.eq("username", userName),
                Restrictions.eq("password", password)
        )).uniqueResult();
    }

    public List<StaffModel> test() throws Exception {
//        SELECT * FROM dbo.staff
        return findBySQL("SELECT * FROM dbo.staff", "3", 5, 6);
    }
}
