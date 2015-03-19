package com.ese.service;

import com.ese.model.TableValue;
import com.ese.model.dao.StatusDAO;
import com.ese.model.db.StatusModel;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class DomesticLoadService extends Service {
    private static final long serialVersionUID = 4112578634263394999L;
    @Resource private StatusDAO statusDAO;

    public List<StatusModel> getStatusAll(){
        List<StatusModel> statusModelList = Utils.getEmptyList();
        try {
            statusModelList = statusDAO.findByTablePickingOrder(TableValue.LOADING_ORDER.getId());
        } catch (Exception e) {
            log.debug("Exception error getStatusAll", e);
        }
        return statusModelList;
    }
}
