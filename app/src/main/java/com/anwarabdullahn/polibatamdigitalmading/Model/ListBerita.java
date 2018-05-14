package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/2/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ListBerita {

    @SerializedName("result")
    List<Berita> beritaList;

    public List<Berita> getBeritaList() {
        return beritaList;
    }

    public void setBeritaList(List<Berita> beritaList) {
        this.beritaList = beritaList;
    }
}
