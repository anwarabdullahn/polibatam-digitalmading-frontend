package com.anwarabdullahn.polibatamdigitalmading.Model;

import org.parceler.Parcel;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Token {
    String token ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
