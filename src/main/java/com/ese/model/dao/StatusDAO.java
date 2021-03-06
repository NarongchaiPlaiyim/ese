package com.ese.model.dao;


import com.ese.model.db.StatusModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusDAO extends GenericDAO<StatusModel, Integer>{

    public StatusModel findByStatusSeqTablePickingOrder(int tableId){
        StatusModel statusModel = new StatusModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("statusSeq", 1));
            criteria.add(Restrictions.eq("tableId.id", tableId));

            statusModel = (StatusModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findByIdTablePickingOrder : ", e);
        }

        return statusModel;
    }

    public StatusModel findByStatusId(int id){
        StatusModel statusModel = new StatusModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", id));

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
            criteria.add(Restrictions.eq("tableId.id", tableId));

            modelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByTablePickingOrder : ", e);
        }

        return modelList;
    }

    public StatusModel findByTableIdAndStatus(int tableId, int status){
        StatusModel model = new StatusModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("tableId.id", tableId));
            criteria.add(Restrictions.eq("statusSeq", status));

            model = (StatusModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findByTablePickingOrder : ", e);
        }

        return model;
    }
}
