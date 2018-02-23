package com.example.mcs.mmm_project;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.mcs.mmm_project.fragment.EventListFragment;
import com.example.mcs.mmm_project.fragment.EventSearch;
import com.example.mcs.mmm_project.fragment.OnSearchEnd;
import com.example.mcs.mmm_project.pojo.Event;

import java.util.List;

/**
 * Created by mcs on 22/02/18.
 */

public class SearchActivity extends Activity implements OnSearchEnd {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_search);
        //((ArticleListFragment)(getFragmentManager().findFragmentById(R.id.list))).setListener(this);
        getFragmentManager().findFragmentById(R.id.fragment_container);

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
