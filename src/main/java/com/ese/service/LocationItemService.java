package com.ese.service;

import com.ese.model.dao.LocationItemsDAO;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.LocationView;
import com.ese.transform.LocationItemTransform;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class LocationItemService extends Service{

    @Resource
    LocationItemsDAO locationItemsDAO;
    @Resource private LocationItemTransform locationItemTransform;

    public List<LocationItemView> getfindLocationAll(){
        log.debug("getAll Location()");
        List<LocationItemView> locationItemViewList = new ArrayList<LocationItemView>();
        try{
            List<MSLocationItemsModel> msLocationItemsModels = locationItemsDAO.findAll();
            log.debug("msLocationItemsModels Size : {}", msLocationItemsModels.size());
            locationItemViewList = locationItemTransform.transformToViewList(msLocationItemsModels);
            log.debug("locationItemViewList Size : {}", locationItemViewList.size());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
        return locationItemViewList;
    }

}
