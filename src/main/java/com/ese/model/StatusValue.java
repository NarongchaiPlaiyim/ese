package com.ese.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum StatusValue {
    Cancel(0, "Cancel"),
    Create(1, "Create"),
    Completed(2, "Completed"),
    Printed(3, "Printed"),
    Located(4, "Located"),
    reserved(5, "reserved"),
    Closed(6, "Closed");

    int id;
    String name;

    StatusValue(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int value(){
        return this.id;
    }

    public String statusName(){
        return this.name;
    }
}
