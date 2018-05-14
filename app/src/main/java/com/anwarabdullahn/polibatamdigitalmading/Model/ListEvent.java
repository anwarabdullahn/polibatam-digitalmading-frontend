package com.anwarabdullahn.polibatamdigitalmading.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/6/18.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ListEvent {

    @SerializedName("result")
    List<Event> eventList;

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
