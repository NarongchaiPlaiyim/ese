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
@Table(name = "forcing_line")
@Proxy(lazy=false)
public class ForcingLineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "barcode_register_id")
    private MSItemModel barcode_register_id;

    @OneToOne
    @JoinColumn(name = "working_area_id")
    private MSWorkingAreaModel workingAreaId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_datetime")
    private Date startDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_datetime")
    private Date finishDatetime;

    @Column(name = "grade")
    private String grade;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("barcode_register_id", barcode_register_id)
                .append("workingAreaId", workingAreaId)
                .append("startDatetime", startDatetime)
                .append("finishDatetime", finishDatetime)
                .append("grade", grade)
                .toString();
    }
}
