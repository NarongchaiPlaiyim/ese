package com.ese.model.view;

import com.ese.model.StatusBarcodeRegiterValue;
import com.ese.model.db.MSItemModel;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class BarcodeRegisterView extends View{
    private int id;
    private MSItemModel msItemModel;
    private String documentNo;
    private Date documentDate;
    private String batchNo;
    private int qty;
    private String startBarcode;
    private String finishBarcode;
    private String startBarcodeText;
    private String finishBarcodeText;
    private String remark;
    private StatusBarcodeRegiterValue status;
    private int isValid;
    private int version;
    private BigDecimal cost;

    public BarcodeRegisterView() {
        init();
    }

    private void init(){
        msItemModel = new MSItemModel();
        documentNo = Utils.getDocumentNo();
        documentDate = Utils.currentDate();
        batchNo = Utils.getBatchNo();
        qty = 0;
        startBarcode =  "";
        finishBarcode = "000000000";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("msItemModel", msItemModel)
                .append("documentNo", documentNo)
                .append("documentDate", documentDate)
                .append("batchNo", batchNo)
                .append("qty", qty)
                .append("startBarcode", startBarcode)
                .append("finishBarcode", finishBarcode)
                .append("remark", remark)
                .toString();
    }
}
