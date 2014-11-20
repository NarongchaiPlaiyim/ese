package com.ese.service;

import com.ese.model.db.WarehouseModel;
import com.ese.model.dao.WarehouseDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class WarehouseService extends Service implements Serializable {
    @Resource private WarehouseDAO warehouseDAO;

    public List<WarehouseModel> getWarehouseList(){
        log.debug("getWarehouseList(). ");
        try{
            return  warehouseDAO.findAll();
        } catch (Exception e){
            log.debug("Exception {}",e);
            return new ArrayList<WarehouseModel>();
        }
    }

    public WarehouseModel findByid(int warehouseId){
        try {
            return warehouseDAO.findByID(warehouseId);
        } catch (Exception e){
            return new WarehouseModel();
        }
    }
}
