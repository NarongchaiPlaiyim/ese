package com.ese.model.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class View {
    private int createBy;
    private Date createDate;
    private int updateBy;
    private Date updateDate;
}
