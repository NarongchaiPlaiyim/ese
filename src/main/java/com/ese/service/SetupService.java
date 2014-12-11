package com.ese.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
public class SetupService extends Service{
    @PostConstruct
    private void init(){
        log.debug("-- init()");
    }
}
