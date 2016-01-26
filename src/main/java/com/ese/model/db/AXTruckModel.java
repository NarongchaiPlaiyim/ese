package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ax_truck")
@Proxy(lazy=false)
public class AXTruckModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "truck_id")
    private String truckId;

    @Column(name = "truck_desc")
    private String truckDesc;

}
