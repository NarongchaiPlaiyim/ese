package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.*;
import com.ese.model.view.dilog.WarehouseDialogView;
import com.ese.service.*;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import com.ese.service.LocationService;
import com.ese.service.SetupService;
import com.ese.service.WarehouseService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "setup")
public class SetupBean extends Bean{
    @ManagedProperty("#{setupService}") private SetupService setupService;
    @ManagedProperty("#{locationService}") private LocationService locationService;
    @ManagedProperty("#{warehouseService}") private WarehouseService warehouseService;
    @ManagedProperty("#{locationItemService}") private LocationItemService locationItemService;
    @ManagedProperty("#{itemService}") private ItemService itemService;
    @ManagedProperty("#{stockInOutNoteService}") private StockInOutNoteService stockInOutNoteService;

    private SetupView setupView;
    private WarehouseAndLocationView warehouseAndLocationView;
    private WarehouseDialogView warehouseDialogView;
    private List<WarehouseAndLocationView> warehouseAndLocationViewList;
    private boolean flagBtnNewWarehouse;
    private boolean flagBtnAddShowItem;
    private boolean flagBtnAddEdit;
    private boolean flagBtnDelete;
    private boolean flagBtnDeleteStock;
    private String modeStock;
    private String nameBtnStock;
    private String modeWarehouse;
    private String nameBtn;
    private List<LocationView> locationViewList;
    private LocationView locationView;
    private List<MSWarehouseModel> msWarehouseModelList;
    private MSWarehouseModel msWarehouseModel;
    private List<MSLocationModel> msLocationModelList;
    private MSLocationModel msLocationModel;
    private WarehouseView warehouseView;
    private List<MSLocationItemsModel> msLocationItemsModelList;
    private MSLocationItemsModel msLocationItemsModel;
    private String itemSearch;
    private List<MSItemModel> msItemModelList;
    private String selectType;
    private List<MSItemModel> selectItem;
    private List<MSLocationItemsModel> selectLocationItem;
    private List<MSStockInOutNoteModel> stockInOutNoteModelList;
    private MSStockInOutNoteModel stockInOutNoteModel;
    private StockInOutNoteView stockInOutNoteView;
    private String modeWarehouseDlg;
    private String keySearch;

    public SetupBean() {

    }

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation(). {}", setupService.getKey());
        if(preLoad() && isAuthorize("0700")){
            init();
        }
    }

    private void init(){
        setupView = new SetupView();
        warehouseDialogView = new WarehouseDialogView();
        locationView = new LocationView();
        msWarehouseModel = new MSWarehouseModel();
        warehouseView = new WarehouseView();
        msLocationItemsModel = new MSLocationItemsModel();
        stockInOutNoteModel = new MSStockInOutNoteModel();
        stockInOutNoteView = new StockInOutNoteView();
        modeWarehouse = "Mode(New)";
        nameBtn = "New";
        modeStock = "Mode(New)";
        nameBtnStock = "Cancel";
        btnOnLoad();
        onLoadLocationTB();
        warehouseOnLoad();
        OnLoadStockInOutNote();
    }

