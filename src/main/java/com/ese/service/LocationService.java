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
import java.util.List;

@Component
@Transactional
public class LocationService extends Service{
    @Resource
    LocationDAO locationDAO;

    @Resource private LocationTransform locationTransform;
    @Resource private WarehouseDAO warehouseDAO;

    public List<MSLocationModel> getLocationList(){
        log.debug("getLocationList(). ");
        try{
            return  locationDAO.findAll();
        } catch (Exception e){
            log.debug("Exception {}",e);
            return new ArrayList<MSLocationModel>();
        }
    }

    public List<LocationView> getLocationAll(){
        log.debug("getLocationAll()");
        List<LocationView> locationViewList = null;

        List<MSLocationModel> models = locationDAO.getLocationOrderByUpdateDate();

        if (Utils.isSafetyList(models)){
            locationViewList = locationTransform.transformToViewList(models);
        }

        return locationViewList;
    }

    public List<MSWarehouseModel> getWarehouseAll(){
        log.debug("getWarehouseAll().");
        List<MSWarehouseModel> msWarehouseModels = null;
        try{
            msWarehouseModels = warehouseDAO.findAll();
        } catch (Exception e){
            log.debug("Exception getWarehouse.", e);
        }

        return msWarehouseModels;
    }
}
