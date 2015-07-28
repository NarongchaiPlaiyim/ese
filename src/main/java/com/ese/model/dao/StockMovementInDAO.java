package com.ese.model.dao;

import com.ese.model.db.StockMovementInModel;
import com.ese.model.view.StockMovementInView;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementInDAO extends GenericDAO<StockMovementInModel, Integer>{

    public List<StockMovementInView> findstockMovementOutByStockInOutId(int stockInOutId){
        log.debug("findstockMovementOutByStockInOutId(). {}", stockInOutId);
        List<StockMovementInView> stockMovementInViewViews = new ArrayList<StockMovementInView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.itemid AS ITEM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.sn_barcode AS BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".stock_movement_in.status AS STATUS ");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".stock_movement_in");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".stock_movement_in.sn_barcode = ").append(getPrefix()).append(".inv_onhand.sn_barcode");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".item_master.id = ").append(getPrefix()).append(".inv_onhand.item_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".stock_movement_in.stock_inout_id = ").append(stockInOutId);

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("ITEM", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("BARCODE", StringType.INSTANCE)
                    .addScalar("STATUS", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            int i = 1;

            for (Object[] entity : objects) {
                StockMovementInView stockMovementInView = new StockMovementInView();
                stockMovementInView.setId(Utils.parseInt(entity[0]));
                stockMovementInView.setItemId(Utils.parseString(entity[1]));
                stockMovementInView.setItemDesc(Utils.parseString(entity[2]));
                stockMovementInView.setBarcode(Utils.parseString(entity[3]));
                stockMovementInView.setStatus(Utils.parseInt(entity[4]));
                stockMovementInViewViews.add(stockMovementInView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return stockMovementInViewViews;
    }
}
