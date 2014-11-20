package com.ese.beans;

import com.ese.model.view.SetupView;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "setup")
public class SetupBean extends Bean {
    private SetupView setupView;

    public SetupBean() {

    }

    @PostConstruct
    private void init(){
        setupView = new SetupView();
    }

    public void onClickNewButtonTAB(){
        log.debug("-- onClickNewButtonTAB()");
    }

    public void onClickSaveButtonTAB(){
        log.debug("-- onClickSaveButtonTAB()");
    }

    public void onClickDeleteButtonTAB(){
        log.debug("-- onClickDeleteButtonTAB()");
    }

    public void onClickNewEditButtonTAB(){
        log.debug("-- onClickNewEditButtonTAB()");
    }

    public void onClickShowAddItemButtonTAB(){
        log.debug("-- onClickShowAddItemButtonTAB()");
    }

    public void onClickNewButtonTAB2(){
        log.debug("-- onClickNewButtonTAB2()");
    }

    public void onClickSaveButtonTAB2(){
        log.debug("-- onClickSaveButtonTAB2()");
    }

    public void onClickDeleteButtonTAB2(){
        log.debug("-- onClickDeleteButtonTAB2()");
    }
}
