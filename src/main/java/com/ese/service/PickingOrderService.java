package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.*;
import com.ese.model.view.report.ConfirmationPackingViewModel;
import com.ese.model.view.report.StickerWorkLoadViewReport;
import com.ese.model.view.report.SubPickingOrderWithBarcodeViewReport;
import com.ese.service.security.UserDetail;
import com.ese.transform.PickingOrderLineTransform;
import com.ese.transform.PickingOrderTransform;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class PickingOrderService extends Service {

    @Resource private PickingOrderDAO pickingOrderDAO;
    @Resource private UserAccessDAO userAccessDAO;
    @Resource private StatusDAO statusDAO;
    @Resource private AXCustomerConfirmJourDAO axCustomerConfirmJourDAO;
    @Resource private AXCustomerConfirmTransDAO axCustomerConfirmTransDAO;
    @Resource private AXCustomerTableDAO axCustomerTableDAO;
    @Resource private PickingOrderTransform pickingOrderTransform;
    @Resource private PickingOrderLineDAO pickingOrderLineDAO;
    @Resource private PickingOrderLineTransform pickingOrderLineTransform;
    @Resource private ReportService reportService;
    @Resource private ItemDAO itemDAO;
    @Resource private ReservedOrderDAO reservedOrderDAO;
    @Resource private InvOnHandDAO invOnHandDAO;

    @Value("#{config['report.stikerworkload']}")
    private String pathStikerWorkLoad;
    @Value("#{config['report.confirmationpacking']}")
    private String pathConfirmationPacking;
    @Value("#{config['report.pickingorder']}")
    private String pathPickingOrderWithItemBarcode;
    @Value("#{config['report.pickingorder.sub']}")
    private String pathPickingOrderWithItemBarcodeSub;

    private static final String A031 ="031A";
    private static final String B031 ="031B";
    private static final String ALL ="All";

    public String getTypeBeforeOnLoaf(long staffId){
        List<UserAccessModel> userAccessModelList = userAccessDAO.findByPickingOrder(Utils.parseInt(staffId));
        String mode = ALL;
        final int SEARCH_ALL = 2;
        if(SEARCH_ALL != userAccessModelList.size()){
            for(UserAccessModel model : userAccessModelList){
                if (A031.equals(model.getMenuObjectModel().getCode())){
                    mode = A031;
                } else if (B031.equals(model.getMenuObjectModel().getCode())){
                    mode = B031;
                }
            }
        }
        return mode;
    }

    public List<PickingOrderModel> getPickingOrderByOverSeaOrder(String modeFind){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        log.debug("getPickingOrderByOverSeaOrder : {}", modeFind);
        if (A031.equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaOrder();
        } else if (B031.equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByDomesticOrder();
        } else if (ALL.equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaAndDomesticOrder();
        }
        return pickingOrderModelList;
    }

    public List<StatusModel> getStatusAll(){
        List<StatusModel> statusModelList = Utils.getEmptyList();
        try {
            statusModelList = statusDAO.findByTablePickingOrder(TableValue.PICKING_ORDER.getId());
        } catch (Exception e) {
            log.debug("Exception error getStatusAll", e);
        }
        return statusModelList;
    }

    public List<PickingOrderModel> getPickingOnSearch(PickingOrderView pickingOrderView){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        if (!Utils.isNull(pickingOrderView)){
            pickingOrderModelList = pickingOrderDAO.findByPickingView(pickingOrderView);
        }
        return pickingOrderModelList;
    }

    public List<DataSyncConfirmOrderView> getDataOnSync(){
        return axCustomerConfirmJourDAO.genSQLSyncData();
    }

    public void updateStatus(List<DataSyncConfirmOrderView> viewList){
        for (DataSyncConfirmOrderView view : viewList){
            axCustomerConfirmJourDAO.updateStatusRunning(view.getConfirmId(), view.getConfirmDate(), view.getSaleId());
            axCustomerConfirmTransDAO.updateStatusRunning(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        }
    }

    public void rollbackStatus(){
        axCustomerConfirmJourDAO.rollbackStatus();
        axCustomerConfirmTransDAO.rollbackStatus();
    }

    public void syncOrder(List<DataSyncConfirmOrderView> viewList, UserDetail userDetail){

        for (DataSyncConfirmOrderView view : viewList){

            try {
                AXCustomerTableModel customerTableModel = axCustomerTableDAO.findByAccountNum(view.getCustomerCode());
                StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(TableValue.PICKING_ORDER.getId());

                String group = "";

                if ("OVS".equals(view.getCustomerGroup())){
                    group = "OverSeaOrder";
                } else {
                    group = "DomesticOrder";
                }

                PickingOrderModel model = pickingOrderTransform.tranformToModel(view, customerTableModel, statusModel, userDetail, group);
                log.debug("model : {}" ,model.toString());

                pickingOrderDAO.persist(model);

                onSavePickingOrderLine(view, userDetail);

            } catch (Exception e) {
                log.debug("Exception error syncOrder : ", e);
            }

            axCustomerConfirmJourDAO.updateStatusFinish(view.getConfirmId(), view.getConfirmDate(), view.getSaleId());
            axCustomerConfirmTransDAO.updateStatusFinish(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        }

        axCustomerConfirmJourDAO.rollbackStatus();
        axCustomerConfirmTransDAO.rollbackStatus();
    }

    private void onSavePickingOrderLine(DataSyncConfirmOrderView view, UserDetail userDetail){
        List<CustomerConfirmTransView> confirmTransModelList = axCustomerConfirmTransDAO.findByPrimaryKey(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        PickingOrderModel model = pickingOrderDAO.findByCustomerCode(view.getCustomerCode());
        StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(TableValue.PICKING_LINE.getId());

        PickingOrderLineModel pickingOrderLineModel;
        for (CustomerConfirmTransView axCustomerConfirmTransModel : confirmTransModelList){
            log.debug("-----------axCustomerConfirmTransModel : {}", axCustomerConfirmTransModel.getItemId());
            pickingOrderLineModel = pickingOrderLineTransform.transformToModel(axCustomerConfirmTransModel, model, statusModel, userDetail);
            try {
                pickingOrderLineDAO.persist(pickingOrderLineModel);
            } catch (Exception e) {
                log.debug("Exception error onSavePickingOrderLine : ", e);
            }
        }
    }

    public void getStikerWorkLoadReport(int pickingId, UserDetail user){
        String nameReport = Utils.genReportName("_StikerWorkLoad");
        List<StickerWorkLoadViewReport> viewReports = axCustomerConfirmJourDAO.genStikerWorkLoadReport(pickingId);

        HashMap map = new HashMap();
        map.put("userPrint", user.getUserName());
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());

        try {
            reportService.exportPDF(pathStikerWorkLoad, map, nameReport, viewReports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public void getConfirmationPackingReport(int pickingId, UserDetail user){

        String nameReport = Utils.genReportName("_ConfirmationPacking");
        List<ConfirmationPackingViewModel> viewReports = axCustomerConfirmJourDAO.genConfirmationPackingReport(pickingId);

        HashMap map = new HashMap<String, Object>();
        map.put("userPrint", user.getUserName());
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());

        try {
            reportService.exportPDF(pathConfirmationPacking, map, nameReport, viewReports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public void updateOnCancel(int pickindId){
        pickingOrderDAO.updateStatus(pickindId, 6); //Status Cancle = 6 in mst_status on picking order
        pickingOrderLineDAO.cancelByPickingOrder(pickindId);
    }

    public void cancel(PickingOrderModel pickingModel){
        try {
            List<PickingOrderLineModel> pickingOrderLineModelList = pickingOrderLineDAO.findByPickingId(pickingModel.getId());

            for (PickingOrderLineModel lineModel : pickingOrderLineModelList){
                List<ReservedOrderModel> reservedOrderModelList = reservedOrderDAO.findByLineId(lineModel.getId());

                for (ReservedOrderModel reservedOrderModel : reservedOrderModelList){
                    updateLocationQtyOnRemove(reservedOrderModel.getLocationId(), reservedOrderModel.getBatchNo(), lineModel.getItemId(),  reservedOrderModel.getReservedQty());
                    reservedOrderDAO.delete(reservedOrderDAO.remove(reservedOrderModel.getId()));
                }
            }
        } catch (Exception e) {
            log.debug("Exception error cancel : ", e);
        }
    }

    public void updateLocationQtyOnRemove(int locationId, String batchNo, String itemId, int reservedQty){
        MSItemModel model = itemDAO.findByItemId(itemId);
        LocationQtyView locationQtyView = pickingOrderLineDAO.findLocationQtyByRemoveShowItem(locationId, batchNo, model.getId());
        pickingOrderLineDAO.updateLocationQtyByRemoveShowItem(locationQtyView.getId(), locationQtyView.getReservedQty() - reservedQty);
    }

    public void getPickingOrderWithItemBarcodeReport(int pickingId, UserDetail user){

        String nameReport = Utils.genReportName("_PickingOrderWithItemBarcode");
        List<PickingOrderWithItemBarcodeReport> viewReports = axCustomerConfirmJourDAO.getPcikingOrderWithItemBarcodeReport(pickingId);

        HashMap map = new HashMap<String, Object>();
        map.put("userPrint", user.getUserName());
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());
        map.put("path", FacesUtil.getRealPath(pathPickingOrderWithItemBarcodeSub));
        map.put("subreport", getBySubReport(pickingId));

        try {
            reportService.exportPDF(pathPickingOrderWithItemBarcode, map, nameReport, viewReports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    private List<SubPickingOrderWithBarcodeViewReport> getBySubReport(int pickingId){
//        List<InvOnHandModel> invOnHandModelList = invOnHandDAO.findByPickingId(pickingId);
//        List<InventoryOnHandViewReport> reportList = new ArrayList<InventoryOnHandViewReport>();
//        int i = 1;
//
//        for (InvOnHandModel model : invOnHandModelList){
//            InventoryOnHandViewReport report = new InventoryOnHandViewReport();
//            report.setNo(i);
//
//            if (!Utils.isNull(model.getPalletModel()) && !Utils.isZero(model.getPalletModel().getPalletBarcode().length())){
//                report.setPalletBarcode(model.getPalletModel().getPalletBarcode());
//            } else {
//                report.setPalletBarcode("");
//            }
//
//            report.setSnBarcode(model.getSnBarcode());
//            i++;
//            reportList.add(report);
//        }

        return invOnHandDAO.findByPickingIdOnReport(pickingId);
    }

    public boolean checkPost(int pickingOrderId){
        List<InvOnhandPostView> invOnhandViewList = invOnHandDAO.findCountInvOnhand(pickingOrderId);
        List<PickingOrderLinePostView> pickingOrderLinePostViewList = pickingOrderLineDAO.findOnPostStatus(pickingOrderId);

        log.debug("invOnhandViewList size {}", invOnhandViewList.size());
        log.debug("pickingOrderLinePostViewList size {}", pickingOrderLinePostViewList.size());

        if (!Utils.isSafetyList(invOnhandViewList) || !Utils.isSafetyList(pickingOrderLinePostViewList)){
            return false;
        }

        if (invOnhandViewList.size() != pickingOrderLinePostViewList.size()){
            return false;
        }

        for (int i=0; i<invOnhandViewList.size(); i++){
            if (invOnhandViewList.get(i).getItemId() == pickingOrderLinePostViewList.get(i).getItemId()
                    && invOnhandViewList.get(i).getCountId() != pickingOrderLinePostViewList.get(i).getPickingQty()){
                return false;
            }
        }

        return updateStatusPost(pickingOrderId);
    }

    public boolean authorize(int userId, int pickingOrderId){
        UserAccessModel userAccessModel = userAccessDAO.findPostPickByPickingOrder(Utils.parseInt(userId));

        if (Utils.isNull(userAccessModel)){
            return false;
        }

        return updateStatusPost(pickingOrderId);
    }

    public boolean updateStatusPost(int pickingOrderId){
        pickingOrderDAO.updateStatus(pickingOrderId, 5); //Status Post = 5 in mst_status on picking order
        return true;
    }
}
