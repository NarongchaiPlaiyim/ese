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
@Table(name = "location_items")
//@Proxy(lazy=false)
public class MSLocationItemsModel extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private MSLocationModel msLocationModel;

    @OneToOne//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private MSItemModel msItemModel;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("msLocationModel", msLocationModel)
                .append("msItemModel", msItemModel)
                .append("isValid", isValid)
                .append("version", version)
                .toString();
    }
}
