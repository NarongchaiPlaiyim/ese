package com.ese.model.dao;

import com.ese.model.db.FactionModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FactionDAO extends GenericDAO<FactionModel, Integer>{

    public List<FactionModel> findByDepartmentId(int departmentId){
        List<FactionModel> factionModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msDepartmentModel.id", departmentId));
            criteria.add(Restrictions.eq("isValid", 1));
            factionModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByDepartmentId : ", e);
        }
        return factionModelList;
    }
}
