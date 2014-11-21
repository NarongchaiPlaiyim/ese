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
@Table(name = "pallet_movement")
@Proxy(lazy=false)
public class PalletMovementModel extends AbstractModel{
    @Id
    private int id;
}
