package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "table_info")
@Proxy(lazy=false)
public class TableModel {
    @Id
    private int id;

    @Column(name="table_name")
    private String tableName;

    @Column(name="caption")
    private String caption;

    @Column(name="description")
    private String description;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("tableName", tableName)
                .append("caption", caption)
                .append("description", description)
                .toString();
    }
}
