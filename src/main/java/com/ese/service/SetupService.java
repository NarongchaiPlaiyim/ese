package com.ese.service;

import com.ese.model.dao.ProfileDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;

@Component
@Transactional
public class SetupService extends Service implements Serializable {
    @Resource private ProfileDAO profileDAO;

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
            System.out.println(profileDAO.findAll().toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }



}
