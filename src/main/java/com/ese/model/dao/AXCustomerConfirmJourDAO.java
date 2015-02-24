package com.ese.model.dao;

import com.ese.model.db.AXCustomerConfirmJourModel;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.report.ConfirmationPackingViewModel;
import com.ese.model.view.report.StiketWorkLoadViewReport;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AXCustomerConfirmJourDAO extends GenericDAO<AXCustomerConfirmJourModel, String>{

    public List<DataSyncConfirmOrderView> genSQLSyncData(){
        List<DataSyncConfirmOrderView> syncDataList = new ArrayList<DataSyncConfirmOrderView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT DISTINCT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.AccountNum AS CUST_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.Name AS CUST_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.CustGroup AS CUST_GROUP,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.confirmId AS CONFIRM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.confirmdate AS CONFIRM_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDocNum AS CONFIRM_DOCNUM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId AS SALE_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.purchaseorder AS PURCHASE_ORDER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.deliveryname AS DELIVERY_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.deliveryaddress AS DELIVERY_ADDRESS,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.customerref AS CUST_REF,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.containers AS CONTAINER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.dlvterm AS DLVTERM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.paymentcondition AS PAY_CONDITION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.remark AS REMARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.SalesAdministrator AS SALES_ADMIN,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.modedlv AS MODEDLV,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.quotationid AS QUOTATION_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.quotationcreatedate AS QUOTATION_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_ShippingDateRequested AS SHIPPING_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_EDDDate AS EDD_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_AvailableDate AS AVAILABLE_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_remark AS DSG_REMARK");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".ax_CustConfirmJour");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustTable");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".ax_CustConfirmJour.InvoiceAccount = ").append(getPrefix()).append(".ax_CustTable.AccountNum");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustConfirmTrans");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmId = ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmId");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.SalesId  = ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmDate = ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDate");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.status = 1" );

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("CUST_CODE", StringType.INSTANCE)
                    .addScalar("CUST_NAME", StringType.INSTANCE)
                    .addScalar("CUST_GROUP", StringType.INSTANCE)
                    .addScalar("CONFIRM_ID", StringType.INSTANCE)
                    .addScalar("CONFIRM_DATE", TimestampType.INSTANCE)
                    .addScalar("CONFIRM_DOCNUM", StringType.INSTANCE)
                    .addScalar("SALE_ID", StringType.INSTANCE)
                    .addScalar("PURCHASE_ORDER", StringType.INSTANCE)
                    .addScalar("DELIVERY_NAME", StringType.INSTANCE)
                    .addScalar("DELIVERY_ADDRESS", StringType.INSTANCE)
                    .addScalar("CUST_REF", StringType.INSTANCE)
                    .addScalar("CONTAINER", StringType.INSTANCE)
                    .addScalar("DLVTERM", StringType.INSTANCE)
                    .addScalar("PAY_CONDITION", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE)
                    .addScalar("SALES_ADMIN", StringType.INSTANCE)
                    .addScalar("MODEDLV", StringType.INSTANCE)
                    .addScalar("QUOTATION_ID", StringType.INSTANCE)
                    .addScalar("QUOTATION_DATE", TimestampType.INSTANCE)
                    .addScalar("SHIPPING_DATE", TimestampType.INSTANCE)
                    .addScalar("EDD_DATE", TimestampType.INSTANCE)
                    .addScalar("AVAILABLE_DATE", TimestampType.INSTANCE)
                    .addScalar("DSG_REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                DataSyncConfirmOrderView sync = new DataSyncConfirmOrderView();
                sync.setCustomerCode(Utils.parseString(entity[0], ""));
                sync.setCustomerName(Utils.parseString(entity[1], ""));
                sync.setCustomerGroup(Utils.parseString(entity[2], ""));
                sync.setConfirmId(Utils.parseString(entity[3], ""));
                sync.setConfirmDate(Utils.parseDate(entity[4], null));
                sync.setConfirmDocNo(Utils.parseString(entity[5], ""));
                sync.setSaleId(Utils.parseString(entity[6], ""));
                sync.setPurchaseOrder(Utils.parseString(entity[7], ""));
                sync.setDeliveryName(Utils.parseString(entity[8], ""));
                sync.setDeliveryAddress(Utils.parseString(entity[9], ""));
                sync.setCustomerRef(Utils.parseString(entity[10], ""));
                sync.setContainer(Utils.parseString(entity[11], ""));
                sync.setDlvTerm(Utils.parseString(entity[12], ""));
                sync.setPaymentCondition(Utils.parseString(entity[13], ""));
                sync.setRemark(Utils.parseString(entity[14], ""));
                sync.setSaleAdmin(Utils.parseString(entity[15], ""));
                sync.setModeDlv(Utils.parseString(entity[16], ""));
                sync.setQuotationId(Utils.parseString(entity[17], ""));
                sync.setQuotationDate(Utils.parseDate(entity[18], null));
                sync.setShippingDate(Utils.parseDate(entity[19], null));
                sync.setEddDate(Utils.parseDate(entity[20], null));
                sync.setAvailableDate(Utils.parseDate(entity[21], null));
                sync.setDsgRemark(Utils.parseString(entity[22], ""));
                syncDataList.add(sync);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return syncDataList;
    }

    public void updateStatusRunning(String confirmId, Date confirmDate, String saleId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 2 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmId = ").append("'").append(confirmId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDate = ").append("'").append(confirmDate).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId = ").append("'").append(saleId).append("'");

        log.debug("SQL ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void rollbackStatus(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.status = 2");

        log.debug("SQL Roll Back ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateStatusFinish(String confirmId, Date confirmDate, String saleId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 3 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmId = ").append("'").append(confirmId).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDate = ").append("'").append(confirmDate).append("'");
        stringBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId = ").append("'").append(saleId).append("'");

        log.debug("SQL ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public List<StiketWorkLoadViewReport> genStikerWorkLoadReport(int pickingId){
        List<StiketWorkLoadViewReport> viewReports = new ArrayList<StiketWorkLoadViewReport>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.sales_order AS ORDER_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.docno AS DOC_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.name AS CUSTOMER_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM_NUMBER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS THAI_ITEM_DESCRIPTION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.picked_qty AS QUANTITY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.salesunit AS ORDER_UNIT,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.dsg_remark AS REMARK");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".picking_order");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".picking_order.id = ").append(getPrefix()).append(".picking_order_line.picking_order_id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.customer_code = ").append(getPrefix()).append(".ax_CustTable.AccountNum");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_SalesTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.sales_admin = ").append(getPrefix()).append(".ax_SalesTable.SalesId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".reserved_order");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.id = ").append(getPrefix()).append(".reserved_order.picking_order_line_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = " ).append(pickingId);

        log.debug("--SQL {}",sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ORDER_ID", StringType.INSTANCE)
                    .addScalar("DOC_NO", StringType.INSTANCE)
                    .addScalar("CUSTOMER_NAME", StringType.INSTANCE)
                    .addScalar("ITEM_NUMBER", StringType.INSTANCE)
                    .addScalar("THAI_ITEM_DESCRIPTION", StringType.INSTANCE)
                    .addScalar("QUANTITY", BigDecimalType.INSTANCE)
                    .addScalar("ORDER_UNIT", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();
            log.debug("----------- {}", objects.size());

            for (Object[] entity : objects) {
                StiketWorkLoadViewReport report = new StiketWorkLoadViewReport();
                report.setSalesId(Utils.parseString(entity[0], ""));
                report.setDocNo(Utils.parseString(entity[1], ""));
                report.setCustomerName(Utils.parseString(entity[2], ""));
                report.setItemId(Utils.parseString(entity[3], ""));
                report.setThaiItemDes(Utils.parseString(entity[4], ""));
                report.setQuantity(Utils.parseBigDecimal(entity[5], BigDecimal.ZERO));
                report.setOrderUnit(Utils.parseString(entity[6], ""));
                report.setRemark(Utils.parseString(entity[7], ""));
                viewReports.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return viewReports;
    }

    public List<ConfirmationPackingViewModel> genConfirmationPackingReport(int pickingId){
        List<ConfirmationPackingViewModel> viewReports = new ArrayList<ConfirmationPackingViewModel>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.name AS CUST_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.Address AS CUST_ADDESS,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.BankAcc AS BANKACC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.confirm_docno AS NUMBER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.confirm_date AS DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.sales_order AS SALES_ORDER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.purchase_order AS REQUISITION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.sales_admin AS OUR_REF,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.payment_condition AS PAYMENT_PICKING,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CommissionSalesGroup.Name AS SALES_MAN,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_DlvMode.Txt AS MODE_OF_DELIVERY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.QuotationId AS QUOTATION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.CreatedDate AS PI_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.DlvTerm AS DELIVERY_TERM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_PaymTerm.Description AS PAYMENT_TERM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_DSGBrand.DSGBrandName AS BRAND_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.DSG_SubGroupDescription AS SUB_GROUP,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS THAI_DES,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.DSG_PackingQty AS PACKING_QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.salesunit AS SALES_UNIT,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.DSG_Ext_Item_NO AS ITEM_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.Name AS LINE_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_DataArea.name AS AREA_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.ShippingMark AS SHIPPING_MARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.CustomerRef AS CUSTOMER_REF,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_Container1X40 AS CONTAINER1,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_Container1X20 AS CONTAINER2,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_Container1X40HC AS CONTAINER3,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_Container1X45HC AS CONTAINER4,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.Remarks AS REMARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_AvailablePackingDate AS AVAILABLE_PACKING_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesTable.DSG_IC_Remark AS SALES_REMARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.DocuConclusion AS PACKING,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_SalesQuantationTable.DocuIntro AS DOCUINTRO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.docno AS DOCNO");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".picking_order");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.customer_code = ").append(getPrefix()).append(".ax_CustTable.AccountNum");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_SalesQuantationTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.quotation = ").append(getPrefix()).append(".ax_SalesQuantationTable.QuantationId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_DlvMode");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.mode_delivery = ").append(getPrefix()).append(".ax_DlvMode.Txt");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CommissionSalesGroup");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".ax_CustTable.SalesGroup = ").append(getPrefix()).append(".ax_CommissionSalesGroup.GroupId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_SalesTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.sales_order = ").append(getPrefix()).append(".ax_SalesTable.SalesId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_PaymTerm");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".ax_CustTable.PaymTermId = ").append(getPrefix()).append(".ax_PaymTerm.PaymTermId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.id = ").append(getPrefix()).append(".picking_order_line.picking_order_id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_DSGBrand");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".item_master.DSGBandID = ").append(getPrefix()).append(".ax_DSGBrand.DSGBrandID");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_DataArea");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".item_master.DSG_PrimaryPlant = ").append(getPrefix()).append(".ax_DataArea.id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = " ).append(pickingId);

        log.debug("--SQL {}",sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("CUST_NAME", StringType.INSTANCE)
                    .addScalar("CUST_ADDESS", StringType.INSTANCE)
                    .addScalar("BANKACC", StringType.INSTANCE)
                    .addScalar("NUMBER", StringType.INSTANCE)
                    .addScalar("DATE", DateType.INSTANCE)
                    .addScalar("SALES_ORDER", StringType.INSTANCE)
                    .addScalar("REQUISITION", StringType.INSTANCE)
                    .addScalar("OUR_REF", StringType.INSTANCE)
                    .addScalar("PAYMENT_PICKING", StringType.INSTANCE)
                    .addScalar("SALES_MAN", StringType.INSTANCE)
                    .addScalar("MODE_OF_DELIVERY", StringType.INSTANCE)
                    .addScalar("QUOTATION", StringType.INSTANCE)
                    .addScalar("PI_DATE", DateType.INSTANCE)
                    .addScalar("DELIVERY_TERM", StringType.INSTANCE)
                    .addScalar("PAYMENT_TERM", StringType.INSTANCE)
                    .addScalar("BRAND_NAME", StringType.INSTANCE)
                    .addScalar("SUB_GROUP", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("THAI_DES", StringType.INSTANCE)
                    .addScalar("PACKING_QTY", BigDecimalType.INSTANCE)
                    .addScalar("SALES_UNIT", StringType.INSTANCE)
                    .addScalar("ITEM_NO", StringType.INSTANCE)
                    .addScalar("LINE_NAME", StringType.INSTANCE)
                    .addScalar("AREA_NAME", StringType.INSTANCE)
                    .addScalar("SHIPPING_MARK", StringType.INSTANCE)
                    .addScalar("CUSTOMER_REF", StringType.INSTANCE)
                    .addScalar("CONTAINER1", StringType.INSTANCE)
                    .addScalar("CONTAINER2", StringType.INSTANCE)
                    .addScalar("CONTAINER3", StringType.INSTANCE)
                    .addScalar("CONTAINER4", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE)
                    .addScalar("AVAILABLE_PACKING_DATE", DateType.INSTANCE)
                    .addScalar("SALES_REMARK", StringType.INSTANCE)
                    .addScalar("PACKING", StringType.INSTANCE)
                    .addScalar("DOCUINTRO", StringType.INSTANCE)
                    .addScalar("DOCNO", StringType.INSTANCE);
            List<Object[]> objects = query.list();
            log.debug("----------- {}", objects.size());

            for (Object[] entity : objects) {
                ConfirmationPackingViewModel report = new ConfirmationPackingViewModel();
                report.setCustomerName(Utils.parseString(entity[0], ""));
                report.setCustomerAddess(Utils.parseString(entity[1], ""));
                report.setBankAcc(Utils.parseString(entity[2], ""));
                report.setNumber(Utils.parseString(entity[3], ""));
                report.setDate(Utils.parseDate(entity[4], null));
                report.setSalesOrder(Utils.parseString(entity[5], ""));
                report.setRequisition(Utils.parseString(entity[6], ""));
                report.setOurRef(Utils.parseString(entity[7], ""));
                report.setPaymentPicking(Utils.parseString(entity[8], ""));
                report.setSalesMan(Utils.parseString(entity[9], ""));
                report.setModeOfDelivery(Utils.parseString(entity[10], ""));
                report.setQuotation(Utils.parseString(entity[11], ""));
                report.setPiDate(Utils.parseDate(entity[12], null));
                report.setDeliveryTerm(Utils.parseString(entity[13], ""));
                report.setPaymentTerm(Utils.parseString(entity[14], ""));
                report.setBrandName(Utils.parseString(entity[15], ""));
                report.setSubGroup(Utils.parseString(entity[16], ""));
                report.setItemId(Utils.parseString(entity[17], ""));
                report.setThaiDes(Utils.parseString(entity[18], ""));
                report.setPackingQty(Utils.parseBigDecimal(entity[19], BigDecimal.ZERO));
                report.setSalesUnit(Utils.parseString(entity[20], ""));
                report.setItemNo(Utils.parseString(entity[21], ""));
                report.setLineName(Utils.parseString(entity[22], ""));
                report.setAreaName(Utils.parseString(entity[23], ""));
                report.setShippingMark(Utils.parseString(entity[24], ""));
                report.setCustomerRef(Utils.parseString(entity[25], ""));
                report.setContainer1(Utils.parseString(entity[26], ""));
                report.setContainer2(Utils.parseString(entity[27], ""));
                report.setContainer3(Utils.parseString(entity[28], ""));
                report.setContainer4(Utils.parseString(entity[29], ""));
                report.setRemark(Utils.parseString(entity[30], ""));
                report.setAvailablePackingDate(Utils.parseDate(entity[31], null));
                report.setSalesRemark(Utils.parseString(entity[32], ""));
                report.setPacking(Utils.parseString(entity[33], ""));
                report.setDocuintro(Utils.parseString(entity[34], ""));
                report.setDocno(Utils.parseString(entity[35], ""));
                viewReports.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return viewReports;
    }
}
