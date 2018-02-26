package com.example.mcs.mmm_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.EventPosition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurelienanne on 21/02/18.
 */

public class SplashActivity extends AppCompatActivity {

    private List<EventPosition> eventPositionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventPositionList = new ArrayList<>();

        SQLDatabaseHelper sqlDatabaseHelper = new SQLDatabaseHelper(getApplicationContext());
        try {
            sqlDatabaseHelper.getDao(Event.class).countOf();
        } catch (SQLException e) {
            Log.e("Loading exception", e.getMessage());
        }

        Log.i("Load", "Succes");

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }
}
