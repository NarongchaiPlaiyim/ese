package com.ese.model.dao;

import com.ese.model.db.PalletModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PalletDAO extends GenericDAO<PalletModel, Integer>{

    public List<PalletModel> findPalletTable(){
        log.debug("findOnloadPallet().");
        try {
            Criteria criteria = getCriteria();
//            criteria.setFetchMode("locationId", FetchMode.EAGER);
//            criteria.setFetchMode("wherehouseId", FetchMode.EAGER);
//            criteria.setFetchMode("itemId", FetchMode.EAGER);
//            criteria.setFetchMode("conveyorLine", FetchMode.EAGER);
            criteria.add(Restrictions.eq("status", 2));
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }

    public List<PalletModel> findChang(int statusId, int warehouse, int conveyorLine){
        log.debug("findUnPrint().");
        try {
            Criteria criteria = getCriteria();
//            criteria.setFetchMode("locationId", FetchMode.EAGER);
//            criteria.setFetchMode("wherehouseId", FetchMode.EAGER);
//            criteria.setFetchMode("itemId", FetchMode.EAGER);
//            criteria.setFetchMode("conveyorLine", FetchMode.EAGER);

            if (!Utils.isZero(warehouse)){
                criteria.add(Restrictions.eq("wherehouseId.id", warehouse));
            }

            if (!Utils.isZero(conveyorLine)){
                criteria.add(Restrictions.eq("conveyorLine.id", conveyorLine));
            }

            if (statusId == 1){
                criteria.add(Restrictions.lt("status", 3));
            } else if (statusId == 2){
                criteria.add(Restrictions.eq("qty", 0));
            }
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }
}
