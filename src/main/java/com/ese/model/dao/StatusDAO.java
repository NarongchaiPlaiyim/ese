package com.ese.model.dao;


import com.ese.model.db.StatusModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<StatusModel> findByTablePickingOrder(int tableId){
        List<StatusModel> modelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("tableId.id", 1));

            modelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByTablePickingOrder : ", e);
        }

        return modelList;
    }
}
