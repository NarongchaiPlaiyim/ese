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

    public String getTypeBeforeOnLoaf(long staffId){
        List<UserAccessModel> userAccessModelList = userAccessDAO.findByPickingOrder(Utils.parseInt(staffId, 0));
        String mode = "";
        if (userAccessModelList.size() == 2){
            mode = "All";
        } else {
            for(UserAccessModel model : userAccessModelList){
                log.debug("");
                if ("031A".equals(model.getMenuObjectModel().getCode())){
                    mode = "031A";
                } else if ("031B".equals(model.getMenuObjectModel().getCode())){
                    mode = "031B";
                }
            }
        }

        return mode;
    }

    public List<PickingOrderModel> getPickingOrderByOverSeaOrder(String modeFind){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        log.debug("getPickingOrderByOverSeaOrder : {}", modeFind);

        if ("031A".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaOrder();
        } else if ("031B".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByDomesticOrder();
        } else if ("All".equals(modeFind)){
            pickingOrderModelList = pickingOrderDAO.findByOverSeaAndDomesticOrder();
        }

        return pickingOrderModelList;
    }

    public List<StatusModel> getStatusAll(int tableId){
        try {
            return statusDAO.findByTablePickingOrder(tableId);
        } catch (Exception e) {
            log.debug("Excrption error getStatusAll", e);
            return null;
        }
    }

    public List<PickingOrderModel> getPickingOnSearch(PickingOrderView pickingOrderView){
        if (!Utils.isNull(pickingOrderView)){
            return pickingOrderDAO.findByPickingView(pickingOrderView);
        } else {
            return Utils.getEmptyList();
        }
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
                StatusModel statusModel = statusDAO.findByStatusSeqTablePickingOrder(1);

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

        for (AXCustomerConfirmTransModel axCustomerConfirmTransModel : confirmTransModelList){
            PickingOrderLineModel pickingOrderLineModel = pickingOrderLineTransform.transformToModel(axCustomerConfirmTransModel, model, status, userDetail);

            try {
                pickingOrderLineDAO.persist(pickingOrderLineModel);
            } catch (Exception e) {
                log.debug("Exception error onSavePickingOrderLine : ", e);
            }
        }
    }

    public void getStikerWorkLoadReport(int pickingId, UserDetail user){
        String nameReport = Utils.genDateReportStringDDMMYYYY(new Date()) + "_StikerWorkLoad";
        List<StiketWorkLoadViewReport> viewReports = axCustomerConfirmJourDAO.genStikerWorkLoadReport(pickingId);

        HashMap map = new HashMap<String, Object>();
        map.put("userPrint", user.getUserName());
        map.put("printDate", Utils.convertToStringDDMMYYYY(new Date()));

        try {
            reportService.exportPDF(pathStikerWorkLoad, map, nameReport, viewReports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public void getConfirmationPackingReport(int pickingId, UserDetail user){

        String nameReport = Utils.genDateReportStringDDMMYYYY(new Date()) + "_ConfirmationPacking";
        List<ConfirmationPackingViewModel> viewReports = axCustomerConfirmJourDAO.genConfirmationPackingReport(pickingId);

        HashMap map = new HashMap<String, Object>();
        map.put("userPrint", user.getUserName());
        map.put("printDate", Utils.convertToStringDDMMYYYY(new Date()));

        try {
            reportService.exportPDF(pathConfirmationPacking, map, nameReport, viewReports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }

    }
}
