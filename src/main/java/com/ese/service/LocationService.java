package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.WarehouseDAO;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.transform.LocationTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class LocationService extends Service{
    @Resource private LocationDAO locationDAO;
    @Resource private LocationTransform locationTransform;
    @Resource private WarehouseDAO warehouseDAO;

    public List<MSLocationModel> getLocationList(){
        log.debug("getLocationList()");
        List<MSLocationModel> msLocationModelList = Utils.getEmptyList();
        try{
            msLocationModelList = locationDAO.findAll();
        } catch (Exception e){
            log.debug("Exception getLocationList.", e);
        }
        return msLocationModelList;
    }

    public List<LocationView> getLocationAll(){
        log.debug("getLocationAll()");
        List<LocationView> locationViewList = Utils.getEmptyList();
        try {
            List<MSLocationModel> models = locationDAO.getLocationOrderByUpdateDate();
            if (Utils.isSafetyList(models)){
                locationViewList = locationTransform.transformToViewList(models);
            }
        } catch (Exception e) {
            log.debug("Exception getLocationAll.", e);
        }
        return locationViewList;
    }

    public List<MSWarehouseModel> getWarehouseAll(){
        log.debug("getWarehouseAll().");
        List<MSWarehouseModel> msWarehouseModels = Utils.getEmptyList();
        try{
            msWarehouseModels = warehouseDAO.findAll();
        } catch (Exception e){
            log.debug("Exception getWarehouse.", e);
        }
        return msWarehouseModels;
    }
}
