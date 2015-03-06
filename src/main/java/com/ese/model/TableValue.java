package com.ese.model;

import lombok.Getter;

@Getter
public enum  TableValue {
    PICKING_ORDER(1),
    RESERVED_ORDER(2),
    PICKING_LINE(3),
    LOADING_ORDER(4);

    private int id;

    private TableValue(int id) {
        this.id = id;
    }
}
