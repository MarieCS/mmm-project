package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        System.out.println("Oh hi Mark");
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        switch (menuItem.getItemId()) {
                            case R.id.menu_map:
                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.menu_eventList:
                                return true;
                            case R.id.menu_parcours:
                                return true;
                            default:
                                return true;
                        }
                    }
                }
        );

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