package com.ese.model.view.report;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class RoleAccessViewReport {

    private String roleName;
    private String objCode;
    private String objName;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("roleName", roleName)
                .append("objCode", objCode)
                .append("objName", objName)
                .toString();
    }
}
