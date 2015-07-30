package com.ese.model.dao;

import com.ese.model.db.InvOnHandModel;
import com.ese.model.view.InvOnhandPostView;
import com.ese.model.view.SearchItemView;
import com.ese.model.view.ShowSNView;
import com.ese.model.view.report.SubPickingOrderWithBarcodeViewReport;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvOnHandDAO extends GenericDAO<InvOnHandModel, Integer>{

    public List<InvOnHandModel> findByPickingId(int pickingId){
        List<InvOnHandModel> invOnHandModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("pickingOrderModel.id", pickingId));
            invOnHandModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByPickingId : ", e);
        }

        return invOnHandModels;
    }

    public List<InvOnHandModel> findByPalletId(int palletId){
        List<InvOnHandModel> invOnHandModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("palletModel.id", palletId));
            invOnHandModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByPickingId : ", e);
        }

        return invOnHandModels;
    }

    public List<InvOnHandModel> findByLikeSnBarcode(String snBarcode) throws Exception {
        List<InvOnHandModel> invOnHandModels = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("snBarcode","%"+snBarcode+"%"));
            invOnHandModels = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByLikeSnBarcode : ", e);
        }

        return invOnHandModels;
    }

    public List<SubPickingOrderWithBarcodeViewReport> findByPickingIdOnReport(int pickingId){
        List<SubPickingOrderWithBarcodeViewReport> subPickingOrderWithBarcodeViewReportArrayList = new ArrayList<SubPickingOrderWithBarcodeViewReport>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.id AS PALLET_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_code AS WAREHOUSE_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.ItemId AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS ITEM_DESC,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.create_date AS PALLET_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_barcode AS LOCATION_BARCODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.capacity AS CAPACITY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.qty AS QTY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.combine AS COMBINE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.foil AS FOIL,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.set_to_transfer AS TO_TRANSFER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.id AS ITEM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATCHNO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.salesunit AS UNIT");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".stock_inout_line");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".stock_inout_line.pallet_id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.id = ").append(getPrefix()).append(".inv_onhand.pallet_id, ");
        sqlBuilder.append(getPrefix()).append(".ax_Unit ");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".ax_Unit.UnitId = ").append(getPrefix()).append(".picking_order_line.salesunit");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".pallet.isvalid = 1 ");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".picking_order_line.picking_order_id = ").append(pickingId);
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".pallet.id, ").append(getPrefix()).append(".warehouse.warehouse_code, ");
        sqlBuilder.append(getPrefix()).append(".item_master.ItemId, ").append(getPrefix()).append(".item_master.DSGThaiItemDescription, ");
        sqlBuilder.append(getPrefix()).append(".pallet.pallet_barcode, ").append(getPrefix()).append(".pallet.create_date, ");
        sqlBuilder.append(getPrefix()).append(".location.location_barcode, ").append(getPrefix()).append(".pallet.capacity, ");
        sqlBuilder.append(getPrefix()).append(".pallet.qty, ").append(getPrefix()).append(".pallet.combine, ");
        sqlBuilder.append(getPrefix()).append(".pallet.foil, ").append(getPrefix()).append(".pallet.set_to_transfer, ");
        sqlBuilder.append(getPrefix()).append(".item_master.id, ").append(getPrefix()).append(".inv_onhand.batchno, ");
        sqlBuilder.append(getPrefix()).append(".picking_order_line.salesunit");

        log.debug("findByPickingIdOnReport : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("PALLET_ID", IntegerType.INSTANCE)
                    .addScalar("WAREHOUSE_CODE", StringType.INSTANCE)
                    .addScalar("ITEM_ID", StringType.INSTANCE)
                    .addScalar("ITEM_DESC", StringType.INSTANCE)
                    .addScalar("PALLET_BARCODE", StringType.INSTANCE)
                    .addScalar("PALLET_DATE", DateType.INSTANCE)
                    .addScalar("LOCATION_BARCODE", StringType.INSTANCE)
                    .addScalar("CAPACITY", BigDecimalType.INSTANCE)
                    .addScalar("QTY", IntegerType.INSTANCE)
                    .addScalar("COMBINE", IntegerType.INSTANCE)
                    .addScalar("FOIL", IntegerType.INSTANCE)
                    .addScalar("TO_TRANSFER", IntegerType.INSTANCE)
                    .addScalar("ITEM", IntegerType.INSTANCE)
                    .addScalar("BATCHNO", StringType.INSTANCE)
                    .addScalar("UNIT", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                SubPickingOrderWithBarcodeViewReport subPickingOrderWithBarcodeViewReport = new SubPickingOrderWithBarcodeViewReport();
                subPickingOrderWithBarcodeViewReport.setPalletId(Utils.parseInt(entity[0]));
                subPickingOrderWithBarcodeViewReport.setWarehoseCode(Utils.parseString(entity[1]));
                subPickingOrderWithBarcodeViewReport.setItemId(Utils.parseString(entity[2]));
                subPickingOrderWithBarcodeViewReport.setItemDesc(Utils.parseString(entity[3]));
                subPickingOrderWithBarcodeViewReport.setPalletBarcode(Utils.parseString(entity[4]));
                subPickingOrderWithBarcodeViewReport.setPalletDate(Utils.parseDate(entity[5], null));
                subPickingOrderWithBarcodeViewReport.setLocationBarcode(Utils.parseString(entity[6]));
                subPickingOrderWithBarcodeViewReport.setCapacity(Utils.parseBigDecimal(entity[7]));
                subPickingOrderWithBarcodeViewReport.setQty(Utils.parseInt(entity[8]));
                subPickingOrderWithBarcodeViewReport.setCombine(Utils.parseInt(entity[9]));
                subPickingOrderWithBarcodeViewReport.setFoil(Utils.parseInt(entity[10]));
                subPickingOrderWithBarcodeViewReport.setToTransfer(Utils.parseInt(entity[11]));
                subPickingOrderWithBarcodeViewReport.setItem(Utils.parseInt(entity[12]));
                subPickingOrderWithBarcodeViewReport.setBatchNo(Utils.parseString(entity[13]));
                subPickingOrderWithBarcodeViewReport.setUnit(Utils.parseString(entity[14]));
                subPickingOrderWithBarcodeViewReportArrayList.add(subPickingOrderWithBarcodeViewReport);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByStockInOutLineId : {}", e);
        }

        return subPickingOrderWithBarcodeViewReportArrayList;
    }


    public List<InvOnhandPostView> findCountInvOnhand(int pickingOrderId){
        List<InvOnhandPostView> invOnhandPostViewList = new ArrayList<InvOnhandPostView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" COUNT(").append(getPrefix()).append(".inv_onhand.id) AS COUNT_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.item_id AS ITEM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.picking_order_id AS PICKING_ID");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.picking_order_id = " ).append(pickingOrderId);
        sqlBuilder.append(" GROUP BY ").append(getPrefix()).append(".inv_onhand.item_id, ").append(getPrefix()).append(".inv_onhand.picking_order_id ");
        sqlBuilder.append(" ORDER BY ").append(getPrefix()).append(".inv_onhand.item_id ASC");

        log.debug("findCountInvOnhand : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("COUNT_ID", IntegerType.INSTANCE)
                    .addScalar("ITEM_ID", IntegerType.INSTANCE)
                    .addScalar("PICKING_ID", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                InvOnhandPostView invOnhandPostView = new InvOnhandPostView();
                invOnhandPostView.setCountId(Utils.parseInt(entity[0]));
                invOnhandPostView.setItemId(Utils.parseInt(entity[1]));
                invOnhandPostView.setPickingId(Utils.parseInt(entity[2]));
                invOnhandPostViewList.add(invOnhandPostView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findCountInvOnhand : {}", e);
        }

        return invOnhandPostViewList;
    }

    public List<ShowSNView> findByStockInOutLineId(int stockInOutLineId){
        List<ShowSNView> showSNViewList = new ArrayList<ShowSNView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATCH,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_name AS WAREHOUSE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_name AS LOCATION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.sn_barcode AS SN");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".inv_onhand.pallet_id = ").append(getPrefix()).append(".pallet.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.stock_inout_line_id = " ).append(stockInOutLineId);

        log.debug("findByStockInOutLineId : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("BATCH", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("PALLET", StringType.INSTANCE)
                    .addScalar("SN", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ShowSNView showSNView = new ShowSNView();
                showSNView.setId(Utils.parseInt(entity[0]));
                showSNView.setBatch(Utils.parseString(entity[1]));
                showSNView.setWarehouse(Utils.parseString(entity[2]));
                showSNView.setLocation(Utils.parseString(entity[3]));
                showSNView.setPallet(Utils.parseString(entity[4]));
                showSNViewList.add(showSNView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findByStockInOutLineId : {}", e);
        }

        return showSNViewList;
    }

    public List<ShowSNView> findBySearch(SearchItemView searchItemView){
        log.debug("---------- {}", searchItemView);
        List<ShowSNView> showSNViewList = new ArrayList<ShowSNView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.id AS ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.batchno AS BATCH,");
        sqlBuilder.append(" ").append(getPrefix()).append(".warehouse.warehouse_name AS WAREHOUSE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".location.location_name AS LOCATION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".pallet.pallet_barcode AS PALLET,");
        sqlBuilder.append(" ").append(getPrefix()).append(".inv_onhand.sn_barcode AS SN");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".inv_onhand");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".pallet");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".inv_onhand.pallet_id = ").append(getPrefix()).append(".pallet.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".warehouse");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.warehouse_id = ").append(getPrefix()).append(".warehouse.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".location");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.location_id = ").append(getPrefix()).append(".location.id");
        sqlBuilder.append(" INNER JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".pallet.item_id = ").append(getPrefix()).append(".item_master.id");
//        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.stock_inout_line_id is null" );

        if (!Utils.isNull(searchItemView)){
            if (!Utils.isNull(searchItemView.getItemCode()) && !Utils.isZero(searchItemView.getItemCode().trim().length())){
                sqlBuilder.append(" WHERE ").append(getPrefix()).append(".item_master.itemId like '%" ).append(searchItemView.getItemCode().trim()).append("%'");

                if (!Utils.isNull(searchItemView.getItemDesc()) && !Utils.isZero(searchItemView.getItemDesc().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".item_master.DSGThaiItemDescription like '%" ).append(searchItemView.getItemDesc().trim()).append("%'");
                }

                if (!Utils.isNull(searchItemView.getBatchNo()) && !Utils.isZero(searchItemView.getBatchNo().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".inv_onhand.batchno like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");
                }

                if (!Utils.isNull(searchItemView.getSN()) && !Utils.isZero(searchItemView.getSN().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".inv_onhand.sn_barcode like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");
                }

                if (!Utils.isZero(searchItemView.getWarehouseId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".warehouse.id = " ).append(searchItemView.getWarehouseId());
                }

                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            } else  if (!Utils.isNull(searchItemView.getItemDesc()) && !Utils.isZero(searchItemView.getItemDesc().trim().length())){
                sqlBuilder.append(" WHERE ").append(getPrefix()).append(".item_master.DSGThaiItemDescription like '%" ).append(searchItemView.getItemDesc().trim()).append("%'");

                if (!Utils.isNull(searchItemView.getBatchNo()) && !Utils.isZero(searchItemView.getBatchNo().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".inv_onhand.batchno like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");
                }

                if (!Utils.isNull(searchItemView.getSN()) && !Utils.isZero(searchItemView.getSN().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".inv_onhand.sn_barcode like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");
                }

                if (!Utils.isZero(searchItemView.getWarehouseId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".warehouse.id = " ).append(searchItemView.getWarehouseId());
                }

                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            } else  if (!Utils.isNull(searchItemView.getBatchNo()) && !Utils.isZero(searchItemView.getBatchNo().trim().length())){
                sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.batchno like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");

                if (!Utils.isNull(searchItemView.getSN()) && !Utils.isZero(searchItemView.getSN().trim().length())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".inv_onhand.sn_barcode like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");
                }

                if (!Utils.isZero(searchItemView.getWarehouseId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".warehouse.id = " ).append(searchItemView.getWarehouseId());
                }

                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            } else if (!Utils.isNull(searchItemView.getSN()) && !Utils.isZero(searchItemView.getSN().trim().length())){
                sqlBuilder.append(" WHERE ").append(getPrefix()).append(".inv_onhand.sn_barcode like '%" ).append(searchItemView.getBatchNo().trim()).append("%'");

                if (!Utils.isZero(searchItemView.getWarehouseId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".warehouse.id = " ).append(searchItemView.getWarehouseId());
                }

                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            } else if (!Utils.isZero(searchItemView.getWarehouseId())){
                sqlBuilder.append(" WHERE ").append(getPrefix()).append(".warehouse.id = " ).append(searchItemView.getWarehouseId());

                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" AND ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            } else {
                if (!Utils.isZero(searchItemView.getLocationId())){
                    sqlBuilder.append(" WHERE ").append(getPrefix()).append(".location.id = " ).append(searchItemView.getLocationId());
                }
            }


        }

        log.debug("findBySearch : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ID", IntegerType.INSTANCE)
                    .addScalar("BATCH", StringType.INSTANCE)
                    .addScalar("WAREHOUSE", StringType.INSTANCE)
                    .addScalar("LOCATION", StringType.INSTANCE)
                    .addScalar("PALLET", StringType.INSTANCE)
                    .addScalar("SN", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ShowSNView showSNView = new ShowSNView();
                showSNView.setId(Utils.parseInt(entity[0]));
                showSNView.setBatch(Utils.parseString(entity[1]));
                showSNView.setWarehouse(Utils.parseString(entity[2]));
                showSNView.setLocation(Utils.parseString(entity[3]));
                showSNView.setPallet(Utils.parseString(entity[4]));
                showSNView.setSN(Utils.parseString(entity[5]));
                showSNViewList.add(showSNView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL findBySearch : {}", e);
        }

        return showSNViewList;
    }
}
