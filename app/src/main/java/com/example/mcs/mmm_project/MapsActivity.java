package com.example.mcs.mmm_project;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mcs.mmm_project.helper.SQLDatabaseHelper;
import com.example.mcs.mmm_project.pojo.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import com.example.mcs.mmm_project.pojo.EventPosition;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ClusterManager.OnClusterItemInfoWindowClickListener<EventPosition> {

    private GoogleMap mMap;
    private ClusterManager<EventPosition> mClusterManager;
    private EventPosition clickedClusterItem;
    @BindView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) public NavigationView navigationView;
    SQLDatabaseHelper sqlDatabaseHelper;

    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        if(getIntent().getExtras() != null && !getIntent().getExtras().isEmpty())
            events = (List<Event>) getIntent().getExtras().get("events");

        ActivityToolBar.createToolBar(this, navigationView, mDrawerLayout);

        sqlDatabaseHelper = new SQLDatabaseHelper(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityToolBar.selectMenuItem(navigationView, 0);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpClusterer();

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mMap.setOnInfoWindowClickListener(mClusterManager);

        QueryBuilder<Event, ?> queryBuilder;
        try {
            queryBuilder = sqlDatabaseHelper.getDao(Event.class).queryBuilder();

            // prepare it so it is ready for later query or iterator calls
            PreparedQuery<Event> preparedQuery = queryBuilder.prepare();

            if(events == null){
                events = sqlDatabaseHelper.getDao(Event.class).query(preparedQuery);
            }

            events.forEach(e -> {
                mClusterManager.addItem(new EventPosition(e.latPos, e.longPos, e));
            });

            mClusterManager.cluster();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setUpClusterer() {
        // Position the map on Paris
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48, 2), 5));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this, mMap);

        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<EventPosition>() {
                    @Override
                    public boolean onClusterItemClick(EventPosition item) {
                        clickedClusterItem = item;
                        return false;
                    }
                });

        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new AdapterEventPosition());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    }

    @Override
    public void onClusterItemInfoWindowClick(EventPosition eventPosition) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("event", eventPosition.getEvent());
        startActivity(i);
    }

    private class AdapterEventPosition implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        AdapterEventPosition() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.info_window, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {

            TextView titre = myContentsView.findViewById(R.id.txtTitle);
            TextView adresse = myContentsView.findViewById(R.id.txtSnippet);

            titre.setText(clickedClusterItem.getEvent().titre_fr);
            adresse.setText(clickedClusterItem.getEvent().adresse);

            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
