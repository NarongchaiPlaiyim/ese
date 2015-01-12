package com.ese.model.dao;

import com.ese.model.db.UserAccessModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserAccessDAO extends GenericDAO<UserAccessModel, Integer>{

    public List<UserAccessModel> findByUserId(int userId){
        List<UserAccessModel> userAccessModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("staffModel.id", userId));
            criteria.add(Restrictions.eq("isValid", 1));
            userAccessModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByUserId : ", e);
        }
        return userAccessModels;
    }

    public List<UserAccessModel> findByMenuObjOrKey(int objId, String key){
        List<UserAccessModel> userAccessModels = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(UserAccessModel.class, "ua");
            criteria.createAlias("ua.menuObjectModel", "mo");

            if (!Utils.isZero(objId)){
                criteria.add(Restrictions.eq("mo.parentId", objId));
            }

            if (!Utils.isNull(key) && !Utils.isZero(key.trim().length())){
                Criterion objectCode = Restrictions.like("mo.code", "%" + key.trim() + "%");
                Criterion objecyName = Restrictions.like("mo.name", "%"+key.trim()+"%");
                criteria.add(Restrictions.or(objectCode,objecyName));
            }
            criteria.add(Restrictions.eq("isValid", 1));
            userAccessModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByUserId : ", e);
        }
        return userAccessModels;
    }

    public List<UserAccessModel> findByPickingOrder(int staffId){
        List<UserAccessModel> userAccessModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("staffModel.id", staffId));
            Criterion overSeaOrder = Restrictions.like("menuObjectModel.id", 67);
            Criterion domesticOrder = Restrictions.like("menuObjectModel.id", 68);
            criteria.add(Restrictions.or(overSeaOrder,domesticOrder));
            criteria.add(Restrictions.eq("isValid", 1));
            userAccessModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByPickingOrder : ", e);
        }
        return userAccessModels;
    }
}
