package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.StockInOutNoteDAO;
import com.ese.model.dao.WarehouseDAO;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.model.view.StockInOutNoteView;
import com.ese.transform.LocationTransform;
import com.ese.transform.StockInOutNoteTransform;
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

    public List<MSLocationModel> getLocationAll(){
        log.debug("getLocationAll()");
        List<MSLocationModel> msLocationModels = null;
        try {
            msLocationModels = locationDAO.getLocationOrderByUpdateDate();
        } catch (Exception e) {
            log.debug("Exception error locational : ", e);
        }

        return msLocationModels;
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

    public LocationView clickToWarehouseView(MSLocationModel msLocationModel){
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
