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
@Table(name = "ax_CustGroup")
@Proxy(lazy=false)
public class AxCustomerGroupModel {
    @Id
    @Column(name="CustGroup")
    private String customerGroup;

    @Column(name="Name")
    private String name;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customerGroup", customerGroup)
                .append("name", name)
                .toString();
    }
}
