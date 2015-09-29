package com.ese.beans;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IssuingView;
import com.ese.service.IssuingService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "issuingBean")
public class IssuingBean extends Bean {
    private static final long serialVersionUID = 4112578634263333840L;
    @ManagedProperty("#{message['authorize.menu.stockmovement.issuing']}") private String key;
    @ManagedProperty("#{issuingService}") private IssuingService issuingService;

    private List<StockInOutModel> stockInOutModelList;
    private List<MSStockInOutNoteModel> stockInOutNoteModelList;
    private List<MSStockInOutNoteModel> stockInOutNoteModelListTV;

    @NotNull private IssuingView issuingView;
    @NotNull private StockInOutModel stockInOutModel;
    @NotNull private MSStockInOutNoteModel msStockInOutNoteModel;

    private String mode;
    private boolean modeFlag;
    private boolean flagBtnPrint;
    private boolean flagBtnShow;
    private boolean flagBtnPost;
    private boolean flagBtnSave;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
        onLoadTable();
        onLoadDocumentNote();

        flagBtnPrint = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
        modeFlag = Boolean.TRUE;
        flagBtnPost = Boolean.TRUE;
//        flagBtnSave = Boolean.TRUE;
        mode = "Mode(New)";
        issuingView = new IssuingView();
        msStockInOutNoteModel = new MSStockInOutNoteModel();
    }

    private void onLoadTable(){
        stockInOutModelList = issuingService.getOnLoad();
    }

    private void onLoadDocumentNote(){
        stockInOutNoteModelList = issuingService.getAllStockInOutNote();
        stockInOutNoteModelListTV = issuingService.getAllStockInOutNote();
    }

    public void onClickNew(){
        mode = "Mode(New)";
        issuingView = new IssuingView();
        msStockInOutNoteModel = new MSStockInOutNoteModel();
        stockInOutModel = new StockInOutModel();
        flagBtnPrint = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
        flagBtnSave = Boolean.FALSE;
        modeFlag = Boolean.TRUE;
    }

    public void onClickSave(){

        if (Utils.isZero(issuingView.getMsStockInOutNoteModel().getId())){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Document Note should not be empty");
        } else {
            if (modeFlag) {
                issuingService.save(issuingView);
                showDialogSaved();
            } else {
                issuingService.edit(issuingView);
                showDialogEdited();
            }
            init();
        }
    }

    public void onClickShowItem(){
        HttpSession session = FacesUtil.getSession(false);
        session.setAttribute("stockInOutModel", stockInOutModel);
        FacesUtil.redirect("/site/stockMovementShowItem.xhtml");
    }

    public void onSearch(){
        stockInOutModel = new StockInOutModel();
        stockInOutModelList = issuingService.search(issuingView);
    }

    public void onClickTable(){
        mode = "Mode(Edit)";
        issuingView = new IssuingView();
        issuingView.setId(stockInOutModel.getId());
        issuingView.setDocNo(stockInOutModel.getDocNo());
        issuingView.setDocDate(stockInOutModel.getDocDate());
        issuingView.setRemark(stockInOutModel.getRemark());
        issuingView.setMsStockInOutNoteModel(stockInOutModel.getMsStockInOutNoteModel());
        if(stockInOutModel.getStatus().getStatusSeq() == 3){
            flagBtnPost = Boolean.FALSE;
            flagBtnSave = Boolean.TRUE;
        }else if(stockInOutModel.getStatus().getStatusSeq() == 1){
            flagBtnPost = Boolean.TRUE;
            flagBtnSave = Boolean.FALSE;
        }else{
            flagBtnPost = Boolean.TRUE;
            flagBtnSave = Boolean.TRUE;
        }
        modeFlag = Boolean.FALSE;
        flagBtnShow = Boolean.FALSE;
        //flagBtnPost = Boolean.FALSE;
        flagBtnPrint = Boolean.FALSE;
    }

    public void onClickPost(){
        issuingService.post(issuingView);
        showDialogClosed();
        init();
    }

    public void onPrint(){
        issuingService.printReport(stockInOutModel.getId());
    }
}
