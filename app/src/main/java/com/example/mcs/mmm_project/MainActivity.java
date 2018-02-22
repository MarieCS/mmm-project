package com.example.mcs.mmm_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mcs.mmm_project.fragment.EventListFragment;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EventListFragment fragment = new EventListFragment();

        ft.add(R.id.fragment, fragment);
        ft.commit();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}