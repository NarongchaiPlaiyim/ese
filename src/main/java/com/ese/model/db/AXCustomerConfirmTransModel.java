package com.ese.model.db;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ax_CustConfirmTrans")
@Proxy(lazy=false)
public class AXCustomerConfirmTransModel {

    @Id
    @Column(name="SalesId")
    private String salesId;

    @Column(name="ConfirmId")
    private String confirmId;

    @Temporal(TemporalType.DATE)
    @Column(name="ConfirmDate")
    private Date confirmDate;

    @Column(name="LineNum")
    private BigDecimal lineNum;

    @Column(name="OrigSalesId")
    private String origSalesId;

    @Column(name="ItemId")
    private String itemId;

    @Column(name="InventdimId")
    private String inventdimId;

    @Column(name="Qty")
    private BigDecimal qty;

    @Column(name="SalesPrice")
    private BigDecimal salesPrice;

    @Column(name="DiscPercent")
    private BigDecimal discPercent;

    @Column(name="DiscAmount")
    private BigDecimal discAmount;

    @Column(name="LineAmount")
    private BigDecimal lineAmount;

    @Column(name="LineAmountTax")
    private BigDecimal lineAmountTax;

    @Temporal(TemporalType.DATE)
    @Column(name="DlvDate")
    private Date dlvDate;

    @Column(name="SalesUnit")
    private String salesUnit;

    @Column(name="record_boolean")
    private boolean recordBoolean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdatetime_timestamp")
    private Date createdatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifydatetime_timestamp")
    private Date modifydatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestamp")
    private Date deletedatetimeTimestamp;

    @Column(name="version")
    private int version;

    @Column(name="status")
    private int status;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("salesId", salesId)
                .append("confirmId", confirmId)
                .append("confirmDate", confirmDate)
                .append("lineNum", lineNum)
                .append("origSalesId", origSalesId)
                .append("itemId", itemId)
                .append("inventdimId", inventdimId)
                .append("qty", qty)
                .append("salesPrice", salesPrice)
                .append("discPercent", discPercent)
                .append("discAmount", discAmount)
                .append("lineAmount", lineAmount)
                .append("lineAmountTax", lineAmountTax)
                .append("dlvDate", dlvDate)
                .append("salesUnit", salesUnit)
                .append("record_boolean", recordBoolean)
                .append("createdatetime_timestamp", createdatetimeTimestamp)
                .append("modifydatetime_timestamp", modifydatetimeTimestamp)
                .append("deletedatetime_timestamp", deletedatetimeTimestamp)
                .append("version", version)
                .append("status", status)
                .toString();
    }
}
