package com.ese.model.dao;

import com.ese.model.db.InvOnHandModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvOnHandDAO extends GenericDAO<InvOnHandModel, Integer>{

    public List<InvOnHandModel> findByPickingId(int pickingId){
        List<InvOnHandModel> invOnHandModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("pickingOrderModel.id", pickingId));
            invOnHandModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByPickingId : ", e);
        }

        return invOnHandModels;
    }
}
