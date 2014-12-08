package com.ese.model.dao;

import com.ese.model.db.RoleAccessModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
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

    public List<RoleAccessModel> findByMenuObjectIdAndSystemRoleId(int menuObjId, int systemRoleId, String key){
        List<RoleAccessModel> roleAccessModels = new ArrayList<RoleAccessModel>();
        try {
            Criteria criteria = getSession().createCriteria(RoleAccessModel.class, "ra");
            criteria.createAlias("ra.menuObjectModel", "mo");
            criteria.createAlias("ra.systemRoleModel", "so");

            if (!Utils.isZero(menuObjId)){
                criteria.add(Restrictions.eq("mo.id", menuObjId));
            }

            if (!Utils.isZero(systemRoleId)){
                criteria.add(Restrictions.eq("so.id", systemRoleId));
            }

            if (!Utils.isNull(key) && !Utils.isZero(key.trim().length())){
                Criterion objectCode = Restrictions.like("mo.code", "%" + key.trim() + "%");
                Criterion objecyName = Restrictions.like("mo.name", "%"+ key.trim() +"%");
                criteria.add(Restrictions.or(objectCode,objecyName));
            }

            criteria.add(Restrictions.eq("isValid", 1));
            roleAccessModels = criteria.list();

        } catch (Exception e) {
            log.debug("Exception error findBySystemRoleId : ", e);
        }

        return roleAccessModels;
    }
}
