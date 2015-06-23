package com.ese.beans;

import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.StockInOutLineModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.SearchItemView;
import com.ese.model.view.ShowSNView;
import com.ese.utils.FacesUtil;
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
@ManagedBean(name = "quarantineItemBean")
public class QuarantineItemBean extends Bean {
    private static final long serialVersionUID = 4112578634263333840L;
    @ManagedProperty("#{quarantineItemService}") private com.ese.service.QuarantineItemService quarantineItemService;

    @NotNull private StockInOutModel stockInOutModel;
    @NotNull private StockInOutLineModel stockInOutLineModel;
    @NotNull private ShowSNView showSNView;
    @NotNull private SearchItemView searchItemView;

    private List<StockInOutLineModel> stockInOutLineModelList;
    private List<ShowSNView> showSNViewList;
    private List<ShowSNView> searchItemViewList;
    private List<ShowSNView> selectSearchItemViewList;
    private List<MSWarehouseModel> msWarehouseModelList;
    private List<MSLocationModel> msLocationModelList;

    private Boolean flagBtnShowSN;
    private Boolean flagBtnSearchItem;

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
        stockInOutLineModel = new StockInOutLineModel();
        showSNView = new ShowSNView();
        flagBtnShowSN = Boolean.TRUE;
        flagBtnSearchItem = Boolean.TRUE;
        searchItemView = new SearchItemView();

        onLoadTable();
    }

    private void onLoadTable(){
        stockInOutLineModelList = quarantineItemService.getByStockInOutId(stockInOutModel.getId());
    }

    public void onClickTable(){
        flagBtnShowSN = Boolean.FALSE;

        if (stockInOutModel.getStatus().getId() < 19){ //Status 19 = status_seq 3(Close)
            flagBtnSearchItem = Boolean.FALSE;
        }
    }

    public void onClickShowSN(){
        flagBtnRemove = Boolean.TRUE;
        showSNViewList = quarantineItemService.getInvOnhandByStockInOutLineId(stockInOutLineModel.getId());
    }

    public void onClickRemove(){
        quarantineItemService.remove(showSNView);
        showDialogEdited();
        onLoadShowSN();
    }

    private void onLoadShowSN(){
        showSNView = new ShowSNView();
        flagBtnRemove = Boolean.TRUE;
        showSNViewList = quarantineItemService.getInvOnhandByStockInOutLineId(stockInOutLineModel.getId());
    }

    public void onClickTableShowSNDialg(){
        flagBtnRemove = Boolean.FALSE;
    }

    public void onLoadSearchItem(){
//        searchItemView = new SearchItemView();
        flagBtnSelect = Boolean.TRUE;
        searchItemViewList = new ArrayList<ShowSNView>();
        msWarehouseModelList = quarantineItemService.findWarehouseAll();
        msLocationModelList = quarantineItemService.findLocationAll();
    }

    public void onSearchItemDialog(){
        searchItemViewList = quarantineItemService.getBySearch(searchItemView);
    }

    public void onClickTabkeSearchItemDailog(){
        if (!Utils.isZero(selectSearchItemViewList.size())){
            flagBtnSelect = Boolean.FALSE;
        } else {
            flagBtnSelect = Boolean.TRUE;
        }
    }

    public void onSelect(){
        quarantineItemService.select(selectSearchItemViewList, stockInOutLineModel);
        showDialogEdited();
        onSearchItemDialog();
    }

    public void onChangeWarehouse(){
        msLocationModelList = quarantineItemService.getByWarehouseId(searchItemView.getWarehouseId());
    }
}
