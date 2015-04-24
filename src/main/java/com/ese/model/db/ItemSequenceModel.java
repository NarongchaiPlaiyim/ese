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
@Table(name = "item_sequence")
@Proxy(lazy=false)
public class ItemSequenceModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "loading_order_id")
    private LoadingOrderModel loadingOrderModel;

    @OneToOne
    @JoinColumn(name = "itemId")
    private MSItemModel msItemModel;

    @Column(name = "seq")
    private int seq;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("loadingOrderModel", loadingOrderModel)
                .append("msItemModel", msItemModel)
                .append("seq", seq)
                .toString();
    }
}
