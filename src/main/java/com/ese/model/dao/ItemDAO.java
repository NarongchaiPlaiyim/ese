package com.ese.model.dao;

import com.ese.model.db.MSItemModel;
import com.ese.model.view.ItemQtySearchView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAO extends GenericDAO<MSItemModel, Integer>{

    public List<MSItemModel> findByLike(String filed, String text) throws Exception {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.like(filed, "%"+text+"%"));
        return Utils.safetyList(criteria.list());
    }

    public List<MSItemModel> findBySearch(ItemQtySearchView itemQtySearchView){
        List<MSItemModel> itemModels = new ArrayList<MSItemModel>();
        log.debug("-------itemQtySearchView : {}", itemQtySearchView.toString());
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isZero(itemQtySearchView.getItemName().trim().length())){
                criteria.add(Restrictions.eq("itemName", itemQtySearchView.getItemName().trim()));
            }

            if (!Utils.isZero(itemQtySearchView.getItemCode().trim().length())){
                criteria.add(Restrictions.eq("itemId", itemQtySearchView.getItemCode().trim()));
            }

            if (!Utils.isZero(itemQtySearchView.getDescription().trim().length())){
                criteria.add(Restrictions.eq("dSGThaiItemDescription", itemQtySearchView.getDescription().trim()));
            }
            itemModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }
        return itemModels;
    }

    public MSItemModel findByItemId(String itemId){
        MSItemModel msItemModel = new MSItemModel();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("itemId", itemId));

            msItemModel = (MSItemModel) criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception error findBySearch : ", e);
        }

        return msItemModel;
    }
}
