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
    private int qty;

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

    @Column(name="version")
    private int version;

    @Column(name="status")
    private int status;

    @Column(name="DSG_SubGroupDescription")
    private String dSGSubGroupDescription;

    @Column(name="PIDescription")
    private String pIDescription;

    @Column(name="DSG_Ext_Item_NO")
    private String dSGExtItemNO;

    @Column(name="Name")
    private String name;

    @Column(name="DSG_PackingQty")
    private BigDecimal dSGPackingQty;


    @Column(name="salesUnitTxt")
    private String salesUnitTxt;

    @Column(name="Cum")
    private BigDecimal cum;

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
                .append("version", version)
                .append("status", status)
                .append("dSGSubGroupDescription", dSGSubGroupDescription)
                .append("pIDescription", pIDescription)
                .append("dSGExtItemNO", dSGExtItemNO)
                .append("name", name)
                .append("dSGPackingQty", dSGPackingQty)
                .append("salesUnitTxt", salesUnitTxt)
                .append("cum", cum)
                .toString();
    }
}
