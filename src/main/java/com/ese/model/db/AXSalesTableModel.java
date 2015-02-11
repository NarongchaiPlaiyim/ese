//package com.ese.model.db;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import org.hibernate.annotations.Proxy;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "ax_SalesTable")
//@Proxy(lazy=false)
//public class AXSalesTableModel {
//    @Id
//    @Column(name="SalesId")
//    private String salesId;
//
//    @Column(name="CustAccount")
//    private String customerAccoust;
//
//    @Column(name="SalesStatus")
//    private int salesStatus;
//
//    @Column(name="Order_type")
//    private int orderType;
//
//    @OneToOne
//    @JoinColumn(name="SalesGroup ")
//    private AXCommissionSalesGroupModel axCommissionSalesGroupModel;
//
//    @Column(name="CurrencyCode")
//    private String currencyCode;
//
//    @Column(name="InvoiceAccount")
//    private String invoiceAccount;
//
//    @Column(name="DSG_FromPortId")
//    private String dSG_FromPortId;
//
//    @Column(name="DSG_ToPortId")
//    private String dSG_ToPortId;
//
//    @Column(name="CustomerRef")
//    private String customerRef;
//
//    @Column(name="DlvMode")
//    private String dlvMode;
//
//    @OneToOne
//    @JoinColumn(name="QuotationId")
//    private AXSalesQuantationTableModel quotationId;
//
//    @Column(name="Remarks")
//    private String remarks;
//
//    @Column(name="DSG_Container1X40")
//    private String dSG_Container1X40;
//
//    @Column(name="DSG_Container1X20")
//    private String dSG_Container1X20;
//
//    @Column(name="DSG_Container1X40HC")
//    private String dSG_Container1X40HC;
//
//    @Column(name="DSG_Container1X45HC")
//    private String dSG_Container1X45HC;
//
//    @Column(name="DSG_AvailablePackingDate")
//    private String dSG_AvailablePackingDate;
//
//    @Column(name="DSG_IC_Remark")
//    private String dSG_IC_Remark;
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
//                .append("salesId", salesId)
//                .append("customerAccoust", customerAccoust)
//                .append("salesStatus", salesStatus)
//                .append("orderType", orderType)
//                .append("axCommissionSalesGroupModel", axCommissionSalesGroupModel)
//                .append("currencyCode", currencyCode)
//                .append("invoiceAccount", invoiceAccount)
//                .append("dSG_FromPortId", dSG_FromPortId)
//                .append("dSG_ToPortId", dSG_ToPortId)
//                .append("customerRef", customerRef)
//                .append("dlvMode", dlvMode)
//                .append("quotationId", quotationId)
//                .append("remarks", remarks)
//                .append("dSG_Container1X40", dSG_Container1X40)
//                .append("dSG_Container1X20", dSG_Container1X20)
//                .append("dSG_Container1X40HC", dSG_Container1X40HC)
//                .append("dSG_Container1X45HC", dSG_Container1X45HC)
//                .append("dSG_AvailablePackingDate", dSG_AvailablePackingDate)
//                .append("dSG_IC_Remark", dSG_IC_Remark)
//                .toString();
//    }
//}
