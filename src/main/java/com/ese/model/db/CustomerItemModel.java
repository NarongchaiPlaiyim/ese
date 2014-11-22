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
@Table(name = "customer_item")
@Proxy(lazy=false)
public class CustomerItemModel extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customerId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private MSItemModel itemId;

    @Column(name = "item_barcode")
    private String itemBarcode;

    @Column(name = "is_label")
    private Integer isLabel;

    @Column(name = "size_width")
    private Integer sizeWidth;

    @Column(name = "size_long")
    private Integer sizeLong;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "isvalid")
    private Integer isValid;

    @Column(name = "version")
    private Integer version;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("customerId", customerId)
                .append("itemId", itemId)
                .append("itemBarcode", itemBarcode)
                .append("isLabel", isLabel)
                .append("sizeWidth", sizeWidth)
                .append("sizeLong", sizeLong)
                .append("fileName", fileName)
                .append("remark", remark)
                .append("isValid", isValid)
                .append("version", version)
                .toString();
    }
}
