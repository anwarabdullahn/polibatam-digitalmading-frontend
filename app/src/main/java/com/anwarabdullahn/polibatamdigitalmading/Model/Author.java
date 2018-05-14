package com.anwarabdullahn.polibatamdigitalmading.Model;

import org.parceler.Parcel;

/**
 * Created by anwarabdullahn on 2/2/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Author {
    String name, avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
