package com.ese.transform;

import com.ese.model.StatusBarcodeRegiterValue;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class BarcodeRegisterTransform {

    public BarcodeRegisterView transformToView(final BarcodeRegisterModel model){
        BarcodeRegisterView view = null;
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        view = new BarcodeRegisterView();
        view.setId(model.getId());
        view.setStartBarcode(String.format("%09d", model.getStartBarcode()));
        view.setFinishBarcode(String.format("%09d", model.getFinishBarcode()));
        view.setBatchNo(model.getBatchNo());
        view.setDocumentDate(model.getProductionDate());
        view.setMsItemModel(model.getMsItemModel());
        view.setQty(model.getQty());
        view.setRemark(model.getRemark());
        view.setCreateDate(model.getCreateDate());
        view.setCreateBy(staffModel);
        view.setUpdateDate(model.getUpdateDate());
        view.setUpdateBy(staffModel);
        view.setFinishBarcodeText(model.getFinishBarcodeText());
        view.setStartBarcodeText(model.getStartBarcodeText());
        view.setCost(model.getCost());
        view.setIsValid(model.getIsValid());
        view.setVersion(model.getVersion());
        view.setDocumentNo(model.getDocNo());
        view.setStatus(model.getStatus());
        view.setReceivedQty(model.getReceivedQty());
        return view;
    }

    public BarcodeRegisterModel transformToModel(final BarcodeRegisterView view){
        BarcodeRegisterModel model = null;
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        model = new BarcodeRegisterModel();

        if(Utils.isZero(view.getId())){
            model.setCreateDate(Utils.currentDate());
            model.setCreateBy(staffModel);
            model.setUpdateDate(Utils.currentDate());
            model.setUpdateBy(staffModel);
            model.setIsValid(1);
            model.setVersion(1);
            model.setStatus(StatusBarcodeRegiterValue.CREATE);
        }  else {
            model.setStatus(view.getStatus());
            model.setCreateDate(view.getCreateDate());
            model.setCreateBy(view.getCreateBy());
            model.setUpdateDate(Utils.currentDate());
            model.setUpdateBy(view.getUpdateBy());
            model.setIsValid(view.getIsValid());
            model.setReceivedQty(view.getReceivedQty());
        }

        model.setId(view.getId());
        model.setStartBarcode(Utils.parseInt(view.getStartBarcode(), 0));
        model.setFinishBarcode(Utils.parseInt(view.getFinishBarcode(), 0));
        model.setBatchNo(view.getBatchNo());
        model.setProductionDate(view.getDocumentDate());
        model.setMsItemModel(view.getMsItemModel());
        model.setQty(view.getQty());
        model.setRemark(view.getRemark());
        model.setFinishBarcodeText(view.getFinishBarcodeText());
        model.setStartBarcodeText(view.getStartBarcodeText());
        model.setCost(view.getCost());
        model.setVersion(view.getVersion());
        model.setMsItemModel(view.getMsItemModel());
        model.setDocNo(view.getDocumentNo());

        return model;
    }
}
