package com.ese.model.view;

import com.ese.model.view.dilog.WarehouseDialogView;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@Setter
public class SetupView {
    /* warehouse and location */
    private int warehouseId;
    //Warehouse
    private String locationCode;
    private String locationName;
    private int capacities;
    private boolean isMix;
    private String remark;
    private List<LocationView> locationViewList;

    /* Stock In/Out Note */
    private int stockId;
    private int type;
    private String code;
    private String note;
    private String remark2;

    public SetupView() {
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("warehouseId", warehouseId)
                .append("locationCode", locationCode)
                .append("locationName", locationName)
                .append("capacities", capacities)
                .append("isMix", isMix)
                .append("remark", remark)
                .append("locationViewList", locationViewList)
                .append("stockId", stockId)
                .append("type", type)
                .append("code", code)
                .append("note", note)
                .append("remark2", remark2)
                .toString();
    }
}
