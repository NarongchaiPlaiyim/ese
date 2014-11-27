package com.ese.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum StatusValue {
    CANCEL(0, "Cancel"),
    CREATE(1, "Create"),
    COMPLETED(2, "Completed"),
    PRINTED(3, "Printed"),
    LOCATED(4, "Located"),
    RESERVED(5, "reserved"),
    CLOSED(6, "Closed");

    private int id;
    private String name;

    private StatusValue(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
