package com.ese.service;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.PalletModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.dao.PalletDAO;
//import com.ese.model.view.PalletMeanagementView;
import com.ese.model.view.PalletMeanagementView;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<PalletMeanagementView> findPalletJoinLocation(){
        log.debug("findPalletJoinLocation().");
        List<PalletMeanagementView> palletMeanegementViewList = new ArrayList<PalletMeanagementView>();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletMeanagementView> findByChang(int status, int warehouse, int conveyorLine){
        log.debug("findByChang().");
        List<PalletMeanagementView> palletMeanegementViewList = new ArrayList<PalletMeanagementView>();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyorLine);

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }
}
