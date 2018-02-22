package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.fragment.EventSearch;
import com.example.mcs.mmm_project.pojo.Event;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    SQLDatabaseHelper sqlDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        EventListFragment fragment = new EventListFragment();
        Fragment fragment = new EventSearch();
        ft.add(R.id.fragment, fragment);
        ft.commit();
        sqlDatabaseHelper = new SQLDatabaseHelper(this);
        // Faire une requête pour init la bd au premier lancement de l'appli
        try {
            sqlDatabaseHelper.getDao(Event.class).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}