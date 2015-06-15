package com.ese.model.dao;


import com.ese.model.db.AxInventPickingListJourModel;
import com.ese.model.view.AxInventPickingListJourView;
import com.ese.model.view.StatusPickingValue;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AxInventPickingListJourDAO extends GenericDAO<AxInventPickingListJourModel, Integer>{

    public List<AxInventPickingListJourModel> findByStatus(){
        List<AxInventPickingListJourModel> axInventPickingListJourModelList = Utils.getEmptyList();

        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("syncStatus", 1));
            axInventPickingListJourModelList = criteria.list();
        } catch (Exception e) {
            log.debug("Exception e : ", e);
        }

        return axInventPickingListJourModelList;
    }

    public List<AxInventPickingListJourModel> findBySearchStatusPost(AxInventPickingListJourView axInventPickingListJourView){
        List<AxInventPickingListJourModel> pickingOrderModelList = Utils.getEmptyList();
        try {
            Criteria criteria = getCriteria();

            if (!Utils.isNull(axInventPickingListJourView)){
                if (!Utils.isNull(axInventPickingListJourView.getPickingListId()) && !Utils.isZero(axInventPickingListJourView.getPickingListId().length())){
                    criteria.add(Restrictions.like("pickingListId", "%" + axInventPickingListJourView.getPickingListId().trim() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getCustomer()) && !Utils.isZero(axInventPickingListJourView.getCustomer().length())){
                    criteria.add(Restrictions.like("custAccount", "%" + axInventPickingListJourView.getCustomer() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getSaleOrderId()) && !Utils.isZero(axInventPickingListJourView.getSaleOrderId().length())){
                    criteria.add(Restrictions.like("orderId", "%" + axInventPickingListJourView.getSaleOrderId() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getLoadingDate()) && !Utils.isZero(axInventPickingListJourView.getLoadingDate().length())){
                    criteria.add(Restrictions.eq("wContainerDate", Utils.convertStringToDate(axInventPickingListJourView.getLoadingDate())));
                }

                if (!Utils.isNull(axInventPickingListJourView.getEtd()) && !Utils.isZero(axInventPickingListJourView.getEtd().length())){
                    criteria.add(Restrictions.eq("wETDDate", Utils.convertStringToDate(axInventPickingListJourView.getLoadingDate())));
                }

                if (!Utils.isNull(axInventPickingListJourView.getAgent()) && !Utils.isZero(axInventPickingListJourView.getAgent().length())){
                    criteria.add(Restrictions.like("wAgent", "%" + axInventPickingListJourView.getAgent() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getInvNo()) && !Utils.isZero(axInventPickingListJourView.getInvNo().length())){
                    criteria.add(Restrictions.like("iNV", "%" + axInventPickingListJourView.getInvNo() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getPiNo()) && !Utils.isZero(axInventPickingListJourView.getPiNo().length())){
                    criteria.add(Restrictions.like("refNo", "%" + axInventPickingListJourView.getPiNo() + "%"));
                }

                if (!Utils.isNull(axInventPickingListJourView.getTruckAgent()) && !Utils.isZero(axInventPickingListJourView.getTruckAgent().length())){
                    criteria.add(Restrictions.like("wTrucking", "%" + axInventPickingListJourView.getTruckAgent() + "%"));
                }
            }

            criteria.add(Restrictions.eq("syncStatus", 1));
            pickingOrderModelList = Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByOverSeaAndDomesticOrder : ", e);
        }

        return pickingOrderModelList;
    }

    public void updateAxPickingListStatus(String axPickingListId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_inventpickinglistjour SET ").append(getPrefix()).append(".ax_inventpickinglistjour.sync_status =  1");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_inventpickinglistjour.PickingListId = ").append("'").append(axPickingListId).append("'");

        log.debug("SQL updateAxPickingListStatus : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception error updateToWrap: ", e);
        }
    }
}
