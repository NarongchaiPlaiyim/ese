package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StockViewReport {
    private int id;
    private String docNo;
    private Date docDate;
    private String inoutCode;
    private String remark;
}
