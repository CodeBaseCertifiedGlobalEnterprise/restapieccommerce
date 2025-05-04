package com.sampleProject.sampleProjectRestApi.Registeration;

import java.time.Instant;
import java.time.LocalDate;


public class Registration {
    private String emailAddress;
    private String userName;
    private String password;
    private String confirmPassword;
    private final LocalDate createdAt;
    public Registration(String emailAddress, String userName, String password, String confirmPassword) {
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.createdAt = LocalDate.now();
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

    public LocalDate getCreatedAt() { return createdAt; }
}
