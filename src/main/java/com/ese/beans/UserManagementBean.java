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


    @PostConstruct
    private void onLoand(){
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
    }

    private void departmentOnload(){
        msDepartmentModelList = userManagementService.getDepartAll();
    }

//    private void factionOnload(){
//        factionModelList = userManagementService.getFactionAll();
//    }

    private void userOnload(){
        staffModelList = userManagementService.getUserAll();
    }

    public void test(){
//        log.debug("########## {}, {}, {}", msDepartmentModel.getId(), factionModel.getId(), keySearchUserAccess);
        log.debug("######## {}", userView.toString());
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
        onLoand();
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

    }

    public void onChangeMenuObject(){
        menuObjectModelTableList = userManagementService.getMenuObjectId(menuObjectModel.getId(), keySearchUserAccessDialog);
    }

    public void onAddToUserAuthorize(){
        log.debug("selectList Size : {}", selectList.size());

        if (!Utils.isSafetyList(selectList)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Please select Menu Object and Action.", "confirmUserAccessDlg");
        } else {
        }
    }
}
