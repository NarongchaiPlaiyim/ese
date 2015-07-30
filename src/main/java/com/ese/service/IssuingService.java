package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.StatusDAO;
import com.ese.model.dao.StockInOutDAO;
import com.ese.model.dao.StockInOutNoteDAO;
import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.model.db.StatusModel;
import com.ese.model.db.StockInOutModel;
import com.ese.model.view.IssuingView;
import com.ese.model.view.report.IncomingViewReport;
import com.ese.utils.AttributeName;
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
public class IssuingService extends Service {
    private static final long serialVersionUID = 4442578634029876540L;
    @Resource private StockInOutDAO stockInOutDAO;
    @Resource private StockInOutNoteDAO stockInOutNoteDAO;
    @Resource private StatusDAO statusDAO;
    @Resource private ReportService reportService;
    @Value("#{config['report.issuing']}")
    private String pathPrintIssuing;
    @Value("#{config['report.issuing.sub']}")
    private String pathPrintIssuingSub;

    public List<StockInOutModel> getOnLoad(){
        return stockInOutDAO.findByDocNoIOUAndCurrentDate();
    }

    public List<MSStockInOutNoteModel> getAllStockInOutNote(){
        return stockInOutNoteDAO.getStockInOutNoteOrderByTypeO();
    }

    public void save(IssuingView issuingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = new StockInOutModel();
            stockInOutModel.setDocNo(issuingView.getDocNo());
            stockInOutModel.setDocDate(issuingView.getDocDate());
            stockInOutModel.setMsStockInOutNoteModel(issuingView.getMsStockInOutNoteModel());
            stockInOutModel.setRemark(issuingView.getRemark());
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutModel.setCreateDate(Utils.currentDate());
            stockInOutModel.setCreateBy(staffModel);
            stockInOutModel.setIsValid(1);
            StatusModel statusModel = new StatusModel();
            statusModel.setId(17);
            stockInOutModel.setStatus(statusModel);
            stockInOutDAO.persist(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during save ", e);
        }
    }

    public void edit(IssuingView issuingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = stockInOutDAO.findByID(issuingView.getId());
            stockInOutModel.setDocDate(issuingView.getDocDate());
            stockInOutModel.setMsStockInOutNoteModel(issuingView.getMsStockInOutNoteModel());
            stockInOutModel.setRemark(issuingView.getRemark());
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutDAO.update(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during edit ", e);
        }
    }

    public List<StockInOutModel> search(IssuingView issuingView){
        return stockInOutDAO.findBySearchIOU(issuingView);
    }

    public void post(IssuingView issuingView){
        try {
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            StockInOutModel stockInOutModel = stockInOutDAO.findByID(issuingView.getId());
            stockInOutModel.setDocDate(issuingView.getDocDate());
            stockInOutModel.setMsStockInOutNoteModel(issuingView.getMsStockInOutNoteModel());
            stockInOutModel.setRemark(issuingView.getRemark());
            stockInOutModel.setStatus(statusDAO.findByTableIdAndStatus(TableValue.STOCK_IN_OUT.getId(), 3));
            stockInOutModel.setUpdateDate(Utils.currentDate());
            stockInOutModel.setUpdateBy(staffModel);
            stockInOutDAO.update(stockInOutModel);
        } catch (Exception e) {
            log.debug("Exception error during edit ", e);
        }
    }

    public void printReport(int stockInoutId){
        String reportName = Utils.genReportName("_Issuing");
        List<IncomingViewReport> reportViews = stockInOutDAO.findReportByStickInoutId(stockInoutId);
        HashMap map = new HashMap();

        try {
            map.put("path", FacesUtil.getRealPath(pathPrintIssuingSub));
//            map.put("subReport", stockMovementInDAO.findSubReportByStickInoutId(stockInoutId));
        } catch (Exception e) {
            log.debug("Exception error onPrintTag : ", e);
        }

        try {
            reportService.exportPDF(pathPrintIssuing, map, reportName, reportViews);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }
}
