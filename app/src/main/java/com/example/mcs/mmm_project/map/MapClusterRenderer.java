package com.example.mcs.mmm_project.map;

import android.content.Context;

import com.example.mcs.mmm_project.pojo.EventPosition;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by aurelienanne on 21/02/18.
 */

public class MapClusterRenderer extends DefaultClusterRenderer<EventPosition> {

    GoogleMap map;

    public MapClusterRenderer(Context context, GoogleMap map, ClusterManager<EventPosition> clusterManager) {
        super(context, map, clusterManager);
        this.map = map;
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<EventPosition> cluster) {
        System.out.println(map.getCameraPosition().zoom);
        //if (map.getCameraPosition().zoom > 9) return false;
        return true;
    }
}
