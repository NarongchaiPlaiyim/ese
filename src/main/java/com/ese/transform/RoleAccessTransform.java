package com.ese.transform;

import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.StaffModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RoleAccessTransform extends Transform{

    public RoleAccessModel transformToModel(MenuObjectModel menuObjectModel, SystemRoleModel systemRoleModel){
        int staffModel = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        RoleAccessModel roleAccessModel = new RoleAccessModel();

        roleAccessModel.setId(roleAccessModel.getId());
        roleAccessModel.setSystemRoleModel(systemRoleModel);
        roleAccessModel.setMenuObjectModel(menuObjectModel);
        roleAccessModel.setCreateBy(staffModel);
        roleAccessModel.setCreateDate(Utils.currentDate());
        roleAccessModel.setUpdateBy(staffModel);
        roleAccessModel.setUpdateDate(Utils.currentDate());
        roleAccessModel.setIsValid(1);

       return roleAccessModel;
    }
}
