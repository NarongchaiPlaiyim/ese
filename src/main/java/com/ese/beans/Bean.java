package com.ese.beans;

import com.ese.service.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

import javax.faces.bean.ManagedProperty;

@Getter
@Setter
public abstract class Bean {
    @ManagedProperty("#{indexService}") protected IndexService indexService;
    @ManagedProperty("#{loginService}") protected LoginService loginService;
    @ManagedProperty("#{sessionRegistry}") protected SessionRegistry sessionRegistry;
    @ManagedProperty("#{sas}") protected CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;
    @ManagedProperty("#{log}") protected Logger log;
    @ManagedProperty("#{moLogger}") protected Logger moLogger;
    @ManagedProperty("#{mtLogger}") protected Logger mtLogger;
    @ManagedProperty("#{warehouseService}") protected WarehouseService warehouseService;
    @ManagedProperty("#{conveyorLineService}") protected ConveyorLineService conveyorLineService;
    @ManagedProperty("#{palletService}") protected PalletService palletService;


}
