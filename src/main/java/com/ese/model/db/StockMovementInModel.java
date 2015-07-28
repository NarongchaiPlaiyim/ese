package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "stock_movement_in")
@Proxy(lazy=false)
public class StockMovementInModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "status")
    private int status;

    @OneToOne
    @JoinColumn(name = "stock_inout_id")
    private StockInOutModel stockInOutModel;

    @Column(name="pallet_barcode")
    private String palletBarcode;

    @Column(name="sn_barcode")
    private String snBarcode;

    @Column(name="batchno")
    private String batchNo;

    @Column(name = "isvalid" , columnDefinition="int default 1")
    private Integer isValid;
}
