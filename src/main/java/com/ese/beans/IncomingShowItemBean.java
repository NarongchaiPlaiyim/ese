package com.ese.beans;

import com.ese.model.db.InvOnHandModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.model.view.IncomingView;
import com.ese.model.view.StockMovementInView;
import com.ese.service.BarcodeRegisterService;
import com.ese.service.IncomingService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.NamesUtil;
import com.ese.utils.Utils;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "incomingShowItemBean")
public class IncomingShowItemBean extends Bean{
    private static final long serialVersionUID = 4112578634029874840L;
    @ManagedProperty("#{barcodeRegisterService}") private BarcodeRegisterService barcodeRegisterService;
    @ManagedProperty("#{incomingService}") private IncomingService incomingService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private BarcodeRegisterView barcodeRegisterView;
    private IncomingView incomingView;

//    private List<MSItemModel> msItemModelList;

    @NotNull
    private List<InvOnHandModel> invOnHandModelList;
    @NotNull
    private List<InvOnHandModel> select;

//    private List<BarcodeRegisterModel> barcodeRegisterModelList;
    private List<StockMovementInView> stockMovementInViewList;
//    private BarcodeRegisterModel barcodeRegisterModel;
    private StockMovementInView stockMovementInView;
    private String selectType;
    private String productSearch;
    private String barcodeSearch;
    private String barcodeSelectType;
    private boolean flagBtnSelect;
    private boolean flagBtnDelete;
    private boolean flagBtnSave;
    private boolean flagBtnPrint;
    private boolean flagBtnEdit;
    private boolean flagBtnNewItem;

    private boolean flagItem;
    private boolean flagQty;
    private boolean flagStartBarcode;

    private String modeBarcode;
    private StockInOutModel stockInOutModel;

