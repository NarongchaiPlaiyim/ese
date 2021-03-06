package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.*;
import com.ese.service.security.UserDetail;
import com.ese.transform.PickingOrderLineTransform;
import com.ese.transform.ReservedOrderTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PickingOrderShowItemService extends Service{
    private static final long serialVersionUID = 4112577774029874840L;
    @Resource private PickingOrderLineDAO pickingOrderLineDAO;
    @Resource private ReservedOrderDAO reservedOrderDAO;
    @Resource private ReservedOrderTransform reservedOrderTransform;
    @Resource private LocationDAO locationDAO;
    @Resource private StatusDAO statusDAO;
    @Resource private WarehouseDAO warehouseDAO;
    @Resource private ItemDAO itemDAO;
    @Resource private PickingOrderLineTransform pickingOrderLineTransform;
    @Resource private PickingOrderDAO pickingOrderDAO;

    private int sumReservedOrder;
    private int inventoryQty;

    private String message;

    public List<PickingOrderShowItemView> getPickingOrderLineByPickingOrderId(int pickingOrderId){
        return pickingOrderLineDAO.findByPickingOrderId(pickingOrderId);
    }

    public void updateIsFoil(int pickingLineId, int isFoil){
        if (!Utils.isZero(isFoil)){
            pickingOrderLineDAO.updateToUnWrap(pickingLineId);
        } else {
            pickingOrderLineDAO.updateToWrap(pickingLineId);
        }
    }

    public String checkQty(int pickingOrderLineId){
        FIFOReservedView fifoReservedView = pickingOrderLineDAO.findQtyOnInventTran(pickingOrderLineId);
        inventoryQty = fifoReservedView.getInventtransQty();
        sumReservedOrder = pickingOrderLineDAO.getSumReservedOrder(pickingOrderLineId);
        log.debug("InvenQty : [{}], sumReservedOrder : [{}]", inventoryQty, sumReservedOrder);

        if (inventoryQty > sumReservedOrder){
            return "Success";
        }

        return "Fail";
    }

    public void onReserved(int pickingOrderLineId, String startBatch, String toBatch){
        ReservedOrderModel reservedOrderModel;
        PickingOrderLineModel pickingOrderLineModel;
        MSLocationModel msLocationModel;
        List<LocationQtyView> locationQtyViewList = Utils.getEmptyList();
        int restQty = 0;

        //Check reserved qty in reserved order
        int reservedQty = reservedOrderDAO.reservedOrderQtyByPickingOrderLineId(pickingOrderLineId);

        FIFOReservedView fifoReservedView = pickingOrderLineDAO.findQtyOnInventTran(pickingOrderLineId);
            if (Utils.isZero(startBatch.trim().length()) && Utils.isZero(toBatch.trim().length())){
                //FIFO Reserved
                locationQtyViewList = pickingOrderLineDAO.findByItemId(fifoReservedView.getItemId(), "", "", 0, 0, 0);
            } else if (!Utils.isZero(startBatch.trim().length()) && !Utils.isZero(toBatch.trim().length())){
                //Period Reserved
                locationQtyViewList = pickingOrderLineDAO.findByItemId(fifoReservedView.getItemId(), startBatch.trim(), toBatch.trim(), 0, 0, 0);
            }

            StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(TableValue.RESERVED_ORDER.getId());

            if (Utils.isSafetyList(locationQtyViewList)){
                pickingOrderLineDAO.updateInventTransByUse(fifoReservedView.getInventtransId());
                for (LocationQtyView locationQtyView : locationQtyViewList){
                    log.debug("-- restQty : [{}]", restQty);
                    if (locationQtyView.getQty() - locationQtyView.getReservedQty() > 0){
                        try {
                            pickingOrderLineModel = pickingOrderLineDAO.findByID(fifoReservedView.getPickingOrderLineId());
                            msLocationModel = locationDAO.findByID(locationQtyView.getLocationId());
                            if (!Utils.isZero(restQty)){
                                if (locationQtyView.getAvailable() - restQty >= 0){
                                    log.debug("---------------4");
                                    if (!Utils.isZero(reservedQty)){
                                        reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, restQty - reservedQty, locationQtyView.getBatchNo());
                                        pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), restQty - reservedQty);
                                    } else {
                                        reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, restQty, locationQtyView.getBatchNo());
                                        pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), restQty);
                                    }


                                    reservedOrderDAO.persist(reservedOrderModel);
                                    pickingOrderLineDAO.updateInventTransByUseFinish(fifoReservedView.getInventtransId());
                                    break;
                                } else {
                                    log.debug("---------------3");
                                    if (!Utils.isZero(reservedQty)){
                                        reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, restQty - (restQty - locationQtyView.getAvailable() - reservedQty), locationQtyView.getBatchNo());
                                        pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), restQty - (restQty - locationQtyView.getAvailable() - reservedQty));
                                    } else {
                                        reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, restQty - (restQty - locationQtyView.getAvailable()), locationQtyView.getBatchNo());
                                        pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), restQty - (restQty - locationQtyView.getAvailable()));
                                    }


                                    reservedOrderDAO.persist(reservedOrderModel);
                                    //update location_qty.reserved_qty = restQty - (restQty - locationQtyView.getAvilable);
                                    restQty = restQty - locationQtyView.getAvailable();
                                }
                            } else if (locationQtyView.getAvailable() - fifoReservedView.getInventtransQty() >= 0 && Utils.isZero(restQty)){
                                log.debug("---------------2");
                                if (!Utils.isZero(reservedQty)){
                                    reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, fifoReservedView.getInventtransQty() - reservedQty, locationQtyView.getBatchNo());
                                    pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), fifoReservedView.getInventtransQty() - reservedQty);
                                } else {
                                    reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, fifoReservedView.getInventtransQty(), locationQtyView.getBatchNo());
                                    pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), fifoReservedView.getInventtransQty());
                                }


                                reservedOrderDAO.persist(reservedOrderModel);
                                pickingOrderLineDAO.updateInventTransByUseFinish(fifoReservedView.getInventtransId());
                                break;
                            } else {
                                log.debug("------------1");
                                if (!Utils.isZero(reservedQty)){
                                    reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, fifoReservedView.getInventtransQty() - (fifoReservedView.getInventtransQty() - locationQtyView.getAvailable() - reservedQty), locationQtyView.getBatchNo());
                                    pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), fifoReservedView.getInventtransQty() - (fifoReservedView.getInventtransQty() - locationQtyView.getAvailable()) - reservedQty);
                                } else {
                                    reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, fifoReservedView.getInventtransQty() - (fifoReservedView.getInventtransQty() - locationQtyView.getAvailable()), locationQtyView.getBatchNo());
                                    pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyView.getId(), fifoReservedView.getInventtransQty() - (fifoReservedView.getInventtransQty() - locationQtyView.getAvailable()));
                                }

                                reservedOrderDAO.persist(reservedOrderModel);
                                // update location_qty.reserved_qty = fifoReservedView.getInventtransQty - (fifoReservedView.getInventtransQty - locationQtyView.getAvailable());
                                restQty = fifoReservedView.getInventtransQty() - locationQtyView.getAvailable();
                            }
                        } catch (Exception e){
                            log.debug("Exception error onFIFOReServed : ", e);
                        }
                    }
                }
