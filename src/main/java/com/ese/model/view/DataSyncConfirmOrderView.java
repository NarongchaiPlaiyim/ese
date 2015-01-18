package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class DataSyncConfirmOrderView {

    private int id;
    private String customerCode;
    private String customerName;
    private String customerGroup;
    private String confirmId;
    private Date confirmDate;
    private String saleId;
    private String purchaseOrder;
    private String deliveryName;
    private String deliveryAddress;
    private String customerRef;
    private String container;
    private String dlvTerm;
    private String paymentCondition;
    private String remark;
    private String saleAdmin;
    private String modeDlv;
    private String quotationId;
    private Date quotationDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customerCode", customerCode)
                .append("customerName", customerName)
                .append("customerGroup", customerGroup)
                .append("confirmId", confirmId)
                .append("confirmDate", confirmDate)
                .append("saleId", saleId)
                .append("purchaseOrder", purchaseOrder)
                .append("deliveryName", deliveryName)
                .append("deliveryAddress", deliveryAddress)
                .append("customerRef", customerRef)
                .append("container", container)
                .append("dlvTerm", dlvTerm)
                .append("paymentCondition", paymentCondition)
                .append("remark", remark)
                .append("saleAdmin", saleAdmin)
                .append("modeDlv", modeDlv)
                .append("quotationId", quotationId)
                .append("quotationDate", quotationDate)
                .toString();
    }
}
