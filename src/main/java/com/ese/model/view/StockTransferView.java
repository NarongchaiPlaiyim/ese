package com.ese.model.view;

import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class StockTransferView {
    private String docNo;
    private Date formDate;
    private Date toDate;
    private int docNoteId;

    public StockTransferView() {
        formDate = Utils.currentDate();
        toDate = Utils.currentDate();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("docNo", docNo)
                .append("formDate", formDate)
                .append("toDate", toDate)
                .append("docNoteId", docNoteId)
                .toString();
    }
}
