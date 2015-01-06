package com.ese.beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Document implements Serializable, Comparable<Document> {
    private int id;
    private String code;
    private String name;
    private String size;
    private String type;

    public Document(String name, String size, String type) {
        this.name = name;
        this.size = size;
        this.type = type;
    }

    @Override
    public int compareTo(Document o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
