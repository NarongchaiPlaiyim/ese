package com.ese.model.dao;

import com.ese.model.db.StockInOutLineModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockInOutLineDAO extends GenericDAO<StockInOutLineModel, Integer> {

    public List<StockInOutLineModel> findByStockInOutId(int stockInOutId){
        log.debug("findByStockInOutId(). {}", stockInOutId);
        List<StockInOutLineModel> stockInOutLineModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("stockInOutModel.id", stockInOutId));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            stockInOutLineModelList = Utils.safetyList(criteria.list());
            log.debug("stockInOutLineModelList Size : {}", stockInOutLineModelList.size());
        } catch (Exception e) {
            log.debug("Exception Error findByStockInOutId : ", e);
        }
        return stockInOutLineModelList;
    }
}
