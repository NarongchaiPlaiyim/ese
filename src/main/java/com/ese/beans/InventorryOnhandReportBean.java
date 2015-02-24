package com.ese.beans;

import com.ese.model.view.InventorryOnhandReportView;
import com.ese.service.InventorryOnhandReportService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "inventoryOnhandReportBean")
public class InventorryOnhandReportBean extends Bean{
    private static final long serialVersionUID = 4112578334029874840L;
    @ManagedProperty("#{onhandReportService}") private InventorryOnhandReportService onhandReportService;
    @ManagedProperty("#{message['authorize.menu.barcode']}") private String key;

    private List<InventorryOnhandReportView> onhandReportViewList;
    private List<InventorryOnhandReportView> filterOnhandReport;

    @PostConstruct
    private void init(){

    }
}
