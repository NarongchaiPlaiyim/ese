package com.ese.model.view;

import com.ese.model.db.LoadingOrderModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class ContainerView {
    private int id;
    private LoadingOrderModel loadingOrderModel;
    private String containerNo;
    private String sealNo;
    private int quantity;
    private Integer createBy;
    private Date createDate;
    private Integer updateBy;
    private Date updateDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("loadingOrderModel", loadingOrderModel)
                .append("containerNo", containerNo)
                .append("sealNo", sealNo)
                .append("quantity", quantity)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .toString();
    }
}
