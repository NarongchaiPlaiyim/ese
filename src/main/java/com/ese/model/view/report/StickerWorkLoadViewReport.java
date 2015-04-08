package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@Getter
@Setter
public class StickerWorkLoadViewReport {

    private String salesId;
    private String docNo;
    private String customerName;
    private String itemId;
    private String thaiItemDes;
    private BigDecimal quantity;
    private String orderUnit;
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("salesId", salesId)
                .append("docNo", docNo)
                .append("customerName", customerName)
                .append("itemId", itemId)
                .append("thaiItemDes", thaiItemDes)
                .append("quantity", quantity)
                .append("orderUnit", orderUnit)
                .append("remark", remark)
                .toString();
    }
}
