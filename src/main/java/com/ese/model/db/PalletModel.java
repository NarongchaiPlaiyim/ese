package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "reserved_qty", nullable=false, columnDefinition="int default 0")
    private Integer reservedQty;

    @OneToOne
    @JoinColumn(name = "working_area_id", nullable=false, columnDefinition="int default 0")
    private MSWorkingAreaModel msWorkingAreaModel;

    @OneToOne
    @JoinColumn(name = "shift_id")
    private MSShiftModel msShiftModel;

    @Column(name = "seq", nullable=false, columnDefinition="int default 0")
    private int seq;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private MSWarehouseModel msWarehouseModel;

    @OneToOne
    @JoinColumn(name = "item_id")
    private MSItemModel msItemModel;

    @OneToOne
    @JoinColumn(name = "location_id")
    private MSLocationModel msLocationModel;

    @Column(name="combine", nullable=false, columnDefinition="int default 0")
    private int isCombine;

    @Column(name="foil", nullable=false, columnDefinition="int default 0")
    private int isFoil;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("palletBarcode", palletBarcode)
                .append("tagPrint", tagPrint)
                .append("qty", qty)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("capacity", capacity)
                .append("reservedQty", reservedQty)
                .append("seq", seq)
                .append("msWarehouseModel", msWarehouseModel)
                .append("msItemModel", msItemModel)
                .append("msLocationModel", msLocationModel)
                .append("isCombine", isCombine)
                .append("isFoil", isFoil)
                .toString();
    }
}
