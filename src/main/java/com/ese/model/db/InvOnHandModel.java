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
@Table(name = "inv_onhand")
@Proxy(lazy=false)
public class InvOnHandModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @Column(name = "sn_barcode")
    private String snBarcode;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "grade")
    private String grade;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletId;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel locationId;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "isfoil")
    private Integer isFoil;

    @OneToOne
    @JoinColumn(name = "stock_inout_line_id")
    private StockInOutLineModel stockInoutLineId;

    @OneToOne
    @JoinColumn(name = "working_area_id")
    private WorkingAreaModel workingAreaId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("snBarcode", snBarcode)
                .append("batchNo", batchNo)
                .append("grade", grade)
                .append("pallet_id", palletId)
                .append("locationId", locationId)
                .append("cost", cost)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("barcode", barcode)
                .append("isFoil", isFoil)
                .append("stockInoutLineId", stockInoutLineId)
                .append("workingAreaId", workingAreaId)
                .toString();
    }
}
