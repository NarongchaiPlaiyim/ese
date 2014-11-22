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
@Table(name = "location_movement")
@Proxy(lazy=false)
public class LocationMovementModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pallet_barcode")
    private String palletBarcode;

    @Column(name = "location_barcode")
    private String locationBarcode;

    @Column(name = "isvalid")
    private Integer isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("palletBarcode", palletBarcode)
                .append("locationBarcode", locationBarcode)
                .append("isValid", isValid)
                .toString();
    }
}
