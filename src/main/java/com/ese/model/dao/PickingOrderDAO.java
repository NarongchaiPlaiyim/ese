package com.ese.model.dao;

import com.ese.model.TableValue;
import com.ese.model.db.PickingOrderModel;
import com.ese.model.view.PickingOrderView;
import com.ese.model.view.StatusPickingValue;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PickingOrderDAO extends GenericDAO<PickingOrderModel, Integer> {

    public List<PickingOrderModel> findByOverSeaOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.status", "st");
            criteria.add(Restrictions.lt("st.statusSeq", StatusPickingValue.PICKING.getId()));
            criteria.add(Restrictions.ne("st.statusSeq", StatusPickingValue.CANCEL.getId()));
            criteria.add(Restrictions.like("docNo", "PKO%"));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {

            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.status", "st");
            criteria.add(Restrictions.lt("st.statusSeq", StatusPickingValue.PICKING.getId()));
            criteria.add(Restrictions.ne("st.statusSeq", StatusPickingValue.CANCEL.getId()));
            criteria.add(Restrictions.like("docNo", "PKD%"));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByOverSeaAndDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.status", "st");
            criteria.add(Restrictions.lt("st.statusSeq", StatusPickingValue.PICKING.getId()));
            criteria.add(Restrictions.ne("st.statusSeq", StatusPickingValue.CANCEL.getId()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByPickingView(PickingOrderView pickingOrderView){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.customerCode", "cc");

            if (!Utils.isNull(pickingOrderView)){
                if (!Utils.isNull(pickingOrderView.getConfirmId()) && !Utils.isZero(pickingOrderView.getConfirmId().length())){
                    log.debug("confirmId {}", pickingOrderView.getConfirmId());
                    criteria.add(Restrictions.like("confirmId", "%" + pickingOrderView.getConfirmId().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getPurchaseOrder()) && !Utils.isZero(pickingOrderView.getPurchaseOrder().length())){
                    log.debug("purchaseOrder {}", pickingOrderView.getPurchaseOrder());
                    criteria.add(Restrictions.like("purchaseOrder", "%" + pickingOrderView.getPurchaseOrder() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getRequestShipDate()) && !Utils.isZero(pickingOrderView.getRequestShipDate().length())){
                    log.debug("requestShiftDate {}", pickingOrderView.getRequestShipDate());
                    criteria.add(Restrictions.eq("requestShiftDate", Utils.convertStringToDate(pickingOrderView.getRequestShipDate())));
                }

                criteria.createAlias("po.status", "st");
                log.debug("----------------------- : {}", pickingOrderView.getStatus());
                if (pickingOrderView.getStatus() < 10){
                    criteria.add(Restrictions.like("st.statusSeq", pickingOrderView.getStatus()));
                }

                if (!Utils.isNull(pickingOrderView.getConfirmDate()) && !Utils.isZero(pickingOrderView.getConfirmDate().length())){
                    log.debug("confirmDate {}", Utils.convertStringToDate(pickingOrderView.getConfirmDate()));
                    criteria.add(Restrictions.eq("confirmDate", Utils.convertStringToDate(pickingOrderView.getConfirmDate())));
                }

                if (!Utils.isNull(pickingOrderView.getSaleOrder()) && !Utils.isZero(pickingOrderView.getSaleOrder().length())){
                    log.debug("salesOrder {}", pickingOrderView.getSaleOrder());
                    criteria.add(Restrictions.like("salesOrder", "%" + pickingOrderView.getSaleOrder() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getEddDate()) && !Utils.isZero(pickingOrderView.getEddDate().length())){
                    log.debug("eddDate {}", pickingOrderView.getEddDate());
                    criteria.add(Restrictions.eq("eddDate", Utils.convertStringToDate(pickingOrderView.getEddDate())));
                }



                if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1 && Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
//                    Criterion overseaOrder = Restrictions.like("docNo", "o%");
                    criteria.add(Restrictions.or(Restrictions.like("docNo", "PKO%"), Restrictions.like("docNo", "PKD%")));
                } else {
                    if (Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
                        log.debug("isOverseaOrder {}", pickingOrderView.isOverseaOrder());
                        criteria.add(Restrictions.like("docNo", "PKO%"));
                    }

                    if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1){
                        log.debug("isDomesticOrder {}", pickingOrderView.isDomesticOrder());
                        criteria.add(Restrictions.like("docNo", "PKD%"));
                    }
                }

                if (!Utils.isNull(pickingOrderView.getCustomerCode()) && !Utils.isZero(pickingOrderView.getCustomerCode().length())){
                    log.debug("customerCode {}", pickingOrderView.getCustomerCode());
                    criteria.add(Restrictions.like("cc.accountNum", "%" + pickingOrderView.getCustomerCode().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryName()) && !Utils.isZero(pickingOrderView.getDeliveryName().length())){
                    log.debug("deliveryName {}", pickingOrderView.getDeliveryName());
                    criteria.add(Restrictions.like("deliveryName", "%" + pickingOrderView.getDeliveryName().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getAvailableDate()) && !Utils.isZero(pickingOrderView.getAvailableDate().length())){
                    log.debug("avalibleDate {}", Utils.convertStringToDate(pickingOrderView.getAvailableDate()));
                    criteria.add(Restrictions.eq("avalibleDate", Utils.convertStringToDate(pickingOrderView.getAvailableDate())));
                }

                if (!Utils.isNull(pickingOrderView.getCustomerName()) && !Utils.isZero(pickingOrderView.getCustomerName().length())){
                    log.debug("customerName {}", pickingOrderView.getCustomerName());
                    criteria.add(Restrictions.like("cc.name", "%" + pickingOrderView.getCustomerName() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryAddress()) && !Utils.isZero(pickingOrderView.getDeliveryAddress().length())){
                    log.debug("deliveryAddress {}", pickingOrderView.getDeliveryAddress());
                    criteria.add(Restrictions.like("deliveryAddress", "%" + pickingOrderView.getDeliveryAddress() + "%"));
                }
            }

            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public PickingOrderModel findByCustomerCode(String customerCode){
        PickingOrderModel model = new PickingOrderModel();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("customerCode.accountNum", customerCode));

            model = (PickingOrderModel) criteria.list().iterator().next();

            log.debug("PickingOrderModel : {}", model);
        } catch (Exception e) {
            log.debug("Exception error  findByCustomerCode : ", e);
        }

        return model;
    }

    public List<PickingOrderModel> findByCustomerCode2(String customerCode){
        List<PickingOrderModel> model = new ArrayList<>();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("customerCode.accountNum", customerCode));

            model = criteria.list();

            log.debug("PickingOrderModel : {}", model);
        } catch (Exception e) {
            log.debug("Exception error  findByCustomerCode : ", e);
        }

        return model;
    }

    public void updateStatus(int pickingId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order SET ").append(getPrefix()).append(".picking_order.status = 2 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = ").append("'").append(pickingId).append("'");

        log.debug("SQL updateStatus : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public void updateStatus(int pickingId, int status){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order SET ").append(getPrefix()).append(".picking_order.status =  ").append(status);
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = ").append("'").append(pickingId).append("'");

        log.debug("SQL updateToWrap : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public void updateStatus(int pickingId, int status, int loadingOrderId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order SET ").append(getPrefix()).append(".picking_order.status =  ").append(status);

        if (!Utils.isZero(loadingOrderId)){
            stringBuilder.append(", ").append(getPrefix()).append(".picking_order.loading_order_id = ").append(loadingOrderId);
        }

        stringBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = ").append("'").append(pickingId).append("'");



        log.debug("SQL updateStatus : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateStatus: ", e);
        }
    }

    public void updateStatus(String axPickingId, int status, int loadingOrderId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".picking_order SET ").append(getPrefix()).append(".picking_order.status =  ").append(status);

        if (!Utils.isZero(loadingOrderId)){
            stringBuilder.append(", ").append(getPrefix()).append(".picking_order.loading_order_id = ").append(loadingOrderId);
        }

        if (!Utils.isNull(axPickingId) && !Utils.isZero(axPickingId.trim())){
            stringBuilder.append("WHERE ").append(getPrefix()).append(".picking_order.docno = '").append(axPickingId).append("'");
        }



        log.debug("SQL updateStatus : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateStatus: ", e);
        }
    }

    public void updateAxPickingListStatus(String axPickingListId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_inventpickinglisttrans SET ").append(getPrefix()).append(".ax_inventpickinglisttrans.sync_status =  ").append(1);
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_inventpickinglisttrans.PickingListId = ").append("'").append(axPickingListId).append("'");

        log.debug("SQL updateAxPickingListStatus : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }

    public List<PickingOrderModel> findByLoadingOrder(int loadingOrderId){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {

            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("loadingOrderModel.id", loadingOrderId));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByStatusPostPick(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {

            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.status", "st");
            criteria.createAlias("st.tableId", "table");
            criteria.add(Restrictions.eq("table.id", TableValue.PICKING_ORDER.getId()));
            criteria.add(Restrictions.eq("st.statusSeq", StatusPickingValue.POST.getId()));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findBySearchStatusPost(PickingOrderView pickingOrderView){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getSession().createCriteria(PickingOrderModel.class, "po");
            criteria.createAlias("po.customerCode", "cc");

            if (!Utils.isNull(pickingOrderView)){
                if (!Utils.isNull(pickingOrderView.getConfirmId()) && !Utils.isZero(pickingOrderView.getConfirmId().length())){
                    log.debug("confirmId {}", pickingOrderView.getConfirmId());
                    criteria.add(Restrictions.like("confirmId", "%" + pickingOrderView.getConfirmId().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getPurchaseOrder()) && !Utils.isZero(pickingOrderView.getPurchaseOrder().length())){
                    log.debug("purchaseOrder {}", pickingOrderView.getPurchaseOrder());
                    criteria.add(Restrictions.like("purchaseOrder", "%" + pickingOrderView.getPurchaseOrder() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getRequestShipDate()) && !Utils.isZero(pickingOrderView.getRequestShipDate().length())){
                    log.debug("requestShiftDate {}", pickingOrderView.getRequestShipDate());
                    criteria.add(Restrictions.eq("requestShiftDate", Utils.convertStringToDate(pickingOrderView.getRequestShipDate())));
                }

                criteria.createAlias("po.status", "st");
                criteria.add(Restrictions.like("st.statusSeq", StatusPickingValue.POST.getId()));


                if (!Utils.isNull(pickingOrderView.getConfirmDate()) && !Utils.isZero(pickingOrderView.getConfirmDate().length())){
                    log.debug("confirmDate {}", Utils.convertStringToDate(pickingOrderView.getConfirmDate()));
                    criteria.add(Restrictions.eq("confirmDate", Utils.convertStringToDate(pickingOrderView.getConfirmDate())));
                }

                if (!Utils.isNull(pickingOrderView.getSaleOrder()) && !Utils.isZero(pickingOrderView.getSaleOrder().length())){
                    log.debug("salesOrder {}", pickingOrderView.getSaleOrder());
                    criteria.add(Restrictions.like("salesOrder", "%" + pickingOrderView.getSaleOrder() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getEddDate()) && !Utils.isZero(pickingOrderView.getEddDate().length())){
                    log.debug("eddDate {}", pickingOrderView.getEddDate());
                    criteria.add(Restrictions.eq("eddDate", Utils.convertStringToDate(pickingOrderView.getEddDate())));
                }



//                if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1 && Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
////                    Criterion overseaOrder = Restrictions.like("docNo", "o%");
//                    criteria.add(Restrictions.or(Restrictions.like("docNo", "PKO%"), Restrictions.like("docNo", "PKD%")));
//                } else {
//                    if (Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
//                        log.debug("isOverseaOrder {}", pickingOrderView.isOverseaOrder());
//                        criteria.add(Restrictions.like("docNo", "PKO%"));
//                    }
//
//                    if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1){
//                        log.debug("isDomesticOrder {}", pickingOrderView.isDomesticOrder());
//                        criteria.add(Restrictions.like("docNo", "PKD%"));
//                    }
//                }

                if (!Utils.isNull(pickingOrderView.getCustomerCode()) && !Utils.isZero(pickingOrderView.getCustomerCode().length())){
                    log.debug("customerCode {}", pickingOrderView.getCustomerCode());
                    criteria.add(Restrictions.like("cc.accountNum", "%" + pickingOrderView.getCustomerCode().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryName()) && !Utils.isZero(pickingOrderView.getDeliveryName().length())){
                    log.debug("deliveryName {}", pickingOrderView.getDeliveryName());
                    criteria.add(Restrictions.like("deliveryName", "%" + pickingOrderView.getDeliveryName().trim() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getAvailableDate()) && !Utils.isZero(pickingOrderView.getAvailableDate().length())){
                    log.debug("avalibleDate {}", Utils.convertStringToDate(pickingOrderView.getAvailableDate()));
                    criteria.add(Restrictions.eq("avalibleDate", Utils.convertStringToDate(pickingOrderView.getAvailableDate())));
                }

                if (!Utils.isNull(pickingOrderView.getCustomerName()) && !Utils.isZero(pickingOrderView.getCustomerName().length())){
                    log.debug("customerName {}", pickingOrderView.getCustomerName());
                    criteria.add(Restrictions.like("cc.name", "%" + pickingOrderView.getCustomerName() + "%"));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryAddress()) && !Utils.isZero(pickingOrderView.getDeliveryAddress().length())){
                    log.debug("deliveryAddress {}", pickingOrderView.getDeliveryAddress());
                    criteria.add(Restrictions.like("deliveryAddress", "%" + pickingOrderView.getDeliveryAddress() + "%"));
                }
            }

            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }
}
