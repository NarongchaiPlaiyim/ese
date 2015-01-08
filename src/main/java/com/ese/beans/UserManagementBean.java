package com.ese.beans;

import com.ese.model.db.*;
import com.ese.model.view.UserView;
import com.ese.service.UserManagementService;
import com.ese.service.security.UserDetail;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ManagedBean(name = "userManagement")
@ViewScoped
public class UserManagementBean extends Bean{
    private static final long serialVersionUID = 4312578634029874840L;
    @ManagedProperty("#{userManagementService}") private UserManagementService userManagementService;
    @ManagedProperty("#{message['authorize.menu.user']}") private String key;

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

    //Root RoleAccessMode
    private TreeNode rootMenuObj;
    private TreeNode rootAccessModel;
    //Select Root
    private TreeNode[] selectRootMenuObj;
    private TreeNode[] selectRootUserAuthorize;

    Map<Integer, CheckboxTreeNode> treeNodeMenuObjMap;
    Map<Integer, CheckboxTreeNode> treeNodeUserAccessMap;

    //Add User Dialog
    private List<MSDepartmentModel> departmentDialogList;
    private List<FactionModel> factionDialogList;
    private boolean flagDepartment;
    private boolean flagLoginName;
    private boolean flagFaction;
    private boolean flagTitle;
    private boolean flagUserName;
    private boolean flagPosition;
    private String value;

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

