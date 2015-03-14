package com.ese.model.view;

import com.ese.model.db.StaffModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Setter
@Getter
public class SystemRoleView extends View{

    private int id;
    private String code;
    private String name;
    private String description;
    private Integer version;
    private int isValid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("description", description)
                .append("version", version)
                .append("isValid", isValid)
                .toString();
    }
}
