package com.anwarabdullahn.polibatamdigitalmading.API;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

public class APIError {
    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public APIError(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
