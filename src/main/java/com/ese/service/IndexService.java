package com.ese.service;

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

    @Autowired private Logger moLogger;
    @Autowired protected Logger log;
    @Autowired protected Logger mtLogger;

}
