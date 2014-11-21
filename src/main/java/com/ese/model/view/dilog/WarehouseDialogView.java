package com.ese.model.view.dilog;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class WarehouseDialogView {
    private int id;
    private String warehouseCode;
    private String warehouseName;
    private String remark;

    public WarehouseDialogView() {

    }

    private void init(){
        id = 5555555;
        warehouseCode = "warehouseCodeTEST";
        warehouseName = "warehouseNameTEST";
        remark = "remarkTEST";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouseCode", warehouseCode)
                .append("warehouseName", warehouseName)
                .append("remark", remark)
                .toString();
    }
}
