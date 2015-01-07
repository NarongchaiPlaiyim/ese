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
    private Date confirmDate;
    private String customerCode;
    private String customerName;
    private String purchaseOrder;
    private String saleOrder;
    private String deliveryName;
    private String deliveryAddress;
    private Date requestShipDate;
    private Date eddDate;
    private Date availableDate;
    private String status;
    private boolean overseaOrder;
    private boolean domesticOrder;

    public PickingOrderView() {
        init();
    }

    private void init(){
        confirmDate = Utils.currentDate();
        requestShipDate = Utils.currentDate();
        eddDate = Utils.currentDate();
        availableDate = Utils.currentDate();
    }

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
