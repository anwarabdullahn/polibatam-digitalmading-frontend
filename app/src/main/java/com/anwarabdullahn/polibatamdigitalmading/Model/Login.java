package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

public class Login {

    @SerializedName("data")
    Mahasiswa data;

    @SerializedName("meta")
    Token meta;

    public Mahasiswa getData() {
        return data;
    }

    public void setData(Mahasiswa data) {
        this.data = data;
    }

    public Token getMeta() {
        return meta;
    }

    public void setMeta(Token meta) {
        this.meta = meta;
    }

}
