package com.ese.service;

import com.ese.model.StatusValue;
import com.ese.model.TableValue;
import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.*;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class ShowPickingListService extends Service {
    private static final long serialVersionUID = 4112578634263394999L;
    @Resource private PickingOrderDAO pickingOrderDAO;
    @Resource private LoadingOrderDAO loadingOrderDAO;
    @Resource private ContainerDAO containerDAO;
    @Resource private ContainerItemDAO containerItemDAO;
    @Resource private ItemSequenceDAO itemSequenceDAO;
    @Resource private ItemDAO itemDAO;

    public List<PickingOrderModel> getPickingByLoadingOrderId(int loadingOrderId){
        return pickingOrderDAO.findByLoadingOrder(loadingOrderId);
    }


    public List<PickingOrderModel> getPickingByStatusPostPick(){
        return pickingOrderDAO.findByStatusPostPick();
    }

    public List<PickingOrderModel> getPickingBySearchStatusPostPick(PickingOrderView pickingOrderView){
        return pickingOrderDAO.findBySearchStatusPost(pickingOrderView);
    }

    public void select(int loadingOrderId, List<PickingOrderModel> pickingOrderModelList){
        for (PickingOrderModel pickingOrderModel : pickingOrderModelList){
            pickingOrderDAO.updateStatus(pickingOrderModel.getId(), StatusPickingValue.PREPARE_LOAD.getId(), loadingOrderId);
            loadingOrderDAO.updateStatus(loadingOrderId, StatusLoadingOrderView.SELECT_PICKING_LIST.getId());
        }
    }

    public void saveOrupdate(ContainerView containerView, LoadingOrderModel loadingOrderModel) throws Exception{
        ContainerModel containerModel = new ContainerModel();
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        if (Utils.isZero(containerView.getId())){
            containerModel.setContainerNo(containerView.getContainerNo());
            containerModel.setLoadingOrderModel(loadingOrderModel);
            containerModel.setQuantity(containerView.getQuantity());
            containerModel.setSealNo(containerView.getSealNo());
            containerModel.setCreateBy(staffModel);
            containerModel.setCreateDate(Utils.currentDate());
            containerModel.setUpdateBy(staffModel);
            containerModel.setUpdateDate(Utils.currentDate());

            containerDAO.persist(containerModel);
        } else {
            containerModel.setId(containerView.getId());
            containerModel.setContainerNo(containerView.getContainerNo());
            containerModel.setLoadingOrderModel(loadingOrderModel);
            containerModel.setQuantity(containerView.getQuantity());
            containerModel.setSealNo(containerView.getSealNo());
            containerModel.setCreateBy(staffModel);
            containerModel.setCreateDate(Utils.currentDate());
            containerModel.setUpdateBy(staffModel);
            containerModel.setUpdateDate(Utils.currentDate());

            containerDAO.update(containerModel);
        }
    }

    public List<ContainerModel> getContainerByLoadingOrderId(int loadingOrderId){
        return containerDAO.findByLoadingOrderId(loadingOrderId);
    }

    public ContainerView transformToView(ContainerModel containerModel){
        ContainerView containerView = new ContainerView();
        containerView.setId(containerModel.getId());
        containerView.setContainerNo(containerModel.getContainerNo());
        containerView.setSealNo(containerModel.getSealNo());
        containerView.setQuantity(containerModel.getQuantity());
        containerView.setLoadingOrderModel(containerModel.getLoadingOrderModel());
        containerModel.setCreateBy(containerModel.getCreateBy());
        containerView.setCreateDate(containerModel.getCreateDate());
        containerView.setUpdateBy(containerModel.getUpdateBy());
        containerView.setUpdateDate(containerModel.getUpdateDate());

        return containerView;
    }

    public void delete(ContainerModel containerModel) throws Exception{
        containerDAO.delete(containerModel);
    }

    public List<ContainerItemModel> getContainerItemByLoadingOrderId(int loadingOrderId){
        return containerItemDAO.findByLoadingOrderId(loadingOrderId);
    }

    public List<ItemSequenceModel> getItemSeq(int loadingOrderId){
        List<ItemSequenceModel> itemSequenceModelList = itemSequenceDAO.findByLoadingOrderId(loadingOrderId);

        if (Utils.isZero(itemSequenceModelList.size())){
            List<ItemSequenceView> itemList = loadingOrderDAO.findByLoadingOrderId(loadingOrderId);
            LoadingOrderModel loadingOrderModel = new LoadingOrderModel();
            int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
            try {
                loadingOrderModel  = loadingOrderDAO.findByID(loadingOrderId);
            } catch (Exception e) {
                log.debug("Exception error find loading order by id: ", e);
            }

            for (ItemSequenceView item : itemList){
                ItemSequenceModel itemSequenceModel = new ItemSequenceModel();
                MSItemModel msItemModel = itemDAO.findByItemId(item.getItemName());
                itemSequenceModel.setMsItemModel(msItemModel);
                itemSequenceModel.setLoadingOrderModel(loadingOrderModel);
                itemSequenceModel.setCreateBy(staffModel);
                itemSequenceModel.setCreateDate(Utils.currentDate());
                itemSequenceModel.setUpdateBy(staffModel);
                itemSequenceModel.setUpdateDate(Utils.currentDate());
                itemSequenceModelList.add(itemSequenceModel);

                try {
                    itemSequenceDAO.persist(itemSequenceModel);
                } catch (Exception e) {
                    log.debug("Exception error pirsist item sequence : ", e);
                }
            }
        }

        return itemSequenceModelList;
    }

    public void save(List<ItemSequenceModel> itemSequenceModelList) throws Exception {
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        for (ItemSequenceModel itemSequenceModel : itemSequenceModelList){
            itemSequenceModel.setUpdateBy(staffModel);
            itemSequenceModel.setUpdateDate(Utils.currentDate());
            itemSequenceDAO.update(itemSequenceModel);
        }
    }
}
