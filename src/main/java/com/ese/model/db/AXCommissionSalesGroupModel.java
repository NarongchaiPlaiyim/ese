//package com.ese.model.db;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import org.hibernate.annotations.Proxy;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "ax_CommissionSalesGroup")
//@Proxy(lazy=false)
//public class AXCommissionSalesGroupModel {
//    @Id
//    @Column(name="GroupId")
//    private String groupId;
//
//    @Column(name="Name")
//    private String name;
//
//    @Column(name="record_boolean")
//    private boolean record_boolean;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="createdatetime_timestamp")
//    private Date createdatetime_timestamp;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="modifydatetime_timestamp")
//    private Date modifydatetime_timestamp;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="deletedatetime_timestamp")
//    private Date deletedatetime_timestamp;
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
//                .append("groupId", groupId)
//                .append("name", name)
//                .append("record_boolean", record_boolean)
//                .append("createdatetime_timestamp", createdatetime_timestamp)
//                .append("modifydatetime_timestamp", modifydatetime_timestamp)
//                .append("deletedatetime_timestamp", deletedatetime_timestamp)
//                .toString();
//    }
//}
