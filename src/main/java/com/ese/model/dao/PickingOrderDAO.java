package com.ese.model.dao;

import com.ese.model.db.PickingOrderModel;
import com.ese.model.view.PickingOrderView;
import com.ese.model.view.StatusPickingValue;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PickingOrderDAO extends GenericDAO<PickingOrderModel, Integer> {

    public List<PickingOrderModel> findByOverSeaOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "PKO%"));
            criteria.add(Restrictions.lt("status.id", StatusPickingValue.POST.getId()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.like("docNo", "PKD%"));
            criteria.add(Restrictions.lt("status.id", StatusPickingValue.POST.getId()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public List<PickingOrderModel> findByOverSeaAndDomesticOrder(){
        List<PickingOrderModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.lt("status.id", StatusPickingValue.POST.getId()));
            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
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
                    criteria.add(Restrictions.eq("confirmId", pickingOrderView.getConfirmId().trim()));
                }

                if (!Utils.isNull(pickingOrderView.getPurchaseOrder()) && !Utils.isZero(pickingOrderView.getPurchaseOrder().length())){
                    log.debug("purchaseOrder {}", pickingOrderView.getPurchaseOrder());
                    criteria.add(Restrictions.eq("purchaseOrder", pickingOrderView.getPurchaseOrder()));
                }

                if (!Utils.isNull(pickingOrderView.getRequestShipDate()) && !Utils.isZero(pickingOrderView.getRequestShipDate().length())){
                    log.debug("requestShiftDate {}", pickingOrderView.getRequestShipDate());
                    criteria.add(Restrictions.eq("requestShiftDate", Utils.convertStringToDate(pickingOrderView.getRequestShipDate())));
                }

                log.debug("status {}", pickingOrderView.getStatus());

                if (!Utils.isZero(pickingOrderView.getStatus())){
                    criteria.add(Restrictions.eq("status.id", pickingOrderView.getStatus()));
                }

                if (!Utils.isNull(pickingOrderView.getConfirmDate()) && !Utils.isZero(pickingOrderView.getConfirmDate().length())){
                    log.debug("confirmDate {}", Utils.convertStringToDate(pickingOrderView.getConfirmDate()));
                    criteria.add(Restrictions.eq("confirmDate", Utils.convertStringToDate(pickingOrderView.getConfirmDate())));
                }

                if (!Utils.isNull(pickingOrderView.getSaleOrder()) && !Utils.isZero(pickingOrderView.getSaleOrder().length())){
                    log.debug("salesOrder {}", pickingOrderView.getSaleOrder());
                    criteria.add(Restrictions.eq("salesOrder", pickingOrderView.getSaleOrder()));
                }

                if (!Utils.isNull(pickingOrderView.getEddDate()) && !Utils.isZero(pickingOrderView.getEddDate().length())){
                    log.debug("eddDate {}", pickingOrderView.getEddDate());
                    criteria.add(Restrictions.eq("eddDate", Utils.convertStringToDate(pickingOrderView.getEddDate())));
                }



                if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1 && Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
//                    Criterion overseaOrder = Restrictions.like("docNo", "o%");
                    criteria.add(Restrictions.or(Restrictions.like("docNo", "o%"), Restrictions.like("docNo", "d%")));
                } else {
                    if (Utils.isTrue(pickingOrderView.isOverseaOrder()) == 1){
                        log.debug("isOverseaOrder {}", pickingOrderView.isOverseaOrder());
                        criteria.add(Restrictions.like("docNo", "o%"));
                    }

                    if (Utils.isTrue(pickingOrderView.isDomesticOrder()) == 1){
                        log.debug("isDomesticOrder {}", pickingOrderView.isDomesticOrder());
                        criteria.add(Restrictions.like("docNo", "d%"));
                    }
                }

                if (!Utils.isNull(pickingOrderView.getCustomerCode()) && !Utils.isZero(pickingOrderView.getCustomerCode().length())){
                    log.debug("customerCode {}", pickingOrderView.getCustomerCode());
                    criteria.add(Restrictions.eq("cc.accountNum", pickingOrderView.getCustomerCode().trim()));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryName()) && !Utils.isZero(pickingOrderView.getDeliveryName().length())){
                    log.debug("deliveryName {}", pickingOrderView.getDeliveryName());
                    criteria.add(Restrictions.eq("deliveryName", pickingOrderView.getDeliveryName().trim()));
                }

                if (!Utils.isNull(pickingOrderView.getAvailableDate()) && !Utils.isZero(pickingOrderView.getAvailableDate().length())){
                    log.debug("avalibleDate {}", Utils.convertStringToDate(pickingOrderView.getAvailableDate()));
                    criteria.add(Restrictions.eq("avalibleDate", Utils.convertStringToDate(pickingOrderView.getAvailableDate())));
                }

                if (!Utils.isNull(pickingOrderView.getCustomerName()) && !Utils.isZero(pickingOrderView.getCustomerName().length())){
                    log.debug("customerName {}", pickingOrderView.getCustomerName());
                    criteria.add(Restrictions.eq("cc.name", pickingOrderView.getCustomerName()));
                }

                if (!Utils.isNull(pickingOrderView.getDeliveryAddress()) && !Utils.isZero(pickingOrderView.getDeliveryAddress().length())){
                    log.debug("deliveryAddress {}", pickingOrderView.getDeliveryAddress());
                    criteria.add(Restrictions.eq("deliveryAddress", pickingOrderView.getDeliveryAddress()));
                }
            }

            criteria.add(Restrictions.eq("isValid", 1));
            criteria.addOrder(Order.asc("requestShiftDate"));

            pickingOrderModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }
}
