package com.ese.beans;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.StockTransferView;
import com.ese.service.StockTransferService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpSession;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "stockTransferBean")
@ViewScoped
public class StockTransferBean extends Bean{
    private static final long serialVersionUID = 4412578634029874840L;
    @ManagedProperty("#{stockTransferService}") private StockTransferService stockTransferService;

    private List<StockInOutModel> stockInOutModelList;
    private List<MSStockInOutNoteModel> stockInOutNoteModelList;

    private StockInOutModel stockInOutModel;
    private MSStockInOutNoteModel msStockInOutNoteModel;
    private StockTransferView stockTransferView;

    private String mode;
    private boolean flagDocNo;
    private boolean flagBtnShowTransferPallet;
    private boolean flagBtnPrint;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
        onLoadTable();
        mode = "Mode:New  ";
        flagBtnShowTransferPallet = Boolean.TRUE;
        flagBtnPrint = Boolean.TRUE;
        flagDocNo = Boolean.FALSE;
        stockInOutModel = new StockInOutModel();
        msStockInOutNoteModel = new MSStockInOutNoteModel();
        stockTransferView = new StockTransferView();
        stockInOutNoteModelList = stockTransferService.getAllStockInOutNote();
        onLoadFromView();
    }

    private void onLoadFromView(){
        stockInOutModel.setDocNo(Utils.getDocumentNo());
        stockInOutModel.setDocDate(Utils.currentDate());
    }

    private void onLoadTable(){
        stockInOutModelList = stockTransferService.getOnload();
    }

    public void onClickTable(){
        mode = "Mode:Edit  ";
        flagBtnShowTransferPallet = Boolean.FALSE;
        flagBtnPrint = Boolean.FALSE;
        flagDocNo = Boolean.TRUE;
        msStockInOutNoteModel = stockInOutModel.getMsStockInOutNoteModel();
    }

    public void onClickNew(){
        mode = "Mode:New  ";
        flagBtnShowTransferPallet = Boolean.TRUE;
        flagBtnPrint = Boolean.TRUE;
        flagDocNo = Boolean.FALSE;
        stockInOutModel = new StockInOutModel();
        stockInOutModel.setDocNo(Utils.getDocumentNo());
        stockInOutModel.setDocDate(Utils.currentDate());
        msStockInOutNoteModel = new MSStockInOutNoteModel();
    }

    public void onSave(){
        if (Utils.isZero(msStockInOutNoteModel.getId())){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "กรุณาเลือก Document Note");
        } else {
            stockTransferService.saveOrupdate(stockInOutModel, msStockInOutNoteModel.getId());
            showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
        }
        init();
    }

    public void onSearch(){
        stockInOutModel = new StockInOutModel();
        stockInOutModelList = stockTransferService.search(stockTransferView);
    }

    public void onShowTransferPallet(){
        HttpSession session = FacesUtil.getSession(true);
        session.setAttribute("stockInOutModel", stockInOutModel);
        FacesUtil.redirect("/site/showTransferPallet.xhtml");
    }
}
