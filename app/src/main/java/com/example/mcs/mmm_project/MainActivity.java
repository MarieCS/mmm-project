package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.fragment.EventListFragment;
import com.example.mcs.mmm_project.pojo.Event;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    SQLDatabaseHelper sqlDatabaseHelper;
    @BindView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityToolBar.createToolBar(this, navigationView, mDrawerLayout);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EventListFragment fragment = new EventListFragment();
        //Fragment fragment = new EventSearch();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static Context getAppContext() {
        return MainActivity.context;
    }
}