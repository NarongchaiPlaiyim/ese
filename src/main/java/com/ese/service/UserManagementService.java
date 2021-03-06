package com.ese.service;

import com.ese.beans.Document;
import com.ese.model.dao.*;
import com.ese.model.db.*;
import com.ese.model.view.UserView;
import com.ese.model.view.report.UserAndRoleViewReport;
import com.ese.service.security.UserDetail;
import com.ese.service.security.encryption.EncryptionService;
import com.ese.transform.StaffRoleTransform;
import com.ese.transform.UserAccessTransform;
import com.ese.transform.UserManagementTranstorm;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional
public class UserManagementService extends Service{
    private static final long serialVersionUID = 4112578634029894840L;
    @Resource MSDepartmentDAO msDepartmentDAO;
    @Resource StaffDAO staffDAO;
    @Resource FactionDAO factionDAO;
    @Resource MSTitleDAO msTitleDAO;
    @Resource UserManagementTranstorm userManagementTranstorm;
    @Resource MenuObjectDAO menuObjectDAO;
    @Resource StaffRolesDAO staffRolesDAO;
    @Resource UserAccessTransform userAccessTransform;
    @Resource UserAccessDAO userAccessDAO;
    @Resource SystemRoleDAO systemRoleDAO;
    @Resource StaffRoleTransform staffRoleTransform;
    @Resource RoleAccessDAO roleAccessDAO;
    @Resource private ReportService reportService;

