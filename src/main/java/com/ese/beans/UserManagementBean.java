package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.UserView;
import com.ese.service.UserManagementService;
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
@ManagedBean(name = "userManagement")
@ViewScoped
public class UserManagementBean extends Bean{
    @ManagedProperty("#{userManagementService}") private UserManagementService userManagementService;

    private String modeUserManage;
    private List<MSDepartmentModel> msDepartmentModelList;
    private MSDepartmentModel msDepartmentModel;
    private List<StaffModel> staffModelList;
    private StaffModel staffModel;
    private List<FactionModel> factionModelList;
    private FactionModel factionModel;
    private UserView userView;
    private List<MSTitleModel> msTitleModelList;
    private MSTitleModel msTitleModel;
    private List<StaffRolesModel> staffRolesModelList;
    private StaffRolesModel staffRolesModel;
    private List<UserAccessModel> userAccessModelList;
    private List<UserAccessModel> selectUserAuthorize;

    //Item
    private List<MenuObjectModel> menuObjectModelList;
    private MenuObjectModel menuObjectModel;

    //Table
    private List<MenuObjectModel> menuObjectModelTableList;
    private List<MenuObjectModel> selectList;

    //Add User Dialog
    private List<MSDepartmentModel> departmentDialogList;
//    private MSDepartmentModel departDialog;
    private List<FactionModel> factionDialogList;
//    private FactionModel factionDialog;

    private boolean flagBtnEdit;
    private boolean flagBtnDelete;
    private boolean flagPrint;
    private boolean flagUserAccess;
    private String keySearchUser;
    private String modeBtnAddUser;

    //Dialog User Access
    private String keySearchUserAccessDialog;
    private UserView userAuthorizeView;
    private List<MenuObjectModel> userAuthorize;
    private String keySearchUserAuthorize;
    private List<MenuObjectModel> objectUserAuthorizeList;
    private MenuObjectModel objectUserAuthorize;
    private int roleId;

    //Dialog Add User Role
    private List<SystemRoleModel> systemRoleModelDialogList;
    private List<SystemRoleModel> selectRole;
    private String keySearchRole;



    @PostConstruct
    private void onLoad(){
        newObjectOnload();
        actionButton();
        departmentOnload();
        userOnload();
//        factionOnload();
    }

    private void newObjectOnload(){
        msDepartmentModel = new MSDepartmentModel();
        staffModel = new StaffModel();
        factionModel = new FactionModel();
        menuObjectModel = new MenuObjectModel();
        staffRolesModel = new StaffRolesModel();
        objectUserAuthorize = new MenuObjectModel();
//        msTitleModel = new MSTitleModel();
    }

    private void actionButton(){
        modeUserManage = "Mode = Search  ";
        flagBtnEdit = true;
        flagBtnDelete = true;
        flagPrint = true;
        flagUserAccess = true;
        keySearchUser = "";
        modeBtnAddUser = "";
        keySearchUserAccessDialog = "";
        keySearchUserAuthorize = "";
        keySearchRole = "";
    }

    private void departmentOnload(){
        msDepartmentModelList = userManagementService.getDepartAll();
    }


    private void userOnload(){
        staffModelList = userManagementService.getUserAll();
    }

    public void test(){
        log.debug("######## {}", roleId);
    }

    public void onChangeSearchMenu(String target){
        log.debug("Target : {}", target);
        if ("department".equalsIgnoreCase(target)){
            factionModelList = userManagementService.getFactionByDepartment(msDepartmentModel.getId());
        }

        staffModelList = userManagementService.getUserBySearch(msDepartmentModel.getId(), factionModel.getId(), keySearchUser);
        actionButton();
    }

    public void onClickUserAccess(){
        log.debug("staffModel : {}", staffModel.toString());
        modeUserManage = "Mode = Edit  ";
        modeBtnAddUser = "Edit";
        flagBtnEdit = false;
        flagBtnDelete = false;
        flagPrint = false;
        flagUserAccess = false;
    }

    public void preDelete(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Please click Yes to confirm delete this UserAccess.", "confirmUserAccessDlg");
    }

