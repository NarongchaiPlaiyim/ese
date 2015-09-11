package com.ese.beans;

import com.ese.service.security.UserDetail;
import com.ese.service.*;
import com.ese.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class Bean implements Serializable {
    @ManagedProperty("#{indexService}") protected IndexService indexService;
    @ManagedProperty("#{log}") protected Logger log;
    @ManagedProperty("#{moLogger}") protected Logger moLogger;
    @ManagedProperty("#{mtLogger}") protected Logger mtLogger;

    private String messageHeader;
    private String message;
    private UserDetail userDetail;

    protected void showDialogError(String message){
        showDialog(MessageDialog.ERROR.getMessageHeader(), message);
    }

    protected void showDialogWarning(String message){
        showDialog(MessageDialog.WARNING.getMessageHeader(), message);
    }

    protected void showDialogUpdated(){
        showDialog(MessageDialog.UPDATE.getMessageHeader(), MessageDialog.UPDATE.getMessage());
    }

    protected void showDialogSaved(){
        showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
    }

    protected void showDialogEdited(){
        showDialog(MessageDialog.EDIT.getMessageHeader(), MessageDialog.EDIT.getMessage());
    }

    protected void showDialogCreated(){
        showDialog(MessageDialog.CREATE.getMessageHeader(), MessageDialog.CREATE.getMessage());
    }
    protected void showDialogClosed(){
        showDialog(MessageDialog.POST.getMessageHeader(), MessageDialog.POST.getMessage());
    }

    protected void showDialogDeleted(){
        showDialog(MessageDialog.DELETE.getMessageHeader(), MessageDialog.DELETE.getMessage());
    }

    protected void showDialog(String messageHeader, String message){
        setMessageHeader(messageHeader);
        setMessage(message);
        FacesUtil.showDialog(NamesUtil.DIALOG_NAME.getName());
    }

    protected void showDialog(String messageHeader, String message, String dialogName){
        setMessageHeader(messageHeader);
        setMessage(message);
        FacesUtil.showDialog(dialogName);
    }

    protected void showDialog(String dialogName){
        FacesUtil.showDialog(dialogName);
    }

    protected boolean preLoad(){
        boolean result = true;
        try{
            UserDetail userDetail = (UserDetail) FacesUtil.getSession(false).getAttribute(AttributeName.USER_DETAIL.getName());
            if(Utils.isNull(userDetail)){
                FacesUtil.redirect(NamesUtil.LOGIN_PAGE.getName());
                result = false;
            }
            setUserDetail(userDetail);
            return result;
        } catch (Exception e) {
            FacesUtil.redirect(NamesUtil.LOGIN_PAGE.getName());
            return false;
        }
    }

    protected boolean isAuthorize(String key){
        boolean result = true;
        try {
            Map<String,String> map = (Map<String, String>) FacesUtil.getSession(false).getAttribute(AttributeName.AUTHORIZE.getName());
            if(!map.containsKey(key)){
                FacesUtil.redirect(NamesUtil.MAIN_PAGE.getName());
                result = false;
            }
            return result;
        } catch (Exception e) {
            FacesUtil.redirect(NamesUtil.LOGIN_PAGE.getName());
            return false;
        }
    }

    protected UserDetail getUser(){
        if(!Utils.isNull(this.userDetail)){
            return userDetail;
        } else {
            return (UserDetail)FacesUtil.getSession(false).getAttribute(AttributeName.USER_DETAIL.getName());
        }
    }
}
