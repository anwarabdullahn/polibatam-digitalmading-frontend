package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/1/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ListKategori {

    @SerializedName("result")
    List<Kategori> kategoriList;

    public List<Kategori> getKategoriList() {
        return kategoriList;
    }

    public void setKategoriList(List<Kategori> kategoriList) {
        this.kategoriList = kategoriList;
    }
}
