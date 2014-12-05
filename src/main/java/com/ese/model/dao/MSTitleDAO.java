package com.ese.model.dao;

import com.ese.model.db.MSTitleModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MSTitleDAO extends GenericDAO<MSTitleModel, Integer>{

    public MSTitleModel findById(int titleId){
        MSTitleModel msTitleModel = new MSTitleModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", titleId));
            msTitleModel = (MSTitleModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findById : ", e);
        }

        return msTitleModel;
    }
}
