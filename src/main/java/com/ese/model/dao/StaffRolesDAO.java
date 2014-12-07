package com.ese.model.dao;
import com.ese.model.db.StaffRolesModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRolesDAO extends GenericDAO<StaffRolesModel, Integer> {

    public List<StaffRolesModel> findByUserId(int userId){
        List<StaffRolesModel> systemRoleModels = new ArrayList<StaffRolesModel>();
        try {
            Criteria criteria = getSession().createCriteria(StaffRolesModel.class, "sr");
            criteria.createAlias("sr.roles", "r");
            criteria.add(Restrictions.eq("staff.id", userId));
            criteria.add(Restrictions.eq("r.isValid", 1));
            systemRoleModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByUserId : ", e);
        }

        return systemRoleModels;
    }

    public StaffRolesModel findById(int roleId){
        StaffRolesModel staffRolesModel = new StaffRolesModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", roleId));
            staffRolesModel = (StaffRolesModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findByUserId : ", e);
        }

        return staffRolesModel;
    }
}
