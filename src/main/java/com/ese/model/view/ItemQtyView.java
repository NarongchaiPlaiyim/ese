package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ItemQtyView {
    private int pickLineId;
    private String itemCode;
    private String itemDes;
    private int orderQty;
    private int reservedQty;
    private String itemName;
    private int pickingQty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pickLineId", pickLineId)
                .append("itemCode", itemCode)
                .append("itemDes", itemDes)
                .append("orderQty", orderQty)
                .append("reservedQty", reservedQty)
                .append("itemName", itemName)
                .append("pickingQty", pickingQty)
                .toString();
    }
}
