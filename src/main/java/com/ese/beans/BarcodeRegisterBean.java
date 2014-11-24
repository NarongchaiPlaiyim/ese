package com.ese.beans;



import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.service.BarcodeRegisterService;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodeRegisterBean")
public class BarcodeRegisterBean extends Bean{
    @ManagedProperty("#{barcodeRegisterService}") private BarcodeRegisterService barcodeRegisterService;

    private BarcodeRegisterView barcodeRegisterView;
    private List<MSItemModel> msItemModelList;
    private List<BarcodeRegisterModel> barcodeRegisterModelList;
    private BarcodeRegisterModel barcodeRegisterModel;
    private String selectType;
    private String productSearch;
    private boolean flagBtnSelect;
    private boolean flagBtnDelete;
    private boolean flagBtnSave;
    private boolean flagBtnEdit;

    @PostConstruct
    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
        msItemModelList = Collections.EMPTY_LIST;
        barcodeRegisterModelList = Collections.EMPTY_LIST;
        initBtn();
        onLoadDataTable();
    }

    private void initBtn(){
        flagBtnSelect = true;
        flagBtnDelete = true;
        flagBtnSave = true;
        flagBtnEdit = true;
    }

    private void onLoadDataTable(){
        barcodeRegisterModelList = barcodeRegisterService.getByIsValid();
        log.debug("-- onLoadDataTable() returned size = {}", barcodeRegisterModelList.size());
    }

    public void onClickButtonNew(){
        log.debug("-- onClickButtonNew()");
        System.out.println("-- onClickButtonNew()");
        barcodeRegisterView = new BarcodeRegisterView();
        flagBtnDelete = true;
        flagBtnSave = false;
        flagBtnEdit = true;
    }

    public void calculator(){
        log.debug("-- calculator()");
        final int qty = barcodeRegisterView.getQty();
        final int start = Utils.parseInt(barcodeRegisterView.getStartBarcode(), 0);
        final int finish = (qty + start) - 1;
        final String result = finish > 999999999 ? "999999999" : String.format("%09d", finish);
        barcodeRegisterView.setFinishBarcode(result);
    }

    public void onInitSearch(){
        log.debug("-- onInitSearch()");
        selectType = "3";
        productSearch = "";
    }

    public void onSubmitSearch(){
        log.debug("-- onSubmitSearch()");
        msItemModelList = barcodeRegisterService.findByCondition(selectType, productSearch);
    }

    public void onClickTableDialog(){
        log.debug("-- onClickTable()");
        flagBtnSelect = false;
    }

    public void onClickTable(){
        log.debug("-- onClickTable()");
        flagBtnSave = true;
        flagBtnDelete = false;
        flagBtnEdit = flagBtnDelete;
        barcodeRegisterView = barcodeRegisterService.convertToView(barcodeRegisterModel);
    }

    public void onClickSelectOnDialog(){
        log.debug("-- onClickSelectOnDialog()");
        flagBtnSelect = true;
    }

    public void onDelete(){
        log.debug("-- onDelete()");
        flagBtnDelete = true;
        barcodeRegisterService.delete(barcodeRegisterModel);
        onLoadDataTable();
        initBtn();
    }

    public void onSave(){
        System.out.println("onSave()");
        try {
            Thread.sleep(2000);
            initBtn();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void onEdit(){
        System.out.println("onEdit()");
        try {
            Thread.sleep(2000);
            initBtn();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
