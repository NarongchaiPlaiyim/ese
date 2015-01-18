package com.ese.model.view;

import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class PickingOrderView {
    private String confirmId;
    private String confirmDate;
    private String customerCode;
    private String customerName;
    private String purchaseOrder;
    private String saleOrder;
    private String deliveryName;
    private String deliveryAddress;
    private String requestShipDate;
    private String eddDate;
    private String availableDate;
    private int status;
    private boolean overseaOrder;
    private boolean domesticOrder;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("confirmId", confirmId)
                .append("confirmDate", confirmDate)
                .append("customerCode", customerCode)
                .append("customerName", customerName)
                .append("purchaseOrder", purchaseOrder)
                .append("saleOrder", saleOrder)
                .append("deliveryName", deliveryName)
                .append("deliveryAddress", deliveryAddress)
                .append("requestShipDate", requestShipDate)
                .append("eddDate", eddDate)
                .append("availableDate", availableDate)
                .append("status", status)
                .append("overseaOrder", overseaOrder)
                .append("domesticOrder", domesticOrder)
                .toString();
    }
}
