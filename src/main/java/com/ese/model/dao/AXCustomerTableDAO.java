package com.ese.model.dao;


import com.ese.model.db.AXCustomerTableModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AXCustomerTableDAO extends GenericDAO<AXCustomerTableModel, String>{

    public AXCustomerTableModel findByAccountNum(String customerCode){
        AXCustomerTableModel customerTableModel = new AXCustomerTableModel();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("accountNum", customerCode));
            customerTableModel = (AXCustomerTableModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findByAccountNum : {}", e);
        }

        return customerTableModel;
    }
}
