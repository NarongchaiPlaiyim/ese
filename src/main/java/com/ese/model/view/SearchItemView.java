package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class SearchItemView {
    private String itemCode;
    private String itemDesc;
    private String batchNo;
    private int warehouseId;
    private int locationId;
    private String sN;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("itemCode", itemCode)
                .append("itemDesc", itemDesc)
                .append("batchNo", batchNo)
                .append("warehouseId", warehouseId)
                .append("locationId", locationId)
                .append("sN", sN)
                .toString();
    }
}
