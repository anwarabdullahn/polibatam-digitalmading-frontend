package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by anwarabdullahn on 1/25/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ListBanner {

    @SerializedName("result")
    ArrayList<Banner> data;

    public ArrayList<Banner> getData() {
        return data;
    }

    public void setData(ArrayList<Banner> data) {
        this.data = data;
    }


}

