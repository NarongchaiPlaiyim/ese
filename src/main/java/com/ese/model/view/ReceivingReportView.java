package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class ReceivingReportView {

    private Date receivingDate;
    private String warehouseCode;
    private String conveyorLine;
    private String itemName;
    private String itemDesc;
    private String grade;
    private int qty;

    public ReceivingReportView() {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("receivingDate", receivingDate)
                .append("warehouseCode", warehouseCode)
                .append("conveyorLine", conveyorLine)
                .append("itemName", itemName)
                .append("itemDesc", itemDesc)
                .append("grade", grade)
                .append("qty", qty)
                .toString();
    }
}
