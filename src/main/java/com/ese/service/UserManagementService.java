package com.ese.service;

import com.ese.model.dao.FactionDAO;
import com.ese.model.dao.MSDepartmentDAO;
import com.ese.model.dao.MSTitleDAO;
import com.ese.model.dao.StaffDAO;
import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.MSTitleModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.UserView;
import com.ese.service.security.encryption.EncryptionService;
import com.ese.transform.UserManagementTranstorm;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class UserManagementService extends Service{
    @Resource MSDepartmentDAO msDepartmentDAO;
    @Resource StaffDAO staffDAO;
    @Resource FactionDAO factionDAO;
    @Resource MSTitleDAO msTitleDAO;
    @Resource UserManagementTranstorm userManagementTranstorm;

    public List<MSDepartmentModel> getDepartAll(){
        return msDepartmentDAO.findDepartmentByIsValid();
    }

    public List<StaffModel> getUserAll(){
        return staffDAO.findUserByIsValid();
    }

    public List<FactionModel> getFactionByDepartment(int departmentId){
        return factionDAO.findByDepartmentId(departmentId);
    }

    public List<StaffModel> getUserBySearch(int departmentId, int factionId, String keySearch){
        return staffDAO.findUserBySearch(departmentId, factionId, keySearch);
    }

    public void delete(StaffModel staffModel){
        try {
            staffDAO.deleteByUpdate(staffModel);
        } catch (Exception e) {
            log.debug("Exception error delete : ", e);
        }
    }

    public List<MSTitleModel> getTitleAll(){
        try {
            return msTitleDAO.findAll();
        } catch (Exception e) {
            log.debug("Exception error getTitleAll : ", e);
            return new ArrayList<MSTitleModel>();
        }
    }

    public UserView setModelToViewUserAccess(StaffModel staffModel){
        return userManagementTranstorm.transformToView(staffModel);
    }

    public void onSaveUserAccess(UserView userView){
        StaffModel staffModel = userManagementTranstorm.transformToModel(userView);

        if (!Utils.isZero(userView.getId())){
            try {
                staffModel.setPassword(EncryptionService.encryption(staffModel.getPassword()));
                staffDAO.update(staffModel);
            } catch (Exception e) {
                log.debug("Exception error update : ", e);
            }
        } else {
            try {
                staffDAO.persist(staffModel);
            } catch (Exception e) {
                log.debug("Exception error persist : ", e);
            }
        }
    }

    public MSTitleModel getTitleById(int titleId){
        return msTitleDAO.findById(titleId);
    }
}
