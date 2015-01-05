package com.ese.model;

import lombok.Getter;

@Getter
public enum StatusBarcodeRegiterValue {

    CANCEL(0, "Cancel"),
    INPROCESS(1, "In Process"),
    COMPLETED(2, "Completed"),
    HOLD(3, "Hold"),
    CLOSE(4, "Close");

    private int id;
    private String name;

    private StatusBarcodeRegiterValue(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
