package com.ese.transform;

import com.ese.model.db.StaffModel;
import com.ese.model.db.StaffRolesModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StaffRoleTransform extends Transform{

    public StaffRolesModel tramsformToModel(SystemRoleModel systemRoleModel, StaffModel staffModel){
        int staffModel2 = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        log.debug("systemRoleModel {}", systemRoleModel.toString());
        log.debug("staffModel {}", staffModel.toString());
        StaffRolesModel staffRolesModel = new StaffRolesModel();

        staffRolesModel.setId(staffRolesModel.getId());
        staffRolesModel.setCreateBy(staffModel2);
        staffRolesModel.setCreateDate(Utils.currentDate());
        staffRolesModel.setUpdateBy(staffModel2);
        staffRolesModel.setUpdateDate(Utils.currentDate());
        staffRolesModel.setIsValid(1);
        staffRolesModel.setRoles(systemRoleModel);
        staffRolesModel.setStaff(staffModel);

        log.debug("########### {}", staffRolesModel.toString());

        return staffRolesModel;
    }
}
