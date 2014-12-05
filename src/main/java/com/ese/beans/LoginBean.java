package com.ese.beans;

import com.ese.model.db.StaffModel;
import com.ese.security.SimpleAuthenticationManager;
import com.ese.security.encryption.EncryptionService;
import com.ese.service.LoginService;
import com.ese.utils.AttributeName;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import com.ese.security.UserDetail;
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
import java.io.Serializable;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "loginBean")
public class LoginBean extends Bean{
    @ManagedProperty("#{loginService}") private LoginService loginService;
    @ManagedProperty("#{sessionRegistry}") private SessionRegistry sessionRegistry;
    @ManagedProperty("#{sas}") private CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;

    private String userName = "";
    private String password = "";
    private UserDetail userDetail;
    private String s = "";
    private String f = "";
    private String url = "http://primefaces-extensions.github.io";

    @PostConstruct
    private void init(){
        if(!Utils.isNull(SecurityContextHolder.getContext().getAuthentication())){
            System.out.println("old user");
            userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            System.out.println(userDetail.hashCode());
        } else {
            System.out.println("new user");
        }
    }

    public String login(){
        log.info("-- SessionRegistry principle size: {}", sessionRegistry.getAllPrincipals().size());
        if(!Utils.isZero(userName.length()) && !Utils.isZero(password.length())) {
            setPassword(EncryptionService.encryption(password));
//            System.out.println("password : "+password);
            if(true){//loginService.isUserExist(getUserName(), getPassword())){
                StaffModel staffModel = loginService.getStaffModel();
                userDetail = new UserDetail(userName,
                                            password,
                                            "USER",  //staffModel.getRole(),
                                            "",
                                            "");
                HttpServletRequest httpServletRequest = FacesUtil.getRequest();
                HttpServletResponse httpServletResponse = FacesUtil.getResponse();
                UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(getUserDetail(), getPassword());
                request.setDetails(new WebAuthenticationDetails(httpServletRequest));
                SimpleAuthenticationManager simpleAuthenticationManager = new SimpleAuthenticationManager();
                Authentication result = simpleAuthenticationManager.authenticate(request);
                log.debug("-- authentication result: {}", result.toString());
//                System.out.println("Result : " + result.hashCode());
//                System.out.println("Result : "+result.toString());
                SecurityContextHolder.getContext().setAuthentication(result);
                compositeSessionAuthenticationStrategy.onAuthentication(request, httpServletRequest, httpServletResponse);
                HttpSession httpSession = FacesUtil.getSession(false);
                httpSession.setAttribute(AttributeName.USER_DETAIL.getName(), getUserDetail());
                System.out.println(userDetail.hashCode());
                log.debug("-- userDetail[{}]", userDetail.toString());
                return "USER";//<-- for test  //userDetail.getRole();<-- for production.
            }
        }
        showDialog(MessageDialog.WARNING.getMessageHeader(), "Invalid username or password.");
        return "loggedOut";
    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        SecurityContextHolder.clearContext();
        return "loggedOut";
    }

    public void test(){
        System.out.println("test");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        loginService.test(s, f);
    }

    public void updateURL(){
        System.out.println("updateURL");
        url = "http://www.primefaces.org/showcase-ext/views/layout.jsf";
    }

}
