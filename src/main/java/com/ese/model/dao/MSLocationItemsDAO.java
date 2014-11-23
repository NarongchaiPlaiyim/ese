package com.ese.model.dao;

import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.LocationItemView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MSLocationItemsDAO extends GenericDAO<MSLocationItemsModel, Integer> {

    public List<LocationItemView> findLocationByItemId(int itemId) throws Exception {



//        Criteria criteria = getCriteria();       getSession().createCriteria(getEntityClass());
//        Criteria criteria = getSession().createCriteria(MSLocationItemsModel.class, "b");
//        criteria.createAlias("b.msLocationModel", "c");
////        criteria.add(Restrictions.eq("msLocationModel.id", 2));
//        criteria.add(Restrictions.eq("c.status", 2));
//        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        List<MSLocationItemsModel> returnInfoList = criteria.list();
//        System.out.println(returnInfoList.size());
//        for (MSLocationItemsModel msLocationItemsModel : returnInfoList){
//            System.out.println("location_items.id "+msLocationItemsModel.getId());
//            System.out.println("--location.id "+msLocationItemsModel.getMsLocationModel().getId());
//            System.out.println("--location.status "+msLocationItemsModel.getMsLocationModel().getStatus());
//        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT ppwms03.dbo.location.id, ppwms03.dbo.warehouse.warehouse_code, ppwms03.dbo.location.location_barcode, ppwms03.dbo.location.capacity, (ppwms03.dbo.location.capacity - ppwms03.dbo.location.qty - ppwms03.dbo.location.reserved_qty) as avaliable");
        stringBuilder.append(" FROM ppwms03.dbo.location_items");
        stringBuilder.append(" LEFT JOIN ppwms03.dbo.location on ppwms03.dbo.location_items.location_id = location.id");
        stringBuilder.append(" LEFT JOIN ppwms03.dbo.warehouse on ppwms03.dbo.location.warehouse_id = warehouse.id");
        stringBuilder.append(" WHERE ppwms03.dbo.location_items.item_id = " + itemId);
        stringBuilder.append(" AND ppwms03.dbo.location.status < 2");
        stringBuilder.append(" AND ppwms03.dbo.location.qty - ppwms03.dbo.location.reserved_qty < ppwms03.dbo.location.capacity");
        stringBuilder.append(" ORDER BY (ppwms03.dbo.location.capacity - ppwms03.dbo.location.qty - ppwms03.dbo.location.reserved_qty), ppwms03.dbo.location.ismix");

        SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
        List<Object[]> objects = q.list();
        List<LocationItemView> locationItemViewList = new ArrayList<LocationItemView>();
        for (Object[] entity : objects) {
            LocationItemView locationItemView = new LocationItemView();
            locationItemView.setId(Utils.parseInt(entity[0], 0));
            locationItemView.setWarehouse(Utils.parseString(entity[1], ""));
            locationItemView.setLocation(Utils.parseString(entity[2], ""));
            locationItemView.setCapacity(Utils.parseInt(entity[3], 0));
            locationItemView.setAvaliable(Utils.parseInt(entity[4], 0));
            locationItemViewList.add(locationItemView);
        }


        return locationItemViewList;
//        return returnInfoList;
    }
}