    @PostConstruct
    public void onCreation(){
        HttpSession session = FacesUtil.getSession(false);
        stockInOutModel = (StockInOutModel) session.getAttribute("stockInOutModel");
        if (stockInOutModel != null) {
            incomingView = new IncomingView();
            incomingView.setDocDate(stockInOutModel.getDocDate());
            incomingView.setDocNo(stockInOutModel.getDocNo());
            incomingView.setMsStockInOutNoteModel(stockInOutModel.getMsStockInOutNoteModel());
            incomingView.setRemark(stockInOutModel.getRemark());
            log.debug("onCreation().");
            if(preLoad()){// && isAuthorize(key)){
                init();
            }
        } else {
            FacesUtil.redirect(NamesUtil.LOGIN_PAGE.getName());
        }
    }

    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
//        msItemModelList = Utils.getEmptyList();
//        barcodeRegisterModelList = Utils.getEmptyList();
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
        onLoadDataBarcode();
//        log.debug("-- onLoadDataTable() returned size = {}", barcodeRegisterModelList.size());
//        barcodeRegisterModel = new BarcodeRegisterModel();
        stockMovementInView = new StockMovementInView();
    }

    private void onLoadDataBarcode(){
//        barcodeRegisterModelList = barcodeRegisterService.getByStockInOut(stockInOutModel.getDocNo());
        stockMovementInViewList = incomingService.getStockMoveInByStockInOutId(stockInOutModel.getId());
    }

    public void onClickButtonNew(){
        log.debug("-- onClickButtonNew()");
        barcodeRegisterView = new BarcodeRegisterView();

        flagBtnSave = false;
        flagBtnEdit = true;
        flagBtnPrint = true;

        if (stockInOutModel.getStatus().getStatusSeq() == 1 || stockInOutModel.getStatus().getStatusSeq() == 2){
            flagBtnNewItem = Boolean.FALSE;
            flagBtnDelete = Boolean.FALSE;
        } else {
            flagBtnNewItem = Boolean.TRUE;
            flagBtnDelete = Boolean.TRUE;
        }
//        barcodeRegisterModel = new BarcodeRegisterModel();
        stockMovementInView = new StockMovementInView();
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
//        flagItem = Utils.isZero(barcodeRegisterView.getMsItemModel().getId()) ? true : false ;
        return flagItem;
    }

    public void onInitSearch(){
        log.debug("-- onInitSearch()");
        selectType = "3";
        productSearch = "";
        invOnHandModelList = Utils.getEmptyList();
    }

    public void onSubmitSearchBarcode(){
        log.debug("-- onSubmitSearch()");
        if(!Utils.isZero(barcodeSearch.length())){
//            barcodeRegisterModelList = barcodeRegisterService.findBarcodeByCondition(barcodeSelectType, barcodeSearch);
        } else {
            onLoadDataBarcode();
        }

    }

    public void onSubmitSearch(){
        log.debug("-- onSubmitSearch()");
        invOnHandModelList = incomingService.findInvOnHand(productSearch);
        //msItemModelList = barcodeRegisterService.findByCondition(selectType, productSearch);
//
//        invOnHandModelList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            InvOnHandModel invOnHandModel = new InvOnHandModel();
//            invOnHandModel.setId(i);
//            invOnHandModel.setSnBarcode(""+i);
//            invOnHandModelList.add(invOnHandModel);
//        }

    }

    public void onClickTableDialog(){
        log.debug("-- onClickTable()");
        if (select.size() > 0) {
            flagBtnSelect = false;
        }
    }

    public void onClickTable(){
        log.debug("-- onClickTable() {}", stockMovementInView);
//        flagBtnSave = true;
//
//        flagBtnEdit = false;
//        flagBtnPrint = false;
//        barcodeRegisterView = barcodeRegisterService.convertToView(barcodeRegisterModel);

        //if stock_movement_in.status = 1; delete button should be enable.

        if (!Utils.isNull(stockInOutModel)){
            if (stockInOutModel.getStatus().getStatusSeq() == 1){
                flagBtnDelete = Boolean.FALSE;
            }
        }
//        modeBarcode = "Mode(Edit)     ";
    }

    //TODO
    public void onClickSelectOnDialog(){
        log.debug("-- onClickSelectOnDialog() {}", select);
        if (select.size() > 0) {
            incomingService.save(productSearch, select, stockInOutModel.getId());
            showDialog("Select", "Select success.", "msgBoxSystemMessageDlg");
            flagBtnSelect = true;
            select = new ArrayList<>();
        }
    }

    public void onCloseDialog(){
        initBtn();
        initField();
        onLoadDataTable();
        onClickButtonNew();
    }

    public void preDelete(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Are you want to delete this item?", "confirmClosePalletDlg");
    }
    public void onDelete(){
        log.debug("-- onDelete()");
        try {
//            barcodeRegisterService.deleteFormSetValid(barcodeRegisterModel);
            incomingService.delete(stockMovementInView.getId());
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
                barcodeRegisterView.setDocumentNo(stockInOutModel.getDocNo());
                barcodeRegisterService.saveOrUpdate(barcodeRegisterView);
                if (!Utils.isZero(barcodeRegisterView.getId())){
                    showDialogEdited();
                } else {
                    showDialogSaved();
                }
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
//        barcodeRegisterService.onPrintBarcode(barcodeRegisterModel.getId());
    }

    private String replaceFormat(String startBarcode){
        return startBarcode.replace("T", "").replace("-", "").replace("_", "");
    }

    public void onChangeDocumentDate(){
        log.debug("--- {}", barcodeRegisterView.getDocumentDate());
        barcodeRegisterView.setBatchNo(Utils.getBatchNo(barcodeRegisterView.getDocumentDate()));
        log.debug("------- {}", Utils.getBatchNo(barcodeRegisterView.getDocumentDate()));
    }

    public void onClose(){
        HttpSession session = FacesUtil.getSession(false);
        session.removeAttribute("stockInOutModel");
        FacesUtil.redirect("/site/incoming.xhtml");
    }
}
