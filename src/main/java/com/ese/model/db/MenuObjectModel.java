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
@Table(name = "menu_object")
@Proxy(lazy=false)
public class MenuObjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="app_category")
    private Integer appCategory;

    @Column(name="parent_id")
    private Integer parentId;

    @Column(name="code")
    private String code;

    @Column(name="obj_category")
    private Integer objCategory;

    @Column(name="name")
    private String name;

    @Column(name="remark")
    private String remark;

    @Column(name="seq")
    private String seq;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("appCategory", appCategory)
                .append("parentId", parentId)
                .append("code", code)
                .append("objCategory", objCategory)
                .append("name", name)
                .append("remark", remark)
                .append("seq", seq)
                .toString();
    }
}
