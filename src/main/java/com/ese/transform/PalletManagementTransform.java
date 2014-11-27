package com.ese.transform;

import com.ese.model.StatusValue;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.PalletModel;
import com.ese.model.view.PalletManagementView;
import com.ese.model.view.report.PalletListReport;
import com.ese.model.view.report.PalletManagemengModelReport;
import com.ese.model.view.report.PalletManagementViewReport;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
            log.debug("------Model : {}",model.getId());
            log.debug("###### View : {}",palletMeanegementView.getId());
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

        if(Utils.isNull(palletModel.getTagPrint())){
            palletMeanegementView.setTagPrint(0);
        } else {
            palletMeanegementView.setTagPrint(palletModel.getTagPrint());
        }
        if(Utils.isNull(palletModel.getQty())){
            palletMeanegementView.setQty(0);
        } else {
            palletMeanegementView.setQty(palletModel.getQty());
        }
        if(Utils.isNull(palletModel.getReservedQty())){
            palletMeanegementView.setReservedQty(0);
        } else {
            palletMeanegementView.setReservedQty(palletModel.getReservedQty());
        }

        if(Utils.isNull(palletModel.getStatus())){
            palletMeanegementView.setStatus(transformStatusToString(0));
        } else {
            palletMeanegementView.setStatus(transformStatusToString(palletModel.getStatus()));
        }


        if(Utils.isNull(palletModel.getCreateBy())){
            palletMeanegementView.setCreateBy(0);
        } else {
            palletMeanegementView.setCreateBy(palletModel.getCreateBy());
        }


//        if(Utils.isNull(palletModel.getCreateDate())){
//            palletMeanegementView.setCreateDate(palletModel.getCreateDate());
//        } else {
            palletMeanegementView.setCreateDate(palletModel.getCreateDate());
//        }


        if(Utils.isNull(palletModel.getUpdateBy())){
            palletMeanegementView.setUpdateBy(0);
        } else {
            palletMeanegementView.setUpdateBy(palletModel.getUpdateBy());
        }


//        if(Utils.isNull(palletModel.getUpdateDate())){
//            palletMeanegementView.setUpdateDate(palletModel.getUpdateDate());
//        } else {
            palletMeanegementView.setUpdateDate(palletModel.getUpdateDate());
//        }


        if(Utils.isNull(palletModel.getIsValid())){
            palletMeanegementView.setIsValid(0);
        } else {
            palletMeanegementView.setIsValid(palletModel.getIsValid());
        }


        if(Utils.isNull(palletModel.getVersion())){
            palletMeanegementView.setVersion(0);
        } else {
            palletMeanegementView.setVersion(palletModel.getVersion());
        }


        if(Utils.isNull(palletModel.getCapacity())){
            palletMeanegementView.setCapacity(BigDecimal.ZERO);
        } else {
            palletMeanegementView.setCapacity(palletModel.getCapacity());
        }


        palletMeanegementView.setConvetorLine(palletModel.getMsWorkingAreaModel());
        palletMeanegementView.setShift(palletModel.getMsShiftModel());

        return palletMeanegementView;
    }

    public StatusValue transformStatusToString(int status){
        log.debug("tranformStatus().");
        StatusValue statusName = null;
        switch (status){
            case 0 : statusName = StatusValue.CANCEL;break;
            case 1 : statusName = StatusValue.CREATE;break;
            case 2 : statusName = StatusValue.COMPLETED;break;
            case 3 : statusName = StatusValue.PRINTED;break;
            case 4 : statusName = StatusValue.LOCATED;break;
            case 5 : statusName = StatusValue.RESERVED;break;
            case 6 : statusName = StatusValue.CLOSED;break;
        }
        return statusName;
    }

    public int transformStatusInt(StatusValue status){
        log.debug("transformStatusInt().");
        return status.getId();
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

    public PalletModel transformToModelByChangeLocation(PalletManagementView palletManagementView, MSLocationModel msLocationModel){
        PalletModel palletModel = new PalletModel();

        palletModel.setId(palletManagementView.getId());
        palletModel.setPalletBarcode(palletManagementView.getPalletBarcode());
        palletModel.setMsWarehouseModel(palletManagementView.getWarehouseModel());
        palletModel.setMsItemModel(palletManagementView.getItemModel());
        log.debug("msLocationModel : {}", msLocationModel.getId());
        palletModel.setMsLocationModel(msLocationModel);
        palletModel.setTagPrint(palletManagementView.getTagPrint() + 1);
        palletModel.setQty(palletManagementView.getQty());
        palletModel.setReservedQty(palletManagementView.getReservedQty());
        palletModel.setStatus(transformStatusInt(palletManagementView.getStatus()));
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

    public List<PalletManagementViewReport> transformSQL(List<PalletManagemengModelReport> modelReportList){
        log.debug("transformSQL().");
        List<PalletManagementViewReport> reportViews = new ArrayList<PalletManagementViewReport>();
        PalletListReport palletListReport = new PalletListReport();

        String dSGThaiItemDescription = "";
        String warehouseCode = "";
        String palletBarcode = "";
        String locationBarcode = "";
        String createDate = "";
        String grade = "";
        String workingName = "";
        String bathcgNo = "";
        int countId = 0;

        for (PalletManagemengModelReport model : modelReportList){
            PalletManagementViewReport palletManagementViewReport = new PalletManagementViewReport();
            List<PalletListReport> palletListReports = new ArrayList<PalletListReport>();

            if (!dSGThaiItemDescription.equalsIgnoreCase(model.getDSGThaiItemDescription())){
                palletManagementViewReport.setDSGThaiItemDescription(model.getDSGThaiItemDescription());
                dSGThaiItemDescription = model.getDSGThaiItemDescription();
            }

            if (!warehouseCode.equalsIgnoreCase(model.getWarehouseCode())){
                palletManagementViewReport.setWarehouseCode(model.getWarehouseCode());
                warehouseCode = model.getWarehouseCode();
            }

            if (!palletBarcode.equalsIgnoreCase(model.getPalletBarcode())){
                palletManagementViewReport.setPalletBarcode(model.getPalletBarcode());
                palletBarcode = model.getPalletBarcode();
            }

            if (!locationBarcode.equalsIgnoreCase(model.getLocationBarcode())){
                palletManagementViewReport.setLocationBarcode(model.getLocationBarcode());
                locationBarcode = model.getLocationBarcode();
            }

            if (!createDate.equalsIgnoreCase(model.getCreateDate())){
                palletManagementViewReport.setCreateDate(model.getCreateDate());
                createDate = model.getCreateDate();
            }

            if (!grade.equalsIgnoreCase(model.getGrade())){
                palletManagementViewReport.setGrade(model.getGrade());
                grade = model.getGrade();
            }

            if (!workingName.equalsIgnoreCase(model.getWorkingName())){
                palletManagementViewReport.setWorkingName(model.getWorkingName());
                workingName = model.getWorkingName();
            }

            if (!bathcgNo.equalsIgnoreCase(model.getBathcgNo()) && countId != model.getCountId()){
                palletListReport.setBathcgNo(model.getBathcgNo());
                palletListReport.setCountId(model.getCountId());
                palletListReports.add(palletListReport);
            }
            palletManagementViewReport.setPalletListReports(palletListReports);
            reportViews.add(palletManagementViewReport);
        }
        return reportViews;
    }
}
