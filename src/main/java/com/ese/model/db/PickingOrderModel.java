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
@Table(name = "picking_order")
@Proxy(lazy=false)
public class PickingOrderModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "docno")
    private String docNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "doc_date")
    private Date docDate;

    @OneToOne
    @JoinColumn(name = "customer_code", nullable=false)
    private AXCustomerTableModel customerCode;

    @Column(name = "confirmid")
    private String confirmId;

    @Temporal(TemporalType.DATE)
    @Column(name = "confirm_date")
    private Date confirmDate;

    @Column(name = "sales_order")
    private String salesOrder;

    @Column(name = "purchase_order")
    private String purchaseOrder;

    @Column(name = "delivery_name")
    private String deliveryName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "customer_ref")
    private String customerRef;

    @Column(name = "containers")
    private String containers;

    @Column(name = "payment_condition")
    private String paymentCondition;

    @Column(name = "delivery_term")
    private String deliveryTerm;

    @Column(name = "remark")
    private String remark;

    @Column(name = "sales_admin")
    private String salesAdmin;

    @Column(name = "mode_delivery")
    private String modeDelivery;

    @Column(name = "quotation")
    private String quotation;

    @Temporal(TemporalType.DATE)
    @Column(name = "quotation_date")
    private Date quotationDate;

    @OneToOne
    @JoinColumn(name = "status", nullable=false, columnDefinition="int default 0")
    private StatusModel status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Temporal(TemporalType.DATE)
    @Column(name = "request_shift_date")
    private Date requestShiftDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "edd_date")
    private Date eddDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "avaliable_date")
    private Date avalibleDate;

    @Column(name="dsg_remark")
    private String dsgRemark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("docNo", docNo)
                .append("docDate", docDate)
                .append("customerCode", customerCode)
                .append("confirmId", confirmId)
                .append("confirmDate", confirmDate)
                .append("salesOrder", salesOrder)
                .append("purchaseOrder", purchaseOrder)
                .append("deliveryName", deliveryName)
                .append("deliveryAddress", deliveryAddress)
                .append("customerRef", customerRef)
                .append("containers", containers)
                .append("paymentCondition", paymentCondition)
                .append("deliveryTerm", deliveryTerm)
                .append("remark", remark)
                .append("salesAdmin", salesAdmin)
                .append("modeDelivery", modeDelivery)
                .append("quotation", quotation)
                .append("quotationDate", quotationDate)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("requestShiftDate", requestShiftDate)
                .append("eddDate", eddDate)
                .append("avalibleDate", avalibleDate)
                .append("dsgRemark", dsgRemark)
                .toString();
    }
}
