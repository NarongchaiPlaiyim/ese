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
@Table(name = "system_role")
@Proxy(lazy=false)
public class SystemRoleModel extends AbstractModel{
    @Id
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String desxription;

    @Column(name = "version")
    private Integer version;

    @Column(name="isvalid")
    private int isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("desxription", desxription)
                .append("version", version)
                .append("isValid", isValid)
                .toString();
    }
}
