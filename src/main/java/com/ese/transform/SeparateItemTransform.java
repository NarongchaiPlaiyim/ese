package com.ese.transform;

import com.ese.model.db.ContainerItemModel;
import com.ese.model.db.ContainerModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.ContainerItemView;
import com.ese.model.view.SeparateItemView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SeparateItemTransform extends Transform{

    private String emtry = "  ";

    public List<SeparateItemView> transformtoView(List<ContainerItemModel> modelList){
        List<SeparateItemView> separateItemViewList = new ArrayList<SeparateItemView>();
        HashMap<Integer, String> hashMap = new HashMap<>();
        SeparateItemView separateItemViews = null;
        List<ContainerItemView> containerItemViewList = null;
        ContainerItemView containerItemView;
        int i = 0;

        try{
            for (ContainerItemModel model : modelList){
                i++;

                if (!hashMap.containsKey(model.getMsItemModel().getId())){
                    hashMap.put(model.getMsItemModel().getId(), model.getMsItemModel().getItemId());
                    if (Utils.isSafetyList(containerItemViewList)){
                        separateItemViews.setContainerItemViewList(containerItemViewList);
                        separateItemViewList.add(separateItemViews);
                    }

                    separateItemViews = new SeparateItemView();
                    StringBuilder header = new StringBuilder();
                    containerItemViewList = new ArrayList<ContainerItemView>();

                    header.append("Item Id : ").append(model.getMsItemModel().getItemId()).append(emtry);
                    header.append("Item Description : ").append(model.getMsItemModel().getDSGThaiItemDescription());
                    separateItemViews.setHeaderSubName(header.toString());

                    containerItemView = new ContainerItemView(model.getId(), model.getContainerModel().getId(), model.getContainerModel().getContainerNo(), model.getContainerQty());
                    containerItemViewList.add(containerItemView);
                } else {
                    containerItemView = new ContainerItemView(model.getId(), model.getContainerModel().getId(), model.getContainerModel().getContainerNo(), model.getContainerQty());
                    containerItemViewList.add(containerItemView);
                }

                if (i == modelList.size()){
                    separateItemViews.setContainerItemViewList(containerItemViewList);
                    separateItemViewList.add(separateItemViews);
                }
            }
        } catch (Exception e){
            log.debug("Exception error : ", e.getMessage());
        }

        return separateItemViewList;
    }

    public ContainerItemModel transformToNewModel(ContainerModel containerModel, MSItemModel msItemModel, int containerQty){
        log.debug("containerModel id : {}", containerModel.getId());
        log.debug("msItemModel id : {}", msItemModel.getId());
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        ContainerItemModel containerItemModel = new ContainerItemModel();

        containerItemModel.setContainerModel(containerModel);
        containerItemModel.setMsItemModel(msItemModel);
        containerItemModel.setContainerQty(containerQty);
        containerItemModel.setCreateBy(staffModel);
        containerItemModel.setCreateDate(Utils.currentDate());
        containerItemModel.setUpdateBy(staffModel);
        containerItemModel.setUpdateDate(Utils.currentDate());

        return containerItemModel;
    }
}
