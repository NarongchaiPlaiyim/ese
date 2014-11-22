package com.ese.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

@ManagedBean(name = "index")
@ViewScoped
public class IndexBean extends Bean{

    @PostConstruct
    private void init(){
    }

    public void onSubmit(){
//        log.debug("log");
        log.debug("log");
        moLogger.debug("moLogger");
        mtLogger.debug("mtLogger");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.err.println("Service : "+e);
        }
    }

}
