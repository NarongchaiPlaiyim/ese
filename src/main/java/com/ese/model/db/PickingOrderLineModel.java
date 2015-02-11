package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal line_num;

    @Column(name = "ItemId")
    private String itemId;

    @Column(name = "origsaleid")
    private String origSaleId;

    @Column(name = "qty")
    private int qty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ship_date")
    private Date shipDate;

    @Column(name = "salesunit")
    private String salesUnit;

    @Column(name = "isfoil", nullable=false, columnDefinition="int default 0")
    private boolean isFoil;

    @OneToOne
    @JoinColumn(name = "status", nullable=false, columnDefinition="int default 0")
    private StatusModel status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Column(name="DSG_SubGroupDescription")
    private String dSGSubGroupDescription;

    @Column(name="PIDescription")
    private String pIDescription;

    @Column(name="DSG_Ext_Item_NO")
    private String dSGExtItemNO;

    @Column(name="Name")
    private String name;

    @Column(name="DSG_PackingQty")
    private BigDecimal dSGPackingQty;


    @Column(name="salesUnitTxt")
    private String salesUnitTxt;

    @Column(name="Cum")
    private BigDecimal cum;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("pickingOrderId", pickingOrderId)
                .append("line_num", line_num)
                .append("itemId", itemId)
                .append("origSaleId", origSaleId)
                .append("qty", qty)
                .append("shipDate", shipDate)
                .append("salesUnit", salesUnit)
                .append("isFoil", isFoil)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("dSGSubGroupDescription", dSGSubGroupDescription)
                .append("pIDescription", pIDescription)
                .append("dSGExtItemNO", dSGExtItemNO)
                .append("name", name)
                .append("dSGPackingQty", dSGPackingQty)
                .append("salesUnitTxt", salesUnitTxt)
                .append("cum", cum)
                .toString();
    }
}
