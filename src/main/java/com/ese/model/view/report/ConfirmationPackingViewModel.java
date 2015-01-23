package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ConfirmationPackingViewModel {
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
    private String itemNo;
    private String lineName;
    private String areaName;
    private String shippingMark;
    private String customerRef;
    private String container1;
    private String container2;
    private String container3;
    private String container4;
    private String remark;
    private Date availablePackingDate;
    private String salesRemark;
    private String packing;
    private String docuintro;
    private String docno;

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
                .append("itemNo", itemNo)
                .append("lineName", lineName)
                .append("areaName", areaName)
                .append("shippingMark", shippingMark)
                .append("customerRef", customerRef)
                .append("container1", container1)
                .append("container2", container2)
                .append("container3", container3)
                .append("container4", container4)
                .append("remark", remark)
                .append("availablePackingDate", availablePackingDate)
                .append("salesRemark", salesRemark)
                .append("packing", packing)
                .append("docuintro", docuintro)
                .append("docno", docno)
                .toString();
    }
}
