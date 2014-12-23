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
    private String lastBarcode;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        if(preLoad()){ //&& isAuthorize("0100")){
            init();
        }
    }

    private void init(){
        setLastBarcode(barcodePrintingService.getLastSeq());
        String formatBarcode = "TW"+String.format("%08d", Utils.parseInt(getLastBarcode(), 0));
        setStartBarcode(formatBarcode);
        setFinishBarcode(formatBarcode);
        setQty(0);
    }

    public void onClickOk(){
        if(!Utils.isZero(getQty())){
            if(getLastBarcode().equalsIgnoreCase(barcodePrintingService.getLastSeq())){
                barcodePrintingService.save(getQty(), replaceFormat(getStartBarcode()), replaceFormat(getFinishBarcode()));
                showDialogSaved();
                try {
                    barcodePrintingService.onPrintBarcode(getStartBarcode(), getQty());
                } catch (Exception e) {

                }

                onCreation();
            } else {
                showDialogWarning("Plz try again.");
                init();
            }
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
