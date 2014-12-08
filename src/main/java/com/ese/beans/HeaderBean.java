package com.ese.beans;

import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.MSTitleModel;
import com.ese.model.db.StaffModel;
import com.ese.service.UserManagementService;
import com.ese.service.security.UserDetail;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.NamesUtil;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "headerBean")
public class HeaderBean extends Bean {
    @ManagedProperty("#{userManagementService}") private UserManagementService userManagementService;
    private UserDetail userDetail;
    private String fullName;
    private StaffModel staffModel;
    private String password;

    @PostConstruct
    private void onCreation(){
        init();
    }

    private void init(){
        userDetail = (UserDetail) FacesUtil.getSession(false).getAttribute(AttributeName.USER_DETAIL.getName());
        makeData();
        if(!Utils.isNull(userDetail)){
            fullName = ""+userDetail.getFullName();
        }
    }

    private void makeData(){
        staffModel = new StaffModel();
        MSTitleModel msTitleModel = new MSTitleModel();
        msTitleModel.setName("Mr");
        staffModel.setMsTitleModel(msTitleModel);
        staffModel.setName("ASUS DELL");
        staffModel.setPassword("abcd");
        staffModel.setUsername("TEST");
        FactionModel factionModel = new FactionModel();
        factionModel.setName("TESTFaction");
        MSDepartmentModel msDepartmentModel = new MSDepartmentModel();
        msDepartmentModel.setName("TESTDepartment");
        factionModel.setMsDepartmentModel(msDepartmentModel);
        staffModel.setPosition("TESTPosition");
        staffModel.setFactionModel(factionModel);
    }

    public void onClickSave(){
        System.out.println("onClickSave()");
//        userManagementService.onChangePassword(staffModel);
        init();
        showDialogUpdated();
    }

    public void onClickEdit(){
        FacesUtil.redirect(NamesUtil.EDIT_PROFILE_PAGE.getName());
    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        SecurityContextHolder.clearContext();
        return "loggedOut";
    }
}
