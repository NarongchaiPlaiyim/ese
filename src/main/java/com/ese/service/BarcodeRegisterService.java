package com.ese.service;

import com.ese.model.dao.BarcodeRegisterDAO;
import com.ese.model.dao.ItemDAO;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.model.view.report.BarcodeRegisterModelReport;
import com.ese.transform.BarcodeRegisterTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class BarcodeRegisterService extends Service{
    @Resource private ItemDAO itemDAO;
    @Resource private BarcodeRegisterDAO barcodeRegisterDAO;
    @Resource private BarcodeRegisterTransform barcodeRegisterTransform;
    @Resource private ReportService reportService;

    public List<MSItemModel> findByCondition(final String type, final String text){
        log.debug("-- findByCondition({}, {})", type, text);
        try {
            List<MSItemModel> msItemModelList;
            if("3".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("dSGThaiItemDescription", text);
            } else if("2".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("itemId", text);
            } else {
                msItemModelList = itemDAO.findByLike("itemName", text);
            }
            return msItemModelList;
        } catch (Exception e) {
            log.error("{}",e);
            return Collections.EMPTY_LIST;
        }
    }

    public List<BarcodeRegisterModel> getByIsValid(){
        log.debug("-- getByIsValid()");
        try {
            return barcodeRegisterDAO.findByIsValid();
        } catch (Exception e) {
            log.error("{}",e);
            return Collections.EMPTY_LIST;
        }
    }

    public void delete(BarcodeRegisterModel model){
        log.debug("-- delete(id : {})", model.getId());
        System.out.println(model.getId());
        try {
            barcodeRegisterDAO.deleteByUpdate(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public BarcodeRegisterView convertToView(BarcodeRegisterModel model){
        log.debug("-- convertToView(BarcodeRegisterModel.id[{}])", model.getId());
        try {
            return barcodeRegisterTransform.transformToView(model);
        } catch (Exception e) {
            log.error("{}",e);
            return new BarcodeRegisterView();
        }
    }

    public void save(BarcodeRegisterView view){
        log.debug("-- save(BarcodeRegisterView.id[{}])", view.getId());
        try {
            view.setCost(barcodeRegisterDAO.getPrice(view.getMsItemModel().getItemId()));
            barcodeRegisterDAO.persist(barcodeRegisterTransform.transformToModel(view));
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public void edit(BarcodeRegisterView view){
        log.debug("-- edit(BarcodeRegisterView.id[{}])", view.getId());
        try {
            view.setCost(barcodeRegisterDAO.getPrice(view.getMsItemModel().getItemId()));
            barcodeRegisterDAO.update(barcodeRegisterTransform.transformToModel(view));
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public void onPrintBarcode(int barcodeId){
        String printBarcodeName = Utils.convertToStringDDMMYYYY(new Date()) + "_BarcodeRegister";
        List<BarcodeRegisterModelReport> reports = barcodeRegisterDAO.genSQLReportBarcode(barcodeId);

        log.debug("reportViews {}", reports.size());
        HashMap map = new HashMap<String, Object>();
        try {
            reportService.exportPDF("D:/parttime/ESE's source/ese/web/site/report/BarcodeRegisterReport.jrxml", map, printBarcodeName, reports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }
}
