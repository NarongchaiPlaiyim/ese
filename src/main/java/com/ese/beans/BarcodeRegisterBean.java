package com.ese.beans;



import com.ese.model.db.MSItemModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.service.BarcodeRegisterService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodeRegisterBean")
public class BarcodeRegisterBean extends Bean{
    @ManagedProperty("#{barcodeRegisterService}") private BarcodeRegisterService barcodeRegisterService;

    private BarcodeRegisterView barcodeRegisterView;
    private List<MSItemModel> msItemModelList;
    private String selectType;
    private String productSearch;
    private boolean flagBtnSelect;
    private MSItemModel msItemModel;

    @PostConstruct
    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
        msItemModelList = new ArrayList<MSItemModel>();
        msItemModel = new MSItemModel();
        flagBtnSelect = true;
    }



    public void onInitSearch(){
        selectType = "3";
        productSearch = "";
    }

    public void onSubmitSearch(){
        log.debug("-- onSubmitSearch()");
        msItemModelList = barcodeRegisterService.findByCondition(selectType, productSearch);
    }

    public void onClickTable(){
        flagBtnSelect = false;
        System.out.println(msItemModel.toString());
    }
}
