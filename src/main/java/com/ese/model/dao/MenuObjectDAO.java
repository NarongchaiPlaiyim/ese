package com.ese.model.dao;

import com.ese.model.db.MenuObjectModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuObjectDAO extends GenericDAO<MenuObjectModel, Integer>{

    public List<MenuObjectModel> findByObjCategory(){
        List<MenuObjectModel> menuObjectModels = null;
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("objCategory", 1));
            menuObjectModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByObjCategory : ", e);
        }

        return menuObjectModels;
    }

    public List<MenuObjectModel> findByObjectId(int menuObjectId){
        List<MenuObjectModel> menuObjectModels = null;
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isZero(menuObjectId)){
                criteria.add(Restrictions.eq("parentId", menuObjectId));
            }
            menuObjectModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByObjCategory : ", e);
        }

        return menuObjectModels;
    }
}
