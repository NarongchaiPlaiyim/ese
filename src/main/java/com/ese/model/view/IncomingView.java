package com.ese.model.view;

import com.ese.model.db.MSStockInOutNoteModel;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class IncomingView extends View{
    private int id;
    private String docNo;
    private Date docDate;
    private String remark;
    private int docNoteId;
    private Date formDate;
    private Date toDate;
    private MSStockInOutNoteModel msStockInOutNoteModel;

    public IncomingView() {
        docNo = Utils.getDocumentIn();
        docDate = Utils.currentDate();
        formDate = Utils.currentDate();
        toDate = Utils.currentDate();
        remark = "";
        msStockInOutNoteModel = new MSStockInOutNoteModel();
    }
}
