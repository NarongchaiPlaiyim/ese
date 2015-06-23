package com.ese.beans;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.QuarantineView;
import com.ese.service.QuarantineService;
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
@ManagedBean(name = "quarantineBean")
public class QurantineBean extends Bean {
    private static final long serialVersionUID = 4112578634263333840L;
    @ManagedProperty(value = "#{message['authorize.menu.quarantine']}") private String key;
    @ManagedProperty("#{quarantineService}") private QuarantineService quarantineService;

    private List<StockInOutModel> stockInOutModelList;

    @NotNull private StockInOutModel stockInOutModel;
    @NotNull private QuarantineView quarantineView;

    private String mode;
    private boolean modeFlag;
    private boolean flagBtnPrint;
    private boolean flagBtnShow;
    private boolean flagBtnPost;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
        onLoadTable();

        flagBtnPrint = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
        modeFlag = Boolean.TRUE;
        flagBtnPost = Boolean.TRUE;
        mode = "Mode(New)";
        quarantineView = new QuarantineView();
    }

    private void onLoadTable(){
        stockInOutModelList = quarantineService.getOnLoad();
    }

    public void onClickNew(){
        mode = "Mode(New)";
        quarantineView = new QuarantineView();
        stockInOutModel = new StockInOutModel();
        flagBtnPrint = Boolean.TRUE;
        flagBtnShow = Boolean.TRUE;
        modeFlag = Boolean.TRUE;
    }

    public void onClickSave(){
        if (modeFlag) {
            quarantineService.save(quarantineView);
            showDialogSaved();
        } else {
            quarantineService.edit(quarantineView);
            showDialogEdited();
        }
        init();
    }

    public void onClickPost(){
        quarantineService.post(quarantineView);
        showDialogEdited();
        init();
    }

    public void onSearch(){
        stockInOutModel = new StockInOutModel();
        stockInOutModelList = quarantineService.search(quarantineView);
    }

    public void onClickTable(){
        mode = "Mode(Edit)";
        quarantineView = new QuarantineView();
        quarantineView.setId(stockInOutModel.getId());
        quarantineView.setDocNo(stockInOutModel.getDocNo());
        quarantineView.setDocDate(stockInOutModel.getDocDate());
        quarantineView.setRemark(stockInOutModel.getRemark());
        modeFlag = Boolean.FALSE;
        flagBtnShow = Boolean.FALSE;
        flagBtnPost = Boolean.FALSE;
    }

    public void onClickShowItem(){
        HttpSession session = FacesUtil.getSession(false);
        session.setAttribute("stockInOutModel", stockInOutModel);
        FacesUtil.redirect("/site/quarantineItem.xhtml");
    }
}
