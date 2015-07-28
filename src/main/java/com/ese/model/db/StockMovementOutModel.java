package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "stock_movement_out")
@Proxy(lazy=false)
public class StockMovementOutModel extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="stock_inout_id")
    private StockInOutModel stockInOut;

    @Column(name="pallet_barcode")
    private String palletBarcode;

    @Column(name="sn_barcode")
    private String snBarcode;

    @Column(name="batchno")
    private String batchNo;

    @Column(name="status")
    private int status;

    @Column(name="isvalid" , nullable=false, columnDefinition="int default 0")
    private int isValid;
}
