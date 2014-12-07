package com.ese.model.dao;

import com.ese.model.db.SystemRoleModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SystemRoleDAO extends GenericDAO<SystemRoleModel, Integer> {

    public List<SystemRoleModel> findByIsValid(){
        List<SystemRoleModel> systemRoleModels = new ArrayList<SystemRoleModel>();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            systemRoleModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByIsValid : ", e);
        }

        return systemRoleModels;
    }

    public List<SystemRoleModel> findByKey(String key){
        List<SystemRoleModel> systemRoleModels = new ArrayList<SystemRoleModel>();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));

            if (!Utils.isNull(key.trim()) && key.length() > 0){
                Criterion code = Restrictions.like("code", "%" + key.trim() + "%");
                Criterion name = Restrictions.like("name", "%"+ key.trim() +"%");
                criteria.add(Restrictions.or(code,name));
            }

            systemRoleModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByIsValid : ", e);
        }

        return systemRoleModels;
    }
}
