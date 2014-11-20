package com.ese.service;

import com.ese.model.ConveyorLineModel;
import com.ese.model.PalletModel;
import com.ese.model.WarehouseModel;
import com.ese.model.dao.PalletDAO;
import com.ese.model.view.PalletMeanegementView;
import com.ese.transform.PalletManegementTranform;
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
    PalletManegementTranform palletManegementTranform;

    public List<PalletMeanegementView> findPalletJoinLocation(){
        log.debug("findPalletJoinLocation().");
        List<PalletMeanegementView> palletMeanegementViewList = new ArrayList<PalletMeanegementView>();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletMeanegementView> findByChang(int status, int warehouse, int conveyor){
        log.debug("findByChang().");
        List<PalletMeanegementView> palletMeanegementViewList = new ArrayList<PalletMeanegementView>();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyor);

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManegementTranform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }
}
