package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "inv_transaction")
@Proxy(lazy=false)
public class InvTransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "stock_inout_note_id")
    private StockInOutNoteModel stockInOutNoteId;

    @OneToOne
    @JoinColumn(name = "pallet_id")
    private PalletModel palletId;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationModel locationId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @Column(name = "sn_barcode")
    private String snBarcode;

    @Column(name = "grade")
    private String grade;

    @Column(name = "remark")
    private String remark;

    @Column(name = "do_no")
    private String doNo;

    @Column(name = "pl_no")
    private String plNo;

    @Column(name = "so_no")
    private String soNo;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customerId;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Column(name = "barcode")
    private String barcode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("stockInOutNoteId", stockInOutNoteId)
                .append("palletId", palletId)
                .append("locationId", locationId)
                .append("itemId", itemId)
                .append("snBarcode", snBarcode)
                .append("grade", grade)
                .append("remark", remark)
                .append("doNo", doNo)
                .append("plNo", plNo)
                .append("soNo", soNo)
                .append("customerId", customerId)
                .append("referenceId", referenceId)
                .append("status", status)
                .append("isValid", isValid)
                .append("version", version)
                .append("barcode", barcode)
                .toString();
    }
}
