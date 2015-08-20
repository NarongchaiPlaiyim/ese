package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.PalletDAO;
import com.ese.model.dao.StockInOutDAO;
import com.ese.model.dao.StockInOutLineDAO;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.PalletModel;
import com.ese.model.db.StockInOutLineModel;
import com.ese.model.view.LocationItemView;
import com.ese.model.view.PalletManagementView;
import com.ese.model.view.PalletTransferView;
import com.ese.model.view.report.PalletManagemengModelReport;
import com.ese.transform.PalletManagementTransform;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
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
public class ShowTransPalletService extends Service{
    private static final long serialVersionUID = 4112578634029876540L;

    @Resource PalletDAO palletDAO;
    @Resource private PalletManagementTransform palletManagementTransform;
    @Resource private ReportService reportService;
    @Resource private LocationDAO locationDAO;
    @Resource private StockInOutDAO stockInOutDAO;
    @Resource private StockInOutLineDAO stockInOutLineDAO;
    @Value("#{config['report.printtag']}")
    private String pathPrintTagReport;
    @Value("#{config['report.printtagv2']}")
    private String pathPrintTagV2Report;
    @Value("#{config['report.subpallet']}")
    private String pathSubReport;

    public List<PalletTransferView> getPalletByStockID(int stockId){
        return palletDAO.findByStockInOutId(stockId);
    }

    public void onPrintTag(int palletId){
        String printTagReportName = Utils.genReportName("_PrintTag");
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
                map.put("path", FacesUtil.getRealPath(pathSubReport));
                map.put("MainPallet", palletDAO.findByIdToReport(palletModel.getId()));
                map.put("SubPallet", palletDAO.genSQLReportPalletV2(palletModel.getId()));
            }
        } catch (Exception e) {
            log.debug("Exception error onPrintTag : ", e);
        }

        try {
            reportService.exportPDF(partReport, map, printTagReportName, reportViews);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public void onUpdateByPrintTag(PalletTransferView palletManagementView, String redirect) {
        log.debug("onSaveByPrintTag().");
        PalletModel palletModel = null;
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        try{
            if (!Utils.isNull(palletManagementView)){
                palletModel = palletDAO.findByID(palletManagementView.getId());

                if (palletModel.getStatus() == 2){
                    palletModel.setStatus(palletModel.getStatus() + 1);
                } else {
                    palletModel.setStatus(palletModel.getStatus());
                }
                palletModel.setUpdateBy(staffModel);
                palletModel.setUpdateDate(new Date());
            }

            palletDAO.update(palletModel);
        } catch (Exception e){
            log.debug("Exception : {}", e);
        }
    }

    public List<PalletTransferView> search(String palletTag, String itemId, int locationId, int warehouseId){
        return palletDAO.findBySearch(palletTag, itemId, locationId, warehouseId);
    }

    public void changeLocation(PalletTransferView palletManagementView, LocationItemView locationItemView, int stockInOutId){
        log.debug("changeLocation().");
        try {
            MSLocationModel model = locationDAO.findByID(locationItemView.getId());
            PalletModel palletModel = palletDAO.findByID(palletManagementView.getId());

            palletDAO.updateNewPalletTransferByChangeLocation(palletModel.getId(), model.getId());

            if (palletModel.getStatus() == 3){
                //Print Tag
                palletDAO.updateLocationByStatusPrinted(palletModel.getMsLocationModel().getId());
            } else {
                //Find Location
                palletDAO.updateLocationByStatusLocated(model.getId());
            }

            //บวกของใหม่  ลบของเก่า
            palletDAO.updateLocationByOld(model.getId());
//            palletDAO.updateLocationByNew(palletModel.getMsLocationModel().getId());

            saveOrUpdateStockInOutLine(palletModel, stockInOutId, model.getId());

        } catch (Exception e) {
            log.debug("Exception : {}", e);
        }
    }

    private void saveOrUpdateStockInOutLine(PalletModel palletModel, int stockInOutId, int previousLocationId){

        StockInOutLineModel stockInOutLineModel = null;

        stockInOutLineModel = stockInOutLineDAO.findByPalletIdAndStockInOutId(palletModel.getId(), stockInOutId);
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());

       try{
           if (!Utils.isNull(stockInOutLineModel) && !Utils.isZero(stockInOutLineModel.getId())){
               log.debug("Update Stock In Out Line");
               stockInOutLineModel.setUpdateBy(staffModel);
               stockInOutLineModel.setUpdateDate(Utils.currentDate());
               stockInOutLineModel.setMsItemModel(palletModel.getMsItemModel());
               stockInOutLineModel.setPalletModel(palletModel);
               stockInOutLineModel.setMsLocationModel(palletModel.getMsLocationModel());
               stockInOutLineModel.setBarcode(palletModel.getPalletBarcode());
               stockInOutLineModel.setPreviousLocationId(previousLocationId);
               stockInOutLineDAO.update(stockInOutLineModel);
           } else {
               log.debug("Prsist Stock In Out Line");
               stockInOutLineModel = new StockInOutLineModel();

               stockInOutLineModel.setCreateBy(staffModel);
               stockInOutLineModel.setCreateDate(Utils.currentDate());
               stockInOutLineModel.setUpdateBy(staffModel);
               stockInOutLineModel.setUpdateDate(Utils.currentDate());
               stockInOutLineModel.setMsItemModel(palletModel.getMsItemModel());
               stockInOutLineModel.setPalletModel(palletModel);
               stockInOutLineModel.setMsLocationModel(palletModel.getMsLocationModel());
               stockInOutLineModel.setBarcode(palletModel.getPalletBarcode());
               stockInOutLineModel.setStockInOutModel(stockInOutDAO.findByID(stockInOutId));
               stockInOutLineModel.setPreviousLocationId(previousLocationId);
               stockInOutLineModel.setIsValid(1);
               stockInOutLineModel.setVersion(1);
//               stockInOutLineModel.setQty(0);
               stockInOutLineDAO.persist(stockInOutLineModel);
           }
       } catch (Exception e){
           log.debug("Exception error saveOrUpdateStockInOutLine : ", e);
       }
    }
}
