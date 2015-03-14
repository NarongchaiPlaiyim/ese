package com.ese.beans;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "overseasLoadBean")
public class OverseasLoadBean extends Bean {
    private static final long serialVersionUID = 4112532934029874840L;
}
