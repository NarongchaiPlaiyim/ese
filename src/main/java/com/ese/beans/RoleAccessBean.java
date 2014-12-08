package com.ese.beans;

import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.service.RoleAccessService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "roleAccess")
@ViewScoped
public class RoleAccessBean extends Bean{
    @ManagedProperty("#{roleAccessService}") private RoleAccessService roleAccessService;

    private String modeRole;
    private String modeRoleAccess;
    private boolean flagBtnSaveRole;
    private boolean flagBtnDeleteRole;
    private boolean flagBtnPrint;
    private String keySearchRole;
    private boolean flagBtnAddRoleAccess;
    private boolean flagBtnDeleteRoleAccess;
    private boolean flagItemRoleAccess;
    private boolean flagSearchRoleAccess;
    private boolean flagBtnSearchRoleAccess;

    private List<SystemRoleModel> systemRoleModelList;
    private SystemRoleModel systemRoleModel;
    private List<RoleAccessModel> roleAccessModelList;
    private List<RoleAccessModel> selectRoleAccess;
    private SystemRoleView systemRoleView;
    private List<MenuObjectModel> menuObjectModelList;
    private MenuObjectModel menuObjectModel;

    @PostConstruct
    private void onLoad(){
        btnAndtextOnload();
        newObjectOnload();
        roleTBOnload();
    }

    private void btnAndtextOnload(){
        modeRole = "Mode:Search";
        modeRoleAccess = "Mode:Search";
//        flagBtnSaveRole = true;
        flagBtnDeleteRole = true;
        flagBtnPrint = true;
        flagBtnAddRoleAccess = true;
        flagBtnDeleteRoleAccess = true;
        flagItemRoleAccess = true;
        flagSearchRoleAccess = true;
        flagBtnSearchRoleAccess = true;
        keySearchRole = "";
    }

    private void newObjectOnload(){
        systemRoleModel = new SystemRoleModel();
        systemRoleView = new SystemRoleView();
        menuObjectModel = new MenuObjectModel();
    }

    private void roleTBOnload(){
        systemRoleModelList = roleAccessService.getSystemRoleByIsValid();
        menuObjectModelList = roleAccessService.getMenuObjectByObjCategory();
    }

    public void onClickRoleTB(){
        log.debug("systemRoleModel Id : {}", systemRoleModel.getId());
        modeRole = "Mode:Edit";
//        flagBtnSaveRole = false;
        flagBtnDeleteRole = false;
        flagBtnPrint = false;
        flagBtnAddRoleAccess = false;
        flagBtnDeleteRoleAccess = false;
        flagItemRoleAccess = false;
        flagSearchRoleAccess = false;
        flagBtnSearchRoleAccess = false;
        systemRoleView = roleAccessService.getModelToView(systemRoleModel);
        roleAccessModelList = roleAccessService.getRoleAccessBySystemRoleId(systemRoleModel.getId());
        log.debug("roleAccessModelList Size : ", roleAccessModelList.size());
    }

    public void onFilterRoleTB(){
        systemRoleModelList = roleAccessService.getSystemRoleByKey(keySearchRole);
    }

    public void clearTextBox(){
        modeRole = "Mode:New";
        modeRoleAccess = "Mode:Search";
//        flagBtnSaveRole = true;
        flagBtnDeleteRole = true;
        flagBtnPrint = true;
        flagBtnAddRoleAccess = true;
        flagBtnDeleteRoleAccess = true;
        flagItemRoleAccess = true;
        flagSearchRoleAccess = true;
        flagBtnSearchRoleAccess = true;
        keySearchRole = "";
        systemRoleModel = new SystemRoleModel();
        systemRoleView = new SystemRoleView();
    }

    public void onSaveRole(){
        roleAccessService.saveSystemRole(systemRoleView);
        showDialogSaved();
        onLoad();
    }

    public void onDeleteRole(){
        roleAccessService.deleteRole(systemRoleModel);
        showDialogDeleted();
        onLoad();
    }

    public void onFilterRoleAccess(){
        log.debug("Object ID : {}, SystemRoleId : {}", menuObjectModel.getId(), systemRoleModel.getId());
        roleAccessModelList = roleAccessService.getRoleAccessByMenuObjectIdAndSystemRoleId(menuObjectModel.getId(), systemRoleModel.getId());
    }
}
