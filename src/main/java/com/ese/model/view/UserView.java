package com.ese.model.view;

import com.ese.model.db.FactionModel;
import com.ese.model.db.MSDepartmentModel;
import com.ese.model.db.MSTitleModel;
import com.ese.model.db.StaffModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
public class UserView  extends View{

    private int id;
    private String password;
    private String username;
    private Integer version;
    private String role;
    private MSDepartmentModel msDepartmentModel;
    private FactionModel factionModel;
    private String name;
    private int isValid;
    private String position;
    private MSTitleModel msTitleModel;

    public UserView() {
        msDepartmentModel = new MSDepartmentModel();
        factionModel = new FactionModel();
        msTitleModel = new MSTitleModel();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("password", password)
                .append("username", username)
                .append("version", version)
                .append("role", role)
                .append("msDepartmentModel", msDepartmentModel)
                .append("factionModel", factionModel)
                .append("name", name)
                .append("isValid", isValid)
                .append("position", position)
                .append("msTitleModel", msTitleModel)
                .toString();
    }
}
