package com.ese.model.db;

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
@Table(name = "warehouse")
@Proxy(lazy=false)
public class WarehouseModel {
    @Id
    private int id;

    @Column(name = "warehouse_code")
    private String warehouseCode;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_by")
    private BigDecimal createBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "isvalid", nullable=false, columnDefinition="int default 0")
    private Integer isvalid;

    @Column(name = "status")
    private String status;

    @Column(name = "version", columnDefinition="int default 0")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("warehouseCode", warehouseCode)
                .append("warehouseName", warehouseName)
                .append("remark", remark)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .append("isvalid", isvalid)
                .append("status", status)
                .append("version", version)
                .toString();
    }
}
