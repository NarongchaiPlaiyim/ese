package com.ese.utils;

import lombok.Getter;

@Getter
public enum NamesUtil {
    LOGIN_PAGE("/login.xhtml"),
    DIALOG_NAME("msgBoxSystemMessageDlg")
    ;
    private String name;

    private NamesUtil(String name) {
        this.name = name;
    }
}
