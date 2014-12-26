package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "location")
@Proxy(lazy=false)
public class MSLocationModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location_barcode")
    private String locationBarcode;

    @Column(name = "location_name")
    private String locationName;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private MSWarehouseModel msWarehouseModel;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "remark")
    private String remark;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid", nullable=false, columnDefinition="int default 0")
    private Integer isValid;

    @Column(name = "version", nullable=false, columnDefinition="int default 0")
    private Integer version;

    @Column(name = "reserved_qty")
    private Integer reservedQty;

    @Column(name = "ismix", nullable=false, columnDefinition="int default 0")
    private Integer isMix;

    @Column(name = "isFoil", nullable=false, columnDefinition="int default 0")
    private Integer isFoil;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("locationBarcode", locationBarcode)
                .append("locationName", locationName)
                .append("msWarehouseModel", msWarehouseModel)
                .append("capacity", capacity)
                .append("remark", remark)
                .append("qty", qty)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("reservedQty", reservedQty)
                .append("isMix", isMix)
                .append("isFoil", isFoil)
                .toString();
    }
}
