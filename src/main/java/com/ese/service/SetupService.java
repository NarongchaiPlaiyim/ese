package com.ese.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
public class SetupService extends Service{
    private static final long serialVersionUID = 4112578634029574840L;

    @PostConstruct
    private void init(){
        log.debug("-- init()");
    }
}
