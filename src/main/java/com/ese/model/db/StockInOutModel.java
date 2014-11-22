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
@Table(name = "stock_inout")
@Proxy(lazy=false)
public class StockInOutModel extends AbstractModel{
    @Id
    private int id;

    @Column(name = "docno")
    private String docNo;

    @OneToOne
    @JoinColumn(name = "stock_inout_note_id")
    private StockInOutNoteModel stockInOutNoteId;

    @Column(name = "do_no")
    private String doNo;

    @Column(name = "pl_no")
    private String plNo;

    @Column(name = "so_no")
    private String soNo;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customerId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @OneToOne
    @JoinColumn(name = "working_area_id")
    private WorkingAreaModel workingAreaId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("docNo", docNo)
                .append("stockInOutNoteId", stockInOutNoteId)
                .append("doNo", doNo)
                .append("plNo", plNo)
                .append("soNo", soNo)
                .append("customerId", customerId)
                .append("status", status)
                .append("remark", remark)
                .append("isValid", isValid)
                .append("version", version)
                .append("workingAreaId", workingAreaId)
                .toString();
    }
}
