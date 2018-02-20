package com.example.mcs.mmm_project;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.EventPosition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by aurelienanne on 19/02/18.
 */

public class GetData extends AsyncTask<Void, EventPosition, Void> {

    private DatabaseReference mDatabase;
    private ClusterManager<EventPosition> mClusterManager;

    public GetData(DatabaseReference mDatabase, ClusterManager<EventPosition> mClusterManager) {
        this.mDatabase = mDatabase;
        this.mClusterManager = mClusterManager;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(EventPosition... values){
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
    }

    @Override
    protected Void doInBackground(Void... arg0) {

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

                    mClusterManager.addItem(eventPosition);
                    mClusterManager.cluster();

                    publishProgress(eventPosition);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
    }
}
