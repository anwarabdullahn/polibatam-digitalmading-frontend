package com.anwarabdullahn.polibatamdigitalmading.Request;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

public class LoginForm {
    String email , password;
    private int platfom;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlatfom() {
        return platfom;
    }

    public void setPlatfom(int platform) {
        this.platfom = platfom;
    }
}