package com.example.mcs.mmm_project.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mcs.mmm_project.pojo.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class SQLDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context context;
    private Dao<Event, String> eventDAO;
    private static final String DB_NAME = "event_database";
    private static final int VERSION = 15;
    private DatabaseReference mDatabase;

    public SQLDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference("features");
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.d("TAG", "Creation de la table");
            TableUtils.createTable(connectionSource, Event.class);
            Log.d("TAG", "Initialize");
            initialize(context);
            Log.d("TAG", "Ajout des données fini");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Event.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Event, String> getEventPojoDao() {
        if (eventDAO == null) {
            try {
                eventDAO = getDao(Event.class);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return eventDAO;
    }

    private void initialize(Context context){
            Query query = mDatabase.orderByKey();
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    DataSnapshot coordinates = dataSnapshot.child("geometry").child("coordinates");
                    if(coordinates.getValue() != null){
                        Double lon = (Double) coordinates.child("0").getValue();
                        Double lat = (Double) coordinates.child("1").getValue();
                        Event event = dataSnapshot.child("properties").getValue(Event.class);
                        event.longPos = lon;
                        event.latPos = lat;
                        event.firebaseIndex = dataSnapshot.getKey();
                        try {
                            getEventPojoDao().create(event);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override public void onCancelled(DatabaseError databaseError) {}
            });
    }
}
