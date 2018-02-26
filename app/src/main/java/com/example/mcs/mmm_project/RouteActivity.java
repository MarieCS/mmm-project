package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.mcs.mmm_project.fragment.EventFragment;
import com.example.mcs.mmm_project.fragment.EventListFragment;
import com.example.mcs.mmm_project.pojo.Parcours;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteActivity extends FragmentActivity {
    private EventListFragment listfragment;

    @BindView(R.id.showOnMap) public Button showOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            listfragment = new EventListFragment();
            listfragment.setArguments(getIntent().getExtras());
            listfragment.setRouteActivityObserver(this);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_container, listfragment);
            ft.commit();

            showOnMap.setOnClickListener(new View.OnClickListener() {
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

    public void onRecyclerEventListLoaded() {
        listfragment.update(Parcours.getInstance().getParcours());
    }
}
