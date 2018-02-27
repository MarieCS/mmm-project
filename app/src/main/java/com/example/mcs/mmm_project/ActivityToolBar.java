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

        System.out.println(activity.getClass().getSimpleName());
        String className = activity.getClass().getSimpleName();
        if(className.equals("MapsActivity")) navigationView.getMenu().getItem(0).setChecked(true);
        if(className.equals("SearchActivity")) navigationView.getMenu().getItem(1).setChecked(true);
        if(className.equals("RouteActivity")) navigationView.getMenu().getItem(2).setChecked(true);

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
                                activity.startActivity(intent);
                                activity.finish();
                                return true;
                            case R.id.menu_eventList:
                                Intent intentSearch = new Intent(activity.getApplicationContext(), SearchActivity.class);
                                activity.startActivity(intentSearch);
                                activity.finish();
                                return true;
                            case R.id.menu_parcours:
                                Intent intentParcours = new Intent(activity.getApplicationContext(), RouteActivity.class);
                                activity.startActivity(intentParcours);
                                activity.finish();
                                return true;
                            default:
                                return true;
                        }
                    }
                }
        );
    }

    private ActivityToolBar(){}
}
