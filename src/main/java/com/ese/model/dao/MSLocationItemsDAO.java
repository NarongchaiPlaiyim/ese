package com.ese.model.dao;

import com.ese.model.db.MSLocationItemsModel;
import com.ese.model.db.StaffModel;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class MSLocationItemsDAO extends GenericDAO<MSLocationItemsModel, Integer> {

    public List<MSLocationItemsModel> eeeeeeee() throws Exception {



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


        SQLQuery q = getSession().createSQLQuery("SELECT * FROM ppwms03.dbo.staff");
        q.addEntity(StaffModel.class);
        List<StaffModel> entities = q.list();
        for (StaffModel entity : entities) {
            System.out.println(entity);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" select * from ppwms03.dbo.location_items");
        stringBuilder.append(" left join ppwms03.dbo.location on ppwms03.dbo.location_items.location_id = ppwms03.dbo.location.id");
        stringBuilder.append(" left join ppwms03.dbo.pallet on ppwms03.dbo.location_items.item_id = ppwms03.dbo.pallet.item_id");
        stringBuilder.append(" LEFT JOIN ppwms03.dbo.warehouse on ppwms03.dbo.pallet.warehouse_id = warehouse.id");
        stringBuilder.append(" where ppwms03.dbo.location.status < 2 and ppwms03.dbo.location.qty + ppwms03.dbo.location.reserved_qty < ppwms03.dbo.location.capacity");
        stringBuilder.append(" order by (ppwms03.dbo.location.capacity - ppwms03.dbo.location.qty - ppwms03.dbo.location.reserved_qty), ppwms03.dbo.location.ismix");

        q = getSession().createSQLQuery(stringBuilder.toString());
        List<Object[]> objects = q.list();
        for (Object[] entity : objects) {
            for (Object entityCol : entity) {
                System.out.print(" " + entityCol);
            }
            System.out.println("");
        }


        return Collections.EMPTY_LIST;
//        return returnInfoList;
    }
}
