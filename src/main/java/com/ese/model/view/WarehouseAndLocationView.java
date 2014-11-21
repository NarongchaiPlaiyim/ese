package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class WarehouseAndLocationView {
    private int id;
    private int warehouseId;
    private String locationCode;
    private String locationName;
    private int capacities;
    private boolean isMix;
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouseId", warehouseId)
                .append("locationCode", locationCode)
                .append("locationName", locationName)
                .append("capacities", capacities)
                .append("isMix", isMix)
                .append("remark", remark)
                .toString();
    }
}
