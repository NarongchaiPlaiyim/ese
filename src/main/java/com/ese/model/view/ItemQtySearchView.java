package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ItemQtySearchView {
    private String itemCode;
    private String itemName;
    private String description;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("itemCode", itemCode)
                .append("itemName", itemName)
                .append("description", description)
                .toString();
    }
}
