package com.ese.service;

import com.ese.model.dao.LocationItemsDAO;
import com.ese.model.db.PalletModel;
import com.ese.model.dao.PalletDAO;
import com.ese.model.view.PalletManagementView;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PalletService extends Service{
    @Resource private PalletDAO palletDAO;
    @Resource private LocationItemsDAO locationItemsDAO;
    @Resource private PalletManagementTransform palletManagementTransform;

    public void test(){
        locationItemsDAO.findLocationByPallet();
    }

    public List<PalletManagementView> findPalletJoinLocation(){
        log.debug("findPalletJoinLocation().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManagementTransform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletManagementView> findByChang(int status, int warehouse, int conveyorLine, int location, String keyItemDescription){
        log.debug("findByChang().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyorLine, location, keyItemDescription);

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels.size());
            palletMeanegementViewList = palletManagementTransform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public void onUpdateByPrintTag(PalletManagementView palletManagementView, String redirect) {
        log.debug("onSaveByPrintTag().");
        PalletModel palletModel = null;

        try{
            if (!Utils.isNull(palletManagementView)){
                palletModel = palletManagementTransform.transformToMode(palletManagementView, redirect);
            } else {
                palletModel = new PalletModel();
            }

            palletDAO.update(palletModel);
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
    }
}
