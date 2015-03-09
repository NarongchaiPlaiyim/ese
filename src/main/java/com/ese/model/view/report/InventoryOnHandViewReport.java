package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class InventoryOnHandViewReport {
    private int no;
    private String palletBarcode;
    private String snBarcode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("no", no)
                .append("palletBarcode", palletBarcode)
                .append("snBarcode", snBarcode)
                .toString();
    }
}
