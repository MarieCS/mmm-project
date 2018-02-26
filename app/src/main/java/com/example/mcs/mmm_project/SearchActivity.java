package com.example.mcs.mmm_project;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mcs.mmm_project.fragment.EventListFragment;
import com.example.mcs.mmm_project.fragment.EventSearch;
import com.example.mcs.mmm_project.fragment.OnSearchEnd;
import com.example.mcs.mmm_project.pojo.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcs on 22/02/18.
 */

public class SearchActivity extends AppCompatActivity implements OnSearchEnd {

    @BindView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_search);
        //((ArticleListFragment)(getFragmentManager().findFragmentById(R.id.list))).setListener(this);
        getFragmentManager().findFragmentById(R.id.fragment_container);
        ButterKnife.bind(this);

        if (findViewById(R.id.layout_large) != null) {
            System.out.println("create large");
            ((EventSearch)(getFragmentManager().findFragmentById(R.id.search))).setListener(this);
        }else{
            System.out.println("create small");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            EventSearch fragment = new EventSearch();

            ft.add(R.id.fragment_container, fragment);
            ft.commit();

            fragment.setListener(this);
        }

        ActivityToolBar.createToolBar(this, navigationView, mDrawerLayout);
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

    @Override
    public void onSearchEnd(List<Event> events) {
        if (findViewById(R.id.layout_large) != null) {
            ((EventListFragment)(getFragmentManager().findFragmentById(R.id.list_viewer))).update(events);
        }else{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            EventListFragment fragment = new EventListFragment();


            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
            getFragmentManager().executePendingTransactions();

            fragment.update(events);
        }
    }
}
