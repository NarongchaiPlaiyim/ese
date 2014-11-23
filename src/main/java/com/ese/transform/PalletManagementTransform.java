package com.ese.transform;

import com.ese.model.db.MSItemModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.PalletModel;
//import com.ese.model.view.PalletMeanagementView;
import com.ese.model.view.ItemView;
import com.ese.model.view.LocationView;
import com.ese.model.view.PalletManagementView;
import com.ese.model.view.WarehouseView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class PalletManagementTransform extends Transform {

    public List<PalletManagementView> tranformToViewList(List<PalletModel> palletModelList){
        log.debug("tranformToViewList().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        for (PalletModel model : palletModelList){
            PalletManagementView palletMeanegementView = tranformToView(model);
            palletMeanegementViewList.add(palletMeanegementView);
        }

        return palletMeanegementViewList;
    }

    private PalletManagementView tranformToView(PalletModel palletModel){
        log.debug("tranformToView().");
        PalletManagementView palletMeanegementView = new PalletManagementView();

        palletMeanegementView.setId(palletModel.getId());
        palletMeanegementView.setPalletBarcode(palletModel.getPalletBarcode());
        palletMeanegementView.setWarehouseModel(palletModel.getMsWarehouseModel());
        palletMeanegementView.setItemModel(palletModel.getMsItemModel());
        palletMeanegementView.setLocationModel(palletModel.getMsLocationModel());

        palletMeanegementView.setTagPrint(palletModel.getTagPrint());
        palletMeanegementView.setQty(palletModel.getQty());
        palletMeanegementView.setReservedQty(palletModel.getReservedQty());
        log.debug("palletModel.getStatus() {}", palletModel.getStatus());
        palletMeanegementView.setStatus(transformStatusToString(palletModel.getStatus()));
        palletMeanegementView.setCreateBy(palletModel.getCreateBy());
        palletMeanegementView.setCreateDate(palletModel.getCreateDate());
        palletMeanegementView.setUpdateBy(palletModel.getUpdateBy());
        palletMeanegementView.setUpdateDate(palletModel.getUpdateDate());
        palletMeanegementView.setIsValid(palletModel.getIsValid());
        palletMeanegementView.setVersion(palletModel.getVersion());
        palletMeanegementView.setCapacity(palletModel.getCapacity());
        palletMeanegementView.setConvetorLine(palletModel.getMsWorkingAreaModel());
        palletMeanegementView.setShift(palletModel.getMsShiftModel());

        return palletMeanegementView;
    }

    private List<WarehouseView> transformWarehouseToViewList(List<MSWarehouseModel> warehouseModelList){
        log.debug("transFormWarehouseToView().");
        List<WarehouseView> warehouseViewList = new ArrayList<WarehouseView>();

        if (Utils.isSafetyList(warehouseModelList)){
            log.debug("warehouseModelList Size : {}",warehouseModelList.size());
            for (MSWarehouseModel model : warehouseModelList){
                WarehouseView warehouseView = tramsformWarehouseToView(model);
                warehouseViewList.add(warehouseView);
            }
        } else {
            log.debug("warehouseModels Size Zero.");
        }
        return warehouseViewList;
    }

    private WarehouseView tramsformWarehouseToView(MSWarehouseModel warehouseModel){
        log.debug("tramsformWarehouseToView().");
        WarehouseView warehouseView = new WarehouseView();

        if (!Utils.isNull(warehouseModel)){
            warehouseView.setId(warehouseModel.getId());
            warehouseView.setWarehouseCode(warehouseModel.getWarehouseCode());
            warehouseView.setWarehouseName(warehouseModel.getWarehouseName());
            warehouseView.setRemark(warehouseModel.getRemark());
            warehouseView.setCreateBy(warehouseModel.getCreateBy());
            warehouseView.setCreateDate(warehouseModel.getCreateDate());
            warehouseView.setUpdateBy(warehouseModel.getUpdateBy());
            warehouseView.setUpdateDate(warehouseModel.getUpdateDate());
            warehouseView.setIsvalid(warehouseModel.getIsValid());
            warehouseView.setStatus(warehouseModel.getStatus());
            warehouseView.setVersion(warehouseModel.getVersion());
        }
        return warehouseView;
    }

    private List<ItemView> transformItemToViewList(List<MSItemModel> itemModelList){
        log.debug("transformItemToViewList().");
        List<ItemView> itemViewList = new ArrayList<ItemView>();

        if (Utils.isSafetyList(itemModelList)){
            log.debug("itemModelList Size : {}", itemModelList.size());
            for (MSItemModel modelList : itemModelList){
                ItemView itemView = transformItemToView(modelList);
                itemViewList.add(itemView);
            }
        } else {
            log.debug("itemModelList Size Zero");
        }

        return itemViewList;
    }

    private ItemView transformItemToView(MSItemModel itemModel){
        log.debug("transformItemToViewList().");
        ItemView itemView = new ItemView();

        if (!Utils.isNull(itemModel)){
            itemView.setId(itemModel.getId());
            itemView.setItemId(itemModel.getItemId());
            itemView.setItemName(itemModel.getItemName());
            itemView.setItemGrpupId(itemModel.getItemGroupId());
            itemView.setItemType(itemModel.getItemType());
            itemView.setPackagingGroupId(itemModel.getPackagingGroupId());
            itemView.setNetWeight(itemModel.getNetWeight());
            itemView.setDSG_MaxStock(itemModel.getDSG_MaxStock());
            itemView.setDSG_MinStock(itemModel.getDSG_MinStock());
            itemView.setDSG_SafetyStock(itemModel.getDSG_SafetyStock());
            itemView.setDSG_InternalItemId(itemModel.getDSG_InternalItemId());
            itemView.setDSGSize(itemModel.getDSGSize());
            itemView.setDSG_RimWidth(itemModel.getDSG_RimWidth());
            itemView.setDSG_MaxLoad(itemModel.getDSG_MaxLoad());
            itemView.setDSG_MaxInflation(itemModel.getDSG_MaxInflation());
            itemView.setDSG_MaxSpeed(itemModel.getDSG_MaxSpeed());
            itemView.setDSG_E_Mark(itemModel.getDSG_E_Mark());
            itemView.setDSG_ETRTO(itemModel.getDSG_ETRTO());
            itemView.setDSG_LoadIndex(itemModel.getDSG_LoadIndex());
            itemView.setDSG_SpeedIndex(itemModel.getDSG_SpeedIndex());
            itemView.setDSG_RecommendedRim(itemModel.getDSG_RecommendedRim());
            itemView.setDSG_TreadDept(itemModel.getDSG_TreadDept());
            itemView.setDSG_AlternativeRim(itemModel.getDSG_AlternativeRim());
            itemView.setDSG_OfMold(itemModel.getDSG_OfMold());
            itemView.setDSG_Valve(itemModel.getDSG_Valve());
            itemView.setDSG_ID(itemModel.getDSG_ID());
            itemView.setDSG_QtyPerPallet(itemModel.getDSG_QtyPerPallet());
            itemView.setDSG_CurPerShif(itemModel.getDSG_CurPerShif());
            itemView.setDSG_ColorId1(itemModel.getDSG_ColorId1());
            itemView.setDSG_ColorSeparate1(itemModel.getDSG_ColorSeparate1());
            itemView.setDSG_ColorId2(itemModel.getDSG_ColorId2());
            itemView.setDSG_ColorSeparate2(itemModel.getDSG_ColorSeparate2());
            itemView.setDSG_ColorId3(itemModel.getDSG_ColorId3());
            itemView.setDSG_CreateDate(itemModel.getDSG_CreateDate());
            itemView.setDSG_Originator(itemModel.getDSG_Originator());
            itemView.setDSG_ImageDir(itemModel.getDSG_ImageDir());
            itemView.setDSGProductGroupID(itemModel.getDSGProductGroupID());
            itemView.setDSGSubGroupID(itemModel.getDSGSubGroupID());
            itemView.setDSGPatternID(itemModel.getDSGPatternID());
            itemView.setDSGBandID(itemModel.getDSGBandID());
            itemView.setDSG_Model(itemModel.getDSG_Model());
            itemView.setDSGTyre_Types(itemModel.getDSGTyre_Types());
            itemView.setTyre_Type(itemModel.getTyre_Type());
            itemView.setDSGRimSize(itemModel.getDSGRimSize());
            itemView.setDSGPLYRating(itemModel.getDSGPLYRating());
            itemView.setDSGThaiItemDescription(itemModel.getDSGThaiItemDescription());
            itemView.setDSG_EU(itemModel.getDSG_EU());
            itemView.setDSG_ItemLabelId(itemModel.getDSG_ItemLabelId());
            itemView.setDSG_DescriptionTextId(itemModel.getDSG_DescriptionTextId());
            itemView.setDSG_GradeProductId(itemModel.getDSG_GradeProductId());
            itemView.setDSG_FeatureProductId(itemModel.getDSG_FeatureProductId());
            itemView.setDSG_DescriptionPrefixId(itemModel.getDSG_DescriptionPrefixId());
            itemView.setDSG_GroupDescription(itemModel.getDSG_GroupDescription());
            itemView.setDSG_CopyFrom(itemModel.getDSG_CopyFrom());
            itemView.setDSG_MKProductGroupId(itemModel.getDSG_MKProductGroupId());
            itemView.setDSG_MKSubproductGroupId(itemModel.getDSG_MKSubproductGroupId());
            itemView.setDSG_MKProductTypeId(itemModel.getDSG_MKProductTypeId());
            itemView.setVersion(itemModel.getVersion());
        }

        return itemView;
    }

    private List<LocationView> transformLocationToViewList(List<MSLocationModel> locationModelList){
        log.debug("transformLocationToViewList().");
        List<LocationView> locationViewList = new ArrayList<LocationView>();

        if (Utils.isSafetyList(locationModelList)){
            log.debug("locationModelList Size : {}", locationModelList.size());
            for (MSLocationModel model : locationModelList){
                LocationView locationView = transformLocationToView(model);
                locationViewList.add(locationView);
            }
        } else {
            log.debug("locationModelList Size Zero");
        }

        return locationViewList;
    }

    private LocationView transformLocationToView(MSLocationModel locationModel){
        LocationView locationView = new LocationView();

        if (!Utils.isNull(locationModel)){
            locationView.setId(locationModel.getId());
            locationView.setLocationBarcode(locationModel.getLocationBarcode());
            locationView.setLocationName(locationModel.getLocationName());
            locationView.setWarehouseModel(locationModel.getMsWarehouseModel());
            locationView.setCapacity(locationModel.getCapacity());
            locationView.setRemark(locationModel.getRemark());
            locationView.setQty(locationModel.getQty());
            locationView.setStatus(locationModel.getStatus());
            locationView.setCreateBy(locationModel.getCreateBy());
            locationView.setCreateDate(locationModel.getCreateDate());
            locationView.setUpdateBy(locationModel.getUpdateBy());
            locationView.setUpdateDate(locationModel.getUpdateDate());
            locationModel.setIsValid(locationModel.getIsValid());
            locationView.setVersion(locationModel.getVersion());
            locationView.setReservedQty(locationModel.getReservedQty());
            locationView.setIsMix(locationModel.getIsMix());
        }

        return locationView;
    }

    public String transformStatusToString(int status){
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

    public int transformStatusInt(String status){
        log.debug("transformStatusInt().");
        int statusId;

        if (("Cancel").equalsIgnoreCase(status)){
            return statusId = 0;
        } else if (("Create").equalsIgnoreCase(status)){
            return statusId = 1;
        } else if (("Completed").equalsIgnoreCase(status)){
            return statusId = 2;
        } else if (("Printed").equalsIgnoreCase(status)){
            return statusId = 3;
        } else if (("Located").equalsIgnoreCase(status)){
            return statusId = 4;
        } else if (("reserved").equalsIgnoreCase(status)){
            return statusId = 5;
        } else {
            return statusId = 6;
        }
    }

    public PalletModel transformToMode(PalletManagementView palletManagementView, String redirect){
        log.debug("transformToMode().");
        PalletModel palletModel = new PalletModel();

        palletModel.setId(palletManagementView.getId());
        palletModel.setPalletBarcode(palletManagementView.getPalletBarcode());
        palletModel.setMsWarehouseModel(palletManagementView.getWarehouseModel());
        palletModel.setMsItemModel(palletManagementView.getItemModel());
        palletModel.setMsLocationModel(palletManagementView.getLocationModel());
        palletModel.setTagPrint(palletManagementView.getTagPrint() + 1);
        palletModel.setQty(palletManagementView.getQty());
        palletModel.setReservedQty(palletManagementView.getReservedQty());

        if ("PrintTag".equalsIgnoreCase(redirect)){
            if (transformStatusInt(palletManagementView.getStatus()) == 2){
                palletModel.setStatus(transformStatusInt(palletManagementView.getStatus()) + 1);
            } else {
                palletModel.setStatus(transformStatusInt(palletManagementView.getStatus()));
            }
        } else if ("ClosePallet".equalsIgnoreCase(redirect)){
            log.debug("redirect : {}", redirect);
            palletModel.setStatus(6);
        }


        palletModel.setCreateBy(palletManagementView.getCreateBy());
        palletModel.setCreateDate(palletManagementView.getCreateDate());
        palletModel.setUpdateBy(palletManagementView.getUpdateBy());
        palletModel.setUpdateDate(new Date());
        palletModel.setIsValid(palletManagementView.getIsValid());
        palletModel.setVersion(palletManagementView.getVersion());
        palletModel.setCapacity(palletManagementView.getCapacity());
        palletModel.setMsWorkingAreaModel(palletManagementView.getConvetorLine());
        palletModel.setMsShiftModel(palletManagementView.getShift());

        return palletModel;
    }
}
