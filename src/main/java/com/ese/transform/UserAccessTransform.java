package com.ese.transform;

import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.StaffModel;
import com.ese.model.db.UserAccessModel;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserAccessTransform extends Transform{

    public UserAccessModel transformToModel(MenuObjectModel objectModels, StaffModel userId){
        UserAccessModel userAccessModel = new UserAccessModel();

        userAccessModel.setId(userAccessModel.getId());
        userAccessModel.setStaffModel(userId);
        userAccessModel.setMenuObjectModel(objectModels);
        userAccessModel.setCreateDate(Utils.currentDate());
        userAccessModel.setCreateBy(1);
        userAccessModel.setUpdateDate(Utils.currentDate());
        userAccessModel.setUpdateBy(1);
        userAccessModel.setIsValid(1);

        return userAccessModel;
    }
}
