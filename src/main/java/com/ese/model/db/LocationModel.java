package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "location")
@Proxy(lazy=false)
public class LocationModel extends AbstractModel{
    @Id
    private int id;

    @Column(name = "location_barcode")
    private String locationBarcode;

    @Column(name = "location_name")
    private String locationName;

    @OneToMany
    @JoinColumn(name = "warehouse_id")
    private List<WarehouseModel> warehouseId;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "remark")
    private String remark;

    @Column(name = "qty")
    private int qty;

    @Column(name = "status")
    private String status;

    @Column(name = "isvalid", length = 1, nullable = false, columnDefinition = "int default 0")
    private int isvalid;

    @Column(name = "version", nullable=false, columnDefinition="int default 0")
    private Integer version;

    @Column(name = "reserved_qty")
    private int reservedQty;

    @Column(name = "ismix", length = 1, nullable = false, columnDefinition = "int default 0")
    private int ismix;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("locationBarcode", locationBarcode)
                .append("locationName", locationName)
                .append("warehouseId", warehouseId)
                .append("capacity", capacity)
                .append("remark", remark)
                .append("qty", qty)
                .append("status", status)
                .append("isvalid", isvalid)
                .append("version", version)
                .append("reservedQty", reservedQty)
                .append("ismix", ismix)
                .toString();
    }
}
