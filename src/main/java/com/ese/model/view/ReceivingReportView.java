package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ReceivingReportView {

    private int no;
    private String receivingDate;
    private String warehouseCode;
    private String conveyorLine;
    private String itemName;
    private String itemDesc;
    private String grade;
    private int receive;
    private int located;
    private int qty;

    private int sum;

    public ReceivingReportView() {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("no", no)
                .append("receivingDate", receivingDate)
                .append("warehouseCode", warehouseCode)
                .append("conveyorLine", conveyorLine)
                .append("itemName", itemName)
                .append("itemDesc", itemDesc)
                .append("grade", grade)
                .append("qty", qty)
                .append("receive", receive)
                .append("located", located)
                .append("sum", sum)
                .toString();
    }
}