//    public void test(){
//        log.debug("#### {}",locationView.getWarehouseModel().getId());
//    }

    private void btnOnLoad(){
        flagBtnNewWarehouse = false;
        flagBtnAddShowItem = true;
        flagBtnAddEdit = false;
        flagBtnDelete = true;
        flagBtnDeleteStock = true;
    }

    private void warehouseOnLoad(){
        msWarehouseModelList = warehouseService.getWarehouseAll();
    }

    private void onLoadLocationTB(){
        msLocationModelList = locationService.getLocationAll();
    }

    public void OnLoadStockInOutNote(){
        stockInOutNoteView = new StockInOutNoteView();
        stockInOutNoteModelList = stockInOutNoteService.getStockInOutNoteAll();
    }

    private boolean checkLocationCode(LocationView locationView){
        log.debug("checkLocationCode {}",locationView);
        return locationService.isDuplicate(locationView.getWarehouseModel().getId(), locationView.getLocationBarcode(), locationView.getId());
    }

    public void onClickNewOrCancelWarehouse(){
        log.debug("NewOrCancel.");
        modeWarehouse = "Mode(New)";
        nameBtn = "New";
        msLocationModel = new MSLocationModel();
        locationView = new LocationView();
        btnOnLoad();
    }

    public void onSaveWarehouse(){
        log.debug("onSaveOrUpdate() {}", locationView);

        try {
            if (checkLocationCode(locationView)){
                locationService.onSaveOrUpdateLocationToDB(locationView);
                msLocationModel = new MSLocationModel();

                if (Utils.isZero(locationView.getId())){
                    showDialogSaved();
                } else {
                    showDialogUpdated();
                }
                init();
            } else {
                showDialog(MessageDialog.ERROR.getMessageHeader(), "Location Barcode is duplicate");
            }

        } catch (Exception e) {
            log.debug("Exception onSaveLocation : ", e);
            showDialogError(e.getMessage());
        }
    }

    public void onSearchWarehouseAndLocation(){
        log.debug("onSearch Location");
        msLocationModelList = locationService.searchOrderByCodeOrName(keySearch);
    }

    public void preDelete(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Are you want to delete this item?", "confirmWarehouseAndLocationDlg");
    }

    public void onDeleteWarehouseAndLocation(){
        log.debug("-- onDelete()");
        try {
            locationService.delete(msLocationModel);
            showDialogDeleted();
            init();
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }
    }

    public void onClickTableWarehouseAndLocation(){
        log.debug("onClickToLocationTB(), {}", msLocationModel.toString());
        modeWarehouse = "Mode(Edit)";
        nameBtn = "New";
        flagBtnDelete = false;
        flagBtnAddShowItem = false;
        locationView = locationService.clickToWarehouseView(msLocationModel);
    }

    public boolean checkWarehouseCodeOnSave(String warehouseCode, int id){
        log.debug("checkWarehouseCodeOnSave. {}", warehouseCode);

        return warehouseService.isDuplicate(warehouseCode, id);
    }

    public void onEditDialogWarehouse(){
        log.debug("Open Warehouse Dialog.");
        warehouseView = new WarehouseView();
        modeWarehouseDlg = "Mode(New)";
        msWarehouseModel = new MSWarehouseModel();
        msWarehouseModelList = warehouseService.getWarehouseAll();
    }

    public void onNewWarehouseDialog(){
        warehouseView = new WarehouseView();
        modeWarehouseDlg = "Mode(New)";
        msWarehouseModel = new MSWarehouseModel();
    }

    public void onSaveWarehouseDialog(){
        log.debug("OnSave Warehouse. {}", warehouseView);

        if (checkWarehouseCodeOnSave(warehouseView.getWarehouseCode(), warehouseView.getId())){
            warehouseService.onSaveOrUpdateWarehouse(warehouseView);
            msWarehouseModel = new MSWarehouseModel();
            if (Utils.isZero(warehouseView.getId())){
                showDialogSaved();
            } else {
                showDialogUpdated();
            }
            warehouseView = new WarehouseView();
            msWarehouseModelList = warehouseService.getWarehouseAll();
        } else {
        showDialog(MessageDialog.ERROR.getMessageHeader(), "Warehouse Code is duplicate");
        }
    }

    public void preDeleteWarehouseDialog(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Are you want to delete this item?", "confirmWarehouseDialogDlg");
    }

    public void onDeleteWarehouseDialog(){
        log.debug("Delete warehouse.");
        warehouseService.delete(msWarehouseModel);
        showDialogSaved();
        init();
    }

    public void onClickTableWarehouseDialog(){
        modeWarehouseDlg = "Mode(Edit)";
        log.debug("onclickWarehouseTBDlg(). {}",msWarehouseModel.toString());
        warehouseView = warehouseService.converToView(msWarehouseModel);
    }

    public void onClickAddItemWarehouse(){
        log.debug("locationItemDialog(). {}", msLocationModel.getId());
        selectType = "";
        itemSearch = "";
        msItemModelList = new ArrayList<MSItemModel>();
        msLocationItemsModelList = locationItemService.findLocationItemByLocationId(msLocationModel.getId());
    }

    public void onSearchItemWarehouse(){
        log.debug("-- onSubmitSearch() {}, {}", selectType,itemSearch);

        if(!Utils.isZero(itemSearch.length())){
            msItemModelList = itemService.findByCondition(selectType, itemSearch);
            log.debug("msItemModelList Size : {}", msItemModelList.size());
        } else {
            msItemModelList = itemService.findByCondition(selectType, itemSearch);
        }
    }

    public void onAddToLocation(){
        log.debug("addToLocationItem");
        locationItemService.addToLocationItemModel(selectItem, msLocationModel);
        msLocationItemsModelList = locationItemService.findLocationItemByLocationId(msLocationModel.getId());
        selectItem = new ArrayList<MSItemModel>();
        showDialogSaved();
    }

    public void onRemove(){
        log.debug("remove(). {}", selectLocationItem.size());
        locationItemService.deleteLocationItemModel(selectLocationItem);
        onAddToLocation();
    }

    public void OnNewStockInOutNote(){
        log.debug("onNewAndCancelStock()");
        modeStock = "Mode(New)";
        flagBtnDeleteStock = true;
        stockInOutNoteModel = new MSStockInOutNoteModel();
        stockInOutNoteView = new StockInOutNoteView();
    }

    public void onSaveStockInOutNote(){
        log.debug("onSaveStockInOutNote().");


        stockInOutNoteService.onSaveStockInOutNote(stockInOutNoteView);
        if (Utils.isZero(stockInOutNoteView.getId())){
            showDialogSaved();
        } else {
            showDialogUpdated();
        }
        OnLoadStockInOutNote();
        stockInOutNoteModel = new MSStockInOutNoteModel();
    }

    public void onDeleteStockInOutNote(){
        log.debug("onDelectStock()");
        stockInOutNoteService.deleteStockInOutNote(stockInOutNoteModel);
        OnLoadStockInOutNote();
        stockInOutNoteModel = new MSStockInOutNoteModel();
        modeStock = "Mode(New)";
        flagBtnDeleteStock = true;
        showDialogSaved();
    }

    public void onClickTableStockInOutNote(){
        log.debug("onClickStockInOutNote(). {}", stockInOutNoteModel);
        stockInOutNoteView = stockInOutNoteService.clickToStockInOutNoteView(stockInOutNoteModel);
        flagBtnDeleteStock = false;
        modeStock = "Mode(Edit)";
        nameBtnStock = "New";
    }

    public void preDeleteItem(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Are you want to delete this item?", "confirmStockInOutNoteDlg");
    }
}
