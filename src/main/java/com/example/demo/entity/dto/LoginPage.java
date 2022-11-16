package com.example.demo.entity.dto;

public class LoginPage {
    String loginUserName;
    String loginPassword;
    String registerUserName;
    String registerPassword;
    String fullName;
    String phoneNumber;
    String email;
    
    
    public LoginPage() {}


    public LoginPage(String loginUserName, String loginPassword, String registerUserName, String registerPassword,
            String fullName, String phoneNumber, String email) {
        super();
        this.loginUserName = loginUserName;
        this.loginPassword = loginPassword;
        this.registerUserName = registerUserName;
        this.registerPassword = registerPassword;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    
    
    public String getLoginUserName() {
        return loginUserName;
    }


    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }


    public String getLoginPassword() {
        return loginPassword;
    }


    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }


    public String getRegisterUserName() {
        return registerUserName;
    }


    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }


    public String getRegisterPassword() {
        return registerPassword;
    }


    public void setRegisterPassword(String registerPassword) {
        this.registerPassword = registerPassword;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
