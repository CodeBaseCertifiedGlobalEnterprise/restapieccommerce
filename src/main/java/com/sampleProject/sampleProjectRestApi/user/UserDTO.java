package com.sampleProject.sampleProjectRestApi.user;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int id;
    private String userName;
    private String email;
    private String roles;


    public UserDTO(int id, String userName, String email, String roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
