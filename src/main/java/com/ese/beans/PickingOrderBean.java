package com.ese.beans;

import com.ese.service.PickingOrderService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "pickingOrderBean")
public class PickingOrderBean extends Bean {
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{pickingOrderService}") private PickingOrderService pickingOrderService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;
}
