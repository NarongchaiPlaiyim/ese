package com.ese.service;

import com.ese.model.dao.FactionDAO;
import com.ese.model.dao.MSDepartmentDAO;
import com.ese.model.dao.StaffDAO;
import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.StaffModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class UserManagementService extends Service{
    @Resource MSDepartmentDAO msDepartmentDAO;
    @Resource StaffDAO staffDAO;
    @Resource FactionDAO factionDAO;

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
}
