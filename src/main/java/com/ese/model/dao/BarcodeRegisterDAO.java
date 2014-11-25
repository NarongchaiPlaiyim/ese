package com.ese.model.dao;

import com.ese.model.db.BarcodeRegisterModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BarcodeRegisterDAO extends GenericDAO<BarcodeRegisterModel, Integer> {

    public List<BarcodeRegisterModel> findByIsValid() throws Exception {
        Criteria criteria = getCriteria().add(Restrictions.eq("isValid", 1)).addOrder(Order.asc("updateDate"));
        return Utils.safetyList(criteria.list());
    }

    public void deleteByUpdate(final BarcodeRegisterModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }

    public BigDecimal getPrice(String iTemId) throws Exception {
        final String sql = "SELECT a.Price FROM ppwms03.dbo.ax_InventtableModule a WHERE a.ItemId = '"+iTemId+"' AND a.ModuleType = 0";
        return Utils.parseBigDecimal(getSession().createSQLQuery(sql).uniqueResult(), BigDecimal.ZERO);
    }

}
