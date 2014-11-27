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
    @Resource private LocationDAO locationDAO;

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

    public List<MSLocationModel> getLocationAll(){
        log.debug("getLocationAll()");
        List<MSLocationModel> msLocationModels = locationDAO.getLocationOrderByUpdateDate();

        return msLocationModels;
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

    public LocationView clickToView(MSLocationModel msLocationModel){
        LocationView locationView = new LocationView();

        if (!Utils.isNull(msLocationModel)){
            locationView = locationTransform.transformToView(msLocationModel);
        }

        return locationView;
    }

    public void onSaveOrUpdateLocationToDB(LocationView locationView){
        log.debug("onSaveToNew().");

        if (!Utils.isNull(locationView)){
            MSLocationModel model = null;
            try {
                if (Utils.isZero(locationView.getId())){
                    locationDAO.persist(locationTransform.transformToModel(locationView));
                } else if (!Utils.isZero(locationView.getId())){
                    locationDAO.update(locationTransform.transformToModel(locationView));
                }
            } catch (Exception e) {
                log.debug("Exception persist : ", e);
            }
        }
    }

    public void delete(MSLocationModel model){
        log.debug("-- delete(id : {})", model.getId());
        try {
            locationDAO.deleteByUpdate(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }
}
