package com.ese.model.dao;

import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.LocationItemView;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MSLocationItemsDAO extends GenericDAO<MSLocationItemsModel, Integer> {

    public List<LocationItemView> findLocationByItemId(int itemId) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT ").append(getPrefix()).append(".location.id, ").append(getPrefix()).append(".warehouse.warehouse_code, ").append(getPrefix()).append(".location.location_barcode, ").append(getPrefix()).append(".location.capacity, (").append(getPrefix()).append(".location.capacity - ").append(getPrefix()).append(".location.qty - ").append(getPrefix()).append(".location.reserved_qty) as avaliable");
        stringBuilder.append(" FROM ").append(getPrefix()).append(".location_items");
        stringBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".location on ").append(getPrefix()).append(".location_items.location_id = location.id");
        stringBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".warehouse on ").append(getPrefix()).append(".location.warehouse_id = warehouse.id");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".location_items.item_id = " + itemId);
        stringBuilder.append(" AND ").append(getPrefix()).append(".location.status < 2");
        stringBuilder.append(" AND ").append(getPrefix()).append(".location.qty - ").append(getPrefix()).append(".location.reserved_qty < ").append(getPrefix()).append(".location.capacity");
        stringBuilder.append(" AND ").append(getPrefix()).append(".location.capacity - ").append(getPrefix()).append(".location.qty - ").append(getPrefix()).append(".location.reserved_qty > 0");
        stringBuilder.append(" ORDER BY (").append(getPrefix()).append(".location.capacity - ").append(getPrefix()).append(".location.qty - ").append(getPrefix()).append(".location.reserved_qty), ").append(getPrefix()).append(".location.ismix");

        log.debug("{}", stringBuilder.toString());
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
    }

    public List<MSLocationItemsModel> findByLocationId(int locationId){
        log.debug("findByLocationId(). {}", locationId);
        List<MSLocationItemsModel> msLocationItemsModels = new ArrayList<MSLocationItemsModel>();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("msLocationModel.id", locationId));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.desc("updateDate"));
            msLocationItemsModels = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception Error findByLocationIf : ", e);
        }

        return msLocationItemsModels;
    }

    public void deleteByUpdate(final MSLocationItemsModel model) throws Exception {
        model.setIsValid(0); //0 is flag for delete
        model.setUpdateDate(Utils.currentDate());
        update(model);
    }

}
