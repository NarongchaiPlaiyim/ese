package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class SetupView {
    /* warehouse and location */
    private int id;
    //Warehouse
    private String locationCode;
    private String locationName;
    private int capacities;
    private boolean isMix;
    private String remark;

    /* Stock In/Out Note */
    private int id2;
    private int type;
    private String code;
    private String note;
    private String remark2;

    public SetupView() {
        init();
    }

    private void init(){
        id = 111111;
        locationCode = "locationCodeTEST";
        locationName = "locationNameTEST";
        capacities = 9999;
        isMix = true;
        remark = "remarkTEST";

        id2 = 22222;
        type = 0;
        code = "codeTEST";
        note = "noteTEST";
        remark2 = "remark2TEST";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("locationCode", locationCode)
                .append("locationName", locationName)
                .append("capacities", capacities)
                .append("isMix", isMix)
                .append("remark", remark)
                .append("id2", id2)
                .append("type", type)
                .append("code", code)
                .append("note", note)
                .append("remark2", remark2)
                .toString();
    }
}
