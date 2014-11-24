package com.ese.model.dao;

import com.ese.model.db.BarcodeRegisterModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BarcodeRegisterDAO extends GenericDAO<BarcodeRegisterModel, Integer> {

    public List<BarcodeRegisterModel> findByIsValid() throws Exception {
        Criteria criteria = getCriteria().add(Restrictions.eq("isValid", 1)).addOrder(Order.asc("updateDate"));
        return Utils.safetyList(criteria.list());
    }

    public void deleteByUpdate(final BarcodeRegisterModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        update(model);
    }

}
