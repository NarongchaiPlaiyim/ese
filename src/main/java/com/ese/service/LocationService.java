package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.db.LocationModel;
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

    public List<LocationModel> getLocationList(){
        log.debug("getLocationList(). ");
        try{
            return  locationDAO.findAll();
        } catch (Exception e){
            log.debug("Exception {}",e);
            return new ArrayList<LocationModel>();
        }
    }
}
