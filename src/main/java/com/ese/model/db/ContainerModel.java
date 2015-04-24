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
@Table(name = "container")
@Proxy(lazy=false)
public class ContainerModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "loading_order_id")
    private LoadingOrderModel loadingOrderModel;

    @Column(name = "container_no")
    private String containerNo;

    @Column(name = "seal_no")
    private String sealNo;

    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("loadingOrderModel", loadingOrderModel)
                .append("containerNo", containerNo)
                .append("sealNo", sealNo)
                .append("quantity", quantity)
                .toString();
    }
}
