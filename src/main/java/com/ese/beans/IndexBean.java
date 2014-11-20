package com.ese.beans;

import com.ese.model.db.ProfileModel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "index")
@ViewScoped
public class IndexBean extends Bean implements Serializable {
    @Getter @Setter private List<ProfileModel> profileModelList;

    @PostConstruct
    private void init(){
        profileModelList = new ArrayList<ProfileModel>();
    }

    public void onSubmit(){
//        log.debug("log");
        log.debug("log");
        moLogger.debug("moLogger");
        mtLogger.debug("mtLogger");
        try {
            profileModelList = indexService.getList();
        } catch (Exception e) {
            System.err.println("Service : "+e);
        }
    }

}
