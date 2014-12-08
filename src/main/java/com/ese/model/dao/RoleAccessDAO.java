package com.ese.model.dao;

import com.ese.model.db.RoleAccessModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleAccessDAO extends GenericDAO<RoleAccessModel, Integer>{

    public List<RoleAccessModel> findBySystemRoleId(int systemRoleId){
        List<RoleAccessModel> roleAccessModels = new ArrayList<RoleAccessModel>();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("systemRoleModel.id", systemRoleId));
            criteria.add(Restrictions.eq("isValid", 1));
            roleAccessModels = criteria.list();

        } catch (Exception e) {
            log.debug("Exception error findBySystemRoleId : ", e);
        }

        return roleAccessModels;
    }

    public List<RoleAccessModel> findByMenuObjectIdAndSystemRoleId(int menuObjId, int systemRoleId){
        List<RoleAccessModel> roleAccessModels = new ArrayList<RoleAccessModel>();
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isZero(menuObjId)){
                criteria.add(Restrictions.eq("menuObjectModel.id", menuObjId));
            }

            if (!Utils.isZero(systemRoleId)){
                criteria.add(Restrictions.eq("systemRoleModel.id", systemRoleId));
            }

            criteria.add(Restrictions.eq("isValid", 1));
            roleAccessModels = criteria.list();

        } catch (Exception e) {
            log.debug("Exception error findBySystemRoleId : ", e);
        }

        return roleAccessModels;
    }
}
