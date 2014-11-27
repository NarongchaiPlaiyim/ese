package com.ese.service;

import com.ese.model.db.MSWarehouseModel;
import com.ese.model.dao.WarehouseDAO;
import com.ese.model.view.WarehouseView;
import com.ese.transform.WarehouseTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class WarehouseService extends Service{
    @Resource private WarehouseDAO warehouseDAO;
    @Resource private WarehouseTransform warehouseTransform;

    public List<MSWarehouseModel> getAll(){
        log.debug("getALl().");
        List<MSWarehouseModel> msWarehouseModels = null;
        try {
            msWarehouseModels = warehouseDAO.getLocationOrderByUpdateDate();
        } catch (Exception e){
            log.debug("Exception getAll Warehouse : ", e);
        }
        return msWarehouseModels;
    }

    public WarehouseView converToView(MSWarehouseModel model){
        log.debug("converToView(). {}", model);
        WarehouseView view = null;

        if (!Utils.isNull(model)){
            view = warehouseTransform.transformToView(model);
        }

        return view;
    }

    public void onSaveOrUpdateWarehouse(WarehouseView warehouseView){
        log.debug("onSaveWarehouse().");
        try {
            if (Utils.isZero(warehouseView.getId())){
                warehouseDAO.persist(warehouseTransform.transformToModel(warehouseView));
            } else if (!Utils.isZero(warehouseView.getId())){
                warehouseDAO.update(warehouseTransform.transformToModel(warehouseView));
            }
        } catch (Exception e){
            log.debug("Exception onSave Warehouse :", e);
        }
    }

    public void delete(MSWarehouseModel model){
        log.debug("-- delete(id : {})", model.getId());
        try {
            warehouseDAO.deleteByUpdate(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }
}
