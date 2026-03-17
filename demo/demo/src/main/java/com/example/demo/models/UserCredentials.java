package com.example.demo.models;

public class UserCredentials {
    private String username;
    private String password;

    // CONSTRUCTOR VACÍO (Obligatorio para Spring)
    public UserCredentials() {}

    // GETTERS Y SETTERS (Sin esto, el login siempre falla)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
