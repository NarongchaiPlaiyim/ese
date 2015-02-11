package com.ese.service;

import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.PickingOrderView;
import com.ese.model.view.report.ConfirmationPackingViewModel;
import com.ese.model.view.report.StiketWorkLoadViewReport;
import com.ese.service.security.UserDetail;
import com.ese.transform.PickingOrderLineTransform;
import com.ese.transform.PickingOrderTransform;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Value("#{config['report.stikerworkload']}")
    private String pathStikerWorkLoad;
    @Value("#{config['report.confirmationpacking']}")
    private String pathConfirmationPacking;

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

    public List<StatusModel> getStatusAll(int tableId){
        List<StatusModel> statusModelList = Utils.getEmptyList();
        try {
            statusModelList = statusDAO.findByTablePickingOrder(tableId);
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
            axCustomerConfirmJourDAO.updateStatusRunning(view.getCustomerCode());
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
                StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder();

                String group = "";

                if ("OVS".equals(view.getCustomerGroup())){
                    group = "OverSeaOrder";
                } else {
                    group = "DomesticOrder";
                }

                PickingOrderModel model = pickingOrderTransform.tranformToModel(view, customerTableModel, statusModel, userDetail, group);
                log.debug("model : {}" ,model.toString());

                pickingOrderDAO.persist(model);

                onSavePickingOrderLine(view, statusModel, userDetail);

            } catch (Exception e) {
                log.debug("Exception error syncOrder : ", e);
            }

            axCustomerConfirmJourDAO.updateStatusFinish(view.getCustomerCode());
            axCustomerConfirmTransDAO.updateStatusFinish(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        }

        axCustomerConfirmJourDAO.rollbackStatus();
        axCustomerConfirmTransDAO.rollbackStatus();
    }

    private void onSavePickingOrderLine(DataSyncConfirmOrderView view, StatusModel status, UserDetail userDetail){
        List<AXCustomerConfirmTransModel> confirmTransModelList = axCustomerConfirmTransDAO.findByPrimaryKey(view.getSaleId(), view.getConfirmId(), view.getConfirmDate());
        PickingOrderModel model = pickingOrderDAO.findByCustomerCode(view.getCustomerCode());

        PickingOrderLineModel pickingOrderLineModel;
        for (AXCustomerConfirmTransModel axCustomerConfirmTransModel : confirmTransModelList){
            pickingOrderLineModel = pickingOrderLineTransform.transformToModel(axCustomerConfirmTransModel, model, status, userDetail);
            try {
                pickingOrderLineDAO.persist(pickingOrderLineModel);
            } catch (Exception e) {
                log.debug("Exception error onSavePickingOrderLine : ", e);
            }
        }
    }

    public void getStikerWorkLoadReport(int pickingId, UserDetail user){
        String nameReport = Utils.genReportName("_StikerWorkLoad");
        List<StiketWorkLoadViewReport> viewReports = axCustomerConfirmJourDAO.genStikerWorkLoadReport(pickingId);

        HashMap map = new HashMap<String, Object>();
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
}
