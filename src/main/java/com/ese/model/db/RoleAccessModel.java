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
@Table(name = "role_access")
@Proxy(lazy=false)
public class RoleAccessModel extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "system_role_id", nullable=false, columnDefinition="int default 0")
    private SystemRoleModel systemRoleModel;

    @OneToOne
    @JoinColumn(name = "menu_object_id", nullable =false, columnDefinition = "int default 0")
    private MenuObjectModel menuObjectModel;

    @Column(name="isvalid")
    private int isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("systemRoleModel", systemRoleModel)
                .append("menuObjectModel", menuObjectModel)
                .append("isValid", isValid)
                .toString();
    }
}
