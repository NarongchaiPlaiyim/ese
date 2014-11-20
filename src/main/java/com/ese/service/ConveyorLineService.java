package com.ese.service;

import com.ese.model.ConveyorLineModel;
import com.ese.model.dao.CoveyorLineDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ConveyorLineService extends Service implements Serializable {

    @Resource
    CoveyorLineDAO coveyorLineDAO;

    public List<ConveyorLineModel> getConveyorLineList(){
        log.debug("getConveyorList()");
        try {
            return coveyorLineDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : ", e);
            return new ArrayList<ConveyorLineModel>();
        }
    }

    public ConveyorLineModel findById(int conveyorId){
        log.debug("findById()");
        try{
            return coveyorLineDAO.findByID(conveyorId);
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ConveyorLineModel();
        }
    }
}
