package com.ese.service;

import com.ese.model.dao.MenuObjectDAO;
import com.ese.model.dao.RoleAccessDAO;
import com.ese.model.dao.SystemRoleDAO;
import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.transform.SystemRoleTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class RoleAccessService extends Service{
    @Resource SystemRoleDAO systemRoleDAO;
    @Resource RoleAccessDAO roleAccessDAO;
    @Resource SystemRoleTransform systemRoleTransform;
    @Resource MenuObjectDAO menuObjectDAO;

    public List<SystemRoleModel> getSystemRoleByIsValid(){
        return systemRoleDAO.findByIsValid();
    }

    public List<SystemRoleModel> getSystemRoleByKey(String key){
        return systemRoleDAO.findByKey(key);
    }

    public List<RoleAccessModel> getRoleAccessBySystemRoleId(int systemRoleId){
        return roleAccessDAO.findBySystemRoleId(systemRoleId);
    }

    public SystemRoleView getModelToView(SystemRoleModel systemRoleModel){
        return systemRoleTransform.transformToView(systemRoleModel);
    }

    public void saveSystemRole(SystemRoleView view){
        SystemRoleModel roleModel = systemRoleTransform.transformToModel(view);

        log.debug("roleModel {}", roleModel.toString());
        if (!Utils.isZero(view.getId())){
            try {
                systemRoleDAO.update(roleModel);
            } catch (Exception e) {
                log.debug("Exception error UpdateSysRole : ", e);
            }
        } else {
            try {
                systemRoleDAO.persist(roleModel);
            } catch (Exception e) {
                log.debug("Exception error SaveSystemRole : ", e);
            }
        }
    }

    public void deleteRole(SystemRoleModel model){
        model.setIsValid(0);
        try {
            systemRoleDAO.update(model);
        } catch (Exception e) {
            log.debug("Exception error deleteRole : ", e);
        }
    }

    public List<MenuObjectModel> getMenuObjectByObjCategory(){
        return menuObjectDAO.findByObjCategory();
    }

    public List<RoleAccessModel> getRoleAccessByMenuObjectIdAndSystemRoleId(int menuObjId, int systemRoleId){
        return roleAccessDAO.findByMenuObjectIdAndSystemRoleId(menuObjId, systemRoleId);
    }
}
