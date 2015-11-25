package com.ese.beans;

import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.PalletTransferView;
import com.ese.service.LocationItemService;
import com.ese.service.LocationService;
import com.ese.service.ShowTransPalletService;
import com.ese.service.WarehouseService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
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
@ManagedBean(name = "showTransferPalletBean")
@ViewScoped
public class ShowTransferPalletBean extends Bean{
    private static final long serialVersionUID = 4412578634029874840L;
    @ManagedProperty("#{showTransPalletService}") private ShowTransPalletService showTransPalletService;
    @ManagedProperty("#{locationItemService}") private LocationItemService locationItemService;
    @ManagedProperty("#{warehouseService}") private WarehouseService warehouseService;
    @ManagedProperty("#{locationService}") private LocationService locationService;

    private List<PalletTransferView> palletTransferViewList;
    private List<LocationItemView> locationItemViewList;
    private List<MSWarehouseModel> warehouseModelList;
    private List<MSLocationModel> msLocationModelList;
    private List<PalletTransferView> newPalletTranferDialogViewList;
    private PalletTransferView selectPallet;
    private MSLocationModel msLocationModel;
    private LocationItemView locationItemViews;
    private HttpSession session;
    private StockInOutModel stockInOutModel;
    private MSWarehouseModel warehouseMode;

    @Getter @Setter private String palletTagID;
    @Getter @Setter private String itemId;

    private boolean flagBtnFindLocation = Boolean.TRUE;
    private boolean flagBtnPrintTag = Boolean.TRUE;
    private boolean isCheckLocationDialog = Boolean.TRUE;
    private boolean flagBtnNewTransferPallet = Boolean.TRUE;
    private int pmvId;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            session = FacesUtil.getSession(true);
            stockInOutModel = (StockInOutModel) session.getAttribute("stockInOutModel");
            init();
        }
    }

    private void init(){
        onLoad();
        warehouseMode = new MSWarehouseModel();
        msLocationModel = new MSLocationModel();
        selectPallet = new PalletTransferView();
    }

    private void onLoad(){
        palletTransferViewList = showTransPalletService.getPalletByStockID(stockInOutModel.getId());
        if (stockInOutModel.getStatus().getStatusSeq()==4) {
            flagBtnNewTransferPallet = Boolean.TRUE ;
        }else flagBtnNewTransferPallet = Boolean.FALSE;
    }

    public void onClose(){
        FacesUtil.redirect("/site/stockTransfer.xhtml");
        session.removeAttribute("stockInOutModel");
    }

    public void onNewPalletTransfer(){
        palletTagID = "";
        itemId = "";
        warehouseMode = new MSWarehouseModel();
        msLocationModel = new MSLocationModel();
        warehouseModelList = warehouseService.getWarehouseAll();
        msLocationModelList = locationService.getLocationAll();
    }

    public void onFindLocation(){
        locationItemViews = new LocationItemView();
        locationItemViewList = locationItemService.findLocationByItemId(selectPallet.getItem());
    }

    public void OnChangeLocationToPallet(){
        log.debug("OnChangeLocationToPallet().");
        showTransPalletService.changeLocation(selectPallet, locationItemViews, stockInOutModel.getId());
        showDialogUpdated();
        onSearch();
        onLoad();
        selectPallet = new PalletTransferView();
    }

    public void onClickLocationTB(){
        isCheckLocationDialog = Boolean.FALSE;
    }

    public void callReport() {
        showTransPalletService.onPrintTag(pmvId);
    }

    public void onPrintTag(String redirect){
        pmvId = selectPallet.getId();
        showTransPalletService.onUpdateByPrintTag(selectPallet, redirect);
        showDialog(MessageDialog.UPDATE.getMessageHeader(), MessageDialog.UPDATE.getMessage(), "msgBoxSystemMessageDlg2");
    }

    public void onSearch(){
        flagBtnFindLocation = Boolean.TRUE;
        flagBtnPrintTag = Boolean.TRUE;
        newPalletTranferDialogViewList = showTransPalletService.search(palletTagID, itemId, msLocationModel.getId(), warehouseMode.getId());
        selectPallet = new PalletTransferView();
    }

    public void  onClickTable(){
        if (selectPallet.getToTransfer() == 1){
            flagBtnPrintTag = Boolean.FALSE;
        }

    }

    public void  onClickTableDialog(){
        flagBtnFindLocation = Boolean.FALSE;

        if (selectPallet.getToTransfer() == 1){
            flagBtnPrintTag = Boolean.FALSE;
        }
    }

    public void onCloseDialog(){
        selectPallet = new PalletTransferView();
    }
}
