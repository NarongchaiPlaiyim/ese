package com.ese.utils;

import lombok.Getter;

@Getter
public enum AttributeName {
    USER_DETAIL("userDetail");
    private String name;
    private AttributeName(String name) {
        this.name = name;
    }
}