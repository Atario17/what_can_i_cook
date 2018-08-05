package com.kondrat.wcic.controllers;

public class CreateUserRequest {

    String login;
    String password;

    public CreateUserRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CreateUserRequest() {
    }

    public static CreateUserRequest createUser(String login, String password) {
        return new CreateUserRequest(login, password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
