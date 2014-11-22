package com.ese.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
public class SetupService extends Service{

    public SetupService() {
//        init();
    }

    @PostConstruct
    private void init(){
        log.debug("-- init()");
    }

    public void test(){
        try {
            System.out.println("test()");
        } catch (Exception e) {
            System.err.println(e);
        }
    }



}
