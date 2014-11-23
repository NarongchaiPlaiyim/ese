package com.ese.model.dao;

import com.ese.model.db.PalletModel;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
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
            criteria.add(Restrictions.eq("status", 2));
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }

    public List<PalletModel> findChang(int statusId, int warehouse, int conveyorLine, int location, String keyItemDescription){
        log.debug("findUnPrint().");
        try {
            Criteria criteria = getSession().createCriteria(PalletModel.class, "p");

            if (!Utils.isZero(warehouse)){
                criteria.add(Restrictions.eq("msWarehouseModel.id", warehouse));
            }

            if (!Utils.isZero(conveyorLine)){
                criteria.add(Restrictions.eq("msWorkingAreaModel.id", conveyorLine));
            }

            if (!Utils.isZero(location)){
                criteria.add(Restrictions.eq("msLocationModel.id", location));
            }

            if (!Utils.isNull(keyItemDescription) && !"".equalsIgnoreCase(keyItemDescription)){
                criteria.createAlias("p.msItemModel", "c");
                criteria.add(Restrictions.like("c.dSGThaiItemDescription", "%"+keyItemDescription.trim()+"%"));
            }

            if (statusId == 1){
                criteria.add(Restrictions.lt("status", 3));
            } else if (statusId == 2){
                criteria.add(Restrictions.eq("qty", 0));
            }
            criteria.addOrder(Order.desc("updateDate"));
            List<PalletModel> palletModelList = criteria.list();
            log.debug("findOnloadPallet Size : {}", palletModelList.size());
            return palletModelList;
        } catch (Exception e){
            log.debug("Exception : {}", e);
            return new ArrayList<PalletModel>();
        }
    }

    public void updauePalletByChangeLocation(int palletId, int locationId){
        log.debug("updauePalletByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ppwms03.dbo.pallet SET ppwms03.dbo.pallet.location_id = ").append("'").append(locationId).append("'");
        stringBuilder.append(" WHERE ppwms03.dbo.pallet.id = ").append("'").append(palletId).append("'");

        log.debug("SQL Pallet : {}",stringBuilder.toString());
        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateLocationByChangeLocation(int locationId){
        log.debug("updateLocationByChangeLocation().");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ppwms03.dbo.location SET ppwms03.dbo.location.reserved_qty += 1 ");
        stringBuilder.append(" WHERE ppwms03.dbo.location.id = ").append("'").append(locationId).append("'");

        log.debug("SQL Location : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }
}
