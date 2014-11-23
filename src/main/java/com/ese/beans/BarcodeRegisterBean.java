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

    @PostConstruct
    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
//        msItemModelList = barcodeRegisterService.getAllItemMaster();
        msItemModelList = new ArrayList<MSItemModel>();
//        MSItemModel msItemModel;
//        for (int i = 0; i < 100; i++) {
//            msItemModel = new MSItemModel();
//            msItemModel.setId(i);
//            msItemModel.setItemId(i+"id");
//            msItemModel.setItemName(i+"name");
//            msItemModel.setDSGThaiItemDescription(i+"DSG");
//            msItemModelList.add(msItemModel);
//        }
    }

    public void q(){
        System.out.println("q");
    }
    public void q2(){
        System.out.println("q2");
    }
    public void q3(){
        System.out.println("q3");
    }
    public void q4(){
        System.out.println("q4");
    }
    public void q5(){
        System.out.println("q5");
    }
    public void q6(){
        System.out.println("q6");
    }

    public void onInitSearch(){
        System.out.println("onInitSearch");
        selectType = "3";
        productSearch = "";
    }

    public void onSubmitSearch(){
        log.debug("-- onSubmitSearch()");
        msItemModelList = barcodeRegisterService.findByCondition(selectType, productSearch);
    }
}
