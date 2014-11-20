package com.ese.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pallet")
@Proxy(lazy=false)
public class PalletModel {
    @Id
    private int id;

    @Column(name = "pallet_barcode")
    private String palletBarcode;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseModel wherehouseId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel locationId;

    @Column(name = "tagprint", nullable=false, columnDefinition="int default 0")
    private Integer tagPrint;

    @Column(name = "qty", nullable=false, columnDefinition="int default 0")
    private Integer qty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_by")
    private String createBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "isvalid", nullable=false, columnDefinition="int default 0")
    private Integer isValid;

    @Column(name = "version", nullable=false, columnDefinition="int default 0")
    private Integer version;

    @Column(name = "capacity")
    private BigDecimal capacity;

    @OneToOne
    @JoinColumn(name = "conveyor_line", nullable=false, columnDefinition="int default 0")
    private WorkingAreaModel conveyorLine;

    @Column(name = "reserved_qty", nullable=false, columnDefinition="int default 0")
    private Integer reservedQty;

    @Column(name = "shift")
    private String shift;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("palletBarcode", palletBarcode)
                .append("wherehouseId", wherehouseId)
                .append("itemId", itemId)
                .append("locationId", locationId)
                .append("tagPrint", tagPrint)
                .append("qty", qty)
                .append("status", status)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .append("isValid", isValid)
                .append("version", version)
                .append("capacity", capacity)
                .append("conveyorLine", conveyorLine)
                .append("reservedQty", reservedQty)
                .append("shift", shift)
                .toString();
    }
}
