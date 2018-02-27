package com.example.mcs.mmm_project;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by mcs on 26/02/18.
 */

public class ActivityToolBar {

    public static void createToolBar(AppCompatActivity activity, NavigationView navigationView, DrawerLayout mDrawerLayout){
        Toolbar myToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(myToolbar);
        ActionBar actionbar = activity.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        createNavigation(activity, navigationView, mDrawerLayout);
    }



    public static void createToolBar(FragmentActivity activity, NavigationView navigationView, DrawerLayout mDrawerLayout){
        android.widget.Toolbar myToolbar = (android.widget.Toolbar) activity.findViewById(R.id.toolbar);
        activity.setActionBar(myToolbar);
        android.app.ActionBar actionbar = activity.getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        createNavigation(activity, navigationView, mDrawerLayout);
    }

    private static void createNavigation(Activity activity, NavigationView navigationView, DrawerLayout mDrawerLayout) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        switch (menuItem.getItemId()) {
                            case R.id.menu_map:
                                Intent intent = new Intent(activity.getApplicationContext(), MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                return true;
                            case R.id.menu_eventList:
                                Intent intentSearch = new Intent(activity.getApplicationContext(), SearchActivity.class);
                                intentSearch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentSearch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intentSearch);
                                return true;
                            case R.id.menu_parcours:
                                Intent intentParcours = new Intent(activity.getApplicationContext(), RouteActivity.class);
                                intentParcours.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentParcours.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intentParcours);
                                return true;
                            default:
                                return true;
                        }
                    }
                }
        );
    }

    private ActivityToolBar(){}

    public static void selectMenuItem(NavigationView navigationView, int i) {
       navigationView.getMenu().getItem(i).setChecked(true);
    }
}
