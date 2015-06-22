package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class ShowSNView {
    private int id;
    private String batch;
    private String warehouse;
    private String location;
    private String pallet;
    private String sN;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("batch", batch)
                .append("warehouse", warehouse)
                .append("location", location)
                .append("pallet", pallet)
                .append("sN", sN)
                .toString();
    }
}
