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
@Table(name = "loading_order")
@Proxy(lazy=false)
public class LoadingOrderModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="docno")
    private String docNo;

    @Column(name="category")
    private String category;

    @Temporal(TemporalType.DATE)
    @Column(name = "loadingdate")
    private Date loadingDate;

    @Column(name="remark")
    private String remark;

    @OneToOne
    @JoinColumn(name="status")
    private StatusModel statusModel;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("docNo", docNo)
                .append("category", category)
                .append("loadingDate", loadingDate)
                .append("remark", remark)
                .append("statusModel", statusModel)
                .toString();
    }
}
