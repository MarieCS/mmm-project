package com.example.mcs.mmm_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.EventPosition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurelienanne on 21/02/18.
 */

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private List<EventPosition> eventPositionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference("features");
        eventPositionList = new ArrayList<>();

        Query query = mDatabase.orderByKey();

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot coordinates = dataSnapshot.child("geometry").child("coordinates");
                if(coordinates.getValue() != null){
                    Double lon = (Double) coordinates.child("0").getValue();
                    Double lat = (Double) coordinates.child("1").getValue();

                    Event event = dataSnapshot.child("properties").getValue(Event.class);

                    EventPosition eventPosition = new EventPosition(lat, lon, event);

                    eventPositionList.add(eventPosition);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }
}
