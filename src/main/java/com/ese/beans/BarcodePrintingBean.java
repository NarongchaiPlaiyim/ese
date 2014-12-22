package com.ese.beans;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodePrintingBean")
public class BarcodePrintingBean extends Bean {
    private static final long serialVersionUID = 4112578634029879990L;

    private String startBarcode;
    private String qty;
    private String finishBarcode;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){ //&& isAuthorize("0100")){
            init();
        }
    }

    private void init(){

    }

    public void onClickOk(){

    }
}
