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
@Table(name = "barcode_register")
@Proxy(lazy=false)
public class BarcodeRegisterModel extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @Column(name = "docno")
    private String docNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "production_date")
    private Date productionDate;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "qty")
    private String qty;

    @Column(name = "start_barcode")
    private String startBarcode;

    @Column(name = "finish_barcode")
    private String finishBarcode;

    @Column(name = "start_barcode_text")
    private String startBarcodeText;

    @Column(name = "finish_barcode_text")
    private String finishBarcodeText;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Column(name = "cost")
    private Long cost;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("docNo", docNo)
                .append("productionDate", productionDate)
                .append("batchNo", batchNo)
                .append("qty", qty)
                .append("startBarcode", startBarcode)
                .append("finishBarcode", finishBarcode)
                .append("startBarcodeText", startBarcodeText)
                .append("finishBarcodeText", finishBarcodeText)
                .append("remark", remark)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("cost", cost)
                .toString();
    }
}
