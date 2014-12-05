package com.ese.model.view;

import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class UserView {

    private String password;
    private String username;
    private Integer version;
    private String role;
    private MSDepartmentModel msDepartmentModel;
    private FactionModel factionModel;
    private String name;
    private int isValid;
    private String position;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("password", password)
                .append("username", username)
                .append("version", version)
                .append("role", role)
                .append("msDepartmentModel", msDepartmentModel)
                .append("factionModel", factionModel)
                .append("name", name)
                .append("isValid", isValid)
                .append("position", position)
                .toString();
    }
}
