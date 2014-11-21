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
@Table(name = "inv_onhand")
@Proxy(lazy=false)
public class InvOnHandModel extends AbstractModel{
    @Id
    private int id;
}
