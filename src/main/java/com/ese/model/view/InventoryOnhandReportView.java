package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class InventoryOnhandReportView {
    private String itemCode;
    private String itemDesc;
    private String warehouseName;
    private String locationBarcode;
    private String batchNo;
    private AvailivbleView availableView;
    private ReservedView reservedView;
    private PickView pickView;
    private PackView packView;
    private PhysicalView physicalView;

    private int no;
    private int availableQty;
    private int availableAmount;
    private int reservedQty;
    private int reservedAmount;
    private int pickQty;
    private int pickAmount;
    private int packQty;
    private int packAmount;
    private int physicalQty;
    private int physicalAmount;

    public InventoryOnhandReportView() {
        availableView = new AvailivbleView();
        reservedView = new ReservedView();
        pickView = new PickView();
        packView = new PackView();
        physicalView = new PhysicalView();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("itemCode", itemCode)
                .append("itemDesc", itemDesc)
                .append("warehouseName", warehouseName)
                .append("locationBarcode", locationBarcode)
                .append("batchNo", batchNo)
                .append("availableView", availableView)
                .append("reservedView", reservedView)
                .append("pickView", pickView)
                .append("packView", packView)
                .append("physicalView", physicalView)
                .append("no", no)
                .append("availableQty", availableQty)
                .append("availableAmount", availableAmount)
                .append("reservedQty", reservedQty)
                .append("reservedAmount", reservedAmount)
                .append("pickQty", pickQty)
                .append("pickAmount", pickAmount)
                .append("packQty", packQty)
                .append("packAmount", packAmount)
                .append("physicalQty", physicalQty)
                .append("physicalAmount", physicalAmount)
                .toString();
    }



}
