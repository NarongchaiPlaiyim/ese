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
@Table(name = "ax_SalesQuantationTable")
@Proxy(lazy=false)
public class AXSalesQuantationTableModel {
    @Id
    @Column(name="QuantationId")
    private String quanttationId;

    @Column(name="QuantationName")
    private String quantationName;

    @Column(name="BankAcc")
    private String bankAcc;

    @Column(name="ShippingMark")
    private String shippingMark;

    @Temporal(TemporalType.DATE)
    @Column(name="CreatedDate")
    private Date createdate;

    @OneToOne
    @JoinColumn(name="Payment")
    private AXPaymTremModel paymant;

    @Column(name="DocuConclusion")
    private String docuCoclusion;

    @Column(name="DocuIntro")
    private String docuIntro;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("quanttationId", quanttationId)
                .append("quantationName", quantationName)
                .append("bankAcc", bankAcc)
                .append("shippingMark", shippingMark)
                .append("createdate", createdate)
                .append("paymant", paymant)
                .append("docuCoclusion", docuCoclusion)
                .append("docuIntro", docuIntro)
                .toString();
    }
}
