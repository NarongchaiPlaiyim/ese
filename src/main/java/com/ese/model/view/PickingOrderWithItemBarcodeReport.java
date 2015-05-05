package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PickingOrderWithItemBarcodeReport {

    private String customerName;
    private String customerAddess;
    private String bankAcc;
    private String number;
    private Date date;
    private String salesOrder;
    private String requisition;
    private String ourRef;
    private String paymentPicking;
    private String salesMan;
    private String modeOfDelivery;
    private String quotation;
    private Date piDate;
    private String deliveryTerm;
    private String paymentTerm;
    private String brandName;
    private String subGroup;
    private String itemId;
    private String thaiDes;
    private BigDecimal packingQty;
    private String salesUnit;
    private String areaName;
    private String docno;
    private String locationCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customerName", customerName)
                .append("customerAddess", customerAddess)
                .append("bankAcc", bankAcc)
                .append("number", number)
                .append("date", date)
                .append("salesOrder", salesOrder)
                .append("requisition", requisition)
                .append("ourRef", ourRef)
                .append("paymentPicking", paymentPicking)
                .append("salesMan", salesMan)
                .append("modeOfDelivery", modeOfDelivery)
                .append("quotation", quotation)
                .append("piDate", piDate)
                .append("deliveryTerm", deliveryTerm)
                .append("paymentTerm", paymentTerm)
                .append("brandName", brandName)
                .append("subGroup", subGroup)
                .append("itemId", itemId)
                .append("thaiDes", thaiDes)
                .append("packingQty", packingQty)
                .append("salesUnit", salesUnit)
                .append("areaName", areaName)
                .append("docno", docno)
                .append("locationCode", locationCode)
                .toString();
    }
}
