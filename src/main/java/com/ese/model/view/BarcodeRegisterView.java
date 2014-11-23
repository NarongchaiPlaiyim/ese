package com.ese.model.view;

import com.ese.model.db.MSItemModel;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class BarcodeRegisterView {
    private int id;
    private MSItemModel msItemModel;
    private String documentNo;
    private Date documentDate;
    private String batchNo;
    private int qty;
    private int startBarcode;
    private int finishBarcode;
    private String remark;

    public BarcodeRegisterView() {
        init();
    }

    private void init(){
        msItemModel = new MSItemModel();
        Calendar calendar = Calendar.getInstance();
        documentNo = Utils.getDocumentNo();
        documentDate = Utils.currentDate();
        batchNo = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.WEEK_OF_YEAR);
        qty = 0;
        startBarcode = 0;
        finishBarcode = 0;
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
