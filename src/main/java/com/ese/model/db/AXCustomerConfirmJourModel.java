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
    private String dSG_ToPortDesc;

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
    private boolean record_boolean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdatetime_timestamp")
    private Date createdatetime_timestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifydatetime_timestamp")
    private Date modifydatetime_timestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestampg")
    private Date deletedatetime_timestampg;

    @Column(name="version")
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestamp")
    private Date deletedatetime_timestamp;

    @Column(name="status")
    private int status;


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
                .append("dSG_ToPortDesc", dSG_ToPortDesc)
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
                .append("record_boolean", record_boolean)
                .append("createdatetime_timestamp", createdatetime_timestamp)
                .append("modifydatetime_timestamp", modifydatetime_timestamp)
                .append("deletedatetime_timestampg", deletedatetime_timestampg)
                .append("version", version)
                .append("deletedatetime_timestamp", deletedatetime_timestamp)
                .append("status", status)
                .toString();
    }
}
