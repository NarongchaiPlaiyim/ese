package com.ese.transform;

import com.ese.model.db.PalletModel;
//import com.ese.model.view.PalletMeanagementView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PalletManagementTransform extends Transform {

//    public List<PalletMeanagementView> tranformToViewList(List<PalletModel> palletModelList){
//        log.debug("tranformToViewList().");
//        List<PalletMeanagementView> palletMeanegementViewList = new ArrayList<PalletMeanagementView>();
//
//        for (PalletModel model : palletModelList){
//            PalletMeanagementView palletMeanegementView = tranformToView(model);
//            palletMeanegementViewList.add(palletMeanegementView);
//        }
//
//        return palletMeanegementViewList;
//    }

//    public PalletMeanagementView tranformToView(PalletModel palletModel){
//        log.debug("tranformToView().");
//        PalletMeanagementView palletMeanegementView = new PalletMeanagementView();
//
//        palletMeanegementView.setId(palletModel.getId());
//        palletMeanegementView.setPalletBarcode(palletModel.getPalletBarcode());
//        palletMeanegementView.setWarehouseModel(palletModel.getWherehouseId());
//        palletMeanegementView.setItemModel(palletModel.getItemId());
//        palletMeanegementView.setLocationModel(palletModel.getLocationId());
//        palletMeanegementView.setTagPrint(palletModel.getTagPrint());
//        palletMeanegementView.setQty(palletModel.getQty());
//        palletMeanegementView.setReservedQty(palletModel.getReservedQty());
//        palletMeanegementView.setStatus(tranformStatus(palletModel.getStatus()));
//        palletMeanegementView.setCreateBy(palletModel.getCreateBy());
//        palletMeanegementView.setCreateDate(palletModel.getCreateDate());
//        palletMeanegementView.setUpdateBy(palletModel.getUpdateBy());
//        palletMeanegementView.setUpdateDate(palletModel.getUpdateDate());
//        palletMeanegementView.setIsValid(palletModel.getIsValid());
//        palletMeanegementView.setVersion(palletModel.getVersion());
//        palletMeanegementView.setCapacity(palletModel.getCapacity());
//        palletMeanegementView.setConvetorLine(palletModel.getConveyorLine());
//        palletMeanegementView.setShift(palletModel.getShift());
//
//        return palletMeanegementView;
//    }

    public String tranformStatus(int status){
        log.debug("tranformStatus().");
        String statusName = "";
        switch (status){
            case 0 : statusName = "Cancel";break;
            case 1 : statusName = "Create";break;
            case 2 : statusName = "Completed";break;
            case 3 : statusName = "Printed";break;
            case 4 : statusName = "Located";break;
            case 5 : statusName = "reserved";break;
            case 6 : statusName = "Closed";break;
        }
        return statusName;
    }
}
