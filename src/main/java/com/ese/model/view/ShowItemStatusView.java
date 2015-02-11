package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ShowItemStatusView {
    private int id;
    private String warehouse;
    private String location;
    private String batchNo;
    private int reservedQty;
    private int pickedQty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouse", warehouse)
                .append("location", location)
                .append("batchNo", batchNo)
                .append("reservedQty", reservedQty)
                .append("pickedQty", pickedQty)
                .toString();
    }
}
