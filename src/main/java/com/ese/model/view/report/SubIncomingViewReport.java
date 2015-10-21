package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubIncomingViewReport {
    private int stockInoutId;
    private String palletBarcode;
    private String snBarcode;
    private String batchNo;
    private String itemNo;
    private String itemDesc;
    private int no;
    private String itemInternal;
    private String warehouseBarcode;
    private String locationBarcode;
    private int qty;
    private String workingName;
}
