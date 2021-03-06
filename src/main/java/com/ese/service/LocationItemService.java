package com.ese.service;

import com.ese.model.dao.MSLocationItemsDAO;
import com.ese.model.db.MSItemModel;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.view.LocationItemView;
import com.ese.transform.LocationItemTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class LocationItemService extends Service{
    private static final long serialVersionUID = 4112578634029870000L;
    @Resource private MSLocationItemsDAO msLocationItemsDAO;
    @Resource private LocationItemTransform locationItemTransform;

    public List<LocationItemView> findLocationByItemId(int itemId){
        List<LocationItemView> locationItemViewList = Utils.getEmptyList();
        try {
            locationItemViewList = msLocationItemsDAO.findLocationByItemId(itemId);
        } catch (Exception e) {
            log.debug("Exception : {}", e);
        }
        return locationItemViewList;
    }

    public List<MSLocationItemsModel> findLocationItemByLocationId(int locationId){
        log.debug("findLocationItem(). {}", locationId);
        List<MSLocationItemsModel> msLocationItemsModels = Utils.getEmptyList();

        if (!Utils.isZero(locationId)){
            msLocationItemsModels = msLocationItemsDAO.findByLocationId(locationId);
            log.debug("msLocationItemsModels Size : {}", msLocationItemsModels.size());
        }

        return msLocationItemsModels;
    }

    public void addToLocationItemModel(List<MSItemModel> msItemModels, MSLocationModel msLocationModel){
        log.debug("addToLocationItemModel() {}, {}", msItemModels.size(),msLocationModel.toString());
        MSLocationItemsModel model;
        List<MSLocationItemsModel> msLocationItemsModels;

        if (Utils.isSafetyList(msItemModels)){
            for (MSItemModel msItemModel : msItemModels){
                model = locationItemTransform.transformToModel(msItemModel, msLocationModel);
                try {
                    msLocationItemsDAO.persist(model);
                    msLocationItemsModels = msLocationItemsDAO.findByLocationId(msLocationModel.getId());
                    Map<Integer, MSLocationItemsModel> hashMap = new HashMap();
                    for(MSLocationItemsModel msLocationItemsModel : msLocationItemsModels){
                        int key = msLocationItemsModel.getMsItemModel().getId();
                        if (!hashMap.containsKey(key)){
                            hashMap.put(key, msLocationItemsModel);
                        } else {
                            log.debug("Delete : {}", msLocationItemsModel.getId());
                            msLocationItemsDAO.delete(msLocationItemsModel);
                        }
                    }
                    log.debug("hashMap : {}", hashMap.toString());

                } catch (Exception e) {
                    log.debug("Exception persist MSLocationItemModel : ", e);
                }
            }
        }
    }

    public void deleteLocationItemModel(List<MSLocationItemsModel> msLocationItemsModels){
        log.debug("delete(). {}", msLocationItemsModels.size());

        if (Utils.isSafetyList(msLocationItemsModels)){
            for (MSLocationItemsModel model : msLocationItemsModels){
                try {
                    msLocationItemsDAO.deleteByUpdate(model);
                } catch (Exception e) {
                    log.debug("Exception error delete MSLocationItemModel : ", e);
                }
            }
        }
    }
}
