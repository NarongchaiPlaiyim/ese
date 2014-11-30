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
    private boolean flagBtnPrint;
    private boolean flagBtnEdit;

    private boolean flagItem;
    private boolean flagQty;
    private boolean flagStartBarcode;

    private String modeBarcode;

    @PostConstruct
    private void init(){
        preLoad();
        barcodeRegisterView = new BarcodeRegisterView();
        msItemModelList = Utils.getEmptyList();
        barcodeRegisterModelList = Utils.getEmptyList();
        initBtn();
        initField();
        onLoadDataTable();
        onClickButtonNew();

    }

    private void initField(){
        flagItem = false;
        flagQty = false;
        flagStartBarcode = false;
    }

    private void initBtn(){
        flagBtnSelect = true;
        flagBtnDelete = true;
        flagBtnSave = true;
        flagBtnEdit = true;
        flagBtnPrint = true;
    }

    private void onLoadDataTable(){
        barcodeRegisterModelList = barcodeRegisterService.getByIsValid();
        log.debug("-- onLoadDataTable() returned size = {}", barcodeRegisterModelList.size());
        barcodeRegisterModel = new BarcodeRegisterModel();
    }

    public void onClickButtonNew(){
        log.debug("-- onClickButtonNew()");
        barcodeRegisterView = new BarcodeRegisterView();
        flagBtnDelete = true;
        flagBtnSave = false;
        flagBtnEdit = true;
        flagBtnPrint = true;
        barcodeRegisterModel = new BarcodeRegisterModel();
        modeBarcode = "Mode(New)     ";
    }

    public void calculator(){
        log.debug("-- calculator()");
        final int qty = barcodeRegisterView.getQty();
        final int start = Utils.parseInt(replaceFormat(barcodeRegisterView.getStartBarcode()), 0);
        final int finish = (qty + start) - 1;
        final String result = finish > 999999999 ? "999999999" : String.format("%09d", finish);
        barcodeRegisterView.setFinishBarcode(result);
        barcodeRegisterView.setFinishBarcodeText("T" + result);
        barcodeRegisterView.setStartBarcodeText("T" + String.format("%09d", start));
    }

    private boolean mandate(){
//        if(!mandateQty() && !mandateItem() && !mandateStartBarcode()){
//            return true;
//        } else {
            if(mandateQty()){
                setMessage("Qtr should be greater than 0.");
                return false;
            } else if(mandateItem()){
                setMessage("Item should not be empty.");
                return false;
            } else if(mandateStartBarcode()){
                setMessage("Start Barcode should be 10 characters.");
                return false;
            } else if (mandateDuplicateStartBarcode()) {
                setMessage("Start Barcode or Finish Barcode is duplicate.");
                return false;
            } else {
                return true;
            }

//        }
    }

    private boolean mandateQty(){
        flagQty = (Utils.isZero(Utils.parseString(barcodeRegisterView.getQty(), "")) || barcodeRegisterView.getQty() < 1 )? true : false ;
        return flagQty;
    }

    private boolean mandateStartBarcode(){
        final int FIX_LENGTH = 9;
        final String stringReplace = replaceFormat(barcodeRegisterView.getStartBarcode());
        flagStartBarcode = stringReplace.length() != FIX_LENGTH ? true : false ;
        barcodeRegisterView.setStartBarcode(stringReplace);
        return flagStartBarcode;
    }

    private boolean mandateDuplicateStartBarcode(){
        return barcodeRegisterService.isDuplicate(barcodeRegisterView.getStartBarcode(), barcodeRegisterView.getFinishBarcode(), barcodeRegisterView.getId());
    }

    private boolean mandateItem(){
        flagItem = Utils.isZero(barcodeRegisterView.getMsItemModel().getId()) ? true : false ;
        return flagItem;
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
        log.debug("-- onClickTable() {}", barcodeRegisterModel);
        flagBtnSave = true;
        flagBtnDelete = false;
        flagBtnEdit = false;
        flagBtnPrint = false;
        barcodeRegisterView = barcodeRegisterService.convertToView(barcodeRegisterModel);
        modeBarcode = "Mode(Edit)     ";
    }

    public void onClickSelectOnDialog(){
        log.debug("-- onClickSelectOnDialog()");
        flagBtnSelect = true;
    }

    public void onDelete(){
        log.debug("-- onDelete()");
        try {
            barcodeRegisterService.delete(barcodeRegisterModel);
            showDialogDeleted();
            init();
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }
    }

    public void onSave(){
        log.debug("-- onSave()");
        try {
            if(mandate()){
                barcodeRegisterService.save(barcodeRegisterView);
                showDialogSaved();
                init();
            } else {
                showDialogWarning(getMessage());
            }
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }
    }

    public void onEdit(){
        log.debug("-- onEdit()");
        try {
            if(mandate()){
                barcodeRegisterService.edit(barcodeRegisterView);
                showDialogEdited();
                init();
            } else {
                showDialogWarning(getMessage());
            }
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }

    }

    public void onPrint(){
        barcodeRegisterService.onPrintBarcode(barcodeRegisterModel.getId());
    }

    private String replaceFormat(String startBarcode){
        return startBarcode.replace("T", "").replace("-", "").replace("_", "");
    }
}
