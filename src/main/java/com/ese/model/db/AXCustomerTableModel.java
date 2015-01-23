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
@Table(name = "ax_CustTable")
@Proxy(lazy=false)
public class AXCustomerTableModel {
    @Id
    @Column(name="AccountNum")
    private String accountNum;

    @Column(name="DSG_ByProductCode")
    private String dSGByProductCode;

    @Column(name="Name")
    private String name;

    @Column(name="NameAlias")
    private String nameAlias;

    @Column(name="InvoiceAccount")
    private String invoiceAccount;

    @OneToOne
    @JoinColumn(name="CustGroup")
    private AxCustomerGroupModel custGroup;

    @Column(name="Currency")
    private String currency;

    @Temporal(TemporalType.DATE)
    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="DSG_PrimaryAccount")
    private String dSG_PrimaryAccount;

    @Column(name="DlvMode")
    private String dlvMode;

    @Column(name="SalesGroup")
    private String salesGroup;

    @Column(name="SalesPoolId")
    private String salesPoolId;

    @Column(name="Street")
    private String street;

    @Column(name="ZipCode")
    private String zipCode;

    @Column(name="City")
    private String city;

    @Column(name="County")
    private String county;

    @Column(name="State")
    private String state;

    @Column(name="CountryRegionId")
    private String countryRegionId;

    @Column(name="SalesOriginId")
    private String salesOriginId;

    @Column(name="Address")
    private String address;

    @Column(name="PaymTermId")
    private String paymTermId;

    @Column(name="Dimension")
    private String dimension;

    @Column(name="Dimension2_")
    private String dimension2_;

    @Column(name="Dimension3_")
    private String dimension3_;

    @Column(name="record_boolean")
    private boolean recordBoolean;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdatetime_timestamp")
    private Date createdatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modifydatetime_timestamp")
    private Date modifydatetimeTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deletedatetime_timestamp")
    private Date deletedatetimeTimestamp;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("accountNum", accountNum)
                .append("dSG_ByProductCode", dSGByProductCode)
                .append("name", name)
                .append("nameAlias", nameAlias)
                .append("invoiceAccount", invoiceAccount)
                .append("custGroup", custGroup)
                .append("currency", currency)
                .append("createdDate", createdDate)
                .append("dSG_PrimaryAccount", dSG_PrimaryAccount)
                .append("dlvMode", dlvMode)
                .append("salesGroup", salesGroup)
                .append("salesPoolId", salesPoolId)
                .append("street", street)
                .append("zipCode", zipCode)
                .append("city", city)
                .append("county", county)
                .append("state", state)
                .append("countryRegionId", countryRegionId)
                .append("salesOriginId", salesOriginId)
                .append("address", address)
                .append("paymTermId", paymTermId)
                .append("dimension", dimension)
                .append("dimension2_", dimension2_)
                .append("dimension3_", dimension3_)
                .append("record_boolean", recordBoolean)
                .append("createdatetime_timestamp", createdatetimeTimestamp)
                .append("modifydatetime_timestamp", modifydatetimeTimestamp)
                .toString();
    }
}
