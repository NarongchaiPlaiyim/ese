package com.ese.utils;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class FacesUtil implements Serializable {
    private static final long serialVersionUID = 553394611368789880L;
    private static Logger log = LoggerFactory.getLogger(FacesUtil.class);

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    public static HttpSession getSession(boolean createNewSession) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(createNewSession);
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static void showDialog(String nameOfDialog){
        RequestContext.getCurrentInstance().execute("PF('"+nameOfDialog+"').show()");
    }


    public static void redirect(String uriPath) {
        String contextPath = "";
        try {
            ExternalContext ec = getExternalContext();
            contextPath = ec.getRequestContextPath();
            String url = contextPath.concat(uriPath);
            log.debug("redirect to url: {}", url);
            ec.redirect(url);
        } catch (Exception e) {
            log.error("Exception while redirection! (contextPath: {}, uriPath: {})", contextPath, uriPath);
            log.error("", e);
        }
    }
}
