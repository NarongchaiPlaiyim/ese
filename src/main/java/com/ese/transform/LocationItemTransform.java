package com.ese.transform;

import com.ese.model.db.MSItemModel;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.view.LocationItemView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class LocationItemTransform extends Transform{

    public List<LocationItemView> transformToViewList(List<MSLocationItemsModel> locationItemsModelList){
        log.debug("transformToViewList().");
        List<LocationItemView> locationItemViewList = new ArrayList<LocationItemView>();

        for (MSLocationItemsModel model : locationItemsModelList){
            LocationItemView locationItemView = tranformToView(model);
            if (!Utils.isNull(locationItemView)){
                locationItemViewList.add(locationItemView);
            }
        }
        return locationItemViewList;
    }

    public LocationItemView tranformToView(MSLocationItemsModel msLocationItemsModel){
        log.debug("tramsformToView().");
        LocationItemView locationItemView ;

        if (msLocationItemsModel.getMsLocationModel().getStatus() < 2 &&
                (msLocationItemsModel.getMsLocationModel().getQty() - msLocationItemsModel.getMsLocationModel().getReservedQty()) < msLocationItemsModel.getMsLocationModel().getCapacity()){
            locationItemView = new LocationItemView();
            locationItemView.setId(msLocationItemsModel.getId());
            log.debug("--------------------- {}",msLocationItemsModel.getId());
            locationItemView.setWarehouse(msLocationItemsModel.getMsLocationModel().getMsWarehouseModel().getWarehouseCode());
            locationItemView.setLocation(msLocationItemsModel.getMsLocationModel().getLocationBarcode());
            locationItemView.setAvaliable(msLocationItemsModel.getMsLocationModel().getCapacity() - msLocationItemsModel.getMsLocationModel().getQty() - msLocationItemsModel.getMsLocationModel().getReservedQty());
            return locationItemView;
        }

        return null;
    }

    public MSLocationItemsModel transformToModel(MSItemModel msItemModel, MSLocationModel msLocationModel){
        log.debug("transformToModel().");
        MSLocationItemsModel model = new MSLocationItemsModel();

        model.setId(model.getId());
        model.setMsLocationModel(msLocationModel);
        model.setMsItemModel(msItemModel);

        model.setCreateBy(1111);
        model.setCreateDate(Utils.currentDate());
        model.setUpdateBy(1111);
        model.setUpdateDate(Utils.currentDate());
        model.setIsValid(1);
        model.setVersion(1);

        return model;
    }
}
