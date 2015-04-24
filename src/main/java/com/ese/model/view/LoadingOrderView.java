package com.ese.model.view;

import com.ese.model.db.StatusModel;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class LoadingOrderView {
    private int id;
    private String docNo;
    private Date loadingDate;
    private String remark;
    private StatusModel statusModel;
    private String category;
    private Integer createBy;
    private Date createDate;
    private Integer updateBy;
    private Date updateDate;

    public LoadingOrderView() {
        id = 0;
        docNo = Utils.getDocumentDomesticLoad();
        loadingDate = Utils.currentDate();
        remark = "";
        statusModel = new StatusModel();
        category = "D";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("docNo", docNo)
                .append("loadingDate", loadingDate)
                .append("remark", remark)
                .toString();
    }
}
