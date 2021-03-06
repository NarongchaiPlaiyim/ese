package com.ese.service;

import com.ese.model.dao.BarcodeRegisterDAO;
import com.ese.model.dao.ItemDAO;
import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.model.view.report.BarcodeRegisterModelReport;
import com.ese.transform.BarcodeRegisterTransform;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class BarcodeRegisterService extends Service{
    private static final long serialVersionUID = 4112577774029874840L;
    @Resource private ItemDAO itemDAO;
    @Resource private BarcodeRegisterDAO barcodeRegisterDAO;
    @Resource private BarcodeRegisterTransform barcodeRegisterTransform;
    @Resource private ReportService reportService;
    @Value("#{config['report.barcode']}")private String pathBarcodeReport;

    public List<BarcodeRegisterModel> findBarcodeByCondition(final String type, final String text){
        log.debug("-- findBarcodeByCondition({}, {})", type, text);
        List<BarcodeRegisterModel> barcodeRegisterModelList = Utils.getEmptyList();
        try {
            if("2".equalsIgnoreCase(type)){
                barcodeRegisterModelList = barcodeRegisterDAO.findByLike("batchNo", text);
            } else if("3".equalsIgnoreCase(type)){
                barcodeRegisterModelList = barcodeRegisterDAO.findByLike(text);
            } else {
                barcodeRegisterModelList = barcodeRegisterDAO.findByBetween(getOnlyDigit(text));
            }
        } catch (Exception e) {
            log.error("{}",e);
        }
        return barcodeRegisterModelList;
    }

    public List<MSItemModel> findByCondition(final String type, final String text){
        log.debug("-- findByCondition({}, {})", type, text);
        List<MSItemModel> msItemModelList = Utils.getEmptyList();
        try {
            if("3".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("dSGThaiItemDescription", text);
            } else if("2".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("itemId", text);
            } else {
                msItemModelList = itemDAO.findByLike("itemName", text);
            }
        } catch (Exception e) {
            log.error("{}",e);
        }
        return msItemModelList;
    }

    public List<BarcodeRegisterModel> getByIsValid(){
        log.debug("-- getByIsValid()");
        List<BarcodeRegisterModel> barcodeRegisterModelList = Utils.getEmptyList();
        try {
            barcodeRegisterModelList = barcodeRegisterDAO.findByIsValid();
        } catch (Exception e) {
            log.error("{}",e);
        }
        return barcodeRegisterModelList;
    }

    public List<BarcodeRegisterModel> getByStockInOut(String stockInout){
        log.debug("-- getByIsValid()");
        List<BarcodeRegisterModel> barcodeRegisterModelList = Utils.getEmptyList();
        try {
            barcodeRegisterModelList = barcodeRegisterDAO.findByStockInOut(stockInout);
        } catch (Exception e) {
            log.error("{}",e);
        }
        return barcodeRegisterModelList;
    }

    public void delete(BarcodeRegisterModel model){
        log.debug("-- delete(id : {})", model.getId());
        try {
            barcodeRegisterDAO.deleteByUpdate(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public void deleteFormSetValid(BarcodeRegisterModel model){
        log.debug("-- delete(id : {})", model.getId());
        try {
            model.setIsValid(0);
            barcodeRegisterDAO.update(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public BarcodeRegisterView convertToView(BarcodeRegisterModel model){
        log.debug("-- convertToView(BarcodeRegisterModel.id[{}])", model.getId());
        BarcodeRegisterView barcodeRegisterView = new BarcodeRegisterView();
        try {
            barcodeRegisterView = barcodeRegisterTransform.transformToView(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
        return barcodeRegisterView;
    }

    public void saveOrUpdate(BarcodeRegisterView view){
        log.debug("-- save(BarcodeRegisterView.id[{}])", view.getId());
        BarcodeRegisterModel model;
        try {
            view.setCost(barcodeRegisterDAO.getPrice(view.getMsItemModel().getItemId()));
            model = barcodeRegisterTransform.transformToModel(view);
            barcodeRegisterDAO.saveOrUpdate(model);

            /*if (Utils.isZero(view.getId())){
                barcodeRegisterDAO.persist(barcodeRegisterTransform.transformToModel(view));
            } else {
                BarcodeRegisterModel model = barcodeRegisterTransform.transformToModel(view);
                barcodeRegisterDAO.update(model);
            }*/
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public void edit(BarcodeRegisterView view){
        log.debug("-- edit(BarcodeRegisterView.id[{}])", view.getId());
        try {
            view.setCost(barcodeRegisterDAO.getPrice(view.getMsItemModel().getItemId()));
            BarcodeRegisterModel model = barcodeRegisterTransform.transformToModel(view);
            barcodeRegisterDAO.update(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public void onPrintBarcode(int barcodeId){
        String printBarcodeName = Utils.genReportName("_BarcodeRegister");
        List<BarcodeRegisterModelReport> reports = barcodeRegisterDAO.genSQLReportBarcode(barcodeId);

        log.debug("reportViews {}", reports.size());
        HashMap map = new HashMap<String, Object>();
        try {
            reportService.exportPDF(pathBarcodeReport, map, printBarcodeName, reports);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }

    public boolean isDuplicate(String startBarcode, String finishBarcode, int id){
        try {
            boolean result = false;
            List<BarcodeRegisterModel> barcodeRegisterModelList = getByIsValid();
            System.out.println(barcodeRegisterModelList.size());
            int start = Utils.parseInt(startBarcode, 0);
            int finish = Utils.parseInt(finishBarcode, 0);
            for (BarcodeRegisterModel model : barcodeRegisterModelList){
                if(model.getId() != id){
                    int startM = Utils.parseInt(model.getStartBarcode(), 0);
                    int finishM = Utils.parseInt(model.getFinishBarcode(), 0);
                    while (start<=finish){
                        while (startM<=finishM){
                            if(start == startM){
                                return true;
                            }
                            startM++;
                        }
                        startM = Utils.parseInt(model.getStartBarcode(), 0);
                        start++;
                    }
                    start = Utils.parseInt(startBarcode, 0);
                }
            }
            return result;
        } catch (Exception e) {
            log.error("{}", e);
            return true;
        }
    }

    private String getOnlyDigit(String s) {
        StringBuilder result = new StringBuilder("");
        for(int i = 0; i < s.length(); i++) {
            try {
                result.append(Integer.parseInt(String.valueOf(s.charAt(i))));
            } catch (Exception e) {

            }
        }
        return result.toString();
    }
}
