package com.ese.transform;

import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.view.BarcodeRegisterView;
import org.springframework.stereotype.Component;

@Component
public class BarcodeRegisterTransform {
    public BarcodeRegisterView transformToView(BarcodeRegisterModel model){
        BarcodeRegisterView view = null;

        return view;
    }

    public BarcodeRegisterModel transformToModel(BarcodeRegisterView view){
        BarcodeRegisterModel model = null;

        return model;
    }
}
