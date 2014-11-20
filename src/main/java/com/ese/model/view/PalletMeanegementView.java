package com.ese.model.view;

import com.ese.model.ItemModel;
import com.ese.model.LocationModel;
import com.ese.model.WarehouseModel;
import com.ese.model.WorkingAreaModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PalletMeanegementView {
    private int id;
    private String palletBarcode;
    private WarehouseModel warehouseModel;
    private ItemModel itemModel;
    private LocationModel locationModel;
    private int tagPrint;
    private int qty;
    private int reservedQty;
    private String status;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private int isValid;
    private int version;
    private BigDecimal capacity;
    private WorkingAreaModel convetorLine;
    private String shift;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("palletBarcode", palletBarcode)
                .append("warehouseModel", warehouseModel)
                .append("itemModel", itemModel)
                .append("locationModel", locationModel)
                .append("tagPrint", tagPrint)
                .append("qty", qty)
                .append("reservedQty", reservedQty)
                .append("status", status)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .append("isValid", isValid)
                .append("version", version)
                .append("capacity", capacity)
                .append("convetorLine", convetorLine)
                .append("shift", shift)
                .toString();
    }
}
