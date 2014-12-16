package com.ese.model.dao;

import com.ese.model.db.StaffModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffDAO extends GenericDAO<StaffModel, Integer>{

    public StaffModel findByUserName(String userName, String password) throws Exception {
        return (StaffModel) getCriteria().add(Restrictions.and(
                Restrictions.eq("username", userName),
                Restrictions.eq("password", password)
        )).uniqueResult();
    }

    public List<StaffModel> test() throws Exception {
//        SELECT * FROM dbo.staff
        return findBySQL("SELECT * FROM dbo.staff", "3", 5, 6);
    }

    public List<StaffModel> findUserByIsValid(){
        List<StaffModel> staffModels = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(StaffModel.class, "s");
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.createAlias("s.factionModel", "f");
            criteria.createAlias("f.msDepartmentModel", "d");
            criteria.addOrder(Order.asc("d.id"));
            staffModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findUserByIsValid : ", e);
        }

        return staffModels;
    }

    public List<StaffModel> findUserBySearch(int departmentId, int factionId, String keySearch){
        log.debug("departmentId : {}, factionId : {}, keySearch : {}", departmentId, factionId, keySearch);
        List<StaffModel> staffModels = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(StaffModel.class, "s");
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.createAlias("s.factionModel", "f");
            criteria.createAlias("f.msDepartmentModel", "d");

            if (!Utils.isZero(factionId) && !Utils.isZero(departmentId)){
                criteria.add(Restrictions.eq("factionModel.id", factionId));
            }

            if (!Utils.isNull(keySearch.trim()) && keySearch.length() > 0){
                Criterion name = Restrictions.like("name", "%" + keySearch.trim() + "%");
                Criterion userName = Restrictions.like("username", "%"+keySearch.trim()+"%");
                criteria.add(Restrictions.or(name,userName));
            }

            if (!Utils.isZero(departmentId)){
                criteria.add(Restrictions.eq("d.id", departmentId));
            }

            criteria.addOrder(Order.asc("d.id"));
            staffModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findUserByIsValid : ", e);
        }

        return staffModels;
    }

    public void deleteByUpdate(StaffModel model) throws Exception{
        model.setIsValid(0);
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }
}
