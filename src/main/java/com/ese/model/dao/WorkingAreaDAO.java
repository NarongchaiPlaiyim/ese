package com.ese.model.dao;

import com.ese.model.db.MSWorkingAreaModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkingAreaDAO extends GenericDAO<MSWorkingAreaModel, Integer>{

    public List<MSWorkingAreaModel> findByWarehouseId(int warehouseId){

        log.debug("findByWarehouseId().");
        List<MSWorkingAreaModel> msWorkingAreaModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msWarehouseModel.id", warehouseId));
            msWorkingAreaModels = Utils.safetyList(criteria.list());
            log.debug("findByWarehouseId Size : {}", msWorkingAreaModels.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return msWorkingAreaModels;
    }
}
