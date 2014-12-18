package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class PalletSubReport {

    private int no;
    private String itemCode;
    private String itemDescription;
    private String shiftName;
    private String workingAreaName;
    private String batchNo;
    private int qty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("no", no)
                .append("itemCode", itemCode)
                .append("itemDescription", itemDescription)
                .append("shiftName", shiftName)
                .append("workingAreaName", workingAreaName)
                .append("batchNo", batchNo)
                .append("qty", qty)
                .toString();
    }
}
