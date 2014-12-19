package com.ese.beans;

import com.ese.service.message.MessageProvider;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@ApplicationScoped
@ManagedBean(name = "msg")
public class MessageBean extends MessageProvider {
    private static final long serialVersionUID = 4112578684029874840L;
    private ResourceBundle resourceBundle;
    @PostConstruct
    public void onCreation() {
        resourceBundle = getBundle();
    }

    @Override
    public String get(String key) {
        String message;
        try {
            message = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            message = "???";
        }
        return message;
    }
}
