package com.ese.model.view;

import com.ese.model.db.ItemModel;
import com.ese.model.db.LocationModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.db.WorkingAreaModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PalletManagementView {
    private int id;
    private String palletBarcode;
    private List<WarehouseView> warehouseViewList;
    private List<ItemView> itemViewList;
    private List<LocationView> locationViewList;
    private int tagPrint;
    private int qty;
    private int reservedQty;
    private String status;
    private int createBy;
    private Date createDate;
    private int updateBy;
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
                .append("warehouseViewList", warehouseViewList)
                .append("itemViewList", itemViewList)
                .append("locationViewList", locationViewList)
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
