package com.ese.beans;

import com.ese.security.UserDetail;
import com.ese.service.*;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

import javax.faces.bean.ManagedProperty;
import java.io.Serializable;

@Getter
@Setter
public abstract class Bean implements Serializable {
    @ManagedProperty("#{indexService}") protected IndexService indexService;

    @ManagedProperty("#{sessionRegistry}") protected SessionRegistry sessionRegistry;
    @ManagedProperty("#{sas}") protected CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;
    @ManagedProperty("#{log}") protected Logger log;
    @ManagedProperty("#{moLogger}") protected Logger moLogger;
    @ManagedProperty("#{mtLogger}") protected Logger mtLogger;
    @ManagedProperty("#{warehouseService}") protected WarehouseService warehouseService;
    @ManagedProperty("#{conveyorLineService}") protected ConveyorLineService conveyorLineService;

    @ManagedProperty("#{workingAreaService}") protected WorkingAreaService workingAreaService;
    @ManagedProperty("#{locationItemService}") protected LocationItemService locationItemService;
    @ManagedProperty("#{locationService}") protected LocationService locationService;

    private String messageHeader;
    private String message;
    private UserDetail userDetail;

    protected void showDialogError(String message){
        showDialog(MessageDialog.ERROR.getMessageHeader(), message);
    }

    protected void showDialog(String messageHeader, String message){
        setMessageHeader(messageHeader);
        setMessage(message);
        FacesUtil.showDialog("msgBoxSystemMessageDlg");
    }

    protected void preLoad(){
        try{
            UserDetail userDetail = (UserDetail) FacesUtil.getSession(false).getAttribute(AttributeName.USER_DETAIL.getName());
            if(Utils.isNull(userDetail)){
                FacesUtil.redirect("/login.xhtml");
            }
            setUserDetail(userDetail);
        } catch (Exception e) {
            FacesUtil.redirect("/login.xhtml");
        }
    }
}
