package com.ese.model.view;

import lombok.Getter;

@Getter
public enum StatusPickingValue {

    CANCEL(0, "Cancel"),
    CREATE(1, "Create"),
    RESERVED(2, "Reserved"),
    ASSIGN_TO_PICK(3, "Assign to pick"),
    PICKING(4, "Picking"),
    POST(5, "Post");

    private int id;
    private String name;

    private StatusPickingValue(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
