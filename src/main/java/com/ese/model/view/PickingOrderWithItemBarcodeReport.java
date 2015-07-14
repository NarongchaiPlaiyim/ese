package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PickingOrderWithItemBarcodeReport {
    private String pickingDocno;
    private String confirmDocno;
    private String saleOrder;
    private Date createDate;
    private String name;
    private String address;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pickingDocno", pickingDocno)
                .append("confirmDocno", confirmDocno)
                .append("saleOrder", saleOrder)
                .append("createDate", createDate)
                .append("name", name)
                .append("address", address)
                .toString();
    }
}