//            }
        }
    }

    public List<LocationQtyView> onManualReserved(int pickingOrderLineId, String batchNo, int warehouseId, int locationId){
        FIFOReservedView fifoReservedView = pickingOrderLineDAO.findQtyOnInventTran(pickingOrderLineId);
        pickingOrderLineDAO.updateInventTransByUse(fifoReservedView.getInventtransId());
        return pickingOrderLineDAO.findByItemId(fifoReservedView.getItemId(), batchNo, batchNo, warehouseId, locationId, 0);
    }

    public List<MSWarehouseModel> getWarehouseAll(){
        List<MSWarehouseModel> msWarehouseModelList = Utils.getEmptyList();
        try {
            msWarehouseModelList = warehouseDAO.findAll();
        } catch (Exception e) {
            log.debug("Exception error getWarehouseAll : ", e);
        }
        return msWarehouseModelList;
    }

    public List<MSLocationModel> getLocationByWarehouse(int warehouseId){
        return locationDAO.findByWarehouseId(warehouseId);
    }

    public List<LocationQtyView> getBatchByLocation(int locationId){
        return pickingOrderLineDAO.findByLocationId(locationId);
    }

    public List<LocationQtyView> getLocationQtyBySearch(String itemId, int warehouseId, int locationId, int locationQtyId){
        return pickingOrderLineDAO.findByItemId(itemId, "", "", warehouseId, locationId, locationQtyId);
    }

    public boolean saveManualReserved(LocationQtyView locationQtyId, int reservedQty, int pickingLineId){
        StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(TableValue.RESERVED_ORDER.getId());
        FIFOReservedView fifoReservedView = pickingOrderLineDAO.findQtyOnInventTran(pickingLineId);
        inventoryQty = fifoReservedView.getInventtransQty();
        sumReservedOrder = pickingOrderLineDAO.getSumReservedOrder(pickingLineId);
        log.debug("------- sumReservedOrder : {} , inventoryQty : {}", sumReservedOrder, inventoryQty);
        try {
            PickingOrderLineModel pickingOrderLineModel = pickingOrderLineDAO.findByID(pickingLineId);
            MSLocationModel msLocationModel = locationDAO.findByID(locationQtyId.getLocationId());

            if (reservedQty + sumReservedOrder <= inventoryQty){
                ReservedOrderModel reservedOrderModel = reservedOrderTransform.tramsformToModel(pickingOrderLineModel, msLocationModel, statusModel, reservedQty, locationQtyId.getBatchNo());
                pickingOrderLineDAO.updateReservedQtyByLocaitonQtyId(locationQtyId.getId(), reservedQty);
                reservedOrderDAO.persist(reservedOrderModel);
                pickingOrderLineDAO.updateInventTransByUseFinish(fifoReservedView.getInventtransId());

                return true;
            }
        } catch (Exception e) {
            log.debug("Exception error saveManualReserved : ", e);
        }

        return false;
    }

    public void saveItemQty(int pickingLineId, int orderQty){
        pickingOrderLineDAO.updateOrderQty(pickingLineId, orderQty);
    }

    public List<MSItemModel> searchItemQty(ItemQtySearchView itemQtySearchView){
        return itemDAO.findBySearch(itemQtySearchView);
    }

    public void onSavePickingLine(PickingOrderModel pickingOrderModel, UserDetail userDetail, ItemQtyView itemQtyView){
        StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(TableValue.PICKING_LINE.getId());
        PickingOrderLineModel pickingOrderLineModel = pickingOrderLineTransform.transformToModelByAddItemQty(pickingOrderModel, statusModel, userDetail, itemQtyView);

        try {
            pickingOrderLineDAO.persist(pickingOrderLineModel);
        } catch (Exception e) {
            log.debug("Exception error onSavePickingLine : ", e);
        }
    }

    public List<ShowItemStatusView> getReservedOrder(int pickingLineId){
        return reservedOrderDAO.findByPickingLineId(pickingLineId);
    }

    public void onRemove(int reservedOrderId, String itemId){
        try {
            ReservedOrderModel orderModel = reservedOrderDAO.findByID(reservedOrderId);
            updateLocationQtyOnRemove(orderModel.getLocationId(), orderModel.getBatchNo(), itemId,  orderModel.getReservedQty());

            reservedOrderDAO.delete(reservedOrderDAO.remove(reservedOrderId));
        } catch (Exception e) {
            log.debug("Exception error onRemove");
        }
    }

    public void closeManual(int pickingLineId){
        FIFOReservedView fifoReservedView = pickingOrderLineDAO.findQtyOnInventTran(pickingLineId);
        pickingOrderLineDAO.updateInventTransByUseFinish(fifoReservedView.getInventtransId());
    }

    public void setStatusPickingOrder(int pickingOrderId){
        pickingOrderDAO.updateStatus(pickingOrderId);
    }

    public void updateLocationQtyOnRemove(int locationId, String batchNo, String itemId, int reservedQty){
        MSItemModel model = itemDAO.findByItemId(itemId);
        LocationQtyView locationQtyView = pickingOrderLineDAO.findLocationQtyByRemoveShowItem(locationId, batchNo, model.getId());
        pickingOrderLineDAO.updateLocationQtyByRemoveShowItem(locationQtyView.getId(), locationQtyView.getReservedQty() - reservedQty);

    }

    public boolean checkReserve(String itemName){
        int itemId = itemDAO.findByItemId(itemName).getId();
        List<LocationQtyView> locationQtyViewList = pickingOrderLineDAO.findOnHand(itemId);

        for (LocationQtyView qtyView : locationQtyViewList){
            if (qtyView.getQty() > 0){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
