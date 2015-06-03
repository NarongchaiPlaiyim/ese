package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PalletTransferView {

    private int id;
    private String palletBarcode;
    private String warehouseCode;
    private String itemId;
    private String itemDesc;
    private String locationBarcode;
    private int qty;
    private Date createDate;
    private BigDecimal capacity;
    private int isCombine;
    private int isFoil;
    private int toTransfer;
    private int item;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("palletBarcode", palletBarcode)
                .append("warehouseCode", warehouseCode)
                .append("itemId", itemId)
                .append("itemDesc", itemDesc)
                .append("locationBarcode", locationBarcode)
                .append("qty", qty)
                .append("createDate", createDate)
                .append("capacity", capacity)
                .append("isCombine", isCombine)
                .append("isFoil", isFoil)
                .append("toTransfer", toTransfer)
                .append("item", item)
                .toString();
    }
}
