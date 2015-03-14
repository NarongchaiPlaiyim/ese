package com.ese.model.view;

import com.ese.model.db.StaffModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class WarehouseView extends View{
    private int id;
    private String warehouseCode;
    private String warehouseName;
    private String remark;
    private int isvalid;
    private String status;
    private int version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouseCode", warehouseCode)
                .append("warehouseName", warehouseName)
                .append("remark", remark)
                .append("isvalid", isvalid)
                .append("status", status)
                .append("version", version)
                .toString();
    }
}
