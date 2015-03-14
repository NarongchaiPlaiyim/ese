package com.ese.transform;

import com.ese.model.db.StaffModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class SystemRoleTransform extends Transform{

    public SystemRoleView transformToView(SystemRoleModel model){
        SystemRoleView systemRoleView = new SystemRoleView();

        systemRoleView.setId(model.getId());
        systemRoleView.setCode(model.getCode());
        systemRoleView.setName(model.getName());
        systemRoleView.setDescription(model.getDescription());
        systemRoleView.setVersion(model.getVersion());
        systemRoleView.setIsValid(model.getIsValid());
        systemRoleView.setCreateBy(model.getCreateBy());
        systemRoleView.setCreateDate(model.getCreateDate());
        systemRoleView.setUpdateBy(model.getUpdateBy());
        systemRoleView.setUpdateDate(model.getUpdateDate());

        return systemRoleView;
    }

    public SystemRoleModel transformToModel(SystemRoleView view){
        log.debug("view {}", view.toString());
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        SystemRoleModel model = new SystemRoleModel();

        model.setCode(view.getCode());
        model.setName(view.getName());
        model.setDescription(view.getDescription());

        if (!Utils.isZero(view.getId())){
            model.setId(view.getId());
            model.setVersion(view.getVersion());
            model.setIsValid(view.getIsValid());
            model.setCreateBy(view.getCreateBy());
            model.setCreateDate(view.getCreateDate());
            model.setUpdateBy(staffModel);
            model.setUpdateDate(Utils.currentDate());
        } else {
            model.setVersion(1);
            model.setIsValid(1);
            model.setCreateBy(staffModel);
            model.setCreateDate(Utils.currentDate());
            model.setUpdateBy(staffModel);
            model.setUpdateDate(Utils.currentDate());
        }

        return model;
    }
}
