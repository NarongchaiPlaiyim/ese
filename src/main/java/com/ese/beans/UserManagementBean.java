package com.ese.beans;

import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.UserView;
import com.ese.service.UserManagementService;
import com.ese.utils.MessageDialog;
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

    //Add User Dialog
    private List<MSDepartmentModel> departmentDialogList;
    private MSDepartmentModel departDialog;
    private List<FactionModel> factionDialogList;
    private FactionModel factionDialog;

    private boolean flagBtnEdit;
    private boolean flagBtnDelete;
    private boolean flagPrint;
    private boolean flagUserAccess;
    private String keySearchUserAccess;


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
    }

    private void actionButton(){
        modeUserManage = "Mode = Search  ";
        flagBtnEdit = true;
        flagBtnDelete = true;
        flagPrint = true;
        flagUserAccess = true;
        keySearchUserAccess = "";
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
        log.debug("########## {}, {}, {}", msDepartmentModel.getId(), factionModel.getId(), keySearchUserAccess);
    }

    public void onChangeSearchMenu(String target){
        log.debug("Target : {}", target);
        if ("department".equalsIgnoreCase(target)){
            factionModelList = userManagementService.getFactionByDepartment(msDepartmentModel.getId());
        }

        staffModelList = userManagementService.getUserBySearch(msDepartmentModel.getId(), factionModel.getId(), keySearchUserAccess);
    }

    public void onClickUserAccess(){
        log.debug("staffModel : {}", staffModel.toString());
        modeUserManage = "Mode = Edit  ";
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
        userOnload();
    }

    public void onClickNewUser(){
        userView = new UserView();
    }
}
