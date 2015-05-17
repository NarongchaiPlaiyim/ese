package com.ese.service;

import com.ese.model.dao.ContainerDAO;
import com.ese.model.dao.ContainerItemDAO;
import com.ese.model.dao.ItemDAO;
import com.ese.model.db.ContainerItemModel;
import com.ese.model.db.ContainerModel;
import com.ese.model.db.LoadingOrderModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.ContainerItemView;
import com.ese.model.view.SeparateItemView;
import com.ese.transform.SeparateItemTransform;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class SeparateItemService extends Service{
    private static final long serialVersionUID = 4112578634263394999L;
    @Resource private ContainerItemDAO containerItemDAO;
    @Resource private SeparateItemTransform separateItemTransform;
    @Resource private ContainerDAO containerDAO;
    @Resource private ItemDAO itemDAO;

    public List<SeparateItemView> getContainerItemByLoadingOrder(int loadingOrderId){
        List<ContainerItemModel> containerItemModelList = containerItemDAO.findByLoadingOrderIdOrderBy(loadingOrderId);
        return separateItemTransform.transformtoView(containerItemModelList);
    }

    public List<SeparateItemView> getContainerItemByAutoInsert(int loadingOrderId){

        List<ContainerItemView> containerViews = containerItemDAO.findByInsertModel(loadingOrderId);

        try{
            for (ContainerItemView containerItemView : containerViews){
                ContainerModel containerModel = containerDAO.findByID(containerItemView.getContainnerId());
                MSItemModel msItemModel = itemDAO.findByID(containerItemView.getItemId());
                ContainerItemModel model = separateItemTransform.transformToNewModel(containerModel, msItemModel, containerItemView.getContainerQty());
                containerItemDAO.persist(model);
            }
        } catch (Exception e){
            log.debug("Exception error : ", e);
        }

        return getContainerItemByLoadingOrder(loadingOrderId);
    }

    public ContainerItemModel getByContainerItemId(int containerItemId) throws Exception{
        return containerItemDAO.findByID(containerItemId);
    }

    public void update(ContainerItemModel containerItem) throws Exception{
        containerItemDAO.update(containerItem);
    }

    public void delete(int loadingOrderId) throws Exception{
         containerItemDAO.delete(containerItemDAO.findByLoadingOrderId(loadingOrderId));
    }
}
