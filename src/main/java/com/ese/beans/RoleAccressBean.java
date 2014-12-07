package com.ese.beans;

import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.service.RoleAccessServise;
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
public class RoleAccressBean extends Bean{
    @ManagedProperty("#{roleAccessServise}") private RoleAccessServise roleAccessServise;

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
        systemRoleModelList = roleAccessServise.getSystemRoleByIsValid();
        menuObjectModelList = roleAccessServise.getMenuObjectByObjCategory();
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
        systemRoleView = roleAccessServise.getModelToView(systemRoleModel);
        roleAccessModelList = roleAccessServise.getRoleAccessBySystemRoleId(systemRoleModel.getId());
        log.debug("roleAccessModelList Size : ", roleAccessModelList.size());
    }

    public void onFilterRoleTB(){
        systemRoleModelList = roleAccessServise.getSystemRoleByKey(keySearchRole);
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
        roleAccessServise.saveSystemRole(systemRoleView);
        showDialogSaved();
        onLoad();
    }

    public void onDeleteRole(){
        roleAccessServise.deleteRole(systemRoleModel);
        showDialogDeleted();
        onLoad();
    }

    public void onFilterRoleAccess(){
        log.debug("Object ID : {}", menuObjectModel.getId());
        roleAccessModelList = roleAccessServise.getRoleAccessByMenuObjectId(menuObjectModel.getId());
    }
}
