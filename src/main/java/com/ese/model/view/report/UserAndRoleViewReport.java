package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class UserAndRoleViewReport {

    private String department;
    private String faction;
    private String title;
    private String name;
    private String loginName;
    private String position;
    private String createDate;
    private String role;
//    private String userPrint;
//    private Date printDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("department", department)
                .append("faction", faction)
                .append("title", title)
                .append("name", name)
                .append("loginName", loginName)
                .append("position", position)
                .append("createDate", createDate)
                .append("role", role)
//                .append("userPrint", userPrint)
//                .append("printDate", printDate)
                .toString();
    }
}
