package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class PickingOrderLinePostView {
    private int pickingQty;
    private int itemId;
    private int pickingId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pickingQty", pickingQty)
                .append("itemId", itemId)
                .append("pickingId", pickingId)
                .toString();
    }
}
