package com.ese.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Getter
@Setter
public class UserDetail implements Serializable {
    private String userName;
    private String password;
    private String role;
    private Long id;
    private String firstName;
    private String lastName;
    private boolean isRequestFlag;

    public UserDetail(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userName", userName)
                .append("password", password)
                .append("role", role)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("isRequestFlag", isRequestFlag)
                .toString();
    }
}
