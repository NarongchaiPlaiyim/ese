package com.ese.model.dao;

import com.ese.model.db.MSWarehouseModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WarehouseDAO extends GenericDAO<MSWarehouseModel, Integer>{

    public void deleteByUpdate(final MSWarehouseModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }

    public List<MSWarehouseModel> getLocationOrderByUpdateDate(){
        log.debug("getLocationOrderByUpdateDate().");
        List<MSWarehouseModel> locationModels = Utils.getEmptyList();
        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            locationModels = Utils.safetyList(criteria.list());
            log.debug("locationModels Size : {}", locationModels.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return locationModels;
    }

    public MSWarehouseModel findCheckDelete(int warehouseId){
        log.debug("findCheckDelete : {}", warehouseId);
        MSWarehouseModel warehouseModel = null;

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("id", warehouseId));
            criteria.add(Restrictions.eq("isValid", 1));
            warehouseModel = (MSWarehouseModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findCheckDelete : ", e);
        }

        return warehouseModel;
    }

    public List<MSWarehouseModel> findByIsValidEnable(){
        log.debug("findByIsValidEnable().");
        List<MSWarehouseModel> warehouseModel = Utils.getEmptyList();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            warehouseModel = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findCheckDelete : ", e);
        }

        return warehouseModel;
    }

    public List<MSWarehouseModel> findByWarehouseCode(String warehouseCode){
        List<MSWarehouseModel> locationModels = Utils.getEmptyList();
        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("warehouseCode", warehouseCode));
            criteria.add(Restrictions.eq("isValid", 1));
            locationModels = Utils.safetyList(criteria.list());
            log.debug("locationModels Size : {}", locationModels.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return locationModels;
    }
}
