package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ManualReserveView {
    private int id;
    private int warehouseId;
    private String warehouseName;
    private int locationId;
    private String locationName;
    private String batchNo;
    private int avaliableQty;
    private int reservedQty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouseId", warehouseId)
                .append("warehouseName", warehouseName)
                .append("locationId", locationId)
                .append("locationName", locationName)
                .append("batchNo", batchNo)
                .append("avaliableQty", avaliableQty)
                .append("reservedQty", reservedQty)
                .toString();
    }
}
