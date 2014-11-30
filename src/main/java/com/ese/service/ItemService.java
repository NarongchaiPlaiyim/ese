package com.ese.service;

import com.ese.model.dao.ItemDAO;
import com.ese.model.db.MSItemModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class ItemService extends Service{

    @Resource private ItemDAO itemDAO;

    public List<MSItemModel> findByCondition(final String type, final String text){
        log.debug("-- findByCondition({}, {})", type, text);
        try {
            List<MSItemModel> msItemModelList;
            if("3".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("dSGThaiItemDescription", text);
            } else if("2".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("itemId", text);
            } else if ("1".equalsIgnoreCase(type)){
                msItemModelList = itemDAO.findByLike("itemName", text);
            } else {
                msItemModelList = itemDAO.findAll();
            }
            return msItemModelList;
        } catch (Exception e) {
            log.error("{}",e);
            return Collections.EMPTY_LIST;
        }
    }

}
