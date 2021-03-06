package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mcs.mmm_project.fragment.EventListFragment;
import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.Parcours;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteActivity extends FragmentActivity {
    private EventListFragment listfragment;

    @BindView(R.id.btnShowOnMap) public Button btnShowOnMap;
    @BindView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        ActivityToolBar.createToolBar(this, navigationView, mDrawerLayout);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            btnShowOnMap.setText("Chargement...");
            btnShowOnMap.setEnabled(false);
            btnShowOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowOnMapClick();
                }
            });
        }
    }

    private void onShowOnMapClick() {
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("events", Parcours.getInstance().getParcours());
        startActivity(i);
    }

    @Override
    protected void onResume() { // On recharge les événements du parcours
        super.onResume();
        ActivityToolBar.selectMenuItem(navigationView, 2);
        listfragment = new EventListFragment();
        listfragment.setArguments(getIntent().getExtras());
        listfragment.setRouteActivityObserver(this);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, listfragment);
        ft.commit();
    }

    public void onRecyclerEventListLoaded(EventListFragment eventListFragment) {
        ArrayList<Event> list = Parcours.getInstance().getParcours();
        if (list.size() == 0) {
            btnShowOnMap.setText("Aucun événement n'a été ajouté au parcours");
            btnShowOnMap.setEnabled(false);
        }
        else {
            btnShowOnMap.setText("Afficher sur la carte");
            btnShowOnMap.setEnabled(true);
        }
        eventListFragment.update(list);
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
}
