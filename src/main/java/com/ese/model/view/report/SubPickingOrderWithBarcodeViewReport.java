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
    private int palletId;
    private String warehoseCode;
    private String itemId;
    private String itemDesc;
    private String palletBarcode;
    private Date palletDate;
    private String locationBarcode;
    private BigDecimal capacity;
    private int qty;
    private int combine;
    private int foil;
    private int toTransfer;
    private int item;
    private String batchNo;
    private String unit;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("palletId", palletId)
                .append("warehoseCode", warehoseCode)
                .append("itemId", itemId)
                .append("itemDesc", itemDesc)
                .append("palletBarcode", palletBarcode)
                .append("palletDate", palletDate)
                .append("locationBarcode", locationBarcode)
                .append("capacity", capacity)
                .append("qty", qty)
                .append("combine", combine)
                .append("foil", foil)
                .append("toTransfer", toTransfer)
                .append("item", item)
                .append("batchNo", batchNo)
                .append("unit", unit)
                .toString();
    }
}
