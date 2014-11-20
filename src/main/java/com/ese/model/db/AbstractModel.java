package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class AbstractModel implements Serializable {
    @Column(name = "create_by")
    private int createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private int updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .toString();
    }
}
