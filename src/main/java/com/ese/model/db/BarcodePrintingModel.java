package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "barcode_printing")
@Proxy(lazy=false)
public class BarcodePrintingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
