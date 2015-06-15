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
@Table(name = "ax_inventpickinglistjour")
@Proxy(lazy=false)
public class AxInventPickingListJourModel {

    @Id
    @Column(name="PickingListId")
    private String pickingListId;

    @Column(name="CustAccount")
    private String custAccount;

    @Column(name="OrderId")
    private String orderId;

    @Column(name="Voucher_Type")
    private String voucherType;

    @Column(name="Series")
    private String series;

    @Column(name="Voucher_No")
    private String voucherNo;

    @Temporal(TemporalType.DATE)
    @Column(name="PickingListDate")
    private Date pickingListDate;

    @Column(name="InventPickRequesterType")
    private int inventPickRequesterType;

    @Temporal(TemporalType.DATE)
    @Column(name="DeliveryDate")
    private Date deliveryDate;

    @Column(name="DlvTerm")
    private String dlvTerm;

    @Column(name="DSG_Pickinglist_Status")
    private int dSGPickinglistStatus;

    @Column(name="DSG_RemarkWait")
    private String dSGRemarkWait;

    @Column(name="SalesGroup")
    private String salesGroup;

    @Column(name="DeliveryName")
    private String deliveryName;

    @Column(name="DeliveryAddress")
    private String deliveryAddress;

    @Column(name="TotalAmount")
    private BigDecimal totalAmount;

    @Column(name="DataAreaId")
    private String dataAreaId;

    @Temporal(TemporalType.DATE)
    @Column(name="VBS_DueDate")
    private Date vBSDueDate;

    @Column(name="W_Agent")
    private String wAgent;

    @Column(name="Ref_No")
    private String refNo;

    @Column(name="W_Volumn")
    private String wVolumn;

    @Column(name="Remarks")
    private String remarks;

    @Column(name="W_Trucking")
    private String wTrucking;

    @Temporal(TemporalType.DATE)
    @Column(name="W_Container_Date")
    private Date wContainerDate;

    @Column(name="CustName")
    private String custName;

    @Column(name="INV")
    private String iNV;

    @Temporal(TemporalType.DATE)
    @Column(name="W_ETD_Date")
    private Date wETDDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="W_Closing_Date")
    private Date wClosingDate;

    @Column(name="W_Order_By")
    private String wOrderBy;

    @Column(name="DSG_ShippingMark")
    private String dSGShippingMark;

    @Column(name="sync_status")
    private int syncStatus;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pickingListId", pickingListId)
                .append("custAccount", custAccount)
                .append("orderId", orderId)
                .append("voucherType", voucherType)
                .append("series", series)
                .append("voucherNo", voucherNo)
                .append("pickingListDate", pickingListDate)
                .append("inventPickRequesterType", inventPickRequesterType)
                .append("deliveryDate", deliveryDate)
                .append("dlvTerm", dlvTerm)
                .append("dSGPickinglistStatus", dSGPickinglistStatus)
                .append("dSGRemarkWait", dSGRemarkWait)
                .append("voucherType", voucherType)
                .append("salesGroup", salesGroup)
                .append("deliveryName", deliveryName)
                .append("deliveryAddress", deliveryAddress)
                .append("totalAmount", totalAmount)
                .append("dataAreaId", dataAreaId)
                .append("vBSDueDate", vBSDueDate)
                .append("wAgent", wAgent)
                .append("refNo", refNo)
                .append("wVolumn", wVolumn)
                .append("remarks", remarks)
                .append("wTrucking", wTrucking)
                .append("wContainerDate", wContainerDate)
                .append("custName", custName)
                .append("iNV", iNV)
                .append("wETDDate", wETDDate)
                .append("wClosingDate", wClosingDate)
                .append("wOrderBy", wOrderBy)
                .append("dSGShippingMark", dSGShippingMark)
                .append("syncStatus", syncStatus)
                .toString();
    }
}
