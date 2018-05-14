package com.anwarabdullahn.polibatamdigitalmading.Model;

import org.parceler.Parcel;

/**
 * Created by anwarabdullahn on 2/6/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Event {

    String title, date, image, description;
    Author author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
