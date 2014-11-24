package com.ese.transform;

import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class BarcodeRegisterTransform {
    public BarcodeRegisterView transformToView(BarcodeRegisterModel model){
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
        return view;
    }

    public BarcodeRegisterModel transformToModel(BarcodeRegisterView view){
        BarcodeRegisterModel model = null;

        return model;
    }
}
