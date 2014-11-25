package com.ese.transform;

import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationTransform extends Transform {

    public List<LocationView> transformToViewList(List<MSLocationModel> locationModels){
        log.debug("transformToViewList() {}", locationModels.size());
        List<LocationView> locationViewList = new ArrayList<LocationView>();

        for (MSLocationModel model : locationModels){
            locationViewList.add(transformToView(model));
        }

        return locationViewList;
    }

    public LocationView transformToView(MSLocationModel msLocationModel){
        log.debug("transformToView().");
        LocationView locationView = new LocationView();

        locationView.setId(msLocationModel.getId());
        locationView.setLocationBarcode(msLocationModel.getLocationBarcode());
        locationView.setLocationName(msLocationModel.getLocationName());

        if (!Utils.isNull(msLocationModel.getMsWarehouseModel())){
            locationView.setWarehouseModel(msLocationModel.getMsWarehouseModel());
        } else {
            locationView.setWarehouseModel(new MSWarehouseModel());
        }

        if (!Utils.isNull(msLocationModel.getCapacity())){
            locationView.setCapacity(msLocationModel.getCapacity());
        } else {
            locationView.setCapacity(0);
        }

        locationView.setRemark(msLocationModel.getRemark());

        if (!Utils.isNull(msLocationModel.getQty())){
            locationView.setQty(msLocationModel.getQty());
        } else {
            locationView.setQty(0);
        }

        if (!Utils.isNull(msLocationModel.getStatus())){
            locationView.setStatus(msLocationModel.getStatus());
        } else {
            locationView.setStatus(0);
        }

        locationView.setCreateBy(msLocationModel.getCreateBy());
        locationView.setCreateDate(msLocationModel.getCreateDate());
        locationView.setUpdateBy(msLocationModel.getUpdateBy());
        locationView.setUpdateDate(msLocationModel.getUpdateDate());

        if (!Utils.isNull(msLocationModel.getIsValid())){
            locationView.setIsvalid(msLocationModel.getIsValid());
        } else {
            locationView.setIsvalid(0);
        }

        if (!Utils.isNull(msLocationModel.getVersion())){
            locationView.setVersion(msLocationModel.getVersion());
        } else {
            locationView.setVersion(0);
        }

        if (!Utils.isNull(msLocationModel.getReservedQty())){
            locationView.setReservedQty(msLocationModel.getReservedQty());
        } else {
            locationView.setReservedQty(0);
        }

        if (!Utils.isNull(msLocationModel.getIsMix())){
            locationView.setIsMix(msLocationModel.getIsMix());
        } else {
            locationView.setIsMix(0);
        }

        return locationView;
    }
}
