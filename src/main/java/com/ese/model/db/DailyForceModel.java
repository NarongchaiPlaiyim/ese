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
@Table(name = "daily_force")
@Proxy(lazy=false)
public class DailyForceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemModel itemId;

    @Column(name = "sn_barcode")
    private String snBarcode;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "grade")
    private String grade;

    @Column(name = "cost")
    private Long cost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receive_date")
    private Date receiveDate;

    @OneToOne
    @JoinColumn(name = "stock_inout_note_id")
    private StockInOutNoteModel stockInoutNoteId;

    @OneToOne
    @JoinColumn(name = "working_area_id")
    private WorkingAreaModel workingAreaId;

    @OneToOne
    @JoinColumn(name = "forcing_line_id")
    private ForcingLineModel forcingLineId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("itemId", itemId)
                .append("snBarcode", snBarcode)
                .append("batchNo", batchNo)
                .append("grade", grade)
                .append("cost", cost)
                .append("receiveDate", receiveDate)
                .append("stockInoutNoteId", stockInoutNoteId)
                .append("workingAreaId", workingAreaId)
                .append("forcingLineId", forcingLineId)
                .toString();
    }
}
