package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@Setter
@Getter
public class LocationQtyView {
    private int id;
    private String itemId;
    private int locationId;
    private int available;
    private int qty;
    private int reservedQty;
    private String warehouseCode;
    private String batchNo;
    private String locationName;
    private String description;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("locationId", locationId)
                .append("available", available)
                .append("qty", qty)
                .append("reservedQty", reservedQty)
                .append("warehouseCode", warehouseCode)
                .append("batchNo", batchNo)
                .append("locationName", locationName)
                .append("description", description)
                .toString();
    }
}
