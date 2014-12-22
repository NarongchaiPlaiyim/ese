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
@Table(name = "barcode_printing")
@Proxy(lazy=false)
public class BarcodePrintingModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="start_barcode")
    private String startBarcode;

    @Column(name="finish_barcode")
    private String finishBarcode;

    @Column(name="qty")
    private int qty;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("startBarcode", startBarcode)
                .append("finishBarcode", finishBarcode)
                .append("qty", qty)
                .toString();
    }
}
