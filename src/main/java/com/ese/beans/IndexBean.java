package com.ese.beans;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "index")
@ViewScoped
public class IndexBean extends Bean{
    private String messageIndex;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){
            init();
        }
    }

    private void init(){
        messageIndex = "HELLO!!!!!!!!!";
    }

}
