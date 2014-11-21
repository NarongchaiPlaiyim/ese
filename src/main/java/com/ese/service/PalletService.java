package com.ese.service;

import com.ese.model.db.PalletModel;
import com.ese.model.dao.PalletDAO;
//import com.ese.model.view.PalletMeanagementView;
import com.ese.model.view.PalletManagementView;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PalletService extends Service implements Serializable {
    @Resource
    PalletDAO palletDAO;
    @Resource
    PalletManagementTransform palletManegementTranform;

    public List<PalletManagementView> findPalletJoinLocation(){
        log.debug("findPalletJoinLocation().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletManagementView> findByChang(int status, int warehouse, int conveyorLine){
        log.debug("findByChang().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyorLine);

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }
}
