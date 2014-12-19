package com.ese.service;

import com.ese.model.db.MSWorkingAreaModel;
import com.ese.model.dao.WorkingAreaDAO;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class WorkingAreaService extends Service{
    private static final long serialVersionUID = 4112578637029874840L;
    @Resource private WorkingAreaDAO workingAreaDAO;

    public List<MSWorkingAreaModel> getWorkingAreaList(){
        log.debug("getWorkingAreaList().");
        try{
            return workingAreaDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return Utils.getEmptyList();
        }
    }
}
