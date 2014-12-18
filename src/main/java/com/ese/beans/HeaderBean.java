package com.ese.beans;

import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.MSTitleModel;
import com.ese.model.db.StaffModel;
import com.ese.model.view.UserView;
import com.ese.service.UserManagementService;
import com.ese.service.security.UserDetail;
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
    private UserView userView;

    @PostConstruct
    private void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){
            init();
        }
    }

    private void init(){
        userDetail = getUser();
        log.debug("userDetail {}", userDetail.toString());
        onLoadUserView();
        if(!Utils.isNull(userDetail)){
            fullName = ""+userDetail.getFullName();
        }
    }

    private void onLoadUserView(){
        userView = userManagementService.getUserName(userDetail.getUserName());
        userView.setPassword("");
    }

    public void onClickSave(){
        System.out.println("onClickSave()");
//        userManagementService.onChangePassword(staffModel);
        userManagementService.onUpdataeUserAccess(userView);
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
