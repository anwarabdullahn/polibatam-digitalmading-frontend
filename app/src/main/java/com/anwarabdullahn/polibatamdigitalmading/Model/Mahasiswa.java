package com.anwarabdullahn.polibatamdigitalmading.Model;

import org.parceler.Parcel;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Mahasiswa {

    String name , nim, email, avatar, registered;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getRegistered() {
        return registered;
    }
}
