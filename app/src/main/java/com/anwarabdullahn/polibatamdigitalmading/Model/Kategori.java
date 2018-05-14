package com.anwarabdullahn.polibatamdigitalmading.Model;

import org.parceler.Parcel;

/**
 * Created by anwarabdullahn on 2/1/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Kategori {
    String id, name, image, amount;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
