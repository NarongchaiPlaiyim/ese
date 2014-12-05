package com.ese.beans;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@ManagedBean(name = "configurationSize")
@ViewScoped
public class ConfigurationSize {
    @Value("#{config['btn.size']}") private String btnSize;
    @Value("#{config['fix.size.100']}") private String fixSize100;
    @Value("#{config['fix.size.150']}") private String fixSize150;
    @Value("#{config['fix.size.200']}") private String fixSize200;
    @Value("#{config['dialog.message.size']}") private String dialogMessageSize;
}
