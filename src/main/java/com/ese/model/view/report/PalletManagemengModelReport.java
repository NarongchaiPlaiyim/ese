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
    private String bathcgNo;
    private String workingName;
    private int countId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("dSGThaiItemDescription", dSGThaiItemDescription)
                .append("warehouseCode", warehouseCode)
                .append("palletBarcode", palletBarcode)
                .append("locationBarcode", locationBarcode)
                .append("createDate", createDate)
                .append("grade", grade)
                .append("bathcgNo", bathcgNo)
                .append("workingName", workingName)
                .append("countId", countId)
                .toString();
    }
}
