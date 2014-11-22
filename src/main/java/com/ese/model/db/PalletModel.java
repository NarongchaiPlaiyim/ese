package com.ese.model.db;

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
public class PalletModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pallet_barcode")
    private String palletBarcode;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseModel warehouseId;

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
                .append("warehouseId", warehouseId)
                .append("itemId", itemId)
                .append("locationId", locationId)
                .append("tagPrint", tagPrint)
                .append("qty", qty)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("capacity", capacity)
                .append("conveyorLine", conveyorLine)
                .append("reservedQty", reservedQty)
                .append("shift", shift)
                .toString();
    }
}
