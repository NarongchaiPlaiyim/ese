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
@Table(name = "stock_inout_line")
@Proxy(lazy=false)
public class StockInOutLineModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grade")
    private String grade;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "qty", nullable=false, columnDefinition="int default 0")
    private int qty;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isvalid", nullable=false, columnDefinition="int default 1")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @OneToOne
    @JoinColumn(name = "stock_inout_id")
    private StockInOutModel stockInOutModel;

    @OneToOne
    @JoinColumn(name = "item_id")
    private MSItemModel msItemModel;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletModel;

    @OneToOne
    @JoinColumn(name = "location_id")
    private MSLocationModel msLocationModel;

    @Column(name="batchno")
    private String batchNo;

    @Column(name="previous_location_id", nullable=false, columnDefinition="int default 0")
    private int previousLocationId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("grade", grade)
                .append("barcode", barcode)
                .append("qty", qty)
                .append("remark", remark)
                .append("isValid", isValid)
                .append("version", version)
                .append("stockInOutModel", stockInOutModel)
                .append("msItemModel", msItemModel)
                .append("palletModel", palletModel)
                .append("msLocationModel", msLocationModel)
                .append("batchNo", batchNo)
                .append("previousLocationId", previousLocationId)
                .toString();
    }
}
