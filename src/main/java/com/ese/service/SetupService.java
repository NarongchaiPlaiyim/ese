package com.ese.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Component
@Transactional
public class SetupService extends Service implements Serializable {


    public SetupService() {
        init();
    }

    private void init(){
        log.debug("-- init()");
    }





}
