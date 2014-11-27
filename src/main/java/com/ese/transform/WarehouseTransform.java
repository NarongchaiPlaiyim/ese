package com.ese.transform;

import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.WarehouseView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WarehouseTransform extends Transform{

    public WarehouseView transformToView(MSWarehouseModel msWarehouseModel){
        log.debug("teansformToView().");
        WarehouseView warehouseView = new WarehouseView();

        warehouseView.setId(msWarehouseModel.getId());
        warehouseView.setWarehouseCode(msWarehouseModel.getWarehouseCode());
        warehouseView.setWarehouseName(msWarehouseModel.getWarehouseName());
        warehouseView.setRemark(msWarehouseModel.getRemark());
        warehouseView.setIsvalid(msWarehouseModel.getIsValid());
        warehouseView.setStatus(msWarehouseModel.getStatus());
        warehouseView.setVersion(msWarehouseModel.getVersion());
        warehouseView.setCreateBy(msWarehouseModel.getCreateBy());
        warehouseView.setCreateDate(msWarehouseModel.getCreateDate());
        warehouseView.setUpdateBy(msWarehouseModel.getUpdateBy());
        warehouseView.setUpdateDate(msWarehouseModel.getUpdateDate());

        return warehouseView;
    }

    public MSWarehouseModel transformToModel(WarehouseView view){
        MSWarehouseModel model = new MSWarehouseModel();

        model.setId(view.getId());
        model.setWarehouseCode(view.getWarehouseCode());
        model.setWarehouseName(view.getWarehouseName());
        model.setRemark(view.getRemark());

        model.setUpdateDate(Utils.currentDate());

        if (Utils.isZero(view.getId())){
            model.setIsValid(1);
            model.setStatus("1");
            model.setVersion(1);
            model.setCreateBy(1111);
            model.setCreateDate(Utils.currentDate());
            model.setUpdateBy(1111);
        } else {
            model.setIsValid(view.getIsvalid());
            model.setStatus(view.getStatus());
            model.setVersion(view.getVersion());
            model.setCreateBy(view.getCreateBy());
            model.setCreateDate(view.getCreateDate());
            model.setUpdateBy(1111);
        }

        return model;
    }
}
