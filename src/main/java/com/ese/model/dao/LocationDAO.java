package com.ese.model.dao;

import com.ese.model.db.MSLocationModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDAO extends GenericDAO<MSLocationModel, Integer>{

    public List<MSLocationModel> getLocationOrderByUpdateDate() {
        log.debug("getLocationOrderByUpdateDate().");
        List<MSLocationModel> locationModelList = Utils.getEmptyList();
        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            locationModelList = Utils.safetyList(criteria.list());
            log.debug("locationModels Size : {}", locationModelList.size());
            return locationModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return locationModelList;
        }
    }

    public void deleteByUpdate(final MSLocationModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }

    public List<MSLocationModel> findOrderByLocationCodeOrLocationName(String key){
        log.debug("findOrderByLocationCodeOrLocationName(). {}", key);
        List<MSLocationModel> msLocationModels = Utils.getEmptyList();

        try{
            Criteria criteria = getCriteria();
            Criterion locationCode = Restrictions.like("locationBarcode", "%" + key.trim() + "%");
            Criterion locationName = Restrictions.like("locationName", "%"+key.trim()+"%");
            criteria.add(Restrictions.or(locationCode,locationName));
            criteria.addOrder(Order.desc("updateDate"));
            msLocationModels = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findOrderByLocationCodeOrLocationName : ", e);
        }
        log.debug("msLocationModels Size : {}", msLocationModels.size());

        return msLocationModels;
    }

    public List<MSLocationModel> findByWarehouseIdAndLocationCode(int warehouseId, String locationCode){
        List<MSLocationModel> msLocationModels = new ArrayList<MSLocationModel>();

        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msWarehouseModel.id",warehouseId));
            criteria.add(Restrictions.eq("locationBarcode",locationCode));
            criteria.add(Restrictions.eq("isValid", 1));
            msLocationModels = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findOrderByLocationCodeOrLocationName : ", e);
        }
        log.debug("msLocationModels Size : {}", msLocationModels.size());

        return msLocationModels;
    }

    public List<MSLocationModel> findByWarehouseId(int warehouseId){
        List<MSLocationModel> msLocationModels = new ArrayList<MSLocationModel>();

        try{
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msWarehouseModel.id",warehouseId));
            criteria.add(Restrictions.eq("isValid", 1));
            msLocationModels = Utils.safetyList(criteria.list());
        } catch (Exception e){
            log.debug("Exception error findByWarehouseId : ", e);
        }
        log.debug("msLocationModels Size : {}", msLocationModels.size());

        return msLocationModels;
    }
}
