package com.ese.transform;

import com.ese.model.db.StaffModel;
import com.ese.model.view.UserView;
import com.ese.service.security.encryption.EncryptionService;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;

@Component
public class UserManagementTranstorm extends Transform{

    public StaffModel transformToModel(UserView userView){
        int staffModel2 = (int) FacesUtil.getSession(false).getAttribute(AttributeName.STAFF.getName());
        StaffModel staffModel = new StaffModel();

        staffModel.setId(userView.getId());
//        staffModel.setPassword(userView.getPassword());

        staffModel.setPassword(EncryptionService.encryption(userView.getPassword()));

        staffModel.setUsername(userView.getUsername());
        staffModel.setMsTitleModel(userView.getMsTitleModel());
        staffModel.setFactionModel(userView.getFactionModel());
        staffModel.setName(userView.getName());
        staffModel.setPosition(userView.getPosition());
        staffModel.setIsValid(1);

        if (Utils.isZero(userView.getId())){
            staffModel.setCreateBy(staffModel2);
            staffModel.setCreateDate(Utils.currentDate());
            staffModel.setUpdateBy(staffModel2);
            staffModel.setUpdateDate(Utils.currentDate());
        } else {
            staffModel.setUpdateBy(staffModel2);
            staffModel.setUpdateDate(Utils.currentDate());
            staffModel.setCreateBy(userView.getCreateBy());
            staffModel.setCreateDate(userView.getCreateDate());
        }

        return staffModel;
    }

    public UserView transformToView(StaffModel staffModel){
        UserView userView = new UserView();

        userView.setId(staffModel.getId());
//        userView.setPassword(staffModel.getPassword());
        userView.setVersion(staffModel.getVersion());
        userView.setMsDepartmentModel(staffModel.getFactionModel().getMsDepartmentModel());
        userView.setMsTitleModel(staffModel.getMsTitleModel());
        userView.setFactionModel(staffModel.getFactionModel());
        userView.setUsername(staffModel.getUsername());
        userView.setName(staffModel.getName());
        userView.setCreateBy(staffModel.getCreateBy());
        userView.setCreateDate(staffModel.getCreateDate());
        userView.setUpdateBy(staffModel.getUpdateBy());
        userView.setUpdateDate(staffModel.getUpdateDate());
        userView.setIsValid(staffModel.getIsValid());
        userView.setPosition(staffModel.getPosition());

        return userView;
    }
}
