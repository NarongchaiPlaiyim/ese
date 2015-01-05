package com.ese.model.dao;

import com.ese.model.db.MenuObjectModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuObjectDAO extends GenericDAO<MenuObjectModel, Integer>{

    public List<MenuObjectModel> findByObjCategory(){
        List<MenuObjectModel> menuObjectModels = null;
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("objCategory", 1));
            criteria.addOrder(Order.asc("seq"));
            menuObjectModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByObjCategory : ", e);
        }

        return menuObjectModels;
    }

    public List<String> findByStaffId(int staffId) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ").append(getPrefix()).append(".menu_object.code AS CODE");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".menu_object");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".menu_object.id IN (");
        sqlBuilder.append(" SELECT ").append(getPrefix()).append(".user_access.menu_object_id");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".user_access");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".user_access.staff_id = "+staffId+")");
        return Utils.safetyList(getSession().createSQLQuery(sqlBuilder.toString()).addScalar("CODE", StringType.INSTANCE).list());
    }

    public List<MenuObjectModel> findByObjectId(int menuObjectId, String keySearch){
        List<MenuObjectModel> menuObjectModels = null;
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isZero(menuObjectId)){
                criteria.add(Restrictions.eq("parentId", menuObjectId));
            }

            if (!Utils.isNull(keySearch) && !Utils.isZero(keySearch.trim().length())){
                Criterion objectCode = Restrictions.like("code", "%" + keySearch.trim() + "%");
                Criterion objecyName = Restrictions.like("name", "%"+keySearch.trim()+"%");
                criteria.add(Restrictions.or(objectCode,objecyName));
            }
            criteria.addOrder(Order.asc("seq"));
            menuObjectModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByObjCategory : ", e);
        }

        return menuObjectModels;
    }

    public List<MenuObjectModel> findAllOrderBySeq(){
        List<MenuObjectModel> menuObjectModels = null;
        try {
            Criteria criteria = getCriteria();
            criteria.addOrder(Order.asc("seq"));
            menuObjectModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByObjCategory : ", e);
        }

        return menuObjectModels;
    }
}
