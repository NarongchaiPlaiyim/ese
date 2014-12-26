package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.MSLocationItemsDAO;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.PalletModel;
import com.ese.model.dao.PalletDAO;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.PalletManagementView;
import com.ese.model.view.report.PalletManagemengModelReport;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class PalletService extends Service{
    private static final long serialVersionUID = 4112578634920874840L;
    @Resource private PalletDAO palletDAO;
    @Resource private MSLocationItemsDAO msLocationItemsDAO;
    @Resource private LocationDAO locationDAO;
    @Resource private PalletManagementTransform palletManagementTransform;
    @Resource private ReportService reportService;
    @Value("#{config['report.printtag']}")
    private String pathPrintTagReport;
    @Value("#{config['report.printtagv2']}")
    private String pathPrintTagV2Report;
    @Value("#{config['report.subreport']}")
    private String pathSubReport;

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
        List<PalletManagementView> palletMeanegementViewList = Utils.getEmptyList();

        List<PalletModel> palletModels = palletDAO.findPalletTable();

        if (Utils.isSafetyList(palletModels)){
            log.debug("palletModels size. {}", palletModels);
            palletMeanegementViewList = palletManagementTransform.tranformToViewList(palletModels);
        }

        return palletMeanegementViewList;
    }

    public List<PalletManagementView> findByChang(int status, int warehouse, int conveyorLine, int location, String keyItemDescription, int combine, int foil){
        log.debug("findByChang().");
        List<PalletManagementView> palletMeanegementViewList = Utils.getEmptyList();

        List<PalletModel> palletModels = palletDAO.findChang(status, warehouse, conveyorLine, location, keyItemDescription, combine, foil);

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
            }

            palletDAO.update(palletModel);
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
    }

    public void onPrintTag(int palletId){
        String printTagReportname = Utils.genDateReportStringDDMMYYYY(new Date()) + "_PrintTag";
        String partReport = "";
        PalletModel palletModel = null;
        List<PalletManagemengModelReport> reportViews = null;
        HashMap map = new HashMap<String, Object>();

        try {
            palletModel = palletDAO.findByID(palletId);
            if (!Utils.isNull(palletModel) &&Utils.isZero(palletModel.getIsCombine())){
                reportViews = palletDAO.genSQLReportPallet(palletId);
                partReport = pathPrintTagReport;
            } else if (!Utils.isNull(palletModel) && !Utils.isZero(palletModel.getIsCombine())){
                partReport = pathPrintTagV2Report;
                map.put("path", pathSubReport);
                map.put("MainPallet", palletDAO.findByIdToReport(palletModel.getId()));
                map.put("SubPallet", palletDAO.genSQLReportPalletV2(palletModel.getId()));
            }
        } catch (Exception e) {
            log.debug("Exception error onPrintTag : ", e);
        }

        try {
            reportService.exportPDF(partReport, map, printTagReportname, reportViews);
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

            if (palletModel.getStatus() == 3){
                palletDAO.updateLocationByStatusPrinted(palletModel.getMsLocationModel().getId());
            } else {
                palletDAO.updateLocationByStatusLocated(model.getId());
            }

            //บวกของใหม่  ลบของเก่า
            palletDAO.updateLocationByOld(model.getId());
//            palletDAO.updateLocationByNew(palletModel.getMsLocationModel().getId());

        } catch (Exception e) {
            log.debug("Exception : {}", e);
        }
    }
}
