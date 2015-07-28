package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.IssuingView;
import com.ese.model.view.SearchItemView;
import com.ese.model.view.ShowSNView;
import com.ese.model.view.StockMovementOutView;
import com.ese.service.IssuingService;
import com.ese.service.StockMovementShowItemService;
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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "stockMoveItemBean")
public class StockMovementShowItemBean extends Bean{
    private static final long serialVersionUID = 4112578634263333840L;
    @ManagedProperty("#{stockMovementShowItemService}") private StockMovementShowItemService stockMovementShowItemService;

    @NotNull private StockInOutModel stockInOutModel;
//    @NotNull private StockInOutLineModel stockInOutLineModel;
    @NotNull private ShowSNView showSNView;
    @NotNull private SearchItemView searchItemView;
    @NotNull private StockMovementOutView stockMovementOutView;

    private List<StockInOutLineModel> stockInOutLineModelList;
    private List<ShowSNView> showSNViewList;
    private List<MSLocationModel> msLocationModelList;
    private List<MSWarehouseModel> msWarehouseModelList;
    private List<ShowSNView> searchItemViewList;
    private List<ShowSNView> selectSearchItemViewList;
    private List<StockMovementOutView> stockMovementOutViewList;

    private Boolean flagBtnShowSN;
    private Boolean flagBtnSearchItem;
    private Boolean flagBtnDelete;

    //ShowSN Dialog
    private Boolean flagBtnRemove;

    //SearchItem Dialog
    private Boolean flagBtnSelect;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        HttpSession session = FacesUtil.getSession(false);
        stockInOutModel = (StockInOutModel) session.getAttribute("stockInOutModel");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){
//        stockInOutLineModel = new StockInOutLineModel();
        stockMovementOutView = new StockMovementOutView();
        showSNView = new ShowSNView();
        searchItemView = new SearchItemView();
        flagBtnShowSN = Boolean.TRUE;
//        flagBtnSearchItem = Boolean.TRUE;
        flagBtnDelete = Boolean.TRUE;

        onLoadTable();
    }

    private void onLoadTable(){
//        stockInOutLineModelList = stockMovementShowItemService.getByStockInOutId(stockInOutModel.getId());
        stockMovementOutViewList = stockMovementShowItemService.getStockMovementOutByStockInOutId(stockInOutModel.getId());
    }

    public void onClickTable(){
//        flagBtnShowSN = Boolean.FALSE;

//        if (stockInOutModel.getStatus().getId() < 19){ //Status 19 = status_seq 3(Close)
//            flagBtnSearchItem = Boolean.FALSE;
//        }

        if (!Utils.isNull(stockMovementOutView)){
            if (stockMovementOutView.getStatus() == 1){
                flagBtnDelete = Boolean.FALSE;
            }
        }
    }

//    public void onClickShowSN(){
//        flagBtnRemove = Boolean.TRUE;
//        showSNViewList = stockMovementShowItemService.getInvOnhandByStockInOutLineId(stockInOutLineModel.getId());
//
//    }

    public void onDelete(){
        if (!Utils.isNull(stockMovementOutView)){
            stockMovementShowItemService.delete(stockMovementOutView.getId());
            showDialog("Delete", "Delete success.", "msgBoxSystemMessageDlg");
            onLoadTable();
            flagBtnDelete = Boolean.TRUE;
        }
    }

    public void onClickRemove(){
        stockMovementShowItemService.remove(showSNView);
        showDialogEdited();
//        onLoadShowSN();
    }

//    private void onLoadShowSN(){
//        showSNView = new ShowSNView();
//        flagBtnRemove = Boolean.TRUE;
//        showSNViewList = stockMovementShowItemService.getInvOnhandByStockInOutLineId(stockInOutLineModel.getId());
//    }

    public void onClickTableShowSNDialg(){
        flagBtnRemove = Boolean.FALSE;
    }

    public void onLoadSearchItem(){
//        searchItemView = new SearchItemView();
        flagBtnSelect = Boolean.TRUE;
        searchItemViewList = new ArrayList<ShowSNView>();
        msWarehouseModelList = stockMovementShowItemService.findWarehouseAll();
        msLocationModelList = stockMovementShowItemService.findLocationAll();
    }

    public void onChangeWarehouse(){
        msLocationModelList = stockMovementShowItemService.getByWarehouseId(searchItemView.getWarehouseId());
    }

    public void onSearchItemDialog(){
        searchItemViewList = stockMovementShowItemService.getBySearch(searchItemView);
    }

    public void onClickTabkeSearchItemDailog(){
        if (!Utils.isZero(selectSearchItemViewList.size())){
            flagBtnSelect = Boolean.FALSE;
        } else {
            flagBtnSelect = Boolean.TRUE;
        }
    }

    public void onSelect(){
        stockMovementShowItemService.select(selectSearchItemViewList, stockInOutModel.getId());
        showDialogEdited();
        onSearchItemDialog();
    }

    public void onClose(){
        HttpSession session = FacesUtil.getSession(false);
        session.removeAttribute("stockInOutModel");
        FacesUtil.redirect("/site/issuing.xhtml");
    }

    public void onCloseDialog(){
        stockMovementOutView = new StockMovementOutView();
        showSNView = new ShowSNView();
        searchItemView = new SearchItemView();
        flagBtnShowSN = Boolean.TRUE;
//        flagBtnSearchItem = Boolean.TRUE;
        flagBtnDelete = Boolean.TRUE;

        onLoadTable();
    }
}
