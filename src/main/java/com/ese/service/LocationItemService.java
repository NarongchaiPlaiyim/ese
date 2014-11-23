package com.ese.service;

import com.ese.model.dao.LocationItemsDAO;
import com.ese.model.db.LocationItemsModel;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.MSLocationModel;
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

    public List<MSLocationItemsModel> getAll(){
        log.debug("getAll Location()");
        try{
            return locationItemsDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<MSLocationItemsModel>();
        }
    }
}
