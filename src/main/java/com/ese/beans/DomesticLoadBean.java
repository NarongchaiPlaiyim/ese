package com.ese.beans;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "domesticLoadBean")
public class DomesticLoadBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
}
