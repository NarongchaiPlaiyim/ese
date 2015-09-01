package com.ese.beans;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IncomingView;
import com.ese.service.IncomingService;
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
@ManagedBean(name = "incomingBean")
public class IncomingBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{message['authorize.menu.stockmovement.incoming']}") private String key;
    @ManagedProperty("#{incomingService}") private IncomingService incomingService;

    private List<MSStockInOutNoteModel> stockInOutNoteModelList;
    private List<MSStockInOutNoteModel> stockInOutNoteModelListTV;
    @NotNull private IncomingView incomingView;
    @NotNull private MSStockInOutNoteModel msStockInOutNoteModel;

    private List<StockInOutModel> stockInOutModelList;
    @NotNull private StockInOutModel stockInOutModel;
    private boolean flagBtnPrint;
    private String mode;
    private boolean modeFlag;
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
        mode = "Mode(New)";
        incomingView = new IncomingView();
        msStockInOutNoteModel = new MSStockInOutNoteModel();

        stockInOutModel = new StockInOutModel();
        flagBtnPrint = Boolean.TRUE;
        modeFlag = Boolean.TRUE;
        flagBtnPost = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
//        flagBtnSave = Boolean.TRUE;
    }

    private void onLoadDocumentNote(){
        stockInOutNoteModelList = incomingService.getAllStockInOutNote();
        stockInOutNoteModelListTV = incomingService.getAllStockInOutNote();
    }

    private void onLoadTable(){
        stockInOutModelList = incomingService.getOnLoad();
    }

    public void onClickTable(){
        mode = "Mode(Edit)";
        incomingView = new IncomingView();
        incomingView.setId(stockInOutModel.getId());
        incomingView.setDocNo(stockInOutModel.getDocNo());
        incomingView.setDocDate(stockInOutModel.getDocDate());
        incomingView.setRemark(stockInOutModel.getRemark());
        incomingView.setMsStockInOutNoteModel(stockInOutModel.getMsStockInOutNoteModel());
        if (stockInOutModel.getStatus().getStatusSeq() == 3){
            flagBtnPost = Boolean.TRUE;
            flagBtnSave = Boolean.TRUE;
        }
        else{
            flagBtnPost = Boolean.FALSE;
            flagBtnSave = Boolean.FALSE;
        }
        modeFlag = Boolean.FALSE;
        flagBtnShow = Boolean.FALSE;
        //flagBtnPost = Boolean.FALSE;
        flagBtnPrint = Boolean.FALSE;
    }

    public void onClickShowItem(){
        HttpSession session = FacesUtil.getSession(false);
        session.setAttribute("stockInOutModel", stockInOutModel);
        FacesUtil.redirect("/site/incomingShowItem.xhtml");
    }

    public void onClickNew(){
        mode = "Mode(New)";
        incomingView = new IncomingView();
        msStockInOutNoteModel = new MSStockInOutNoteModel();
        stockInOutModel = new StockInOutModel();
        flagBtnPrint = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
        flagBtnSave = Boolean.FALSE;
        modeFlag = Boolean.TRUE;
    }

    public void onClickSave(){

        if (Utils.isZero(incomingView.getMsStockInOutNoteModel().getId())){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Document Note should not be empty");
        } else {
            if (modeFlag) {
                incomingService.save(incomingView);
                showDialogSaved();
            } else {
                incomingService.edit(incomingView);
                showDialogEdited();
            }
            init();
        }
    }

    public void onSearch(){
        stockInOutModel = new StockInOutModel();
        stockInOutModelList = incomingService.search(incomingView);
    }

    public void onPrint(){
        incomingService.printReport(stockInOutModel.getId());
    }

    public void onClickPost(){
        incomingService.post(incomingView);
        showDialogEdited();
        init();
    }

}
