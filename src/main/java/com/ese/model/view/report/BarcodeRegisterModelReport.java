package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class BarcodeRegisterModelReport {

    private String dSGThaiItemDescription;
    private String docNo;
    private String productDate;
    private String batchNo;
    private String startBarcode;
    private String finishBarcode;
    private int qty;
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("dSGThaiItemDescription", dSGThaiItemDescription)
                .append("docNo", docNo)
                .append("productDate", productDate)
                .append("batchNo", batchNo)
                .append("startBarcode", startBarcode)
                .append("finishBarcode", finishBarcode)
                .append("qty", qty)
                .append("remark", remark)
                .toString();
    }
}
