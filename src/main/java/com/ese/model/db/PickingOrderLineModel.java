package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "picking_order_line")
@Proxy(lazy=false)
public class PickingOrderLineModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "picking_order_id")
    private PickingOrderModel pickingOrderId;

    @Column(name = "line_num")
    private Integer line_num;

    @Column(name = "ItemId")
    private String ItemId;

    @Column(name = "origsaleid")
    private String origSaleId;

    @Column(name = "qty")
    private Integer qty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ship_date")
    private Date shipDate;

    @Column(name = "salesunit")
    private String salesUnit;

    @Column(name = "isfoil")
    private Integer isFoil;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("pickingOrderId", pickingOrderId)
                .append("line_num", line_num)
                .append("ItemId", ItemId)
                .append("origSaleId", origSaleId)
                .append("qty", qty)
                .append("shipDate", shipDate)
                .append("salesUnit", salesUnit)
                .append("isFoil", isFoil)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .toString();
    }
}