    public void onDeleteUserAccess(){
        userManagementService.delete(staffModel);
        showDialogDeleted();
        userOnload();
    }

    public void onClickNewUser(String value){
        msTitleModelList = userManagementService.getTitleAll();
        departmentDialogList = userManagementService.getDepartAll();

        if (value.equalsIgnoreCase("New")){
            userView = new UserView();
        } else if (value.equalsIgnoreCase("Edit")){
            userView = userManagementService.setModelToViewUserAccess(staffModel);
            factionDialogList = userManagementService.getFactionByDepartment(userView.getFactionModel().getMsDepartmentModel().getId());
            userView.setMsTitleModel(userManagementService.getTitleById(staffModel.getMsTitleModel().getId()));
            log.debug("#### {}", userView.toString());
        }

    }

    public void onChangeDepartment(){
        factionDialogList = userManagementService.getFactionByDepartment(userView.getFactionModel().getMsDepartmentModel().getId());
    }

    public void onClickSaveUserAccessDialog(){
        userManagementService.onSaveUserAccess(userView);
        onLoad();
        if (Utils.isZero(userView.getId())){
            showDialogSaved();
        } else {
            showDialogUpdated();
        }
    }

    public void onCancel(){
        newObjectOnload();
        actionButton();
        departmentOnload();
        userOnload();
    }

    public void onClickUserAccessDialog(){
        menuObjectModelList = userManagementService.getMenuObjectByObjCategory();
        menuObjectModelTableList = userManagementService.getMenuObjectAll();
        userAuthorizeView = userManagementService.setModelToViewUserAccess(staffModel);
        staffRolesModelList = userManagementService.getStaffRoleByUserId(staffModel.getId());
        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
        objectUserAuthorizeList = userManagementService.getMenuObjectByObjCategory();;
    }

    public void onChangeMenuObject(){
        menuObjectModelTableList = userManagementService.getMenuObjectId(menuObjectModel.getId(), keySearchUserAccessDialog);
    }

    public void onAddToUserAuthorize(){
        log.debug("selectList Size : {}", selectList.size());

        if (!Utils.isSafetyList(selectList)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Please select Menu Object and Action.", "msgBoxSystemMessageDlg");
        } else {
            userManagementService.onSaveUserAccess(selectList, staffModel);
            showDialogSaved();
        }

        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
    }

    public void onRemoveUserAuthorize(){
        StringBuilder selectValue = new StringBuilder();
        for (UserAccessModel model : selectUserAuthorize){
            selectValue = selectValue.append(model.getMenuObjectModel().getCode()).append(" ");
        }
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes to delete " + selectValue.toString(), "confirmRemoveUserAuthorizeDlg");
    }

    public void onDeleteUserAuthorize(){
        log.debug("selectUserAuthorize Size : {}", selectUserAuthorize.size());

        for (UserAccessModel userAccessModel : selectUserAuthorize){
            userManagementService.deleteUserAuthorize(userAccessModel);
        }

        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
    }

    public void onFilterUserAuthorize(){
        userAccessModelList = userManagementService.getUserAuthorizeByMenuObjOrKey(objectUserAuthorize.getId(), keySearchUserAuthorize);
    }

    public void onPopupRole(){
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes to confirm delete?", "confirmRemoveRoleDlg");
    }

    public void onRemoveRole(){
        userManagementService.deleteRole(roleId);
        staffRolesModelList = userManagementService.getStaffRoleByUserId(staffModel.getId());
    }

    public void onLoadRoleDialog(){
        systemRoleModelDialogList = userManagementService.getLoadUserRole();
        selectRole = new ArrayList<SystemRoleModel>();
    }

    public void onFilterRole(){
        log.debug("keySearchRole {}", keySearchRole);
        systemRoleModelDialogList = userManagementService.getRoleByKey(keySearchRole);
    }

    public void onAddToUserByDialogRole(){

        if (!Utils.isSafetyList(selectRole)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Please select role.", "msgBoxSystemMessageDlg");
        } else {
            userManagementService.onSaveRole(selectRole, staffModel);
            showDialogSaved();
            staffRolesModelList = userManagementService.getStaffRoleByUserId(staffModel.getId());
        }
    }
}
