package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMovementInView {
    private int id;
    private String itemId;
    private String itemDesc;
    private String barcode;
    private int status;
}
