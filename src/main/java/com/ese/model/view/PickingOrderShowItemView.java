package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@Getter
@Setter
public class PickingOrderShowItemView {
    private int id;
    private String item;
    private String description;
    private int orderQty;
    private int reservedQty;
    private int perPicked;
    private int foil;
    private BigDecimal foilQty;
    private String status;
    private int statusID;
    private int qty;
    private String itemName;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("item", item)
                .append("description", description)
                .append("orderQty", orderQty)
                .append("reservedQty", reservedQty)
                .append("perPicked", perPicked)
                .append("foil", foil)
                .append("foilQty", foilQty)
                .append("status", status)
                .append("statusID", statusID)
                .append("qty", qty)
                .append("itemName", itemName)
                .toString();
    }
}
