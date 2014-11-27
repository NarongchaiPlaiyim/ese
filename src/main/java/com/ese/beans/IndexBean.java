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
    private List<String> stringList;

    @PostConstruct
    private void init(){
        stringList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            stringList.add(""+i);
        }

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
