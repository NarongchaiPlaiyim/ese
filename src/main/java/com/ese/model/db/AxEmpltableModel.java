package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ax_empltable")
@Proxy(lazy=false)
public class AxEmpltableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emplid")
    private String emplId;

    @Column(name = "name")
    private String name;

    @Column(name="alias")
    private String alias;
}
