package com.ese.model.view;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMovementOutView {
    private int id;
    private String itemId;
    private String itemDesc;
    private String warehouse;
    private String location;
    private String batchNo;
    private String pallet;
    private String snBarcode;
    private int status;

}
