package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.MSLocationItemsDAO;
import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.PalletModel;
import com.ese.model.dao.PalletDAO;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.LocationView;
import com.ese.model.view.PalletManagementView;
import com.ese.model.view.report.PalletManagemengReportView;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class PalletService extends Service{
    @Resource private PalletDAO palletDAO;
    @Resource private MSLocationItemsDAO msLocationItemsDAO;
    @Resource private LocationDAO locationDAO;
    @Resource private PalletManagementTransform palletManagementTransform;
    @Resource private ReportService reportService;

    public void test(){
        int i = 58;
        try {
            msLocationItemsDAO.findLocationByItemId(i);
        } catch (Exception e) {
            log.debug("-------------");
        }
    }

    public List<PalletManagementView> findPalletJoinLocation(){
        log.debug("findPalletJoinLocation().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManagementTransform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletManagementView> findByChang(int status, int warehouse, int conveyorLine, int location, String keyItemDescription){
        log.debug("findByChang().");
        List<PalletManagementView> palletMeanegementViewList = new ArrayList<PalletManagementView>();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyorLine, location, keyItemDescription);

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels.size());
            palletMeanegementViewList = palletManagementTransform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public void onUpdateByPrintTag(PalletManagementView palletManagementView, String redirect) {
        log.debug("onSaveByPrintTag().");
        PalletModel palletModel = null;

        try{
            if (!Utils.isNull(palletManagementView)){
                palletModel = palletManagementTransform.transformToMode(palletManagementView, redirect);
            } else {
                palletModel = new PalletModel();
            }

            palletDAO.update(palletModel);
//            onPrintTag(palletManagementView.getId());
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
    }

    public void onPrintTag(int palletId){
        String printTagReportname = "test_report";
        List<PalletManagemengReportView> reportViews = palletDAO.genSQLReportPallet(palletId);
        log.debug("reportViews {}", reportViews.size());
        HashMap map = new HashMap<String, Object>();
        try {
            reportService.exportPDF("D:/parttime/ESE's source/ese/web/site/report/PalletManagement.jrxml", map, printTagReportname, reportViews);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public void changeLocation(PalletManagementView palletManagementView, LocationItemView locationItemView){
        log.debug("changeLocation().");
        try {
            MSLocationModel model = locationDAO.findByID(locationItemView.getId());
            PalletModel palletModel = palletDAO.findByID(palletManagementView.getId());

            palletDAO.updauePalletByChangeLocation(palletModel.getId(), model.getId());
            palletDAO.updateLocationByChangeLocation(model.getId());

        } catch (Exception e) {
            log.debug("Exception : {}", e);
        }
    }
}