    private UserDetail userDetail;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad() && isAuthorize(key)){
            init();
        }
    }

    private void init(){
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
        rootMenuObj = new CheckboxTreeNode();
        rootAccessModel = new CheckboxTreeNode();
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

//    public void test(){
//        log.debug("######## {}", roleId);
//    }

    public void onChangeSearchMenu(String target){
        log.debug("Target : {}", target);
        if ("department".equalsIgnoreCase(target)){
            factionModelList = userManagementService.getFactionByDepartment(msDepartmentModel.getId());
        }

        staffModelList = userManagementService.getUserBySearch(msDepartmentModel.getId(), factionModel.getId(), keySearchUser);
        actionButton();
        staffRolesModel = new StaffRolesModel();
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
        actionButton();
    }

    public void onClickNewUser(){
        msTitleModelList = userManagementService.getTitleAll();
        departmentDialogList = userManagementService.getDepartAll();

        if (value.equalsIgnoreCase("New")){
            flagDepartment = false;
            flagLoginName = false;
            flagFaction = false;
            flagTitle = false;
            flagUserName = false;
            flagPosition = false;
            userView = new UserView();
        } else if (value.equalsIgnoreCase("Edit")){
            flagDepartment = true;
            flagLoginName = true;
            flagFaction = true;
            flagTitle = true;
            flagUserName = true;
            flagPosition = true;
            userView = userManagementService.setModelToViewUserAccess(staffModel);
            factionDialogList = userManagementService.getFactionByDepartment(userView.getFactionModel().getMsDepartmentModel().getId());
//            userView.setMsTitleModel(userManagementService.getTitleById(staffModel.getMsTitleModel().getId()));
        }

    }

    public void onChangeDepartment(){
        factionDialogList = userManagementService.getFactionByDepartment(userView.getFactionModel().getMsDepartmentModel().getId());
    }

    public void onClickSaveUserAccessDialog(){
        System.out.println(userView.getUsername());

        if (value.equalsIgnoreCase("New") && !userManagementService.isExisted(userView.getUsername())){
            userManagementService.onSaveUserAccess(userView);
            showDialogSaved();
            init();
        } else if (value.equalsIgnoreCase("Edit")){
            userManagementService.onUpdataeUserAccess(userView);
            init();
            showDialogUpdated();
        } else {
            showDialogWarning("Already existed login name");
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
        rootMenuObj = creRootMenuObj();
        userAuthorizeView = userManagementService.setModelToViewUserAccess(staffModel);
        staffRolesModelList = userManagementService.getStaffRoleByUserId(staffModel.getId());
        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
        rootAccessModel = creRootUserAccess();
        objectUserAuthorizeList = userManagementService.getMenuObjectByObjCategory();
        selectList = new ArrayList<MenuObjectModel>();
    }

    public void onChangeMenuObject(){
        menuObjectModelTableList = userManagementService.getMenuObjectId(menuObjectModel.getId(), keySearchUserAccessDialog);
        rootMenuObj = creRootMenuObj();
    }

    private TreeNode creRootUserAccess() {
        TreeNode root = new CheckboxTreeNode(new Document(0, "", ""), null);
        log.debug("userAccessModelList Size : ", userAccessModelList.size());
        final int MENU = 1;
        final int TAB = 2;
        final int ACTION = 3;

        treeNodeUserAccessMap = new HashMap<Integer, CheckboxTreeNode>();
        for (UserAccessModel model : userAccessModelList) {
            if ( !Utils.isNull(model) ) {
                if ( !Utils.isZero(Utils.parseInt(model.getMenuObjectModel().getCode().length(), 0)) && model.getMenuObjectModel().getObjCategory() == MENU ) {
                    treeNodeUserAccessMap.put(model.getMenuObjectModel().getId(), new CheckboxTreeNode(new Document(model.getId(), model.getMenuObjectModel().getCode(), model.getMenuObjectModel().getName()), root));
                } else if ( model.getMenuObjectModel().getObjCategory() == TAB ) {
                    treeNodeUserAccessMap.put(model.getMenuObjectModel().getId(), new CheckboxTreeNode(new Document(model.getId(), model.getMenuObjectModel().getCode(), model.getMenuObjectModel().getName()), treeNodeUserAccessMap.get(model.getMenuObjectModel().getParentId())));
                } else if ( model.getMenuObjectModel().getObjCategory() == ACTION ) {
                    new CheckboxTreeNode(new Document(model.getId(), model.getMenuObjectModel().getCode(), model.getMenuObjectModel().getName()), treeNodeUserAccessMap.get(model.getMenuObjectModel().getParentId()));
                }
            }
        }

        return root;
    }

    private TreeNode creRootMenuObj() {
        TreeNode root = new CheckboxTreeNode(new Document(0, "", ""), null);
        log.debug("menuObjectModelTableList Size : ", menuObjectModelTableList.size());
        final int MENU = 1;
        final int TAB = 2;
        final int ACTION = 3;

        treeNodeMenuObjMap = new HashMap<Integer, CheckboxTreeNode>();
        for (MenuObjectModel model : menuObjectModelTableList) {
            if ( !Utils.isNull(model) ) {
                if ( !Utils.isZero(Utils.parseInt(model.getCode().length(), 0)) && model.getObjCategory() == MENU ) {
                    treeNodeMenuObjMap.put(model.getId(), new CheckboxTreeNode(
                            new Document(model.getId(), model.getCode(), model.getName()), root));
                } else if ( model.getObjCategory() == TAB ) {
                    treeNodeMenuObjMap.put(model.getId(), new CheckboxTreeNode(
                            new Document(model.getId(), model.getCode(), model.getName()), treeNodeMenuObjMap.get(model.getParentId())));
                } else if ( model.getObjCategory() == ACTION ) {
                    new CheckboxTreeNode(new Document(model.getId(), model.getCode(), model.getName()), treeNodeMenuObjMap.get(model.getParentId()));
                }
            }
        }

        return root;
    }

    public List<Document> selectOnAdd(){
        List<Document> documents = new ArrayList<Document>();
        Document document = null;

        if (!Utils.isNull(selectRootMenuObj)){
            for (TreeNode node : selectRootMenuObj){
                document = new Document(0, "", "");
                document = (Document) node.getData();
                documents.add(document);
            }
        }

        return documents;
    }

    public void onAddToUserAuthorize(){
        log.debug("selectList Size : {}", selectList.size());

        if (Utils.isNull(selectRootMenuObj)){
            showDialog(MessageDialog.WARNING.getMessageHeader(), "Please select Menu Object and Action.", "msgBoxSystemMessageDlg");
        } else {
            userManagementService.onSaveUserAccess(selectOnAdd(), staffModel);
            showDialogSaved();
            selectList = new ArrayList<MenuObjectModel>();
        }

        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
        rootAccessModel = creRootUserAccess();
    }

    public List<Document> selectOnRemove(){
        List<Document> documents = new ArrayList<Document>();
        Document document = null;

        if (!Utils.isNull(selectRootUserAuthorize)){
            for (TreeNode node : selectRootUserAuthorize){
                document = new Document(0, "", "");
                document = (Document) node.getData();
                documents.add(document);
            }
        }

        return documents;
    }

    public void onRemoveUserAuthorize(){
        StringBuilder selectValue = new StringBuilder();
        for (Document model : selectOnRemove()){
            selectValue = selectValue.append(model.getCode()).append(" ");
        }
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Click Yes to delete " + selectValue.toString(), "confirmRemoveUserAuthorizeDlg");
    }

    public void onDeleteUserAuthorize(){
//        log.debug("selectRootUserAuthorize length : {}", selectRootUserAuthorize.length);

        if (!Utils.isNull(selectRootUserAuthorize)){
            for (Document document : selectOnRemove()){
                userManagementService.deleteUserAuthorize(document);
            }
        }

        userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
        rootAccessModel = creRootUserAccess();
    }

    public void onFilterUserAuthorize(){
        userAccessModelList = userManagementService.getUserAuthorizeByMenuObjOrKey(objectUserAuthorize.getId(), keySearchUserAuthorize);
        rootAccessModel = creRootUserAccess();
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
            userAccessModelList = userManagementService.getMenuObjectByUserId(staffModel.getId());
            rootAccessModel = creRootUserAccess();
        }
    }

    public void onPrint(){
        userDetail = getUser();
        userManagementService.printReportUserAndRole(userDetail);
    }
}
