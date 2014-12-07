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
@Table(name = "staff")
@Proxy(lazy=false)
public class StaffModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @OneToOne
    @JoinColumn(name="title_id", nullable=false, columnDefinition="int default 0")
    private MSTitleModel msTitleModel;

    @Column(name = "version")
    private Integer version;

    @OneToOne
    @JoinColumn(name="faction_id", nullable=false, columnDefinition="int default 0")
    private FactionModel factionModel;

    @Column(name="name")
    private String name;

    @Column(name="isvalid")
    private int isValid;

    @Column(name="position")
    private String position;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("password", password)
                .append("username", username)
                .append("version", version)
                .append("factionModel", factionModel)
                .append("name", name)
                .append("isValid", isValid)
                .append("position", position)
                .toString();
    }
}
