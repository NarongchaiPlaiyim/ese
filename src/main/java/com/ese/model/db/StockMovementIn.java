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
public class StockMovementIn extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "status")
    private int status;

    @OneToOne
    @JoinColumn(name = "stockInOutModel_id")
    private StockInOutModel stockInOutModel;

    @OneToOne
    @JoinColumn(name="pallet_barcode")
    private PalletModel palletModel;

    @Column(name="sn_barcode")
    private String snBarcode;

    @Column(name="batchno")
    private String batchNo;

}
