//package com.ese.model.db;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import org.hibernate.annotations.Proxy;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.math.BigDecimal;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "ax_InventTrans")
//@Proxy(lazy=false)
//public class AXInventTransModel {
//    @Id
//    private int id;
//
//    @Column(name="transrefid")
//    private String transRedId;
//
//    @Column(name="itemid")
//    private String itemId;
//
//    @Column(name="inventtransid")
//    private String inventTransId;
//
//    @Column(name="qty")
//    private BigDecimal qty;
//
//    @Column(name="status")
//    private String status;
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
//                .append("id", id)
//                .append("transRedId", transRedId)
//                .append("itemId", itemId)
//                .append("inventTransId", inventTransId)
//                .append("qty", qty)
//                .append("status", status)
//                .append("status", status)
//                .toString();
//    }
//}
