package com.ese.service;

import com.ese.model.dao.BarcodePrintingDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class BarcodePrintingService extends Service{
    private static final long serialVersionUID = 4112577394029874840L;
    @Resource private BarcodePrintingDAO barcodePrintingDAO;

    public String getLastSeq(){
        return "";
    }
}
