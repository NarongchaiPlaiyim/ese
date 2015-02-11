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
//@Table(name = "ax_PaymTerm")
//@Proxy(lazy=false)
//public class AXPaymTremModel {
//    @Id
//    @Column(name="PaymTermId")
//    private String paymTermId;
//
//    @Column(name="Description")
//    private String description;
//
//    @Column(name="NumOfDays")
//    private int numOfDays;
//
//    @Column(name="record_boolean")
//    private boolean recordBoolean;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="createdatetime_timestamp")
//    private Date createDateTime;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="modifydatetime_timestamp")
//    private Date modifyDateTime;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="deletedatetime_timestamp")
//    private Date deleteDateTime;
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
//                .append("paymTermId", paymTermId)
//                .append("description", description)
//                .append("numOfDays", numOfDays)
//                .append("recordBoolean", recordBoolean)
//                .append("createDateTime", createDateTime)
//                .append("modifyDateTime", modifyDateTime)
//                .append("deleteDateTime", deleteDateTime)
//                .toString();
//    }
//}
