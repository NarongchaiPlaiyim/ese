package com.ese.model.view;

import com.ese.model.db.PickingOrderModel;
import com.ese.model.db.StatusModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class CustomerConfirmTransView {

    private BigDecimal lineNum;
    private String itemId;
    private String origSaleId;
    private int qty;
    private Date shipDate;
    private String salesUnit;
    private String dSGSubGroupDescription;
    private String pIDescription;
    private String dSGExtItemNO;
    private String name;
    private BigDecimal dSGPackingQty;
    private String salesUnitTxt;
    private BigDecimal cum;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("lineNum", lineNum)
                .append("itemId", itemId)
                .append("origSaleId", origSaleId)
                .append("qty", qty)
                .append("shipDate", shipDate)
                .append("salesUnit", salesUnit)
                .append("dSGSubGroupDescription", dSGSubGroupDescription)
                .append("pIDescription", pIDescription)
                .append("dSGExtItemNO", dSGExtItemNO)
                .append("name", name)
                .append("dSGPackingQty", dSGPackingQty)
                .append("salesUnitTxt", salesUnitTxt)
                .append("cum", cum)
                .toString();
    }
}
