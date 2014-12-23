package com.ese.beans;

import com.ese.service.BarcodePrintingService;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodePrintingBean")
public class BarcodePrintingBean extends Bean {
    private static final long serialVersionUID = 4112578634029879990L;
    @ManagedProperty("#{barcodePrintingService}") private BarcodePrintingService barcodePrintingService;

    private String startBarcode;
    private int qty;
    private String finishBarcode;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){ //&& isAuthorize("0100")){
            init();
        }
    }

    private void init(){
        barcodePrintingService.getLastSeq();//setting to start barcode
        setStartBarcode("TW00000009");

        setFinishBarcode("TW00000009");
    }

    public void onClickOk(){
        if(!Utils.isZero(getQty())){
            //before print we have to check seq. of start barcode for other user may click ok by the same start barcode.
            barcodePrintingService.onPrintBarcode(startBarcode, qty);
        } else {
            showDialogWarning("QTY must more than 0.");
        }
    }

    public void calculator(){
        final int start = Utils.parseInt(replaceFormat(getStartBarcode()), 0);
        final int finish = (getQty() + start) - 1;
        final String result = finish > 99999999 ? "99999999" : String.format("%08d", finish);
        setFinishBarcode("TW"+ result);
    }

    private String replaceFormat(String startBarcode){
        return startBarcode.replace("TW", "");
    }
}
