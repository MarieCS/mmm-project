package com.example.mcs.mmm_project.pojo;

import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by aurelienanne on 25/01/18.
 */

public class EventPosition implements ClusterItem {

    private final LatLng mPosition;
    private Event event;

    public EventPosition(double lat, double lng, Event event){
        mPosition = new LatLng(lat, lng);
        this.event = event;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public Event getEvent() {
        return event;
    }
}
