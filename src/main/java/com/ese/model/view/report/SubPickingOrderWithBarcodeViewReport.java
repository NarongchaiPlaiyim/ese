package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SubPickingOrderWithBarcodeViewReport {
    private String itemInternal;
    private String itemId;
    private String itemDesc;
    private String warehoseCode;
    private String locationBarcode;
    private String batchNo;
    private int qty;
    private String unit;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("itemInternal",itemInternal)
                .append("itemId", itemId)
                .append("itemDesc", itemDesc)
                .append("warehoseCode", warehoseCode)
                .append("locationBarcode", locationBarcode)
                .append("batchNo", batchNo)
                .append("qty", qty)
                .append("unit", unit)
                .toString();
    }
}
