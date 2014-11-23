package com.ese.beans;



import com.ese.model.view.BarcodeRegisterView;
import com.ese.service.BarcodeRegisterService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodeRegisterBean")
public class BarcodeRegisterBean extends Bean{
//    @ManagedProperty("#{setupService}") private BarcodeRegisterService barcodeRegisterService;

    private BarcodeRegisterView barcodeRegisterView;

    @PostConstruct
    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
    }

    public void q(){
        System.out.println("q");
    }
    public void q2(){
        System.out.println("q2");
    }
    public void q3(){
        System.out.println("q3");
    }
    public void q4(){
        System.out.println("q4");
    }
    public void q5(){
        System.out.println("q5");
    }
    public void q6(){
        System.out.println("q6");
    }
}
