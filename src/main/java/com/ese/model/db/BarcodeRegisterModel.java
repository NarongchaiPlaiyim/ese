package com.ese.model.db;

import com.ese.model.StatusBarcodeRegiterValue;
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
@Table(name = "barcode_register")
@Proxy(lazy=false)
public class BarcodeRegisterModel extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private MSItemModel msItemModel;

    @Column(name = "docno")
    private String docNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "production_date")
    private Date productionDate;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "qty")
    private int qty;

    @Column(name = "start_barcode")
    private int startBarcode;

    @Column(name = "finish_barcode")
    private int finishBarcode;

    @Column(name = "start_barcode_text")
    private String startBarcodeText;

    @Column(name = "finish_barcode_text")
    private String finishBarcodeText;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status", columnDefinition="int default 1")
    private StatusBarcodeRegiterValue status;

    @Column(name = "isvalid")
    private int isValid;

    @Column(name = "version")
    private int version;

    @Column(name = "cost")
    private BigDecimal cost;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("msItemModel", msItemModel)
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
