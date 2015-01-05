package com.ese.transform;

import com.ese.model.StatusBarcodeRegiterValue;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class BarcodeRegisterTransform {
    public BarcodeRegisterView transformToView(final BarcodeRegisterModel model){
        BarcodeRegisterView view = null;
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
        view.setCreateBy(model.getCreateBy());
        view.setUpdateDate(model.getUpdateDate());
        view.setUpdateBy(model.getUpdateBy());
        view.setFinishBarcodeText(model.getFinishBarcodeText());
        view.setStartBarcodeText(model.getStartBarcodeText());
        view.setCost(model.getCost());
        view.setIsValid(model.getIsValid());
        view.setVersion(model.getVersion());
        view.setDocumentNo(model.getDocNo());
        view.setStatus(model.getStatus());
        return view;
    }

    public BarcodeRegisterModel transformToModel(final BarcodeRegisterView view){
        BarcodeRegisterModel model = null;
        model = new BarcodeRegisterModel();
        model.setId(view.getId());
        model.setStartBarcode(Utils.parseInt(view.getStartBarcode(), 0));
        model.setFinishBarcode(Utils.parseInt(view.getFinishBarcode(), 0));
        model.setBatchNo(view.getBatchNo());
        model.setProductionDate(view.getDocumentDate());
        model.setMsItemModel(view.getMsItemModel());
        model.setQty(view.getQty());
        model.setRemark(view.getRemark());
        model.setCreateDate(view.getCreateDate());
        model.setCreateBy(view.getCreateBy());
        model.setUpdateDate(Utils.currentDate());
        model.setUpdateBy(view.getUpdateBy());
        model.setIsValid(view.getIsValid());
        model.setStatus(view.getStatus());
        model.setFinishBarcodeText(view.getFinishBarcodeText());
        model.setStartBarcodeText(view.getStartBarcodeText());
        model.setCost(view.getCost());
        model.setVersion(view.getVersion());
        model.setMsItemModel(view.getMsItemModel());
        model.setDocNo(view.getDocumentNo());

        if(Utils.isZero(view.getId())){
            model.setCreateDate(Utils.currentDate());
            model.setCreateBy(9999);
            model.setUpdateDate(Utils.currentDate());
            model.setUpdateBy(8888);
            model.setIsValid(1);
            model.setVersion(1);
            model.setStatus(StatusBarcodeRegiterValue.INPROCESS);
        }
        return model;
    }
}
