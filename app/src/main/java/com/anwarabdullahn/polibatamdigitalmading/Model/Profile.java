package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anwarabdullahn on 1/26/18.
 */

public class Profile {

    @SerializedName("data")
    Mahasiswa data;

    public void setData(Mahasiswa data) {
        this.data = data;
    }

    public Mahasiswa getData() {
        return data;
    }
}
