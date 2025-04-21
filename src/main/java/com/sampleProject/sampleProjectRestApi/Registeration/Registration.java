package com.sampleProject.sampleProjectRestApi.Registeration;

import java.util.Date;

public class Registration {
    private String emailAddress;
    private String userName;
    private String password;
    private String confirmPassword;
    private Date createdAt;

    public Registration(String emailAddress, String userName, String password, String confirmPassword, Date createdAt) {
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.createdAt = createdAt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
