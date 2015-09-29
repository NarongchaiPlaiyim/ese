package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class PalletManagemengModelReport {
    private String dSGThaiItemDescription;
    private String warehouseCode;
    private String palletBarcode;
    private String locationBarcode;
    private String createDate;
    private String grade;
    private String bathcgNo1;
    private String bathcgNo2;
    private String bathcgNo3;
    private String bathcgNo4;
    private String workingName;
    private int countId1;
    private int countId2;
    private int countId3;
    private int countId4;
    private int seq;
    private String itemId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("dSGThaiItemDescription", dSGThaiItemDescription)
                .append("warehouseCode", warehouseCode)
                .append("palletBarcode", palletBarcode)
                .append("locationBarcode", locationBarcode)
                .append("createDate", createDate)
                .append("grade", grade)
                .append("bathcgNo1", bathcgNo1)
                .append("bathcgNo2", bathcgNo2)
                .append("bathcgNo3", bathcgNo3)
                .append("bathcgNo4", bathcgNo4)
                .append("workingName", workingName)
                .append("countId1", countId1)
                .append("countId2", countId2)
                .append("countId3", countId3)
                .append("countId4", countId4)
                .append("seq", seq)
                .append("itemId", itemId)
                .toString();
    }
}
