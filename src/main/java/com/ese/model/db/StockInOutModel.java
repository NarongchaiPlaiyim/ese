package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "stock_inout")
@Proxy(lazy=false)
public class StockInOutModel extends AbstractModel{
    @Id
    private int id;

    @Column(name = "docno")
    private String docNo;
}
