package com.ese.model.view;

import com.ese.model.db.StaffModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class View {
    private Integer createBy;
    private Date createDate;
    private Integer updateBy;
    private Date updateDate;
}
