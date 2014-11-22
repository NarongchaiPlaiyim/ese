package com.ese.model.view;

import com.ese.model.db.WarehouseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LocationView {
    private int id;
    private String locationBarcode;
    private String locationName;
    private WarehouseModel warehouseModel;
    private int capacity;
    private String remark;
    private int qty;
    private String status;
    private int createBy;
    private Date createDate;
    private int updateBy;
    private Date updateDate;
    private int isvalid;
    private int version;
    private int reservedQty;
    private int isMix;
}
