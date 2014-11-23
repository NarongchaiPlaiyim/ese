package com.ese.service;

import com.ese.model.dao.MSLocationItemsDAO;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.view.LocationItemView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class LocationItemService extends Service{
    @Resource private MSLocationItemsDAO msLocationItemsDAO;

    public List<MSLocationItemsModel> getAll(){
        log.debug("getAll Location()");
        try{
            return msLocationItemsDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<MSLocationItemsModel>();
        }
    }

    public List<LocationItemView> findLocationByItemId(int itemId){
        try {
            return msLocationItemsDAO.findLocationByItemId(itemId);
        } catch (Exception e) {
            log.debug("Exception : {}", e);
            return new ArrayList<LocationItemView>();
        }
    }
}
