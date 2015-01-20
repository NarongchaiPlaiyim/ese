package com.ese.model.dao;


import com.ese.model.db.StatusModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StatusDAO extends GenericDAO<StatusModel, Integer>{

    public StatusModel findByStatusSeqTablePickingOrder(int id){
        StatusModel statusModel = new StatusModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("statusSeq", 1));
            criteria.add(Restrictions.eq("tableId.id", 1));

            statusModel = (StatusModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findByIdTablePickingOrder : ", e);
        }

        return statusModel;
    }
}
