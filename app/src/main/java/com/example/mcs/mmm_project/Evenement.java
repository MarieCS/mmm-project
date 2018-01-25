package com.example.mcs.mmm_project;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by aurelienanne on 25/01/18.
 */

public class Evenement implements ClusterItem {

    private final LatLng mPosition;

    public Evenement(double lat, double lng){
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
