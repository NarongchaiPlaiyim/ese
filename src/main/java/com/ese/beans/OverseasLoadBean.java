package com.ese.beans;

import com.ese.service.OverseasLoadService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "overseasLoadBean")
public class OverseasLoadBean extends Bean {
    private static final long serialVersionUID = 4112532934029874840L;
    @ManagedProperty("#{overseasLoadService}") private OverseasLoadService overseasLoadService;
    @ManagedProperty("#{message['authorize.menu.loading.tab.2']}") private String key;

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
