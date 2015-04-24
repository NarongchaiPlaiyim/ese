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
@Table(name = "container_item")
@Proxy(lazy=false)
public class ContainerItemModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "container_id")
    private ContainerModel containerModel;

    @OneToOne
    @JoinColumn(name = "itemId")
    private MSItemModel msItemModel;

    @Column(name = "container_qty")
    private int containerQty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("containerModel", containerModel)
                .append("msItemModel", msItemModel)
                .append("containerQty", containerQty)
                .toString();
    }
}
