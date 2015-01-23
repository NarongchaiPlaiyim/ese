package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ax_CustGroup")
@Proxy(lazy=false)
public class AxCustomerGroupModel {
    @Id
    @Column(name="CustGroup")
    private String customerGroup;

    @Column(name="Name")
    private String name;

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
}
