package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class AxInventPickingListJourView {
    private String pickingListId;
    private String customer;
    private String saleOrderId;
    private String loadingDate;
    private String etd;
    private String agent;
    private String invNo;
    private String piNo;
    private String truckAgent;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pickingListId", pickingListId)
                .append("customer", customer)
                .append("saleOrderId", saleOrderId)
                .append("loadingDate", loadingDate)
                .append("etd", etd)
                .append("agent", agent)
                .append("invNo", invNo)
                .append("piNo", piNo)
                .append("truckAgent", truckAgent)
                .toString();
    }
}