    @Value("#{config['report.userandrole']}")
    private String pathPrintUserAndRole;
    @Value("#{config['report.logo']}")
    private String path;

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
            return Utils.getEmptyList();
        }
    }

    public UserView setModelToViewUserAccess(StaffModel staffModel){
        return userManagementTranstorm.transformToView(staffModel);
    }

    public boolean isExisted(String userName){
        try {
            return staffDAO.isUsernameExist(userName);
        } catch (Exception e) {
            return true;
        }
    }
    public void onSaveUserAccess(UserView userView){
        StaffModel staffModel = userManagementTranstorm.transformToModel(userView);
        try {
            staffDAO.persist(staffModel);
        } catch (Exception e) {
            log.debug("Exception error persist : ", e);
        }
    }

    public void onUpdataeUserAccess(UserView userView){
        StaffModel staffModel = userManagementTranstorm.transformToModel(userView);

        try {
            staffDAO.update(staffModel);
        } catch (Exception e) {
            log.debug("Exception error update : ", e);
        }
    }

    public List<MenuObjectModel> getMenuObjectByObjCategory(){
        return menuObjectDAO.findByObjCategory();
    }

    public List<MenuObjectModel> getMenuObjectAll(){
        try {
            return menuObjectDAO.findAll();
        } catch (Exception e) {
            log.debug("Exception error getMenuObjectAll : ", e);
            return null;
        }
    }

    public List<MenuObjectModel> getMenuObjectId(int menuObjectId, String keySearch){
        return menuObjectDAO.findByObjectId(menuObjectId, keySearch);
    }

    public List<StaffRolesModel> getStaffRoleByUserId(int userId){
        return staffRolesDAO.findByUserId(userId);
    }

    public List<UserAccessModel> getMenuObjectByUserId(int userId){
        return userAccessDAO.findByUserId(userId);
    }

    public void onSaveUserAccess(List<Document> objectModels, StaffModel staffModel){
        log.debug("objectModels Size : {}, staffModel {}", objectModels.size(), staffModel.toString());
        UserAccessModel model;
        List<UserAccessModel> userAccessModelList;

        if (Utils.isSafetyList(objectModels)){
            for (Document document : objectModels){
                try {
                    model = userAccessTransform.transformToModel(menuObjectDAO.findByID(document.getId()), staffModel);
                    userAccessDAO.persist(model);
                    userAccessModelList = userAccessDAO.findByUserId(staffModel.getId());

                    Map<Integer, UserAccessModel> hashMap = new HashMap();
                    for(UserAccessModel userAccessModel : userAccessModelList){
                        int key = userAccessModel.getMenuObjectModel().getId();
                        if (!hashMap.containsKey(key)){
                            hashMap.put(key, userAccessModel);
                        } else {
                            log.debug("Delete : {}", userAccessModel.getId());
                            userAccessDAO.delete(userAccessModel);
                        }
                    }
                } catch (Exception e) {
                    log.debug("Exception error onSaveUserAccess : ", e);
                }
            }
        }
    }

    public void deleteUserAuthorize(Document document){
        try {
            userAccessDAO.delete(userAccessDAO.findByID(document.getId()));
        } catch (Exception e) {
            log.debug("Exception error deleteUserAuthorize : ", e);
        }
    }

    public List<UserAccessModel> getUserAuthorizeByMenuObjOrKey(int objId, String key){
        return userAccessDAO.findByMenuObjOrKey(objId, key);
    }

    public void deleteRole(int roleId){
        try {
            staffRolesDAO.delete(staffRolesDAO.findById(roleId));
        } catch (Exception e) {
            log.debug("Exception error deleteRole : ", e);
        }
    }

    public List<SystemRoleModel> getLoadUserRole(){
        return systemRoleDAO.findByIsValid();
    }

    public List<SystemRoleModel> getRoleByKey(String key){
        return systemRoleDAO.findByKey(key);
    }

    public void onSaveRole(List<SystemRoleModel> roleModels, StaffModel staffModel) {
        log.debug("roleModels Size : {}, staffModel {}", roleModels.size(), staffModel.toString());
        StaffRolesModel staffRolesModel;
        List<StaffRolesModel> staffRolesModelList;
        List<RoleAccessModel> roleAccessModels;
        UserAccessModel userAccessModel;
        List<UserAccessModel> userAccessModelList;

        for (SystemRoleModel roleModel : roleModels){
            staffRolesModel = staffRoleTransform.tramsformToModel(roleModel, staffModel);

            try {
                staffRolesDAO.persist(staffRolesModel);
                staffRolesModelList = staffRolesDAO.findByUserId(staffModel.getId());

                Map<Integer, StaffRolesModel> hashMap = new HashMap();
                for(StaffRolesModel rolesModel : staffRolesModelList){
                    int key = rolesModel.getRoles().getId();
                    if (!hashMap.containsKey(key)){
                        hashMap.put(key, rolesModel);
                    } else {
                        log.debug("Delete : {}", rolesModel.getId());
                        staffRolesDAO.delete(rolesModel);
                    }
                }
            } catch (Exception e) {
                log.debug("Exception error staffRolesDAO : ", e);
            }


            roleAccessModels = roleAccessDAO.findBySystemRoleId(roleModel.getId());

            for (RoleAccessModel roleAccessModel : roleAccessModels){
                userAccessModel = userAccessTransform.transformToModel(roleAccessModel.getMenuObjectModel(), staffModel);

                try {
                    userAccessDAO.persist(userAccessModel);

                    userAccessModelList = userAccessDAO.findByUserId(staffModel.getId());

                    Map<Integer, UserAccessModel> hashMap = new HashMap();
                    for(UserAccessModel userAccess : userAccessModelList){
                        int key = userAccess.getMenuObjectModel().getId();
                        if (!hashMap.containsKey(key)){
                            hashMap.put(key, userAccess);
                        } else {
                            log.debug("Delete : {}", userAccess.getId());
                            userAccessDAO.delete(userAccess);
                        }
                    }
                } catch (Exception e) {
                    log.debug("Exception error userAccessDAO : ", e);
                }
            }

        }
    }

    public UserView getUserName(String userName){
        try {
            StaffModel staffModel = staffDAO.findByUserName(userName);
            UserView userView = userManagementTranstorm.transformToView(staffModel);
            return userView;
        } catch (Exception e) {
            log.debug("Exception error getUserName : {}", e);
            return new UserView();
        }
    }

    public void printReportUserAndRole(String user){
        String printTagReportname = Utils.genReportName("_UserAndRoleReport");
        HashMap map = new HashMap<String, Object>();
        List<UserAndRoleViewReport> reportViews;

        map.put("userPrint", user);
        map.put("printDate", Utils.convertCurrentDateToStringDDMMYYYY());
        map.put("path", FacesUtil.getRealPath(path));

        reportViews = staffDAO.genSQLReportUserAndRole();

        try {
            reportService.exportPDF(pathPrintUserAndRole, map, printTagReportname, reportViews);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }
}
