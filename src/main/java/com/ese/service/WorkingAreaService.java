package com.ese.service;

import com.ese.model.db.WorkingAreaModel;
import com.ese.model.dao.WorkingAreaDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class WorkingAreaService extends Service implements Serializable {

    @Resource
    WorkingAreaDAO workingAreaDAO;

    public List<WorkingAreaModel> getWorkingAreaList(){
        log.debug("getWorkingAreaList().");
        try{
            return workingAreaDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<WorkingAreaModel>();
        }
    }
}
