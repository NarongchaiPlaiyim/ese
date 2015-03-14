package com.ese.beans;

import com.ese.service.DomesticLoadService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "domesticLoadBean")
public class DomesticLoadBean extends Bean {
    private static final long serialVersionUID = 4112578634263394840L;
    @ManagedProperty("#{domesticLoadService}") private DomesticLoadService domesticLoadService;
    @ManagedProperty("#{message['authorize.menu.loading.tab.1']}") private String key;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        if(preLoad()) {//&& isAuthorize(key)){
            init();
        }
    }

    private void init(){

    }
}
