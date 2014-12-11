package com.ese.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
public class SetupService extends Service{
    @Getter @Value("#{config['authorize.menu.setup']}") private String key;

    public SetupService() {
//        init();
    }

    @PostConstruct
    private void init(){
        log.debug("-- init()");
    }
}
