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
@Table(name = "faction")
@Proxy(lazy=false)
public class FactionModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="department_id", nullable=false, columnDefinition="int default 0")
    private MSDepartmentModel msDepartmentModel;

    @Column(name="name")
    private String name;

    @Column(name="isvalid")
    private int isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("msDepartmentModel", msDepartmentModel)
                .append("name", name)
                .append("isValid", isValid)
                .toString();
    }
}
