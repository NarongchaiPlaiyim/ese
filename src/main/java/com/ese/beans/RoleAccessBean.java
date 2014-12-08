package com.ese.beans;

import com.ese.model.db.MenuObjectModel;
import com.ese.model.db.RoleAccessModel;
import com.ese.model.db.SystemRoleModel;
import com.ese.model.view.SystemRoleView;
import com.ese.service.RoleAccessService;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
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
    private String keySearchRoleAccess;

    private List<SystemRoleModel> systemRoleModelList;
    private SystemRoleModel systemRoleModel;
    private List<RoleAccessModel> roleAccessModelList;
    private List<RoleAccessModel> selectRoleAccess;
    private SystemRoleView systemRoleView;
    private List<MenuObjectModel> menuObjectModelList;
    private MenuObjectModel menuObjectModel;

    //Add Role Access Dialog
    //TB
    private List<MenuObjectModel> menuRoleAccessDlgList;
    private List<MenuObjectModel> selectMenuRoleAccessDlg;
    private String keySearchMenuObj;

    //Item
    private List<MenuObjectModel> menuRoleAccessItemList;
    private MenuObjectModel menuRoleAccessItem;

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
        keySearchRoleAccess = "";
        keySearchMenuObj = "";
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

    public void preDeleteRole(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes confirm delete.", "confirmDeleteRoleDlg");
    }

    public void onSaveRole(){
        roleAccessService.saveSystemRole(systemRoleView);
        showDialogSaved();
        onLoad();
        roleAccessModelList = new ArrayList<RoleAccessModel>();
    }

    public void onDeleteRole(){
        roleAccessService.deleteRole(systemRoleModel);
        showDialogDeleted();
        onLoad();
    }

    public void onFilterRoleAccess(){
        log.debug("Object ID : {}, SystemRoleId : {}, keySearchRoleAccess : {}", menuObjectModel.getId(), systemRoleModel.getId(), keySearchRoleAccess);
        roleAccessModelList = roleAccessService.getRoleAccessByMenuObjectIdAndSystemRoleId(menuObjectModel.getId(), systemRoleModel.getId(), keySearchRoleAccess);
    }

    public void preDeleteRoelAccess(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes confirm delete.", "confirmDeleteRoleAccessDlg");
    }

    public void onDeleteRoleAccess(){
        log.debug("selectRoleAccess Size : {}", selectRoleAccess.size());
        roleAccessService.deleteRoleAccess(selectRoleAccess);
        roleAccessModelList = roleAccessService.getRoleAccessBySystemRoleId(systemRoleModel.getId());
    }

    public void onLoadMenuObjDialog(){
        selectMenuRoleAccessDlg = new ArrayList<MenuObjectModel>();
        menuRoleAccessItem = new MenuObjectModel();
        menuRoleAccessDlgList = roleAccessService.getMenuObjAll();
        menuRoleAccessItemList = roleAccessService.getMenuObjectByObjCategory();
    }

    public void onFilterMenuObjDlg(){
        menuRoleAccessDlgList = roleAccessService.getMenuObjByIdAndKey(menuRoleAccessItem.getId(), keySearchMenuObj);
    }

    public void onSaveMenuObjToRoleAccess(){
        if (Utils.isZero(selectMenuRoleAccessDlg.size())){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Please select Menu Object and Action.", "msgBoxSystemMessageDlg");
        } else {
            roleAccessService.saveMenuObjectInRoleAccess(selectMenuRoleAccessDlg, systemRoleModel);
            showDialogSaved();
            onLoadMenuObjDialog();
            roleAccessModelList = roleAccessService.getRoleAccessBySystemRoleId(systemRoleModel.getId());
        }
    }
}
