package com.ese.service;

import com.ese.beans.Document;
import com.ese.model.dao.MenuObjectDAO;
import com.ese.model.dao.RoleAccessDAO;
import com.ese.model.dao.SystemRoleDAO;
import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.model.view.report.RoleAccessViewReport;
import com.ese.service.security.UserDetail;
import com.ese.transform.RoleAccessTransform;
import com.ese.transform.SystemRoleTransform;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
@Transactional
public class RoleAccessService extends Service{
    private static final long serialVersionUID = 4112578634029874480L;
    @Resource SystemRoleDAO systemRoleDAO;
    @Resource RoleAccessDAO roleAccessDAO;
    @Resource SystemRoleTransform systemRoleTransform;
    @Resource MenuObjectDAO menuObjectDAO;
    @Resource RoleAccessTransform roleAccessTransform;
    @Resource private ReportService reportService;

    @Value("#{config['report.roleaccess']}")
    private String pathPrintRoleAccess;
    @Value("#{config['report.logo']}")
    private String path;

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

    public List<RoleAccessModel> getRoleAccessByMenuObjectIdAndSystemRoleId(int menuObjId, int systemRoleId, String key){
        return roleAccessDAO.findByMenuObjectIdAndSystemRoleId(menuObjId, systemRoleId, key);
    }

    public void deleteRoleAccess(List<Document> modelsList){

        for (Document document : modelsList){
            try {
                roleAccessDAO.delete(roleAccessDAO.findByID(document.getId()));
//                RoleAccessModel model = roleAccessDAO.findByID(document.getId());
            } catch (Exception e) {
                log.debug("Exception error deleteRoleAccess :", e);
            }
        }

//        for (RoleAccessModel model : modelsList){
//            log.debug("model : {}", model.toString());
//            try {
//                roleAccessDAO.delete(model);
//            } catch (Exception e) {
//                log.debug("Exception error deleteRoleAccess : ", e);
//            }
//        }
    }

    public List<MenuObjectModel> getMenuObjAll(){
        try {
            return menuObjectDAO.findAll();
        } catch (Exception e) {
            log.debug("Exception error getMenuAll : ", e);
            return Utils.getEmptyList();
        }
    }

    public List<MenuObjectModel> getMenuObjByIdAndKey(int objId, String key){
        return menuObjectDAO.findByObjectId(objId, key);
    }

    public void saveMenuObjectInRoleAccess(List<Document> modelsList, SystemRoleModel systemRoleModel){
        RoleAccessModel roleAccessModel = new RoleAccessModel();
        List<RoleAccessModel> roleAccessModelList = new ArrayList<RoleAccessModel>();
        for (Document model : modelsList){
            try {
                roleAccessModel = roleAccessTransform.transformToModel(menuObjectDAO.findByID(model.getId()), systemRoleModel);
                roleAccessDAO.persist(roleAccessModel);

                roleAccessModelList = roleAccessDAO.findBySystemRoleId(systemRoleModel.getId());

                Map<Integer, RoleAccessModel> hashMap = new HashMap();
                for(RoleAccessModel roleModel : roleAccessModelList){
                    int key = roleModel.getMenuObjectModel().getId();
                    if (!hashMap.containsKey(key)){
                        hashMap.put(key, roleModel);
                    } else {
                        log.debug("Delete : {}", roleModel.getId());
                        roleAccessDAO.delete(roleModel);
                    }
                }
            } catch (Exception e) {
                log.debug("Exception error saveMenuObjectInRoleAccess : ", e);
            }
        }
    }

    public void onPrintRoleAccess(UserDetail userDetail){
        String printTagReportname = Utils.genDateReportStringDDMMYYYY(new Date()) + "_UserAndRoleReport";
        HashMap map = new HashMap<String, Object>();
        List<RoleAccessViewReport> reportViews = null;

        map.put("userPrint", userDetail.getUserName());
        map.put("printDate", Utils.convertToStringYYYYMMDDHHmmss(new Date()));
        map.put("path", FacesUtil.getFacesContext().getExternalContext().getRealPath(path));

        reportViews = roleAccessDAO.genSQLReportUserAndRole();

        try {
            reportService.exportPDF(pathPrintRoleAccess, map, printTagReportname, reportViews);
        } catch (Exception e) {
            log.debug("Exception Report : ", e);
        }
    }
}
