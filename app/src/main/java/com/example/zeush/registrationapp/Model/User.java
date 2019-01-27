package com.example.zeush.registrationapp.Model;

/**
 * Created by zeush on 1/27/2019.
 */

public class User {


    private String name;
    private String email;
    private String phone;
    private String api_token;
    private String password;


    public User()
    {

    }
    public User(String name, String email,String phone, String api_token, String password) {
        this.name = name;
        this.email = email;
        this.phone=phone;
        this.api_token = api_token;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
}
