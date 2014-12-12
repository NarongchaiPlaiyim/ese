package com.ese.service;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.dao.ConveyorLineDAO;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ConveyorLineService extends Service{

    @Resource private ConveyorLineDAO conveyorLineDAO;

    public List<ConveyorLineModel> getConveyorLineList(){
        log.debug("getConveyorList()");
        try {
            return conveyorLineDAO.findAll();
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return Utils.getEmptyList();
        }
    }

    public ConveyorLineModel findById(int conveyorId){
        log.debug("findById()");
        try{
            return conveyorLineDAO.findByID(conveyorId);
        } catch (Exception e){
            log.error("Exception : {}", e);
            return new ConveyorLineModel();
        }
    }
}
