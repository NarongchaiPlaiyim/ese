package com.ese.service;

import com.ese.model.dao.MSLocationItemsDAO;
import com.ese.model.db.MSLocationItemsModel;
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
}
