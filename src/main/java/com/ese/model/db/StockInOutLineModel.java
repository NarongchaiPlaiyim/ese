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

    @OneToOne
    @JoinColumn(name = "stock_inout_id")
    private StockInOutModel stockInOutId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @Column(name = "grade")
    private String grade;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "qty")
    private String qty;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletId;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel locationId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("stockInOutId", stockInOutId)
                .append("itemId", itemId)
                .append("grade", grade)
                .append("barcode", barcode)
                .append("qty", qty)
                .append("palletId", palletId)
                .append("locationId", locationId)
                .append("remark", remark)
                .append("isValid", isValid)
                .append("version", version)
                .toString();
    }
}
