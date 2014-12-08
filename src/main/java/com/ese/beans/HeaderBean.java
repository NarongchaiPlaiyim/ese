package com.ese.beans;

import com.ese.service.security.UserDetail;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "headerBean")
public class HeaderBean extends Bean{
    private UserDetail userDetail;
    private String fullName;

    @PostConstruct
    private void onCreation(){
        init();
    }

    private void init(){
        userDetail = (UserDetail) FacesUtil.getSession(false).getAttribute(AttributeName.USER_DETAIL.getName());
        if(!Utils.isNull(userDetail)){
            fullName = ""+userDetail.getFullName();
        }
    }


    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        SecurityContextHolder.clearContext();
        return "loggedOut";
    }
}
