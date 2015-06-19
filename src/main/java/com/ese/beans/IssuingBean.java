package com.ese.beans;

import com.ese.model.view.IssuingView;
import com.ese.service.IssuingService;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "issuingBean")
public class IssuingBean extends Bean {
    private static final long serialVersionUID = 4112578634263333840L;
    @ManagedProperty("#{message['authorize.menu.stockmovement.issuing']}") private String key;
    @ManagedProperty("#{issuingService}") private IssuingService issuingService;

    @NotNull private IssuingView issuingView;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){

    }
}
