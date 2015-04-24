package com.ese.model.view;

import lombok.Getter;

@Getter
public enum StatusLoadingOrderView {
    CANCEL(13, "Cancel"),
    CREATE(12, "Create"),
    SELECT_PICKING_LIST(15, "select picking list");

    private int id;
    private String name;

    private StatusLoadingOrderView(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
