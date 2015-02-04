package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "reserved_order")
@Proxy(lazy=false)
public class ReservedOrderModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="picking_order_line_id")
    private PickingOrderLineModel pickingOrderLineModel;

    @Column(name="location_id")
    private int locationId;

    @Column(name="location_barcode")
    private String locationBarcode;

    @Column(name="reserved_qty")
    private int reservedQty;

    @Column(name="picked_qty")
    private BigDecimal pickedQty;

    @OneToOne
    @JoinColumn(name="status")
    private StatusModel statusModel;

    @Column(name="foil_qty")
    private BigDecimal foilQty;

    @Column(name = "isvalid", nullable=false, columnDefinition="int default 1")
    private Integer isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("pickingOrderLineModel", pickingOrderLineModel)
                .append("locationId", locationId)
                .append("locationBarcode", locationBarcode)
                .append("reservedQty", reservedQty)
                .append("pickedQty", pickedQty)
                .append("statusModel", statusModel)
                .append("foilQty", foilQty)
                .toString();
    }
}
