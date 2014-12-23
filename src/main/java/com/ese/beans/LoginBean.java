package com.ese.beans;

import com.ese.model.db.StaffModel;
import com.ese.service.BarcodePrintingService;
import com.ese.service.security.SimpleAuthenticationManager;
import com.ese.service.security.encryption.EncryptionService;
import com.ese.service.LoginService;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import com.ese.service.security.UserDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "loginBean")
public class LoginBean extends Bean{
    private static final long serialVersionUID = 4112578634029374840L;
    @ManagedProperty("#{loginService}") private LoginService loginService;
    @ManagedProperty("#{sessionRegistry}") private SessionRegistry sessionRegistry;
    @ManagedProperty("#{sas}") private CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;

    @ManagedProperty("#{barcodePrintingService}") private BarcodePrintingService barcodePrintingService;
    private String userName = "";
    private String password = "";
    private UserDetail userDetail;
    private Map<String,String> map;

    @PostConstruct
    private void init(){
        if(!Utils.isNull(SecurityContextHolder.getContext().getAuthentication())){
            userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            map = (Map<String, String>) FacesUtil.getSession(false).getAttribute(AttributeName.AUTHORIZE.getName());
        } else {
            log.debug("[NEW] CODE MAP");
            map = new HashMap<String, String>();
        }
    }

    public String login(){
        log.info("-- SessionRegistry principle size: {}", sessionRegistry.getAllPrincipals().size());
        if(!Utils.isZero(userName.length()) && !Utils.isZero(password.length())) {
            setPassword(EncryptionService.encryption(password));
            if(loginService.isUserExist(getUserName(), getPassword())){
                StaffModel staffModel = loginService.getStaffModel();
                userDetail = new UserDetail(staffModel.getUsername(),
                                            staffModel.getPassword(),
                                            "USER",
                                            "Test",
                                            "TEst");
                userDetail.setId(Utils.parseLong(staffModel.getId(), 0L));
                HttpServletRequest httpServletRequest = FacesUtil.getRequest();
                HttpServletResponse httpServletResponse = FacesUtil.getResponse();
                UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(getUserDetail(), getPassword());
                request.setDetails(new WebAuthenticationDetails(httpServletRequest));
                SimpleAuthenticationManager simpleAuthenticationManager = new SimpleAuthenticationManager();
                Authentication result = simpleAuthenticationManager.authenticate(request);
                log.debug("-- authentication result: {}", result.toString());
                SecurityContextHolder.getContext().setAuthentication(result);
                compositeSessionAuthenticationStrategy.onAuthentication(request, httpServletRequest, httpServletResponse);
                HttpSession httpSession = FacesUtil.getSession(false);
                httpSession.setAttribute(AttributeName.USER_DETAIL.getName(), getUserDetail());
                httpSession.setAttribute(AttributeName.AUTHORIZE.getName(), loginService.getAuthorize());
                log.debug("-- userDetail[{}]", userDetail.toString());
                return "PASS";
            }
        }
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Invalid username or password.");
        return "loggedOut";
    }

    public boolean isRendered(String key){
        try {
            return map.containsKey(key);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            return false;
        }
    }

    public void test(){
        System.out.println("test");
        barcodePrintingService.insert();
//        try {
////            loginService.getStaffModel()
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

}
