package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class ContainerItemView {
    private int containerItemId;
    private int containnerId;
    private String containnerNo;
    private int containerQty;

    private int itemId;

    public ContainerItemView() {
    }

    public ContainerItemView(int a, int b, String c, int d) {
        containerItemId = a;
        containnerId = b;
        containnerNo = c;
        containerQty = d;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("containerItemId", containerItemId)
                .append("containnerId", containnerId)
                .append("containnerNo", containnerNo)
                .append("containerQty", containerQty)
                .append("itemId", itemId)
                .toString();
    }
}
