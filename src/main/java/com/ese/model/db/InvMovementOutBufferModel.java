package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by pakorn on 01/12/2015.
 */
@Getter
@Setter
@Entity
@Table(name = "inv_movement_out_buffer")
@Proxy(lazy=false)
public class InvMovementOutBufferModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sn_barcode")
    private String snBarcode;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "grade")
    private String grade;

    @Column(name = "cost", nullable=false, columnDefinition="int default 0")
    private Long cost;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "isfoil", nullable=false, columnDefinition="int default 0")
    private Integer isFoil;

    @OneToOne
    @JoinColumn(name = "item_id")
    private MSItemModel msItemModel;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletModel;

    @OneToOne
    @JoinColumn(name = "location_id")
    private MSLocationModel msLocationModel;

    @OneToOne
    @JoinColumn(name = "stock_inout_line_id")
    private StockInOutLineModel stockInOutLineModel;

    @OneToOne
    @JoinColumn(name = "working_area_id")
    private MSWorkingAreaModel msWorkingAreaModel;

    @OneToOne
    @JoinColumn(name="picking_order_id")
    private PickingOrderModel pickingOrderModel;

    @OneToOne
    @JoinColumn(name = "shift_id")
    private MSShiftModel msShiftModel;

    @OneToOne
    @JoinColumn(name = "stock_inout_id")
    private StockInOutModel stockInOutModel;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("snBarcode", snBarcode)
                .append("batchNo", batchNo)
                .append("grade", grade)
                .append("cost", cost)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("barcode", barcode)
                .append("isFoil", isFoil)
                .append("msItemModel", msItemModel)
                .append("palletModel", palletModel)
                .append("msLocationModel", msLocationModel)
                .append("stockInOutLineModel", stockInOutLineModel)
                .append("msWorkingAreaModel", msWorkingAreaModel)
                .append("pickingOrderModel", pickingOrderModel)
                .append("msShiftModel", msShiftModel)
                .append("stockInOutModel", stockInOutModel)
                .toString();
    }
}
