package com.ese.transform;

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
        view.setStartBarcode(model.getStartBarcode());
        view.setFinishBarcode(model.getFinishBarcode());
        view.setBatchNo(model.getBatchNo());
        view.setDocumentDate(model.getProductionDate());
        view.setMsItemModel(model.getMsItemModel());
        view.setQty(Utils.parseInt(model.getQty(), 0));
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
        model.setStartBarcode(view.getStartBarcode());
        model.setFinishBarcode(view.getFinishBarcode());
        model.setBatchNo(view.getBatchNo());
        model.setProductionDate(view.getDocumentDate());
        model.setMsItemModel(view.getMsItemModel());
        model.setQty(Utils.parseString(view.getQty(), "0"));
        model.setRemark(view.getRemark());
        model.setCreateDate(view.getCreateDate());
        model.setCreateBy(view.getCreateBy());
        model.setUpdateDate(view.getUpdateDate());
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
            model.setStatus(1);
        }
        System.out.println(model.getProductionDate());
        return model;
    }
}
