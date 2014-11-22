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
@Table(name = "pallet_movement")
@Proxy(lazy=false)
public class PalletMovementModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sn_barcode")
    private String snBarcode;

    @Column(name = "pallet_barcode")
    private String palletBarcode;

    @Column(name = "isvalid")
    private Integer isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("snBarcode", snBarcode)
                .append("palletBarcode", palletBarcode)
                .append("isValid", isValid)
                .toString();
    }
}
