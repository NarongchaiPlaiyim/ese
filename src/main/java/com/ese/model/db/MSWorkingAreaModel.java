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
@Table(name = "working_area")
@Proxy(lazy=false)
public class MSWorkingAreaModel {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private Integer version;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private MSWarehouseModel msWarehouseModel;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("version", version)
                .toString();
    }
}
