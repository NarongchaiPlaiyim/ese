package com.ese.service;

import com.ese.model.db.ProfileModel;

import com.ese.model.dao.ProfileDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component
@Transactional
public class IndexService implements Serializable{
    @Resource private ProfileDAO profileDAO;

    @Autowired private Logger moLogger;
    @Autowired protected Logger log;
    @Autowired protected Logger mtLogger;

    public  List<ProfileModel> getList(){
        try {
            log.debug("log");
            moLogger.debug("moLogger");
            mtLogger.debug("mtLogger");
            log.debug("-- getList()");
            return profileDAO.findAll();
        } catch (Exception e) {
            System.out.println("Error : " + e);
            return new ArrayList<ProfileModel>();
        } finally {

        }



    }
}
