package com.ese.model.dao;

import com.ese.model.db.MSItemModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDAO extends GenericDAO<MSItemModel, Integer>{

    public List<MSItemModel> findByLike(String filed, String text) throws Exception {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like(filed, "%"+text+"%"));
        return Utils.safetyList(criteria.list());
    }
}
