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
@Table(name = "staff_roles")
@Proxy(lazy=false)
public class StaffRolesModel {
    @Id
    private int id;

    @Column(name = "staff")
    private Integer staff;

    @Column(name = "roles")
    private Integer roles;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("staff", staff)
                .append("roles", roles)
                .toString();
    }
}
