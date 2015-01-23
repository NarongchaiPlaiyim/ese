package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ax_CustConfirmJour")
@Proxy(lazy=false)
public class AXCustomerConfirmJourModel {

    @Id
    @Column(name="ConfirmId")
    private String confirmId;

    @Column(name="SalesId")
    private String salesId;

    @Temporal(TemporalType.DATE)
    @Column(name="ConfirmDate")
    private Date confirmDate;

    @Column(name="InvoiceAccount")
    private String invoiceAccount;

    @Column(name="PurchaseOrder")
    private String purchaseOrder;

    @Column(name="ConfirmDocNum")
    private String confirmDocNum;

    @Column(name="CurrencyCode")
    private String currencyCode;

    @Column(name="ConfirmAmount")
    private int confirmAmount;

    @Column(name="DeliveryAddress")
    private String deliveryAddress;

    @Column(name="DeliveryName")
    private String deliveryName;

    @Column(name="CustomerRef")
    private String customerRef;

    @Column(name="SalesAdministrator")
    private String salesAdministrator;

    @Column(name="SalesBalance")
    private int salesBalance;

    @Column(name="Remark")
    private String remark;

    @Column(name="CuM")
    private int cuM;

    @Column(name="DSG_ToPortDesc")
    private String dSGToPortDesc;

    @Column(name="Containers")
    private String containers;

    @Column(name="FOBPrice")
    private int fOBPrice;

    @Column(name="Totalnetweigth")
    private int totalnetweigth;

    @Column(name="TotalCuM")
    private int totalCuM;

    @Column(name="paymentCondition")
    private String paymentCondition;

    @Column(name="QuaID")
    private String quaID;

    @Column(name="EndDisc")
    private int endDisc;

    @Column(name="SumMarkup")
    private int sumMarkup;

    @Column(name="netAmount")
    private int netAmount;

    @Column(name="SumTax")
    private int sumTax;

    @Column(name="RoundOff")
    private int roundOff;

    @Column(name="ModeDlv")
    private String modeDlv;

    @Column(name="QuotationId")
    private String quotationId;

    @Temporal(TemporalType.DATE)
    @Column(name="QuotationCreateDate")
    private Date quotationCreateDate;

    @Column(name="DlvTerm")
    private String dlvTerm;

    @Column(name="TotalTerm")
    private String totalTerm;

    @Column(name="record_boolean")
    private boolean recordBoolean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdatetime_timestamp")
    private Date createdatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifydatetime_timestamp")
    private Date modifydatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestampg")
    private Date deletedatetimeTimestampg;

    @Column(name="version")
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestamp")
    private Date deletedatetimeTimestamp;

    @Column(name="status")
    private int status;

    @Temporal(TemporalType.DATE)
    @Column(name="DSG_ShippingDateRequested ")
    private Date dSGShippingDateRequested;

    @Temporal(TemporalType.DATE)
    @Column(name="DSG_EDDDate ")
    private Date dSGEDDDate;

    @Temporal(TemporalType.DATE)
    @Column(name="DSG_AvailableDate ")
    private Date dSGAvailableDate;

    @Column(name="DSG_remark")
    private String dSGRemark;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("confirmId", confirmId)
                .append("salesId", salesId)
                .append("confirmDate", confirmDate)
                .append("invoiceAccount", invoiceAccount)
                .append("purchaseOrder", purchaseOrder)
                .append("confirmDocNum", confirmDocNum)
                .append("currencyCode", currencyCode)
                .append("confirmAmount", confirmAmount)
                .append("deliveryAddress", deliveryAddress)
                .append("deliveryName", deliveryName)
                .append("customerRef", customerRef)
                .append("salesAdministrator", salesAdministrator)
                .append("salesBalance", salesBalance)
                .append("remark", remark)
                .append("cuM", cuM)
                .append("dSG_ToPortDesc", dSGToPortDesc)
                .append("containers", containers)
                .append("fOBPrice", fOBPrice)
                .append("totalnetweigth", totalnetweigth)
                .append("totalCuM", totalCuM)
                .append("paymentCondition", paymentCondition)
                .append("quaID", quaID)
                .append("endDisc", endDisc)
                .append("sumMarkup", sumMarkup)
                .append("netAmount", netAmount)
                .append("sumTax", sumTax)
                .append("roundOff", roundOff)
                .append("modeDlv", modeDlv)
                .append("quotationId", quotationId)
                .append("quotationCreateDate", quotationCreateDate)
                .append("dlvTerm", dlvTerm)
                .append("totalTerm", totalTerm)
                .append("record_boolean", recordBoolean)
                .append("createdatetime_timestamp", createdatetimeTimestamp)
                .append("modifydatetime_timestamp", modifydatetimeTimestamp)
                .append("deletedatetime_timestampg", deletedatetimeTimestampg)
                .append("version", version)
                .append("deletedatetime_timestamp", deletedatetimeTimestamp)
                .append("status", status)
                .append("dSG_ShippingDateRequested", dSGShippingDateRequested)
                .append("dSG_EDDDate", dSGEDDDate)
                .append("dSG_AvailableDate", dSGAvailableDate)
                .append("dSG_remark", dSGRemark)
                .toString();
    }
}
