package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class StockInOutDetailViewReport {
    private String itemId;
    private String itemDesc;
    private String palletBarcode;
    private int previousLocationId;
    private String locationBarcode;
    private int qty;
    private int stockInOutId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("itemId", itemId)
                .append("itemDesc", itemDesc)
                .append("palletBarcode", palletBarcode)
                .append("previousLocationId", previousLocationId)
                .append("locationBarcode", locationBarcode)
                .append("qty", qty)
                .append("stockInOutId", stockInOutId)
                .toString();
    }
}
