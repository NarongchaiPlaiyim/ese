package com.ese.beans;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public Document(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    @Override
    public int compareTo(Document o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("size", size)
                .append("type", type)
                .toString();
    }
}
