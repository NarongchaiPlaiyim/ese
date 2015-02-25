package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class PhysicalView {
    private int qty;
    private int amount;

    public PhysicalView() {
        qty = 0;
        amount = 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("qty", qty)
                .append("amount", amount)
                .toString();
    }
}
