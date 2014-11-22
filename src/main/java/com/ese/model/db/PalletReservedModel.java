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
@Table(name = "pallet_reserved")
@Proxy(lazy=false)
public class PalletReservedModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "picking_order_line_id")
    private PickingOrderLineModel pickingOrderLineId;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletId;

    @Column(name = "reserve_qty")
    private Integer reserveQty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("pickingOrderLineId", pickingOrderLineId)
                .append("palletId", palletId)
                .append("reserveQty", reserveQty)
                .append("status", status)
                .append("isValid", isValid)
                .toString();
    }
}
