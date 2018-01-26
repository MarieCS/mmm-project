package com.example.mcs.mmm_project;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventActivity extends FragmentActivity implements EventFragment.OnFragmentInteractionListener {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            EventFragment firstFragment = new EventFragment();
            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("FragmentInteraction : " + uri);
    }
}
