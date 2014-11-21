package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "stock_inout_line")
@Proxy(lazy=false)
public class StockInOutLineModel {
    @Id
    private int id;
}
